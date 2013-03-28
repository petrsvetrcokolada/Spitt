package cz.skylights.spitt.particle;

import java.util.ArrayList;
import java.util.Random;

import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.TextureManager;

public class ParticleCreator implements IParticleFactory {
	
	private Random generator=new Random(); // generator nahodnych cisel
	int _tx;
	TextureManager _textures;
	
	
	public ParticleCreator(TextureManager tm)
	{
		_textures = tm;
	}
	
	public ArrayList<ParticleObject> createParticles(int count) {
		//
		// vytvor a inizializuj particles
		ArrayList<ParticleObject> particles = new ArrayList<ParticleObject>();
		// x,y,z
		// barva
		// alfa ... kdy umre
		// gravitace ???
		// smer atd.
			
		int frame =  0;
		int start = 0;
		for(int i = 0; i < count; i++)
		{
			float sx, sy;						
			sx = 0.8f;							
			sy = 0.3f;
			float spX = 0.007f+generator.nextFloat()/1000;
			float spY = 0.007f-generator.nextFloat()/1000;
			if (frame == 0)
			{
				spX *=-1;
				spY = 0.0f;
			}
			else if (frame == 1)
			{
				spX *=-1;						
			}
			else if (frame == 2)			
			{
				spX = 0;
			}
			else if (frame == 3)
			{
			}
			else if (frame == 4)
			{
				spY = 0;
			}
			else if (frame == 5)
			{
			  spY *=-1.0f;
			}
			else if (frame == 6)
			{
				spX = 0;
				
			}
			else if (frame == 7)	
			{
				spX = 0;
				spY *=-1; 
			}
			else if (frame == 8)	
			{
				spX *= -1.0f;
				spY *=-1.0f; 
			}
			
			
		    frame++;
			if (frame > 8)
			{							
				frame = 0;
				start += 150;
			}
					
			Particle p = new Particle(sx,sy,spX, spY);
			//p.setColor(1.0f, 1.0f, 0.0f, 1.0f); 
			p.setScale(0.02f, 0.02f);			
			p.setTexture(_textures.GetTexture(SpatterEngine.star));		  
			p.StartTime = start+generator.nextInt(22);
			particles.add(p);			
		}

		return particles;
	}

}

