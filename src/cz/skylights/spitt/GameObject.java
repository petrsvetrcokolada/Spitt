package cz.skylights.spitt;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import cz.skylights.spitt.collision.CollisionArray;
import cz.skylights.spitt.interfaces.ITrajectory;

public class GameObject {
	public float X;
	public float Y;
    public float scaleX = 0.25f;
    public float scaleY = 0.25f;
	public float Width;  // bylo by dobre znat sirku
	public float Height; // bylo by dobre znat vysku
	
	// aktivni objekt se muze pohybovat
	public float Speed=0; // ma rychlost
	public int StartTime=0;  // aktivni zacina byt v case
	public int Strength=100; // pokud je znicitelny, ma silu
	public int Live = 100; // musi byt vetsi nez 0
	public float startX;
	public float startY;
	
	protected ITrajectory _trajectory= null;
	protected BitmapTexture _texture;
	//
	protected int _frame=0;
	protected boolean _animation=true;	
	public float AnimationSpeed=500;
	
    protected FloatBuffer vertexBuffer; 
    protected FloatBuffer textureBuffer; 
    protected ByteBuffer indexBuffer;

    public GameObject()
    {
    	startX = 0;
    	startY = 0;
    }
    
    public GameObject(float sx, float sy)
    {
    	startX = sx;
    	startY = sy;
    }
    
	public void setTrajectory(ITrajectory trajectory)
	{
		_trajectory = trajectory;
	}
	
	public BitmapTexture getTexture()
	{
	  return _texture;
	}
	
	public int getFrame()
	{
		return _frame;
	}
	
	public void move()
	{
		if (_trajectory == null)
			return;
		
		if (SpatterEngine.GameTime >= StartTime)
		{
			if (_trajectory !=null)
			{
			  X = _trajectory.getX(this);
			  Y = _trajectory.getY(this);
			}
		}
	}
	
	public float getMinX()
	{
		if (_texture == null)
			return X;
		
		CollisionArray col_array = _texture.getEdge(_frame);		
		float minx = X+col_array.X*Width; 
		return minx;
	}
	
	public float getMinY()
	{
		if (_texture == null)
			return Y;
		
		CollisionArray col_array = _texture.getEdge(_frame);		
		float miny = Y+col_array.Y*Height; 
		return miny;
	}
	
	public float getMinWidth()
	{
		if (_texture == null)
			return Width;
		
		CollisionArray col_array = _texture.getEdge(_frame);		
		float miny = col_array.Width*Width; 
		return miny;		
	}
	
	public float getMinHeight()
	{
		if (_texture == null)
			return Height;
		
		CollisionArray col_array = _texture.getEdge(_frame);		
		float miny = col_array.Height*Height; 
		return miny;		
	}
}
