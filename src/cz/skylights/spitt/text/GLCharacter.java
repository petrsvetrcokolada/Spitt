package cz.skylights.spitt.text;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.SpatterEngine;

public class GLCharacter extends GameObject {
	   private char _char;	
	  
	   private float[] vertices = {  // Vertices of the triangle
			   0.0f, 0.0f, 0.0f, 
			   1.0f, 0.0f, 0.0f, 
			   1.0f, 1.0f, 0.0f, 
			   0.0f, 1.0f, 0.0f, 
	   };
	   
	   
	    private float texture[] = { 
	    		0.0f, 0.0652f,	    	
	    		0.0625f, 0.0625f,
	            0.0625f, 0.0f, 
	            0.0f, 0.0f,	            
	        }; 

	        private byte indices[] = { 
	            0,1,2, 
	            0,2,3, 
	        }; 	        
	   
	   private float chwidth = 1/(float)16;
	   private float chheight = 1/(float)16;
	   public int Index=-1;
	   public float X;
	   public float Y;
	   
	  
	   // Constructor - Setup the data-array buffers
	   public GLCharacter(char chr) {
		  _char = chr;
		  //chwidth = width;
		  Index = GLText.CharString.indexOf(chr);
		  
	      // Setup vertex-array buffer. Vertices in float. A float has 4 bytes
	      ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
	      vbb.order(ByteOrder.nativeOrder()); // Use native byte order
	      vertexBuffer = vbb.asFloatBuffer(); // Convert byte buffer to float
	      vertexBuffer.put(vertices);         // Copy data into buffer
	      vertexBuffer.position(0);           // Rewind
	      
	      vbb = ByteBuffer.allocateDirect(texture.length * 4); 
	      vbb.order(ByteOrder.nativeOrder()); 
	      textureBuffer = vbb.asFloatBuffer(); 
	      textureBuffer.put(texture); 
	      textureBuffer.position(0); 
	  
	    
	      // Setup index-array buffer. Indices in byte.
	      indexBuffer = ByteBuffer.allocateDirect(indices.length);
	      indexBuffer.put(indices);
	      indexBuffer.position(0);
	   }
	  
	   // Render this shape
	   public void draw(GL10 gl, int[] sprite) {
		  gl.glMatrixMode(GL10.GL_MODELVIEW);
		  gl.glLoadIdentity();                 // Reset model-view matrix
		  gl.glEnable(GL10.GL_TEXTURE_2D); 
		  gl.glPushMatrix();	 
		  gl.glScalef(0.05f, 0.05f, 0.0f);
		  gl.glTranslatef(X, Y/0.05f*SpatterEngine.screen_ratio, 0.0f); // Translate left and into the screen	  
		  
	      gl.glMatrixMode(GL10.GL_TEXTURE); 
	      gl.glLoadIdentity();
	      
	      //int rows = _index/16;
	      //int col = _index-rows*16;
	      int rows = (Index/16);
	      int col = Index-rows*16;
	      	     	      	     		  		 
	      gl.glBindTexture(GL10.GL_TEXTURE_2D, sprite[0]);
	      gl.glTranslatef(chwidth*(float)col,chheight*(float)rows, 0.0f);
	      gl.glFrontFace(GL10.GL_CCW); 
	      gl.glEnable(GL10.GL_CULL_FACE); 
	      gl.glCullFace(GL10.GL_BACK);
	      
	      // Enable arrays and define the buffers
	      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	      gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	      gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);	     
	      	      
	      gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer); 
	      gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer); 
	      gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, 
	                          GL10.GL_UNSIGNED_BYTE, indexBuffer);
	      
	      gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	      gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 
	      gl.glPopMatrix();
	      gl.glLoadIdentity();
	   }

}
