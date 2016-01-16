package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.MyGdxGame;
import dawid.orbitprototype.components.Box2dFixtureComponent;
import dawid.orbitprototype.components.SpawnerComponent;
import dawid.orbitprototype.util.EntityFactory;
import dawid.orbitprototype.util.ParticleProvider;

import static dawid.orbitprototype.MyGdxGame.scaleDown;

public class SpawnerSystem extends IteratingSystem {

	private final Engine engine;
	private final World world;
	private final ParticleProvider particleProvider;
	private final ComponentMapper<SpawnerComponent> spawnerMapper;
	private final ComponentMapper<Box2dFixtureComponent> fixtureMapper;

	public SpawnerSystem(Engine engine, World world, ParticleProvider particleProvider) {
		super(Family.all(SpawnerComponent.class).get());
		this.engine = engine;
		this.world = world;
		this.particleProvider = particleProvider;
		spawnerMapper = ComponentMapper.getFor(SpawnerComponent.class);
		fixtureMapper = ComponentMapper.getFor(Box2dFixtureComponent.class);
	}
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		SpawnerComponent component = spawnerMapper.get(entity);
		component.timer += deltaTime;

		if (component.timer > component.timeToSpawn) {
			int v = (int) Math.floor(component.timer / component.timeToSpawn);
			component.timer -= v * component.timeToSpawn;
			for (int i = 0; i < v; i++) {
				float offsetX = (MyGdxGame.random.nextFloat() * component.spread * 2 - component.spread);
				float offsetY = (MyGdxGame.random.nextFloat() * component.spread * 2 - component.spread);
				Entity dustEntity = EntityFactory.createDustEntity(engine, world, component.spawnLocation.x + offsetX, component.spawnLocation.y + offsetY, component.minLifespan, component.lifespanVar, particleProvider.obtainParticle());
				fixtureMapper.get(dustEntity).fixture.getBody().setLinearVelocity(scaleDown(component.spawnVelocity.x), scaleDown(component.spawnVelocity.y));
			}
		}
	}
}
