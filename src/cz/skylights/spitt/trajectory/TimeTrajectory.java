package cz.skylights.spitt.trajectory;

 import android.graphics.Point;
import android.util.Log;

import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.interfaces.*;

/// Trajektorie
public class TimeTrajectory implements ITrajectory 
{

	public float getX(GameObject enemy) {
		return enemy.X;
	}

	public float getY(GameObject enemy) {		
		float s = SpatterEngine.GameObjectTime(enemy);
		float y = enemy.startY-SpatterEngine.GameObjectTime(enemy)*enemy.Speed; 
		return y;
	}		
}
