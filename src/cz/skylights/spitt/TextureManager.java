package cz.skylights.spitt;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
/// Spravce textur
public class TextureManager { 
	
	    public TextureManager() 
	    {	  	    
	    }
	    
		ArrayList<Integer> _list = new ArrayList<Integer>();		
	    private int[] textures;
	    Hashtable<Integer,Integer> _table = new  Hashtable<Integer,Integer>();
	    Hashtable<Integer, Bitmap> _bitmaps = new Hashtable<Integer, Bitmap>();
	    
	    public void AddTexture(int texture)
	    {	    
	    	_list.add(texture);	    		    	
	    }
	    
	    // vytvori pole textur
	    // natahne textury y resources
	    public int[] buildTextures(GL10 gl, Context context)
	    {	    	
	    	textures = new int[_list.size()];
	    	gl.glGenTextures(_list.size(), textures, 0);
	    	
	    	for (int i = 0; i < _list.size(); i++)
	    	{	    	
	    		loadTexture(gl, _list.get(i), context, i);
	    		_table.put(_list.get(i), textures[i]);
	    	}
	    	return textures;
	    }
	    
	    public int GetTexture(int resourceID)
	    {
	    	return _table.get(resourceID);
	    }	
	    
	    public Bitmap GetBitmap(int resourceID)
	    {
	    	return _bitmaps.get(resourceID);
	    }

	    // nacte texturu	   
	    private void loadTexture(GL10 gl, int texture, Context context, int textureNumber) 
	    { 
	        InputStream imagestream = context.getResources().openRawResource(texture); 
	        Bitmap bitmap = null; 

	        try { 
	            bitmap = BitmapFactory.decodeStream(imagestream); 
	            _bitmaps.put(texture, bitmap);
	        }
	        catch(Exception e) { 
	        }
	        finally { 
	            try { 
	                imagestream.close(); 
	                imagestream = null; 
	            } 
	            catch (IOException e) { 
	            } 
	        } 

	        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[textureNumber]); 
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, 
	                           GL10.GL_NEAREST); 
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, 
	                           GL10.GL_LINEAR); 
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, 
	                           GL10.GL_CLAMP_TO_EDGE); 
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, 
	                           GL10.GL_CLAMP_TO_EDGE); 
	        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

	        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0); 
	        bitmap.recycle(); 	         
	    } 

}
