package cz.skylights.spitt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.collision.CollisionRectangle;
import cz.skylights.spitt.collision.CollisionType;
import cz.skylights.spitt.collision.ICollision;


public class WeaponFire extends GameObject implements ICollision {
	private float  _speed;
	public boolean shotFired=false;
	public float _ratio;	
	
	private CollisionRectangle _collision;
	
	private float vertices[] = { 
        0.0f, 0.0f, 0.0f, 
        1.0f, 0.0f, 0.0f, 
        1.0f, 1.0f, 0.0f, 
        0.0f, 1.0f, 0.0f, 
    }; 

    private float texture[] = { 
        0.0f, 0.0f, 
        1.0f, 0.0f, 
        1.0f, 1.0f, 
        0.0f, 1.0f, 
    }; 

    private byte indices[] = { 
        0,1,2, 
        0,2,3, 
    }; 
	
	public WeaponFire(float ratio, float sx, float sy)
	{
		super(sx,sy);
		this.X = sx;
		this.Y = sy;
		_collision = new CollisionRectangle(0.1f,0.1f, 0.9f, 0.9f);
		
		this.setSizeRatio(ratio);			
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
	
	// kazda kulka by mela mit svuj pomer velikosti ... individualne k bitmape
	private void setSizeRatio(float ratio)
	{
		_ratio = ratio;
		
		for(int i = 0; i < vertices.length;i++)
			vertices[i]*=ratio;
		
		Width = 1.0f*ratio;
		Height = 1.0f*ratio;					
	}
	
	public void draw(GL10 gl, int[] sprite)
	{
        gl.glMatrixMode(GL10.GL_MODELVIEW); 
        gl.glLoadIdentity(); 
        gl.glPushMatrix(); 
        //gl.glScalef(this.scaleX, this.scaleY, 0f); 
        gl.glTranslatef(X, Y, 0f); 

        gl.glMatrixMode(GL10.GL_TEXTURE); 
        gl.glLoadIdentity(); 
        gl.glTranslatef(0.0f,0.0f, 0.0f); 
		
        gl.glBindTexture(GL10.GL_TEXTURE_2D, sprite[1]); 
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
	
	public void setSpeed(float speed)
	{
		_speed = speed;
	}

	public boolean CheckCollision(GameObject obj2) {
		return false;
	}

	public CollisionType GetType() {
		return CollisionType.collisionCircle;
	}	
	
}

