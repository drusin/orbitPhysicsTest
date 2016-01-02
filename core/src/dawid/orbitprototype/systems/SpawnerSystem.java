package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.MyGdxGame;
import dawid.orbitprototype.components.SpawnerComponent;
import dawid.orbitprototype.entities.BodyEntity;

public class SpawnerSystem extends IteratingSystem {

	private final Engine engine;
	private final World world;

	public SpawnerSystem(Engine engine, World world) {
		super(Family.all(SpawnerComponent.class).get());
		this.engine = engine;
		this.world = world;
	}
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		SpawnerComponent component = entity.getComponent(SpawnerComponent.class);
		component.timer += deltaTime;

		if (component.timer > component.TIME_TO_SPAWN) {
			component.timer = 0;
			float offset = (MyGdxGame.random.nextInt(200) - 100) / 10f;
			BodyEntity bodyEntity = new BodyEntity(world, component.spawnLocation.x + offset, component.spawnLocation.y + offset);
			engine.addEntity(bodyEntity);
			bodyEntity.setLinearVelocity(component.spawnVelocity);
		}
	}
}
