package cz.skylights.spitt.shape;

import java.nio.*;
import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.GameObject;

public class Shape extends GameObject {
   private FloatBuffer colorBuffer;   
  
   protected float[] vertices = { 
		   0.0f, 0.0f, 0.0f, 
		   1.0f, 0.0f, 0.0f, 
		   1.0f, 0.1f, 0.0f, 
		   0.0f, 0.1f, 0.0f, 
   };
   
   
   protected byte[] indices = { 0, 1, 2, 0,2,3,  };
   protected float[] colors = { // Colors for the vertices (NEW)
      1.0f, 0.0f, 0.0f, 1.0f, // Red (NEW)
      1.0f, 0.0f, 0.0f, 0.8f, // Red (NEW)
      1.0f, 0.0f, 0.0f, 0.8f, // Red (NEW)
      1.0f, 0.0f, 0.0f, 1.0f // Red (NEW)
   };
  

   public Shape(float width, float height) 
   {
	  vertices[3]=width;
	  vertices[6]=width;
	  vertices[7]=height;
	  vertices[10]=height;
	  Width = width;
	  Height = height;
	   
      ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
      vbb.order(ByteOrder.nativeOrder()); 
      vertexBuffer = vbb.asFloatBuffer();
      vertexBuffer.put(vertices);        
      vertexBuffer.position(0);          
     
      ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
      cbb.order(ByteOrder.nativeOrder());
      colorBuffer = cbb.asFloatBuffer();  
      colorBuffer.put(colors);           
      colorBuffer.position(0);           
         
      indexBuffer = ByteBuffer.allocateDirect(indices.length);
      indexBuffer.put(indices);
      indexBuffer.position(0);
   }
  
   // Render this shape
   public void draw(GL10 gl) {
	  gl.glMatrixMode(GL10.GL_MODELVIEW);
	  gl.glLoadIdentity();                
	  gl.glDisable(GL10.GL_TEXTURE_2D); 	
	  gl.glPushMatrix();	 
	  //gl.glScalef(0.25f, 0.25f, 0.0f);
	  gl.glTranslatef(X, Y, 0.0f);	      
    
      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
      gl.glEnableClientState(GL10.GL_COLOR_ARRAY);        
      gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);       
      
      gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
      gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
      gl.glDisableClientState(GL10.GL_COLOR_ARRAY);   
      gl.glPopMatrix();
      gl.glLoadIdentity();
   }
}