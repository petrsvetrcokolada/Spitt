package cz.skylights.spitt.collision;

import java.util.ArrayList;

import android.view.animation.OvershootInterpolator;

import cz.skylights.geometry.Vertex2D;

public class CollisionArray extends ArrayList<Vertex2D> {
	
	public float Unit;
	public float X, Y;
	public float Width, Height;
	
	public void setUnit(float unit)
	{
		Unit = unit;
	}	
	
	public void setRectangle(float x,float y, float width, float height)
	{
		X = x;
		Y = y;
		Width = width;
		Height = height;
	}
}

