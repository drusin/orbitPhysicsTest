package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.components.Box2dFixtureComponent;
import dawid.orbitprototype.components.DestroyableComponent;

public class DestroySystem extends IteratingSystem {
	private final PooledEngine engine;
	private final World world;
	private final ComponentMapper<DestroyableComponent> destroyableMapper;
	private final ComponentMapper<Box2dFixtureComponent> fixtureMapper;

	public DestroySystem(PooledEngine engine, World world) {
		super(Family.all(DestroyableComponent.class, Box2dFixtureComponent.class).get());
		this.engine = engine;
		this.world = world;
		destroyableMapper = ComponentMapper.getFor(DestroyableComponent.class);
		fixtureMapper = ComponentMapper.getFor(Box2dFixtureComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		DestroyableComponent destroyable = destroyableMapper.get(entity);
		if (destroyable.destroy) {
			Box2dFixtureComponent fixtureComponent = fixtureMapper.get(entity);
			world.destroyBody(fixtureComponent.fixture.getBody());
			engine.removeEntity(entity);
		}
	}
}
