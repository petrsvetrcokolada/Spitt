package cz.skylights.spitt.trajectory;

import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.interfaces.ITrajectory;

public class SinTrajectory implements ITrajectory 
{

	public float getX(GameObject enemy) {
		return enemy.startX;
	}

	public float getY(GameObject enemy) {		
		return enemy.Y-enemy.Speed;
	}		

}
