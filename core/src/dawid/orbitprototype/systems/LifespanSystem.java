package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import dawid.orbitprototype.components.DestroyableComponent;
import dawid.orbitprototype.components.LifespanComponent;

public class LifespanSystem extends IteratingSystem {

	public LifespanSystem() {
		super(Family.all(DestroyableComponent.class, LifespanComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		LifespanComponent lifespanComponent = entity.getComponent(LifespanComponent.class);
		lifespanComponent.currentTime += deltaTime;
		if (lifespanComponent.currentTime > lifespanComponent.lifespan) {
			entity.getComponent(DestroyableComponent.class).destroy = true;
		}
	}
}
