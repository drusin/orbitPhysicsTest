package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import dawid.orbitprototype.components.LifespanComponent;
import dawid.orbitprototype.components.ParticleComponent;

public class RemoveParticlesSystem extends IteratingSystem {

	public RemoveParticlesSystem() {
		super(Family.all(LifespanComponent.class, ParticleComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		LifespanComponent lifespanComponent = entity.getComponent(LifespanComponent.class);
		if (lifespanComponent.currentTime > lifespanComponent.lifespan) {
			entity.getComponent(ParticleComponent.class).particle.free();
		}
	}
}
