package cz.skylights.spitt;

import java.util.*;
import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.interfaces.ITrajectory;

import android.content.Context;

// vrstva nepratel
public class EnemyLayer {
     
	TextureManager _textures = new TextureManager();
	// pole aktivnich objektu
	ArrayList<Enemy> _enemies;	
	ArrayList<Enemy> _allenemies;
	
	BitmapTexture _texture1;
	BitmapTexture _texture2;	
	BitmapTexture _texture3;
	BitmapTexture _texture4;
	BitmapTexture _texture6;
	BitmapTexture _texture7;
	
	Trajectory _trajectory = new Trajectory();
	
	public EnemyLayer()
	{
		_enemies = new ArrayList<Enemy>();
		_allenemies = new ArrayList<Enemy>();		
		
		_texture1 = _textures.AddTexture("enemy1", 2,64,64,true);
		_texture2 = _textures.AddTexture("enemy2",2,128,128,true);
		_texture3 = _textures.AddTexture("enemy3",2,128,128,true);
		_texture4 = _textures.AddTexture("enemy4",3,256,256,true);
		_texture6 = _textures.AddTexture("enemy6",1,128,128,true);
		_texture7 = _textures.AddTexture("enemy7",1,64,64,true);
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
		
		Collections.sort(_enemies, new EnemyComparator());
	}
	

	private int _lastIndex = 0;
	public void move()
	{
		ArrayList<Enemy> remove = new ArrayList<Enemy>();
		
		for(int i = 0; i < _allenemies.size(); i++)
		{
			Enemy en = _allenemies.get(i);
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
		  Enemy en = remove.get(c);
		  _allenemies.remove(en);
		}
		
		// pracovat by se melo pouze s aktivnimy objekty
		// work only with active objects
		for (int i =_enemies.size()-1; i >=0; i--)
		{
			Enemy en = _enemies.get(i);
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
			Enemy en = _enemies.get(i);
			if (en.Y > -en.Height)
				en.draw(gl);
		}
	}
	
	public ArrayList<Enemy> GetActive()
	{
		return _enemies;	
	}
	
	public void hitEnemy(Enemy en)
	{
		if (en.Live <= 0)
		{
		  _enemies.remove(en);
		}
	}
}
