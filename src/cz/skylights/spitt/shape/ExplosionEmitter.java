package cz.skylights.spitt.shape;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.TextureManager;

public class ExplosionEmitter {

	TextureManager _textures;
	ArrayList<SpriteAnimation> _animations=new  ArrayList<SpriteAnimation>();
	
	public ExplosionEmitter(TextureManager tm)
	{
		_textures = tm;
	}
	
	// vloz explosi
	public void setExplosion(float X, float Y)
	{
		SpriteAnimation spr = new SpriteAnimation(false);
		spr.setTexture(_textures.GetTexture(SpatterEngine.explose_animation));
		_animations.add(spr);
	}
	// posun animaci
	public void move()
	{
		for(int i =_animations.size()-1; i >= 0; i--)
		{
			SpriteAnimation s = _animations.get(i);
			if (s.isAnimate() == false)
			{
				_animations.remove(i);
				continue;
			}
			
			s.move();
		}
	}
	// kresli animaci
	public void draw(GL10 gl)
	{
	
		for(int i =0; i < _animations.size(); i++)
		{
			SpriteAnimation s = _animations.get(i);
			s.draw(gl);
		}
	}
}
