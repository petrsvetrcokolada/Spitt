package cz.skylights.spitt;

import cz.skylights.spitt.interfaces.ITrajectory;

public class ActiveObject extends GameObject {
	
	// aktivni objekt se muze pohybovat
	public float Speed; // ma rychlost
	public int StartTime=0;  // aktivni zacina byt v case
	public int Strength=100; // pokud je znicitelny, ma silu
	public float startX;
	public float startY;
	
	protected ITrajectory _trajectory= null;
	
	public ActiveObject(float sx, float sy)
	{
		startX  = sx;
		startY = sy;
		X = sx;
		Y = sy;
		Speed = 1.0f/20.0f; // spatne jsem stanovil rychlost ... chci 1.0 prekonat za 2000 milisec.
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
	
	
	public void setTrajectory(ITrajectory trajectory)
	{
		_trajectory = trajectory;
	}
}
