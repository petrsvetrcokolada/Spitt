package cz.skylights.spitt.particle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.SpatterEngine;

public class Particle extends ParticleObject {
	
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
	
	public Particle(float sx, float sy, float spX, float spY)
	{
		super(sx,sy,spX,spY);
		
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
	
	// kazda kulka by mela mit svuj pomer velikosti ... individualne k bitmape
	private void setSizeRatio(float ratio)
	{
		_ratio = ratio;
		
		for(int i = 0; i < vertices.length;i++)
			vertices[i]*=ratio;
		
		Width = 1.0f*ratio;
		Height = 1.0f*ratio;
	}
	
	public void setTexture(int tx)
	{
	  _textureID =tx;
	}
	
	public void setScale(float sx, float sy)
	{
		scaleX = sx;
		scaleY = sy;
	}
	
	public void move()
	{
		if (SpatterEngine.GameTime >= StartTime && SpatterEngine.GameTime <= StartTime+Live)
		{
			Y+=speedY;
			X+=speedX;
	    }
		
		if (SpatterEngine.GameTime > StartTime+Live)
		{
			X=-1.0f;
			Y=-1.0f;
		}	
	}
	
	
	public void draw(GL10 gl)
	{
		gl.glEnable(GL10.GL_TEXTURE_2D);	
        gl.glMatrixMode(GL10.GL_MODELVIEW); 
        gl.glLoadIdentity(); 
        gl.glPushMatrix(); 
        gl.glScalef(scaleX, scaleY, 0f);
        //gl.glScalef(0.25f, 0.25f, 0f);
        gl.glTranslatef(X/scaleX, Y/scaleY*SpatterEngine.screen_ratio, 0f); 

        gl.glMatrixMode(GL10.GL_TEXTURE); 
        gl.glLoadIdentity(); 
        gl.glTranslatef(0.0f,0.0f, 0.0f); 
		
        gl.glBindTexture(GL10.GL_TEXTURE_2D, _textureID); 
        gl.glFrontFace(GL10.GL_CCW); 
        gl.glEnable(GL10.GL_CULL_FACE); 
        gl.glCullFace(GL10.GL_BACK);
        //gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
        //gl.glColor4f(R, G, B, 1.0f);
        //
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
