package cz.skylights.spitt;

import java.util.ArrayList;

import cz.skylights.spitt.collision.CollisionArray;
import cz.skylights.spitt.collision.Collisions;
import cz.skylights.spitt.geometry.Vertex2D;

import android.graphics.Bitmap;

/// Trida popisujici texturu
/// Jmeno, Bitmapa, ID v resourcich
public class BitmapTexture {
	private Bitmap _texture;
	private int resourceID;
	public int textureID;
	private String _name;	
	//private CollisionArray _edge = null;
	private Collisions _edge=null;
	//
	public int Frames=1;
	public int FrameWidth=-1;
	public int FrameHeight=-1;
	public int Width = -1;
	public int Height = -1;
	
	
	public BitmapTexture(String name, int resid,boolean build_edge)
	{
		_name = name;
		resourceID = resid;
		
		if (build_edge == true)
		{
			_edge = new Collisions();
		}
	}
	
	public void setBitmap(Bitmap bitmap)
	{
		if (Frames == 1)
			this.setBitmap(bitmap, 1,bitmap.getWidth(), bitmap.getHeight());
		else
			this.setBitmap(bitmap, -1,-1,-1);
			
	}
	
	public void setBitmap(Bitmap bitmap, int frames, int frame_w, int frame_h)
	{
		if (frames>0)
		{
			Frames = frames;
			FrameWidth = frame_w;
			FrameHeight = frame_h;
		}
		Width =  bitmap.getWidth();
		Height = bitmap.getHeight();
		
		_texture = bitmap;
	}
	
	public Bitmap getBitmap()
	{
		return _texture;
	}
	
	public int getResourceId()
	{
		return resourceID;	
	}
	
		
	public void setGLId(int glid)
	{
		textureID = glid;
	}
	
	public int getGLId()
	{
		return textureID;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setUnit(float unit)
	{
		_edge.Unit = unit;
	}
	
	public void setEdge(int frame, CollisionArray edge)
	{
	  _edge.add(frame, edge);	
	}
	
	public CollisionArray getEdge(int frame)
	{
		return _edge.get(frame);
	}
	
	public boolean hasEdge()
	{
		return _edge!=null;
	}
	
	public CollisionArray getCollision(int frame,float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2)
	{
		if (frame < 0 || frame >= Frames)
			return null;
		
		CollisionArray frame_array = _edge.get(frame);
		
		CollisionArray list = new CollisionArray();
		list.setUnit(_edge.Unit*(w1+h1)/2);
		
		for(int i = 0; i < frame_array.size();i++)
		{
			Vertex2D vert = frame_array.get(i);
			float px = w1*vert.X+x1;
			float py = h1*vert.Y+y1;
			if (px < x2 || px > x2+w2 || py < y2 || py > y2+h2)
			{
				continue;
			}
			//
			Vertex2D nv = new Vertex2D(px,py);
			list.add(nv);
			//
		}
		
		return list;
	}
	
}
