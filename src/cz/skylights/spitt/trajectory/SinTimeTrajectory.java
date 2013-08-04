package cz.skylights.spitt.trajectory;
import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.interfaces.ITrajectory;


/// Trajektorie
public class SinTimeTrajectory implements ITrajectory 
{

	public float getX(GameObject enemy) {
		return enemy.startX+(float)Math.sin(SpatterEngine.GameObjectTime(enemy)/700.0f)*enemy.scaleX;
	}

	public float getY(GameObject enemy) {		
		float s = SpatterEngine.GameObjectTime(enemy);
		float y = enemy.startY-SpatterEngine.GameObjectTime(enemy)*enemy.Speed; 
		return y;
	}		
}