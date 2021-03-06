package cz.skylights.spitt.layer;

import java.util.*;
import javax.microedition.khronos.opengles.GL10;


import cz.skylights.spitt.BitmapTexture;
import cz.skylights.spitt.Bonus;
import cz.skylights.spitt.Enemy;
import cz.skylights.spitt.GameObjectComparator;
import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.OptionsEngine;
import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.TextureManager;
import cz.skylights.spitt.interfaces.ITrajectory;
import cz.skylights.spitt.trajectory.SinTrajectory;
import cz.skylights.spitt.trajectory.Trajectory;

import android.content.Context;

// vrstva nepratel
public class EnemyLayer {
     
	TextureManager _textures = new TextureManager();
	// pole aktivnich objektu
	ArrayList<GameObject> _enemies;	
	ArrayList<GameObject> _allenemies;
	
	BitmapTexture _texture1;
	BitmapTexture _texture2;	
	BitmapTexture _texture3;
	BitmapTexture _texture4;
	BitmapTexture _texture6;
	BitmapTexture _texture7;
	//
	BitmapTexture _bonus1;
	
	Trajectory _trajectory = new Trajectory();
	SinTrajectory _sintrajectory = new SinTrajectory();
	
	public EnemyLayer()
	{
		_enemies = new ArrayList<GameObject>();
		_allenemies = new ArrayList<GameObject>();		
		
		_texture1 = _textures.AddTexture("enemy1", 2,64,64,true);
		_texture2 = _textures.AddTexture("enemy2",2,128,128,true);
		_texture3 = _textures.AddTexture("enemy3",2,128,128,true);
		_texture4 = _textures.AddTexture("enemy4",3,256,256,true);
		_texture6 = _textures.AddTexture("enemy6",1,128,128,true);
		_texture7 = _textures.AddTexture("enemy7",1,64,64,true);
		//
		_bonus1 = _textures.AddTexture("energy",2, 128,128,true);
	}
	
	public void loadTextures(GL10 gl, Context context)
	{
		_textures.buildTextures(gl, context);
	}
	
	public void createEnemies()
	{
		//
		int cnt = 40;
		ITrajectory t = null;
		for(int i = 0; i < cnt; i++)
		{
			float sx, sy;			
			if ((i%2) == 0)
			{							
				sx = 0.25f;
				t= _trajectory;
			}
			else
			{
				sx = 0.75f;
				t = _trajectory;
			}
			sy = OptionsEngine.startY;
			
			Enemy en = new Enemy(sx,sy);
			en.Strength = 20;
			if ((i%2) == 0)
			{					
				en.setTexture(_texture1);
				en.setSizeRatio(0.125f);
			}
			else if ((i%3) == 0)
			{				
				en.setTexture(_texture3);
				en.setSizeRatio(0.185f);
			}
			else if ((i%5) == 0)
			{
				en.setTexture(_texture4);
				en.setSizeRatio(0.25f);				
			}
			else if ((i%7) == 0)
			{
				en.setTexture(_texture6);
				en.setSizeRatio(0.185f);					
			}
			else if ((i%11) == 0)
			{
				en.setTexture(_texture7);
				en.setSizeRatio(0.185f);					
			}
			else
			{				
				en.setTexture(_texture2);
				en.setSizeRatio(0.185f);
			}
														
			en.AnimationSpeed = 500;			
			en.Speed = 0.003f;
			en.setTrajectory(t);			
		  
			en.StartTime = i*1500;
			_allenemies.add(en);			
		}
		
		
		for(int c=0; c < 5; c++)
		{
			float sx=0.5f, sy;
			sy = OptionsEngine.startY;
			Bonus b = new Bonus(sx,sy);
			b.setTexture(_bonus1);
			b.AnimationSpeed = 500;			
			b.StartTime = (c+1)*5000;
			b.setSizeRatio(0.125f);
			b.Speed = 0.003f;
			b.setTrajectory(_sintrajectory);
			_allenemies.add(b);			
		}
		
		Collections.sort(_allenemies, new GameObjectComparator());
	}
	

	private int _lastIndex = 0;
	public void move()
	{
		ArrayList<GameObject> remove = new ArrayList<GameObject>();
		
		for(int i = 0; i < _allenemies.size(); i++)
		{
			GameObject en = _allenemies.get(i);
			if (en.StartTime <= SpatterEngine.GameTime)
			{
				en.X = en.startX;
				en.Y = en.startY;
				_enemies.add(en);
				//_allenemies.remove(en);
				remove.add(en);
			}
			else
			{
				//_lastIndex = i;
				break;
			}
			
		}
		
		for(int c = 0; c< remove.size();c++)
		{
		  GameObject en = remove.get(c);
		  _allenemies.remove(en);
		}
		
		// pracovat by se melo pouze s aktivnimy objekty
		// work only with active objects
		for (int i =_enemies.size()-1; i >=0; i--)
		{
			GameObject en = (GameObject)_enemies.get(i);
			if (en.Y > -en.Height)
			{
				en.move();
			}
			else
			{
				_enemies.remove(i);
			}
		}		
	}		
	
	public void draw(GL10 gl)
	{
		// draw only with active object
		for (int i =0; i < _enemies.size(); i++)
		{
			GameObject en = (GameObject)_enemies.get(i);
			if (en.Y > - en.Height)
				en.draw(gl);
		}
	}
	
	public ArrayList<GameObject> GetActive()
	{
		return _enemies;	
	}
	
	public void updateEnemies()
	{
		for(int i = 0; i < _enemies.size();i++)
		{
			GameObject en = (GameObject)_enemies.get(i);
			if (en.Live <= 0/* && en.Adorner != null && en.Adorner.isAnimate() == false*/)
			{				
			  _enemies.remove(en);
			}
		}
	}
	
}
