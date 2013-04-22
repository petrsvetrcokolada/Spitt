package cz.skylights.spitt.shape;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.TextureManager;

public class ExplosionEmitter 
{

	TextureManager _textures;
	ArrayList<SpriteAnimation> _animations=new  ArrayList<SpriteAnimation>();
	ArrayList<Integer> _array = new ArrayList<Integer>();
	Random generator=new Random(); // generator nahodnych cisel
	
	public ExplosionEmitter(TextureManager tm)
	{
		_textures = tm;
		_array.add(_textures.GetTexture(SpatterEngine.explose_animation));
		_array.add(_textures.GetTexture(SpatterEngine.explose_animation1));
		_array.add(_textures.GetTexture(SpatterEngine.explose_animation2));
		_array.add(_textures.GetTexture(SpatterEngine.explose_animation));
		_array.add(_textures.GetTexture(SpatterEngine.explose_animation1));
		_array.add(_textures.GetTexture(SpatterEngine.explose_animation2));
		_array.add(_textures.GetTexture(SpatterEngine.explose_animation));
		_array.add(_textures.GetTexture(SpatterEngine.explose_animation1));
		_array.add(_textures.GetTexture(SpatterEngine.explose_animation2));
		_array.add(_textures.GetTexture(SpatterEngine.explose_animation));
	}
	
	// vloz explosi
	public void setExplosion(float X, float Y)
	{
		SpriteAnimation spr = new SpriteAnimation(false);		
		Integer txID = generator.nextInt(9);
		spr.setTexture(_textures.GetTexture(txID));
		spr.X = X;
		spr.Y = Y;
		_animations.add(spr);
	}
	// posun animaci
	public void animation()
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
			s.animation();
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
