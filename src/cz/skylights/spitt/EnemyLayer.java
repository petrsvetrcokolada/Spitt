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
		
	Trajectory _trajectory = new Trajectory();
	
	public EnemyLayer()
	{
		_enemies = new ArrayList<Enemy>();
		_allenemies = new ArrayList<Enemy>();
		_textures.AddTexture("enemy");
		_textures.AddTexture("enemy1");		
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
				
			sy = 1.1f;
						
			Enemy en = new Enemy(sx,sy);		
			en.setSizeRatio(0.125f);
			en.Speed = 0.007f;
			en.setTrajectory(t);
			en.setTexture(_textures.GetTexture(SpatterEngine.enemy1));
		  
			en.StartTime = i*1500;
			_allenemies.add(en);			
		}
		
		Collections.sort(_enemies, new EnemyComparator());
	}
	

	private int _lastIndex = 0;
	public void move()
	{
		for(int i = _lastIndex; i < _allenemies.size(); i++)
		{
			Enemy en = _allenemies.get(i);
			if (en.StartTime <= SpatterEngine.GameTime)
			{
				en.X = en.startX;
				en.Y = en.startY;
				_enemies.add(en);
			}
			else
			{
				_lastIndex = i;
				break;
			}
			
		}
		
		// pracovat by se melo pouze s aktivnimy objekty
		// work only with active objects
		for (int i =_enemies.size()-1; i >=0; i--)
		{
			Enemy en = _enemies.get(i);
			if (en.Y >= 0)
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
			if (en.Y > 0)
				en.draw(gl);
		}
	}
	
	public ArrayList<Enemy> GetActive()
	{
		return _enemies;	
	}
	
}
