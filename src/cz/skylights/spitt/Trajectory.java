package cz.skylights.spitt;

import cz.skylights.spitt.interfaces.ITrajectory;

public class Trajectory  implements ITrajectory 
{

	public float getX(GameObject enemy) 
	{
		return enemy.X;
	}

	public float getY(GameObject enemy) 
	{			
		return enemy.Y-enemy.Speed;		
	}		

}
