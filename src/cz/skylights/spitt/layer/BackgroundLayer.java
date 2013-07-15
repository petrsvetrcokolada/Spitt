package cz.skylights.spitt.layer; 

import javax.microedition.khronos.opengles.GL10; 

import cz.skylights.spitt.SpatterEngine;

import java.nio.ByteOrder; 
import java.nio.ByteBuffer; 
import java.nio.FloatBuffer; 
import android.content.Context; 
import android.graphics.Bitmap; 
import android.graphics.BitmapFactory; 
import android.opengl.GLUtils; 
import java.io.IOException; 
import java.io.InputStream; 

public class BackgroundLayer { 
    private FloatBuffer vertexBuffer;  
    private FloatBuffer textureBuffer;  
    private ByteBuffer indexBuffer;  // 

    private int[] textures = new int[1]; 
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

    public BackgroundLayer(float speed) { 
    	_speed = speed;
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
    // nahrej texturu ... 
    public void loadTexture(GL10 gl, int texture, Context context) { 
    	// otevri stream z resources
        InputStream imagestream = context.getResources().openRawResource(texture); 
        Bitmap bitmap = null; 

        try { 
        	//decoduj obrazek
            bitmap = BitmapFactory.decodeStream(imagestream); 
        }
        catch (Exception e) { 
        }
        finally { 
            //Always clear and close 
            try { 
                imagestream.close(); 
                imagestream = null; 
            } 
            catch (IOException e) { 
            } 
        } 

        gl.glGenTextures(1, textures, 0); 
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]); 

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, 
                           GL10.GL_NEAREST); 
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, 
                           GL10.GL_LINEAR); 
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, 
                           GL10.GL_REPEAT); 
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, 
                           GL10.GL_REPEAT); 

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0); 
        bitmap.recycle(); 
    }
    
	public void scrollBackground(GL10 gl)
	{
		if (_offset == Float.MAX_VALUE)
			_offset=0;
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		gl.glScalef(1f, SpatterEngine.screen_ratio, 1f);
		gl.glTranslatef(0f, 0f, 0f);
		
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, _offset, 0.0f);
		
		draw(gl);
		gl.glPopMatrix();
		_offset+=_speed;
		gl.glLoadIdentity();
	}

    
    public void draw(GL10 gl) { 
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]); 

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
