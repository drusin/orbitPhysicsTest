package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Pool;

public class ParticleComponent implements Component, Pool.Poolable {
	public ParticleEffectPool.PooledEffect particle = null;

	@Override
	public void reset() {
		particle = null;
	}
}
