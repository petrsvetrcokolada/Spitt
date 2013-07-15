package cz.skylights.spitt.shape;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.GameObject;
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
	
	public SpriteAnimation setExplosion(GameObject obj)
	{		
		SpriteAnimation spr = setExplosion(obj.X, obj.Y);
		spr.X = obj.X+obj.Width/2-spr.Width/2;
		spr.Y = obj.Y+obj.Height/2-spr.Height/2;
		return spr;
	}
	
	// vloz explosi
	public SpriteAnimation setExplosion(float X, float Y)
	{
		SpriteAnimation spr = new SpriteAnimation(false);		
		Integer rand = generator.nextInt(9);
		int txID = _array.get(rand);
		spr.setTexture(txID);
		spr.X = X;
		spr.Y = Y;
		spr.setSizeRatio(0.25f);
		spr.setFramesParameter(16, _textures.GetBitmap(SpatterEngine.explose_animation).getWidth(),128, 128);		
		spr.setFrame(0);
		_animations.add(spr);
		return spr;
	}
	
	// vloz explosi
	public void setBigExplosion(float X, float Y)
	{
		SpriteAnimation big = new SpriteAnimation(false);		
		Integer rand = generator.nextInt(9);
		int txID = _array.get(rand);
		big.setTexture(txID);
		big.setSizeRatio(0.4f);
		big.X = X;
		big.Y = Y;		
		big.setFramesParameter(16, _textures.GetBitmap(SpatterEngine.explose_animation).getWidth(),128, 128);		
		big.setFrame(0);
		_animations.add(big);
		SpriteAnimation spr = new SpriteAnimation(false);		
		rand = generator.nextInt(9);
		txID = _array.get(rand);
		spr.setTexture(txID);
		spr.setSizeRatio(0.2f);
		spr.X = X;
		spr.Y = Y;
		spr.setFramesParameter(16, _textures.GetBitmap(SpatterEngine.explose_animation).getWidth(),128, 128);		
		spr.setFrame(0);
		_animations.add(spr);
		spr = new SpriteAnimation(false);		
		rand = generator.nextInt(9);
		txID = _array.get(rand);
		spr.setTexture(txID);
		spr.setSizeRatio(0.2f);
		spr.X = X+big.Width-spr.Width;
		spr.Y = Y;		
		spr.setFramesParameter(16, _textures.GetBitmap(SpatterEngine.explose_animation).getWidth(),128, 128);		
		spr.setFrame(0);
		_animations.add(spr);
		spr = new SpriteAnimation(false);		
		rand = generator.nextInt(9);
		txID = _array.get(rand);
		spr.setTexture(txID);
		spr.setSizeRatio(0.2f);
		spr.X = X;
		spr.Y = Y+big.Height-spr.Height;		
		spr.setFramesParameter(16, _textures.GetBitmap(SpatterEngine.explose_animation).getWidth(),128, 128);		
		spr.setFrame(0);
		_animations.add(spr);
		spr = new SpriteAnimation(false);		
		rand = generator.nextInt(9);
		txID = _array.get(rand);
		spr.setTexture(txID);
		spr.setSizeRatio(0.2f);
		spr.X = X+big.Width-spr.Width;
		spr.Y = Y+big.Height-spr.Height;		
		spr.setFramesParameter(16, _textures.GetBitmap(SpatterEngine.explose_animation).getWidth(),128, 128);		
		spr.setFrame(0);
		_animations.add(spr);
	}
	
	// posun animaci
	private boolean switch_pos=false;
	public void animation()
	{
		/*
		if ((SpatterEngine.GameTime % 1500) == 0)
		{
		  if (switch_pos== false)
		  {
			  this.setBigExplosion(0.2f, 0.2f);
			  switch_pos = true;
		  }
		  else			 
		  {
			  this.setExplosion(0.7f, 0.7f);
			  switch_pos = false;
		  }		  
		}*/
				
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
