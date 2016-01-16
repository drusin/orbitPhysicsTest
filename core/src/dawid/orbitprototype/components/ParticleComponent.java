package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import lombok.Setter;

@Setter
public class ParticleComponent implements Component {
	public ParticleEffectPool.PooledEffect particle = null;
}
