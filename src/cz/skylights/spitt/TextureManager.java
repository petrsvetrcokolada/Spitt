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
import cz.skylights.spitt.collision.CollisionArray;

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
	    
	    public BitmapTexture AddTexture(String name, boolean build_edge)
	    {
	    	return AddTexture(name, 1,-1,-1, build_edge);
	    }
	    
	    public BitmapTexture AddTexture(String name, int frames,int fwidth,int fheight,boolean build_edge)
	    {
	    	int dr = getAndroidDrawable(name);	    	
	    	
	    	BitmapTexture texture = new BitmapTexture(name,dr, build_edge);
	    	texture.Frames = frames;
	    	if (fwidth > 0 && fheight > 0)
	    	{
	    		texture.FrameWidth = fwidth;
	    		texture.FrameHeight = fheight;
	    	}
	    	_textures.put(name, texture);
	    	_list_textures.add(texture);
	    	
	    	return texture;
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
	    
	    public BitmapTexture GetTexture(String name)
	    {
	    	BitmapTexture tex = _textures.get(name);
	    	return tex;
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
	            	this.EdgeBitmap(texture);
	            }
	        }
	        catch(Exception e) { 
	        	String str = e.getMessage();
	        	Log.w("ERR", str);
	        	int a = 0;
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
	    public CollisionArray EdgeBitmap(BitmapTexture texture)
	    {
	    	
	    	Bitmap source = texture.getBitmap();
	    	
	    	int len =-1;
	    	int width=0;
	    	int height=0;
	    	if (texture.Frames == 1)
	    	{
	    		len = (source.getWidth()*source.getHeight());
	    		width = source.getWidth();
	    		height = source.getHeight();
	    	}
	    	else
	    	{
	    		len = texture.FrameWidth * texture.FrameHeight;
	    		width = texture.FrameWidth;
	    		height = texture.FrameHeight;
	    	}
	    	
	    	///	
	    	int kolikx = source.getWidth()/width;
	    	for(int frame = 0; frame < texture.Frames; frame++)
	    	{
	    		int pocx = frame % kolikx;
	    		int pocy = frame / kolikx;	  
	    		
	    		    	    	
		    	int[] pixels = new int[len];
		    	source.getPixels(pixels, 0, width, pocx*width, pocy*height, width, height);
		    	/*
		    	int data = 0;
		    	for(int c= 0; c < len; c++)
		    	{
		    		if (pixels[c]!=0)
		    		{
		    			data =c;
		    			break;
		    		}
		    	}*/
		    	
		    	CollisionArray list=new CollisionArray();
	  		    float jednotkax = 1 / (float)width; 
	  		    float jednotkay = 1 / (float)height;
	  		  
	  		    texture.setUnit((jednotkax+jednotkay)/2);
	  		    list.setUnit((jednotkax+jednotkay)/2);
	  		    
	  		    float min_x = 1, min_y = 1;
	  		    float max_x = 0, max_y = 0;  		    
	  		    
		    	for(int idx = 0; idx < pixels.length;idx++)
		    	{
		    		int val = pixels[idx];
		    		if (val != 0)
		    		{
		    			
		    		  int valA = 0;
		    		  if (idx > 0)
		    			  valA = pixels[idx-1];
		    		  
		    		  int valB = 0;
		    		  if (idx <  pixels.length-1)
		    			  valB = pixels[idx+1];
		    		  
		    		  int valC = 0;
		    		  if (idx > width-1)
		    			  valC = pixels[idx-width];
		    		  
		    		  int valD = 0;
		    		  if (idx < pixels.length-width)
		    			  valD = pixels[idx+width];
		    		  
		    		  if (valA != 0 && valB != 0 && valC != 0 && valD != 0)
		    			  continue;
		    		  
		    		  int x = idx % width;
		    		  int y = idx / height;	    		  
		    		  
		    		  Vertex2D vert = new Vertex2D(jednotkax*x, 1-jednotkay*y);
		    		  if (vert.X < min_x)
		    			  min_x = vert.X;
		    		  if (vert.Y < min_y)
		    			  min_y = vert.Y;
		    		  if (vert.X > max_x)
		    			  max_x = vert.X;
		    		  if (vert.Y > max_y)
		    			  max_y = vert.Y;
		    		  list.add(vert);
		    		}
		    	}
		    	
		    	list.setRectangle(min_x, min_y, max_x-min_x, max_y-min_y);
		    	texture.setEdge(frame,list);
	    	}
	    		    
	    	return null; /// pozor
	    }
}
