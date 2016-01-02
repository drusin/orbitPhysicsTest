package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.components.DestroyableComponent;

public class DestroySystem extends IteratingSystem {
	private final Engine engine;
	private final World world;

	public DestroySystem(Engine engine, World world) {
		super(Family.all(DestroyableComponent.class).get());
		this.engine = engine;
		this.world = world;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		DestroyableComponent destroyable = entity.getComponent(DestroyableComponent.class);
		if (destroyable.destroy) {
			world.destroyBody(destroyable.fixture.getBody());
			engine.removeEntity(entity);
		}
	}
}
