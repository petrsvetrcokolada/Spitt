package cz.skylights.spitt;

import cz.skylights.spitt.interfaces.ITrajectory;

public class SinTrajectory implements ITrajectory 
{

	public float getX(GameObject enemy) {
		return enemy.startX+(float)Math.sin(SpatterEngine.GameObjectTime(enemy)/7000.0f);
	}

	public float getY(GameObject enemy) {		
		return enemy.Y-enemy.Speed;
	}		

}
