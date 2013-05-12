package cz.skylights.spitt.shape;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.SpatterEngine;


// Zobrazi sprit s animaci
public class SpriteAnimation  extends GameObject
{
	float _ratio;
	private Context _context;
	protected int _textureID=-1;	
	private int _frame = 6;
	private int _count=0;
	private int _frameWidth  = 0;
	private int _frameHeight = 0;
	private int _texturewidth = 0;
	private boolean _repeat = false;
	
	private float vertices[] = { 
        0.0f, 0.0f, 0.0f, 
        1.0f, 0.0f, 0.0f, 
        1.0f, 1.0f, 0.0f, 
        0.0f, 1.0f, 0.0f, 
    }; 

    private float texture[] = { 
        0.0f, 1.0f, 
        1.0f, 1.0f, 
        1.0f, 0.0f, 
        0.0f, 0.0f, 
    }; 

    private byte indices[] = { 
        0,1,2, 
        0,2,3, 
    };
    
    private float _scaleX = 0.25f;
    private float _scaleY = 0.25f;
	
	public SpriteAnimation(boolean repeat)
	{
		this.X = 0;
		this.Y = 0;		
		_repeat = repeat;
 		
	}
	
	private void initBuffers()
	{
		// 
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4); 
        byteBuf.order(ByteOrder.nativeOrder()); 
        vertexBuffer = byteBuf.asFloatBuffer(); 
        vertexBuffer.put(vertices); 
        vertexBuffer.position(0); 

        byteBuf = ByteBuffer.allocateDirect(texture.length * 4); 
        byteBuf.order(ByteOrder.nativeOrder()); 
        textureBuffer = byteBuf.asFloatBuffer(); 
        textureBuffer.put(texture); 
        textureBuffer.position(0); 

        indexBuffer = ByteBuffer.allocateDirect(indices.length); 
        indexBuffer.put(indices); 
        indexBuffer.position(0);	
	}
	
	public void setTexture(int tx)
	{
	  _textureID =tx;
	}
	
	// kazda kulka by mela mit svuj pomer velikosti ... individualne k bitmape
	public void setSizeRatio(float ratio)
	{
		_ratio = ratio;
		
		for(int i = 0; i < vertices.length;i++)
			vertices[i]*=ratio;
		/*
		for(int i = 0; i < texture.length;i++)
		{
			if ((i % 2)==0)
				texture[i]*=ratio;
			else
				texture[i]*=ratio;
		}*/
		
		Width = 1.0f*ratio;
		Height = 1.0f*ratio;
	}
	/*
	public void setScale(float sx, float sy)
	{
		_scaleX = sx;
		_scaleY = sy;
		
		for(int i = 0; i < texture.length;i++)
		{
			if ((i % 2)==0)
				texture[i]*=sy;
			else
				texture[i]*=sx;
		}
	}*/
	
	// nastav parametry
	public void setFramesParameter(int frames, int tw,int fw, int fh)
	{
	  _frameWidth = fw;
	  _frameHeight = fh;
	  _count = frames;
	  
	  _texturewidth = tw;
	  float factor =1/((float)_texturewidth/(float)_frameWidth);
	  for(int i = 0; i < texture.length;i++)
			texture[i]*=factor;	  
	  // 
	  initBuffers();
	}
	
	public void setFrame(int frame)
	{
		_frame = frame;
	}
	
	public int Count()
	{
		return _count;
	}
	
	public boolean isAnimate()
	{
		if (_frame < _count)
			return true;
		
		return false;
	}
	
	long _nextTime = SpatterEngine.GameTime+40;
	public void animation()
	{
		
		if (_nextTime > SpatterEngine.GameTime)
		{
			return;
		}
		
		if (_frame < _count)
		  _frame++;
		
		if (_repeat == true && _frame >= _count)
			_frame = 0;
			
		_nextTime = SpatterEngine.GameTime+40;
	}
	
	
	public void draw(GL10 gl)
	{
		gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glMatrixMode(GL10.GL_MODELVIEW); 
        gl.glLoadIdentity(); 
        gl.glPushMatrix(); 
        //gl.glScalef(_scaleX, _scaleY, 0f);      
        gl.glTranslatef(X, Y, 0f); 

        gl.glMatrixMode(GL10.GL_TEXTURE); 
        gl.glLoadIdentity();
        
        float factor = (float)_texturewidth/(float)_frameWidth;
        float frame_width = (1/(float)factor); 
        int tx = _frame % (int)factor;
        int ty = _frame / (int)factor;
        gl.glTranslatef(frame_width*tx, frame_width*ty,0.0f); 
		
        gl.glBindTexture(GL10.GL_TEXTURE_2D, _textureID); 
        gl.glFrontFace(GL10.GL_CCW); 
        gl.glEnable(GL10.GL_CULL_FACE); 
        gl.glCullFace(GL10.GL_BACK);
        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); 
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer); 
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer); 

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, 
                          GL10.GL_UNSIGNED_BYTE, indexBuffer); 

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); 
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 
        gl.glDisable(GL10.GL_CULL_FACE);
        
        gl.glPopMatrix(); 
        gl.glLoadIdentity();
	}		
}
