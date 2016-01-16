package dawid.orbitprototype.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import dawid.orbitprototype.MyGdxGame;
import dawid.orbitprototype.components.*;

import static dawid.orbitprototype.util.CollisionBits.*;

public class EntityFactory {

	private final Array<Texture> planetTextures;
	private final Texture dustTexture = new Texture("graphics/dust.png");

	public EntityFactory() {
		planetTextures = new Array<>();
		planetTextures.add(new Texture("graphics/planets/mars.png"));
		planetTextures.add(new Texture("graphics/planets/neptune.png"));
	}

	public Entity createPlanetEntity(Engine engine, World world, float x, float y, float radius) {
		Entity entity = new Entity();
		Fixture fixture = FixtureCreator.createFixture(world, BodyDef.BodyType.StaticBody, x, y, radius, PLANET_BIT, BODY_BIT);
		Box2dFixtureComponent box2dFixtureComponent = new Box2dFixtureComponent();
		box2dFixtureComponent.fixture = fixture;
		entity.add(box2dFixtureComponent);
		PlanetComponent planetComponent = new PlanetComponent();
		planetComponent.texture = planetTextures.get(MyGdxGame.random.nextInt(planetTextures.size));
		entity.add(planetComponent);
		engine.addEntity(entity);
		return entity;
	}

	public Entity createDustEntity(Engine engine, World world, float x, float y, float minLifespan, float lifespanVar, ParticleEffectPool.PooledEffect particle) {
		Entity entity = new Entity();
		Fixture fixture = FixtureCreator.createFixture(world, BodyDef.BodyType.DynamicBody, x, y, 1, BODY_BIT, (short) (PLANET_BIT | GOAL_BIT));
		Box2dFixtureComponent box2dFixtureComponent = new Box2dFixtureComponent();
		box2dFixtureComponent.fixture = fixture;
		entity.add(box2dFixtureComponent);
		entity.add(new DynamicComponent());
		LifespanComponent lifespanComponent = new LifespanComponent();
		lifespanComponent.lifespan = MyGdxGame.random.nextFloat() * lifespanVar + minLifespan;
		entity.add(lifespanComponent);
		DestroyableComponent destroyableComponent = new DestroyableComponent();
		entity.add(destroyableComponent);
		fixture.setUserData(destroyableComponent);
		ParticleComponent particleComponent = new ParticleComponent();
		particleComponent.particle = particle;
		entity.add(particleComponent);
		TextureComponent textureComponent = new TextureComponent();
		textureComponent.texture = dustTexture;
		entity.add(textureComponent);
		engine.addEntity(entity);
		return entity;
	}

	public Entity createGoalEntity(Engine engine, World world, float x, float y, float radius) {
		Entity entity = new Entity();
		Fixture fixture = FixtureCreator.createFixture(world, BodyDef.BodyType.StaticBody, x, y, radius, GOAL_BIT, BODY_BIT);
		Box2dFixtureComponent box2dFixtureComponent = new Box2dFixtureComponent();
		box2dFixtureComponent.fixture = fixture;
		entity.add(box2dFixtureComponent);
		GoalComponent goalComponent = new GoalComponent();
		fixture.setUserData(goalComponent);
		entity.add(goalComponent);
		engine.addEntity(entity);
		return entity;
	}

	public Entity createSpawnerEntity(Engine engine, Vector2 spawnLocation) {
		Entity entity = new Entity();
		SpawnerComponent spawnerComponent = new SpawnerComponent();
		spawnerComponent.spawnLocation.set(spawnLocation.x, spawnLocation.y);
		entity.add(spawnerComponent);
		engine.addEntity(entity);
		return entity;
	}
}
