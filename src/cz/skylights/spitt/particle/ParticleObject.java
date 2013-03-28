package cz.skylights.spitt.particle;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.GameObject;

/// Zakladni casticovy objekt
abstract class ParticleObject extends GameObject 
{
	float _ratio;
	protected int _textureID=-1;	
	//
	public int StartTime=0;  // aktivni zacina byt v case
	public int Live = 1000; // doba zivota
	public float speedX=0.01f;
	public float speedY=0.01f;
	public float startX;
	public float startY; 
	
	public ParticleObject(float sx, float sy, float spX, float spY)
	{
		this.X = sx;
		this.Y = sy;	
		startX = sx;
		startY = sy;
		speedX =  spX;
		speedY = spY;
		// 
	}
	
	public abstract void draw(GL10 gl);
	public abstract void move();
	
}
