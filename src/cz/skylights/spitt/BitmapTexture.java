package cz.skylights.spitt;

import java.util.ArrayList;

import cz.skylights.geometry.Vertex2D;

import android.graphics.Bitmap;

/// Trida popisujici texturu
/// Jmeno, Bitmapa, ID v resourcich
public class BitmapTexture {
	private Bitmap _texture;
	private int _id;
	private int _glid;
	private String _name;	
	private ArrayList<Vertex2D> _edge = null;
	
	public BitmapTexture(String name, int resid, boolean build_edge)
	{
		_name = name;
		_id = resid;
		
		if (build_edge == true)
		{
			_edge = new ArrayList();
		}
	}
	
	public void setBitmap(Bitmap bitmap)
	{
		_texture = bitmap;
	}
	
	public int getResourceId()
	{
		return _id;	
	}
	
	
	
	public void setGLId(int glid)
	{
		_glid = glid;
	}
	
	public void setEdge(ArrayList<Vertex2D> edge)
	{
	  _edge = edge;	
	}
	
	public boolean hasEdge()
	{
		return _edge!=null;
	}
	
}
