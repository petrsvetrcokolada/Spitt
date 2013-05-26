package cz.skylights.spitt;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.geometry.Vertex2D;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.opengl.GLUtils;
import android.util.Log;

/// Spravce textur
/// pracujeme s resourceID
public class TextureManager { 
	
		private GL10 _gl;
		private Context _context;
	    /*
	    public TextureManager(GL10 gl) 
	    {	_gl = gl;  	    
	    	
	    }
	    */
		
		public void setGLContext(GL10 gl, Context context)
		{
			_gl = gl;
			_context = context;
		}		
	    
		ArrayList<Integer> _list = new ArrayList<Integer>();		
	    private int[] textures;
	    Hashtable<Integer,Integer> _table = new  Hashtable<Integer,Integer>();
	    Hashtable<Integer, Bitmap> _bitmaps = new Hashtable<Integer, Bitmap>();
	    //
	    Hashtable<String, BitmapTexture> _textures = new Hashtable<String,BitmapTexture>();
	    ArrayList<BitmapTexture> _list_textures = new ArrayList<BitmapTexture>();
	    
	    /*
	    public void AddTexture(int texture)
	    {	    
	    	_list.add(texture);	    		    	
	    }*/
	    
	    public void AddTexture(String name)
	    {
	    	int dr = getAndroidDrawable(name);	    	
	    	
	    	BitmapTexture texture = new BitmapTexture(name,dr,true);
	    	_textures.put(name, texture);
	    	_list_textures.add(texture);
	    }
	    
	    static public int getAndroidDrawable(String DrawableName){
	    	
	        int resourceId=Resources.getSystem().getIdentifier(DrawableName, "drawable/image", null);
	        //return resourceId;
	        
	    	try {
	    	    Class res = R.drawable.class;
	    	    Field field = res.getField(DrawableName);
	    	    Class cls = GetField.class;
	    	    int drawableId = field.getInt(null);
	    	    return drawableId;
	    	}
	    	catch (Exception e) {
	    	    int a = 0;
	    	}
	    	
	    	return -1;
	    }
	    
	    // vytvori pole textur
	    // natahne textury y resources
	    public int[] buildTextures(GL10 gl, Context context)
	    {	
	    	_gl = gl;
	    	_context = context;
	    	
	    	textures = new int[_list_textures.size()];
	    	gl.glGenTextures(_list_textures.size(), textures, 0);
	    	
	    	for (int i = 0; i < _list_textures.size(); i++)
	    	{	    	
	    		BitmapTexture texture = _list_textures.get(i);
	    		try
	    		{
	    		  loadTexture(gl, texture, context, i);
	    		}
	    		catch(Exception e)
	    		{
	    		  Log.w("err", e.getMessage());
	    		}
	    		texture.setGLId(textures[i]);
	    		_table.put(texture.getResourceId(), textures[i]);
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
	    private Bitmap loadTexture(GL10 gl, BitmapTexture texture, Context context, int textureNumber) 
	    { 
	        InputStream imagestream = context.getResources().openRawResource(texture.getResourceId()); 
	        Bitmap bitmap = null; 

	        try { 
	            bitmap = BitmapFactory.decodeStream(imagestream); 
	            texture.setBitmap(bitmap);
	            _bitmaps.put(texture.getResourceId(), bitmap);
	            if (texture.hasEdge() == true)
	            {
	            	texture.setEdge(this.EdgeBitmap(bitmap));
	            }
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
	        //bitmap.recycle();	        
	        return bitmap;
	    } 
	    
	    // potrebuju pole ... jako hranice
	    // nebo 0...1
	    public ArrayList<Vertex2D> EdgeBitmap(Bitmap source)
	    {
	    	int len = (source.getWidth()*source.getHeight());
	    	int[] pixels = new int[len];
	    	source.getPixels(pixels, 0, source.getWidth(), 0, 0, source.getWidth(), source.getHeight());
	    	
	    	ArrayList<Vertex2D> list=new ArrayList();
	    	for(int idx = 0; idx < pixels.length;idx++)
	    	{
	    		int val = pixels[idx];
	    		if (val > 0)
	    		{
	    			
	    		  int valA = 0;
	    		  if (idx > 0)
	    			  valA = pixels[idx-1];
	    		  
	    		  int valB = 0;
	    		  if (idx <  pixels.length-1)
	    			  valB = pixels[idx+1];
	    		  
	    		  int valC = 0;
	    		  if (idx > source.getWidth()-1)
	    			  valC = pixels[idx-source.getWidth()];
	    		  
	    		  int valD = 0;
	    		  if (idx < pixels.length-source.getWidth())
	    			  valD = pixels[idx+source.getWidth()];
	    		  
	    		  if (valA > 0 && valB > 0 && valC > 0 && valD > 0)
	    			  continue;
	    		  
	    		  int x = idx / source.getWidth();
	    		  int y = idx % source.getWidth();
	    		  
	    		  float jednotkax = 1 / (float)source.getWidth(); 
	    		  float jednotkay = 1 / (float)source.getHeight();
	    		  
	    		  Vertex2D vert = new Vertex2D(jednotkax*x, jednotkay*y);
	    		  list.add(vert);
	    		}
	    	}
	    	return list;
	    }
}
