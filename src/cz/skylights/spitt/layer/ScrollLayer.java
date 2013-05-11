package cz.skylights.spitt.layer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.TextureManager;
import cz.skylights.spitt.shape.Sprite;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

// scrolovaci layer	
public class ScrollLayer {
	private FloatBuffer vertexBuffer;  
    private FloatBuffer textureBuffer;  
    private ByteBuffer indexBuffer;  // 
 
    private float vertices[] = { 
        0.0f, 0.0f, 0.0f, 
        1.0f, 0.0f, 0.0f, 
        1.0f, 1.0f, 0.0f, 
        0.0f, 1.0f, 0.0f, 
    }; // vrcholy pozadi
    private float texture[] = { 
        0.0f, 0.0f, 
        1.0f, 0f, 
        1, 1.0f, 
        0f, 1f, 
    }; // namapovani pozic z obrazku
    private byte indices[] = { 
        0, 1, 2, 
        0, 2, 3, 
    }; // stena
    
    float _speed=0;
    float _offset=0;
    int _index = 0;
		   
    TextureManager _textures;
    ArrayList<Sprite> _layer=new ArrayList<Sprite>();
    ArrayList<ScrollTile> _tiles = new ArrayList<ScrollTile>();

    public ScrollLayer(TextureManager textures, float speed) 
    { 
    	_textures = textures;
    	_speed = speed;
        //
    	_textures.AddTexture(SpatterEngine.tile1);
    	_textures.AddTexture(SpatterEngine.tile2);
    	_textures.AddTexture(SpatterEngine.tile3);

    	/// alokace bufferu
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
    
    public void buildLayer()
    {

    	ScrollTile t = new ScrollTile(_textures.GetTexture(SpatterEngine.tile1),3);
    	_tiles.add(t);
    	t = new ScrollTile(_textures.GetTexture(SpatterEngine.tile2),3);
    	_tiles.add(t);
    	t = new ScrollTile(_textures.GetTexture(SpatterEngine.tile3),3);
    	_tiles.add(t);    	
    	
    }
    
    public void scrollBackground(GL10 gl)
	{
		if (_offset == Float.MAX_VALUE)
			_offset=0;
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		//gl.glScalef(1f, SpatterEngine.screen_ratio, 1f);
		gl.glTranslatef(0f, _offset, 0f);
		
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, 0.0f);
		
		draw(gl);
		gl.glPopMatrix();
		_offset+=_speed;
		gl.glLoadIdentity();	
	}

    public void draw(GL10 gl) 
    { 
    	ScrollTile t = _tiles.get(_index);
    	int txID = t.Texture();
    	
        gl.glBindTexture(GL10.GL_TEXTURE_2D, txID); 

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
    }     
}
