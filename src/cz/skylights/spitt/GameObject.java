package cz.skylights.spitt;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

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
	public float startX;
	public float startY;
	
	protected ITrajectory _trajectory= null;
	
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
}
