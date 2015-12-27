package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.components.DynamicComponent;
import dawid.orbitprototype.components.LifespanComponent;

public class LifespanSystem extends IteratingSystem {

	private final Engine engine;
	private final World world;

	public LifespanSystem(Engine engine, World world) {
		super(Family.all(DynamicComponent.class, LifespanComponent.class).get());
		this.engine = engine;
		this.world = world;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		LifespanComponent lifespanComponent = entity.getComponent(LifespanComponent.class);
		lifespanComponent.currentTime += deltaTime;
		if (lifespanComponent.currentTime > lifespanComponent.lifespan) {
			world.destroyBody(entity.getComponent(DynamicComponent.class).fixture.getBody());
			engine.removeEntity(entity);
		}
	}
}
