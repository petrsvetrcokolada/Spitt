package cz.skylights.spitt.particle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.SpatterEngine;

public class ParticleShape extends ParticleObject 
{
	  private FloatBuffer vertexBuffer;  // Buffer for vertex-array
	   private FloatBuffer colorBuffer;   // Buffer for color-array (NEW)
	   private ByteBuffer indexBuffer;    // Buffer for index-array
	   
	   public float R=1.0f;
	   public float G=1.0f;
	   public float B=0.0f;
	   public float A=1.0f;
	   
	   protected float[] vertices = {  // Vertices of the triangle
			   0.0f, 0.0f, 0.0f, 
			   1.0f, 0.0f, 0.0f, 
			   1.0f, 1.0f, 0.0f, 
			   0.0f, 1.0f, 0.0f, 
	   };
	   
	   
	   protected byte[] indices = { 0, 1, 2, 0,2,3,  };
	   protected float[] colors = { // Colors for the vertices (NEW)
	      1.0f, 0.0f, 0.0f, 1.0f, // Red (NEW)
	      1.0f, 0.0f, 0.0f, 1.0f, // Red (NEW)
	      1.0f, 0.0f, 0.0f, 1.0f, // Red (NEW)
	      1.0f, 0.0f, 0.0f, 1.0f // Red (NEW)
	   };
	  
	   // Constructor - Setup the data-array buffers
	   public ParticleShape(float sx, float sy, float spX, float spY)
	   {
		  super(sx,sy,spX,spY);
		
	      // Setup vertex-array buffer. Vertices in float. A float has 4 bytes
	      ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
	      vbb.order(ByteOrder.nativeOrder()); // Use native byte order
	      vertexBuffer = vbb.asFloatBuffer(); // Convert byte buffer to float
	      vertexBuffer.put(vertices);         // Copy data into buffer
	      vertexBuffer.position(0);           // Rewind
	   
	      // Setup color-array buffer. Colors in float. A float has 4 bytes (NEW)
	      ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
	      cbb.order(ByteOrder.nativeOrder()); // Use native byte order (NEW)
	      colorBuffer = cbb.asFloatBuffer();  // Convert byte buffer to float (NEW)
	      colorBuffer.put(colors);            // Copy data into buffer (NEW)
	      colorBuffer.position(0);            // Rewind (NEW)
	    
	      // Setup index-array buffer. Indices in byte.
	      indexBuffer = ByteBuffer.allocateDirect(indices.length);
	      indexBuffer.put(indices);
	      indexBuffer.position(0);
	   }
	   
	   public void setColor(float r, float g, float b, float a)
	   {
		   R = r;
		   G = g;
		   B = b;
		   A = a;
		   
		   // R
		   colors[0]=r;
		   colors[4]=r;
		   colors[8]=r;
		   colors[12]=r;
		   // G
		   colors[1]=g;
		   colors[5]=g;
		   colors[9]=g;
		   colors[13]=g;
		   // B
		   colors[2]=b;
		   colors[6]=b;
		   colors[10]=b;
		   colors[14]=b;
		   // A
		   colors[3]=a;
		   colors[7]=a;
		   colors[11]=a;
		   colors[15]=a;
		   colorBuffer.clear();
		   colorBuffer.put(colors);   
		   colorBuffer.position(0);
	   }
	  
	   // Render this shape
	   public void draw(GL10 gl) {
		  gl.glMatrixMode(GL10.GL_MODELVIEW);
		  gl.glLoadIdentity();                 // Reset model-view matrix
		  gl.glDisable(GL10.GL_TEXTURE_2D); 
		  gl.glPushMatrix();	 
          gl.glScalef(scaleX, scaleY, 0f);
          //gl.glScalef(0.25f, 0.25f, 0f);
          gl.glTranslatef(X/scaleX, Y/scaleY*SpatterEngine.screen_ratio, 0f);     
	      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);	    
	      
	      gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	      gl.glEnableClientState(GL10.GL_COLOR_ARRAY);          // Enable color-array (NEW)
	      gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);  // Define color-array buffer (NEW)      
	      // Draw the primitives via index-array
	      gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
	      gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	      gl.glDisableClientState(GL10.GL_COLOR_ARRAY);   // Disable color-array (NEW)
	      gl.glPopMatrix();
	      gl.glLoadIdentity();
	      gl.glEnable(GL10.GL_TEXTURE_2D);
	   }
	   
	   // posun particle
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
			
			A-=0.1;			
			setColor(R,G,B, A);
	   }
	   
	   public void setScale(float sx, float sy)
	   {
		 scaleX = sx;
		 scaleY = sy;
	   }
}
