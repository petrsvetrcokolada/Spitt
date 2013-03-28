package cz.skylights.spitt.collision;

import cz.skylights.spitt.GameObject;

public interface ICollision {
	boolean CheckCollision(GameObject obj2);
	CollisionType GetType();
}
