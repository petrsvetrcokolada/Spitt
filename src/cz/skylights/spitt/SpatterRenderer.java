package cz.skylights.spitt;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.OptionsEngine.gameState;
import cz.skylights.spitt.collision.AsyncCollision;
import cz.skylights.spitt.collision.Collisions;
import cz.skylights.spitt.layer.BackgroundLayer;
import cz.skylights.spitt.layer.EnemyLayer;
import cz.skylights.spitt.layer.ScrollLayer;
import cz.skylights.spitt.particle.ParticleCreator;
import cz.skylights.spitt.particle.ParticleEmitter;
import cz.skylights.spitt.particle.ParticleShapeCreator;
import cz.skylights.spitt.shape.*;
import cz.skylights.spitt.text.GLText;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class SpatterRenderer implements Renderer {
	// text
	protected GLText _text;
	protected GLText _score;
	// Layer 1 - scroolujici pozadi
	protected BackgroundLayer _background1;
	protected BackgroundLayer _background2;
	protected BackgroundLayer _background3;
	protected ScrollLayer _scroll;
	// Layer - Objekty na pozad� .... kontejn�ry, plo�iny a tak d�l
	// Layer - Enemy
	protected EnemyLayer _enemyLayer;
	//
	TextureManager _textures = new TextureManager();
	protected ParticleEmitter _particles;
	protected ParticleEmitter _particlesx;
	protected ExplosionEmitter _explosions;
	// Layer - Engine objekty - live prouzek
	protected Shape _shape;
	protected Sprite _gameover = new Sprite();
	private Sprite[] _lives;	
	
	private Player _player = null;
	private int Score = 100;
	//	
	private long _loopStart=0;
	private long _loopEnd=0;
	private long _loopRunTime=0;
	//private Thread _thread = new
	AsyncCollision collision_th = new AsyncCollision();
	
	public SpatterRenderer()
	{
		
		_background1 = new BackgroundLayer(SpatterEngine.scroll_bg1);
		_background2 = new BackgroundLayer(3*SpatterEngine.scroll_bg2/2);
		_background3 = new BackgroundLayer(5*SpatterEngine.scroll_bg2/4);		
		_enemyLayer = new EnemyLayer();
		_textures.AddTexture("particle", false);
		_textures.AddTexture("star", false);
		_textures.AddTexture("explose", 16,128,128,false);
		_textures.AddTexture("explose1", 16,128,128,false);
		_textures.AddTexture("explose2", 16,128,128,false);
		//_textures.AddTexture("gameover", false);
		
		_player = new Player(_textures);
		SpatterEngine.Player = _player;
		
		_scroll = new ScrollLayer(_textures, SpatterEngine.scroll_bg1);
		
		_particles = new ParticleEmitter(40, new ParticleShapeCreator());
		_particlesx = new ParticleEmitter(60, new ParticleCreator(_textures));	
		_text = new GLText();
		_score = new GLText();

		_shape = new Shape(0.35f,0.02f);
		_shape.X = 0.015f;
		_shape.Y = 0.015f;
		
		_lives = new Sprite[3];
		_lives[0] = new Sprite();
		_lives[1] = new Sprite();
		_lives[2] = new Sprite();		
		collision_th.execute();
	}	
	
	//
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		_loopStart = System.currentTimeMillis();
		try
		{
			if (_loopRunTime < SpatterEngine.fps_thread_sleep)
			{
				Thread.sleep(SpatterEngine.fps_thread_sleep - _loopRunTime);	
			}			
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();			
		}
		SpatterEngine.GetGameTime();
		
		// cisteni		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		// pozadi				
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
		// BACKGROUND LAYER
		// scrolling background
		//_background1.scrollBackground(gl);
		//_background2.scrollBackground(gl);
		//_background3.scrollBackground(gl);
		gl.glDisable(GL10.GL_BLEND);
		_scroll.scrollBackground(gl);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
		
		if (SpatterEngine.game_state == gameState.game)
		{		
			// missing BackgroundObject - moving objects
			///
			// PARTICLE
			_particles.move();
			_particles.draw(gl);		
			_particlesx.move();		
			_particlesx.draw(gl);			
			
			//gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );						 
			// ENEMY LAYER
			gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
			_enemyLayer.updateEnemies();
			_enemyLayer.move();
			_enemyLayer.draw(gl);
			// ANIMATION - vzbuch apod
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE); 
			// explosions
			_explosions.animation();
			_explosions.draw(gl);
			// PLAYER		
			if (_player.Live <= 0)
			{
				if (_player.TotalLives > 1)
				{
					_player.TotalLives--;
					_player.Live=100;
				}
				else
				{
					_player.TotalLives--;
					SpatterEngine.game_state = OptionsEngine.gameState.gameOver;
				}
			}
			_player.movePlayer(gl);
			//
			_text.draw(gl);
			if (Score != _player.Score)
			{
				Score = _player.Score;
				_score.BuildCharacters("Score:"+String.valueOf(Score), 0.9f*16, 0.95f);			
			}
			_score.draw(gl);
			if (_player.TotalLives >=1 )
			  _lives[0].draw(gl);
			if (_player.TotalLives >=2) 
				_lives[1].draw(gl);
			if (_player.TotalLives >=3)
				_lives[2].draw(gl);
			
			// FOREGROUND LAYER
			float live_sh = 0.35f * (float)_player.Live/(float)100;		
			_shape.updateShape(live_sh, 0.02f);
			_shape.draw(gl); // live rectangle			
			gl.glDisable(GL10.GL_BLEND);
			gl.glShadeModel(GL10.GL_SMOOTH);	
					
			/// COLLISION
			CheckCollision();
		}
		else if (SpatterEngine.game_state == gameState.gameOver)
		{
			// game over		
			//_gameover.animation();
			_gameover.draw(gl);
		}
		///
		_loopEnd = System.currentTimeMillis();
		_loopRunTime = (_loopEnd-_loopStart);		
	}
	//
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		SpatterEngine.screen_ratio = (float)height / (float)width;
		OptionsEngine.startY = 1.1f * SpatterEngine.screen_ratio;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0f, 1f, 0f, SpatterEngine.screen_ratio, -1f, 1f);
		///			
		loadLevel(gl);
	}
	//
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		int[] max = new int[1];
		gl.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, max, 0); 		
		///
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_SRC_ALPHA);	
		
	}
	
	public void loadLevel(GL10 gl)
	{
		// zde zavedeme nahrani textury
		_background1.loadTexture(gl, SpatterEngine.space_background,SpatterEngine.context);
		_background2.loadTexture(gl, SpatterEngine.space_stars1, SpatterEngine.context);
		_background3.loadTexture(gl, SpatterEngine.space_stars2, SpatterEngine.context);
		_text.loadTexture(gl, SpatterEngine.text_characters, SpatterEngine.context);
		_text.BuildCharacters("Lives", 0.25f, 0.95f);
		_score.loadTexture(gl, SpatterEngine.text_characters, SpatterEngine.context);
		_score.BuildCharacters("Score:"+String.valueOf(Score), 0.9f*16, 0.95f);
		_textures.buildTextures(gl, SpatterEngine.context);
				
		_scroll.buildLayer();
		// vrstva nepratel
		_enemyLayer.loadTextures(gl,SpatterEngine.context);
		_enemyLayer.createEnemies();
		_player.setSizeRatio(0.25f);
		
		_explosions = new ExplosionEmitter(_textures);
		collision_th.setExplosions(_explosions);
		collision_th.setEnemies(_enemyLayer.GetActive());
		
		// nastaveni parametru animace		
		_gameover.loadTexture(gl, R.drawable.gameover, SpatterEngine.context);
		_gameover.setScale(1.0f, 1.0f);
		//_gameover.setFramesParameter(1, _textures.GetBitmap(R.drawable.gameover).getWidth(),256, 256);
		_gameover.X = 0.0f;
		_gameover.Y = 0.15f;
		//_gameover.setFrame(0);
		
		_particles.createParticles();
		_particlesx.createParticles();
		_lives[0].loadTexture(gl,SpatterEngine.sprite_live, SpatterEngine.context);
		_lives[1].loadTexture(gl,SpatterEngine.sprite_live, SpatterEngine.context);
		_lives[2].loadTexture(gl,SpatterEngine.sprite_live, SpatterEngine.context);
		_lives[0].X = 0.15f;
		_lives[0].Y = 0.96f;
		_lives[0].setScale(0.05f,  0.05f);
		_lives[1].X = 0.20f;
		_lives[1].Y = 0.96f;
		_lives[1].setScale(0.05f,  0.05f);
		_lives[2].X = 0.25f;
		_lives[2].Y = 0.96f;
		_lives[2].setScale(0.05f,  0.05f);	
		///
		SpatterEngine.game_state = gameState.game;
	}
	
	private void CheckCollision()
	{
		// collision enemies with weapon
		ArrayList<WeaponFire> fire = _player.GetFiredWeapon(); // 
		ArrayList<GameObject> enemies = _enemyLayer.GetActive();
		CheckWeapon(fire, enemies);
		// collision enemies width player
		CheckEnemy(enemies);
		
	}
	
	// check collision enemies and weapon
	private void CheckWeapon(ArrayList<WeaponFire> fire, ArrayList<GameObject> enemies)
	{
		for(int i =0; i < enemies.size(); i++)
		{
			//
			GameObject en = (GameObject)enemies.get(i);
			if (en.Live <= 0)
			{
				continue;
			}
			
			for(int f = 0; f < fire.size();f++)
			{
			  WeaponFire wf = fire.get(f);
			  if (wf.shotFired == false)
				  continue;
			  
			  try
			  {
					if (Collisions.CheckCollisionRect(en, wf)==true)
					{
						collision_th.Add(en, wf);	
					}
			  }
			  catch(Exception e)
			  {
				  
			  }
			  /*	 			  
			  if (Collisions.CheckCollision(wf,true, en,false)==true)		
			  {
				  wf.shotFired = false;
				  en.Live-=wf.Strength;
				  if(en.Live <=0)
				  {							  
					  Score += en.Strength;
					  _score.BuildCharacters("Score:"+String.valueOf(Score), 0.9f*16, 0.95f);
					  //vybuch
					  en.Adorner = _explosions.setExplosion(en);
				  }
			  }
			  */
			}
		}
	}
	
	private void CheckEnemy(ArrayList<GameObject> enemies)
	{
		for(int i =0; i < enemies.size(); i++)
		{
			//
			GameObject en = (GameObject)enemies.get(i);
			try
			{
				
				// kolize enemy - player se dela pres vlakno
				GameObject[] list = new GameObject[2];
				if (Collisions.CheckCollisionRect(en, _player)==true)
				{
					collision_th.Add(en, _player);
				}
			}
			catch(Exception e)
			{
				Log.w("CheckEnemy", e.getMessage());
			}
		}
	}
}
