package dawid.orbitprototype.util;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

@FunctionalInterface
public interface ParticleProvider {
	ParticleEffectPool.PooledEffect obtainParticle();
}
