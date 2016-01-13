package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParticleComponent implements Component {
	public final ParticleEffectPool.PooledEffect particle;
}
