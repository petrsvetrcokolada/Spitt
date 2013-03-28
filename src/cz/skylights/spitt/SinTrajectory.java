package cz.skylights.spitt;

import cz.skylights.spitt.interfaces.ITrajectory;

public class SinTrajectory implements ITrajectory 
{

	public float getX(GameObject enemy) {
		return enemy.startX+(float)Math.sin(SpatterEngine.GameObjectTime(enemy)/700.0f)*enemy.scaleX;
	}

	public float getY(GameObject enemy) {		
		return enemy.Y-enemy.Speed;
	}		

}
