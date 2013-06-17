package cz.skylights.spitt;

import java.util.ArrayList;

import cz.skylights.geometry.Vertex2D;
import cz.skylights.spitt.collision.CollisionArray;

import android.graphics.Bitmap;

/// Trida popisujici texturu
/// Jmeno, Bitmapa, ID v resourcich
public class BitmapTexture {
	private Bitmap _texture;
	private int resourceID;
	public int textureID;
	private String _name;	
	private CollisionArray _edge = null;
	
	public BitmapTexture(String name, int resid, boolean build_edge)
	{
		_name = name;
		resourceID = resid;
		
		if (build_edge == true)
		{
			_edge = new CollisionArray();
		}
	}
	
	public void setBitmap(Bitmap bitmap)
	{
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
	
	public void setEdge(CollisionArray edge)
	{
	  _edge = edge;	
	}
	
	public CollisionArray getEdge()
	{
		return _edge;
	}
	
	public boolean hasEdge()
	{
		return _edge!=null;
	}
	
	public CollisionArray getCollision(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2)
	{
		CollisionArray list = new CollisionArray();
		list.setUnit(_edge.Unit*(w1+h1)/2);
		
		for(int i = 0; i < _edge.size();i++)
		{
			Vertex2D vert = _edge.get(i);
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
