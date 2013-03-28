package cz.skylights.spitt.particle;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;


import cz.skylights.spitt.Enemy;
import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.TextureManager;
import cz.skylights.spitt.interfaces.ITrajectory;

// ParticleSystem 2013
// Michal Svoboda
public class ParticleSystem {
	// pole castic
	private ArrayList<ParticleObject> _particles = new ArrayList<ParticleObject>();
	// textury pro particle 
	IParticleFactory _factory;
    int _count;
	
	private Random generator=new Random(); // generator nahodnych cisel
	
	public ParticleSystem(int cnt, IParticleFactory factory)
	{
		_count = cnt;
		_factory = factory;
		//_textures.AddTexture(SpatterEngine.particle);				
	}
		
	public void createParticles()
	{
		_particles = _factory.createParticles(_count);
		/*
		//
		// vytvor a inizializuj particles
		
		// x,y,z
		// barva
		// alfa ... kdy umre
		// gravitace ???
		// smer atd.
				
		for(int i = 0; i < _count; i++)
		{
			float sx, sy;						
			sx = 0.5f;							
			sy = 0.5f;
			float spX = 0.001f+generator.nextFloat()/1000;
			if (i%2 == 0)
				spX *=-1;
			else if(i%3 == 0)
				spX = 0;
			
			float spY = 0.01f-generator.nextFloat()/100;
						
			ParticleShape p = new ParticleShape(sx,sy,spX, spY);
			p.setColor(1.0f, 1.0f, 0.0f, 1.0f); 
			p.setScale(0.015f, 0.015f);			
			//p.setTexture(_textures.GetTexture(SpatterEngine.particle));		  
			p.StartTime = i*50;
			_particles.add(p);			
		}*/
	}
	
	public void move()
	{
		for (int i =0; i < _particles.size(); i++)
		{
			ParticleObject p = _particles.get(i);		
		    p.move();
		}		
	}
	
	public void draw(GL10 gl)
	{
		for (int i =0; i < _particles.size(); i++)
		{
			ParticleObject p = _particles.get(i);		
		    p.draw(gl);
		}		
	}
	
	
	// inicializace textur
	/*
	public void loadTextures(GL10 gl, Context context)
	{
		_textures.buildTextures(gl, context);
	}*/
	

	
}
