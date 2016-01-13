package dawid.orbitprototype.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.MyGdxGame;
import dawid.orbitprototype.components.*;

import static dawid.orbitprototype.util.CollisionBits.*;

public class EntityFactory {

	public static Entity createPlanetEntity(PooledEngine engine, World world, float x, float y, float radius, int size, int maxSize, int minSize) {
		Entity entity = engine.createEntity();
		Fixture fixture = FixtureCreator.createFixture(world, BodyDef.BodyType.StaticBody, x, y, radius, PLANET_BIT, BODY_BIT);
		Texture texture = new Texture("graphics/planets/mars.png");
		Box2dFixtureComponent box2dFixtureComponent = engine.createComponent(Box2dFixtureComponent.class);
		box2dFixtureComponent.fixture = fixture;
		entity.add(box2dFixtureComponent);
		PlanetComponent planetComponent = engine.createComponent(PlanetComponent.class);
		planetComponent.texture = texture;
		planetComponent.size = size;
		planetComponent.maxSize = maxSize;
		planetComponent.minSize = minSize;
		entity.add(planetComponent);
		engine.addEntity(entity);
		return entity;
	}

	public static Entity createDustEntity(PooledEngine engine, World world, float x, float y, float minLifespan, float lifespanVar, ParticleEffectPool.PooledEffect particle) {
		Entity entity = engine.createEntity();
		Fixture fixture = FixtureCreator.createFixture(world, BodyDef.BodyType.DynamicBody, x, y, 1, BODY_BIT, (short) (PLANET_BIT | GOAL_BIT));
		Box2dFixtureComponent box2dFixtureComponent = engine.createComponent(Box2dFixtureComponent.class);
		box2dFixtureComponent.fixture = fixture;
		entity.add(box2dFixtureComponent);
		entity.add(engine.createComponent(DynamicComponent.class));
		LifespanComponent lifespanComponent = engine.createComponent(LifespanComponent.class);
		lifespanComponent.lifespan = MyGdxGame.random.nextFloat() * lifespanVar + minLifespan;
		entity.add(lifespanComponent);
		DestroyableComponent destroyableComponent = engine.createComponent(DestroyableComponent.class);
		entity.add(destroyableComponent);
		fixture.setUserData(destroyableComponent);
		ParticleComponent particleComponent = engine.createComponent(ParticleComponent.class);
		particleComponent.particle = particle;
		entity.add(particleComponent);
		engine.addEntity(entity);
		return entity;
	}

	public static Entity createGoalEntity(PooledEngine engine, World world, float x, float y, float radius, float maxTimeBetween, float reduceScale) {
		Entity entity = engine.createEntity();
		Fixture fixture = FixtureCreator.createFixture(world, BodyDef.BodyType.StaticBody, x, y, radius, GOAL_BIT, BODY_BIT);
		Box2dFixtureComponent box2dFixtureComponent = engine.createComponent(Box2dFixtureComponent.class);
		box2dFixtureComponent.fixture = fixture;
		entity.add(box2dFixtureComponent);
		GoalComponent goalComponent = engine.createComponent(GoalComponent.class);
		goalComponent.maxTimeBetween = maxTimeBetween;
		goalComponent.reduceScale = reduceScale;
		fixture.setUserData(goalComponent);
		entity.add(goalComponent);
		engine.addEntity(entity);
		return entity;
	}

	public static Entity createSpawnerEntity(PooledEngine engine, float timeToSpawn, Vector2 spawnLocation, Vector2 spawnVelocity, float minLifespan, float lifespanVar, float spread) {
		Entity entity = engine.createEntity();
		SpawnerComponent spawnerComponent = engine.createComponent(SpawnerComponent.class);
		spawnerComponent.timeToSpawn = timeToSpawn;
		spawnerComponent.spawnLocation.set(spawnLocation.x, spawnLocation.y);
		spawnerComponent.spawnVelocity.set(spawnVelocity.x, spawnVelocity.y);
		spawnerComponent.minLifespan = minLifespan;
		spawnerComponent.lifespanVar = lifespanVar;
		spawnerComponent.spread = spread;
		entity.add(spawnerComponent);
		engine.addEntity(entity);
		return entity;
	}
}
