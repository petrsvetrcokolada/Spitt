package cz.skylights.spitt.particle;

import java.util.ArrayList;
import java.util.Random;

public class ParticleShapeCreator implements IParticleFactory {

	private Random generator=new Random(); // generator nahodnych cisel
	
	public ArrayList<ParticleObject> createParticles(int count) {
		//
		// vytvor a inizializuj particles
		ArrayList<ParticleObject> particles = new ArrayList<ParticleObject>();
		// x,y,z
		// barva
		// alfa ... kdy umre
		// gravitace ???
		// smer atd.
				
		for(int i = 0; i < count; i++)
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
			particles.add(p);			
		}

		return particles;
	}

}
