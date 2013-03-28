package cz.skylights.spitt.particle;

import java.util.ArrayList;

public interface IParticleFactory {
	public ArrayList<ParticleObject> createParticles(int count);
}
