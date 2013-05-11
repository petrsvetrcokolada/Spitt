package cz.skylights.spitt;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



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
	// Layer 1 - scroolujici pozadi
	protected BackgroundLayer _background1;
	protected BackgroundLayer _background2;
	protected BackgroundLayer _background3;
	protected ScrollLayer _scroll;
	// Layer - Objekty na pozadí .... kontejnéry, plošiny a tak dál
	// Layer - Enemy
	protected EnemyLayer _enemyLayer;
	//
	TextureManager _textures = new TextureManager();
	protected ParticleEmitter _particles;
	protected ParticleEmitter _particlesx;
	protected ExplosionEmitter _explosions;
	// Layer - Engine objekty - live prouzek
	protected Shape _shape;
	protected SpriteAnimation _animation = new SpriteAnimation(true);
	private Sprite[] _sprite;
	
	private Player _player = new Player();
	//	
	private long _loopStart=0;
	private long _loopEnd=0;
	private long _loopRunTime=0;
	
	public SpatterRenderer()
	{
		SpatterEngine.Player = _player;
		_background1 = new BackgroundLayer(SpatterEngine.scroll_bg1);
		_background2 = new BackgroundLayer(3*SpatterEngine.scroll_bg2/2);
		_background3 = new BackgroundLayer(5*SpatterEngine.scroll_bg2/4);		
		_enemyLayer = new EnemyLayer();
		_textures.AddTexture(SpatterEngine.particle);
		_textures.AddTexture(SpatterEngine.star);
		_textures.AddTexture(SpatterEngine.explose_animation);
		_textures.AddTexture(SpatterEngine.explose_animation1);
		_textures.AddTexture(SpatterEngine.explose_animation2);
		
		_scroll = new ScrollLayer(_textures, SpatterEngine.scroll_bg1);
		
		_particles = new ParticleEmitter(40, new ParticleShapeCreator());
		_particlesx = new ParticleEmitter(60, new ParticleCreator(_textures));	
		_text = new GLText();
		/*
		_shape = new Shape();
		_shape.X = 1.75f;
		_shape.Y = 0.5f;
		_shape.Width = 0.5f;
		_shape.Height = 0.5f;
		*/
		_shape = new Shape(0.35f,0.02f);
		_shape.X = 0.015f;
		_shape.Y = 0.015f;
		
		_sprite = new Sprite[3];
		_sprite[0] = new Sprite();
		_sprite[1] = new Sprite();
		_sprite[2] = new Sprite();		
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
		_background1.scrollBackground(gl);
		_background2.scrollBackground(gl);
		_background3.scrollBackground(gl);
		gl.glDisable(GL10.GL_BLEND);
		_scroll.scrollBackground(gl);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
		// missing BackgroundObject - moving objects
		///
		// PARTICLE
		_particles.move();
		_particles.draw(gl);		
		_particlesx.move();		
		_particlesx.draw(gl);			
		
		//gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );						 
		//
		// ENEMY LAYER
		_enemyLayer.move();
		_enemyLayer.draw(gl);
		// ANIMATION - vzbuch apod
		_animation.animation();
		_animation.draw(gl);
		// explosions
		_explosions.animation();
		_explosions.draw(gl);
		// PLAYER
		_player.movePlayer(gl);
		//
		_text.draw(gl);
		if (SpatterEngine.lives >=1 )
		  _sprite[0].draw(gl);
		if (SpatterEngine.lives >=1) 
			_sprite[1].draw(gl);
		if (SpatterEngine.lives >=1 )
			_sprite[2].draw(gl);
		
		// FOREGROUND LAYER
		_shape.draw(gl); // live rectangle	
		gl.glDisable(GL10.GL_BLEND);
		gl.glShadeModel(GL10.GL_SMOOTH);	
				
		/// COLLISION
		CheckCollision();
		
		///
		_loopEnd = System.currentTimeMillis();
		_loopRunTime = (_loopEnd-_loopStart);
	}
	//
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		SpatterEngine.screen_ratio = (float)height / (float)width;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0f, 1f, 0f, SpatterEngine.screen_ratio, -1f, 1f);
	}
	//
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		///
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_SRC_ALPHA);	
		// zde zavedeme nahrani textury
		_background1.loadTexture(gl, SpatterEngine.space_background,SpatterEngine.context);
		_background2.loadTexture(gl, SpatterEngine.space_stars1, SpatterEngine.context);
		_background3.loadTexture(gl, SpatterEngine.space_stars2, SpatterEngine.context);
		_player.loadTexture(gl, SpatterEngine.PLAYER_SHIP, SpatterEngine.PLAYER_BULLET, SpatterEngine.context);
		_text.loadTexture(gl, SpatterEngine.text_characters, SpatterEngine.context);
		_text.BuildCharacters("!()LivesJ", 0.25f, 0.95f);
		_textures.buildTextures(gl, SpatterEngine.context);
		
		_scroll.buildLayer();
		// vrstva nepratel
		_enemyLayer.loadTextures(gl,SpatterEngine.context);
		_enemyLayer.createEnemies();		
		
		_explosions = new ExplosionEmitter(_textures);
		
		// nastaveni parametru animace
		_animation.setTexture(_textures.GetTexture(SpatterEngine.explose_animation));
		_animation.setSizeRatio(0.25f);
		_animation.setFramesParameter(16, _textures.GetBitmap(SpatterEngine.explose_animation).getWidth(),128, 128);
		_animation.X = 0.4f;
		_animation.Y = 0.4f;
		_animation.setFrame(0);
		_particles.createParticles();
		_particlesx.createParticles();
		_sprite[0].loadTexture(gl,SpatterEngine.sprite_live, SpatterEngine.context);
		_sprite[1].loadTexture(gl,SpatterEngine.sprite_live, SpatterEngine.context);
		_sprite[2].loadTexture(gl,SpatterEngine.sprite_live, SpatterEngine.context);
		_sprite[0].X = 0.15f;
		_sprite[0].Y = 0.96f;
		_sprite[0].setScale(0.05f,  0.05f);
		_sprite[1].X = 0.20f;
		_sprite[1].Y = 0.96f;
		_sprite[1].setScale(0.05f,  0.05f);
		_sprite[2].X = 0.25f;
		_sprite[2].Y = 0.96f;
		_sprite[2].setScale(0.05f,  0.05f);		
	}
	
	private void CheckCollision()
	{
		// collision enemies with weapon
		ArrayList<WeaponFire> fire = _player.GetFiredWeapon(); // 
		ArrayList<Enemy> enemies = _enemyLayer.GetActive();
		CheckWeapon(fire, enemies);
		// collision enemies width player
		// xxxx
	}
	
	// check collision enemies and weapon
	private void CheckWeapon(ArrayList<WeaponFire> fire, ArrayList<Enemy> enemies)
	{
		for(int i =0; i < enemies.size(); i++)
		{
			//
			Enemy en = enemies.get(i);
			for(int f = 0; f < fire.size();f++)
			{
			  WeaponFire wf = fire.get(f);
			}
		}
	}
}
