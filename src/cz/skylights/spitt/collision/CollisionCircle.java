package cz.skylights.spitt.collision;

import cz.skylights.spitt.GameObject;

// Easiest collision type
// Circle - radius defined
public class CollisionCircle implements ICollision {
	private float _radius=0.0f;
	private float _centerX=0.0f;
	private float _centerY = 0.0f;
	
	/// Check collision 
	public boolean CheckCollision(GameObject obj1) 
	{
		
		return false;
	}
	
	public CollisionType GetType()
	{
		return CollisionType.collisionCircle;
	}

	public float get_radius() {
		return _radius;
	}

	public void set_radius(float _radius) {
		this._radius = _radius;
	}
	
}
