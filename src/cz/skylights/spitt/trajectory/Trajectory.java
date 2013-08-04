package cz.skylights.spitt.trajectory;

import cz.skylights.spitt.GameObject;
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
