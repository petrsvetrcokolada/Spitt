package cz.skylights.spitt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.geometry.Vertex2D;
import cz.skylights.spitt.collision.CollisionArray;
import cz.skylights.spitt.collision.CollisionType;
import cz.skylights.spitt.collision.ICollision;


public class WeaponFire extends GameObject {
	private float  _speed;
	public boolean shotFired=false;
	public float _ratio;		
	
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
	
	public WeaponFire(float ratio, float sx, float sy)
	{
		super(sx,sy);
		this.X = sx;
		this.Y = sy;
		
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
	
	public void setTexture(BitmapTexture texture)
	{
		_texture = texture;
	}
	
	public void draw(GL10 gl)
	{
        gl.glMatrixMode(GL10.GL_MODELVIEW); 
        gl.glLoadIdentity(); 
        gl.glPushMatrix(); 
        //gl.glScalef(this.scaleX, this.scaleY, 0f); 
        //gl.glTranslatef(X, Y*SpatterEngine.screen_ratio, 0f); 
        gl.glTranslatef(X, Y, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE); 
        gl.glLoadIdentity(); 
        gl.glTranslatef(0.0f,0.0f, 0.0f); 
		
        gl.glBindTexture(GL10.GL_TEXTURE_2D, _texture.textureID); 
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

	public boolean CheckCollision(Enemy obj2) {
	   float X1 = X;
	   float Y1 = Y;
	   float X1W = X+Width;
	   float Y1H = Y+(Height/SpatterEngine.screen_ratio);

	   float X2 = obj2.X;
	   float Y2 = obj2.Y;
	   float X2W = obj2.X+obj2.Width;
	   float Y2H = obj2.Y+(obj2.Height/SpatterEngine.screen_ratio);
	   
	   boolean check = false;
	   
	   if (X1W < X2 || X2W < X1 || Y1H < Y2 || Y2H < Y1)
		    check = false;
		else
		    check = true;

	   if (check == true)
	   {
		   // vrat body, ktere jsou s kolizni oblasti
		   CollisionArray l1 = _texture.getCollision(0,X1, Y1, Width, Height/SpatterEngine.screen_ratio, X2, Y2, X2W, Y2H);
		   CollisionArray l2 = obj2.getTexture().getCollision(obj2.getFrame(),X2, Y2, obj2.Width, obj2.Height/SpatterEngine.screen_ratio, X1, Y1, X1W, Y1H);
		   ///
		   float psize = (l1.Unit+l2.Unit)/2;
		   ///
		   for(int i = 0; i < l1.size();i++)
		   {
			 Vertex2D v1 = l1.get(i);
		     for(int j = 0; j < l2.size();j++)
		     {
		    	 Vertex2D v2 = l2.get(j);
		    	 float rozdilX = Math.abs(v1.X-v2.X);		    
		    	 float rozdilY = Math.abs(v1.Y-v2.Y);
		    	 
		    	 if (rozdilX < psize && rozdilY < psize)
		    	 {
		    		 return true;
		    	 }
		     }
		   }		   		  	
	   }
	    
		return false;
	}

}

