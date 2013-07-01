package cz.skylights.spitt.text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class GLText {
	   private int[] textures = new int[1];
	   private Context _context;
	   private ArrayList _text;
	   // znaky ... zatim neeresim diakritiku
	   public static String CharString = " !\"#$%&'()*+,-. 0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[/]^_”abcdefghijklmnopqrstuvwxyz{|}~";

	   
	   public void BuildCharacters(String text, float X, float Y)
	   {
		   _text = new ArrayList();
		   
		   for(int i = 0; i < text.length();i++)
		   {
			   GLCharacter chr = new GLCharacter(text.charAt(i));
			   chr.X = 0.25f+0.5f*i;
			   chr.Y = Y;
			   _text.add(chr);			   
		   }
	   }
	
	   // Render this shape
	   public void draw(GL10 gl) {		   
		   for(int i =0; i < _text.size();i++)
		   {
			   GLCharacter ch = (GLCharacter)_text.get(i);
			   ch.draw(gl, textures);
		   }
	   }
	   
	    // nahrej texturu ... 
	    public void loadTexture(GL10 gl, int texture, Context context) { 
	    	// otevri stream z resources
	        InputStream imagestream = context.getResources().openRawResource(texture); 
	        Bitmap bitmap = null;
	        _context = context;

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
}
