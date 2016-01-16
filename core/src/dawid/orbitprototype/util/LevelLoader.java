package dawid.orbitprototype.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.components.GoalComponent;
import dawid.orbitprototype.components.PlanetComponent;
import dawid.orbitprototype.components.SpawnerComponent;

import java.util.function.Consumer;

public class LevelLoader {

	private final static TmxMapLoader mapLoader = new TmxMapLoader();

	private final static Converter<Float> FLOATER = object -> Float.valueOf((String) object);
	private final static Converter<Integer> INTEGERER = object -> Integer.valueOf((String) object);

	public static void loadMap(String name, Engine engine, World world, EntityFactory entityFactory) {
		TiledMap map = mapLoader.load(name);

		createSpawners(engine, map, entityFactory);
		createPlanets(engine, world, map, entityFactory);
		createGoals(engine, world, map, entityFactory);
	}

	private static void createSpawners(Engine engine, TiledMap map, EntityFactory entityFactory) {
		for (RectangleMapObject object : map.getLayers().get(TiledConstants.SPAWNER_LAYER).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = object.getRectangle();
			MapProperties properties = object.getProperties();
			float x = rectangle.getX() + rectangle.getWidth() / 2;
			float y = rectangle.getY() + rectangle.getHeight() / 2;
			Entity spawnerEntity = entityFactory.createSpawnerEntity(engine, new Vector2(x, y));
			SpawnerComponent spawnerComponent = spawnerEntity.getComponent(SpawnerComponent.class);
			setCustomProperty(properties, "timeToSpawn", FLOATER, spawnerComponent::setTimeToSpawn);
			setCustomProperty(properties, "minLifespan", FLOATER, spawnerComponent::setMinLifespan);
			setCustomProperty(properties, "lifespanVar", FLOATER, spawnerComponent::setLifespanVar);
			setCustomProperty(properties, "spread", FLOATER, spawnerComponent::setSpread);
			setCustomVectorProperty(properties, "vx", "vy", spawnerComponent::setSpawnVelocity);
		}
	}

	private static void createPlanets(Engine engine, World world, TiledMap map, EntityFactory entityFactory) {
		for (EllipseMapObject object : map.getLayers().get(TiledConstants.PLANET_LAYER).getObjects().getByType(EllipseMapObject.class)) {
			Ellipse ellipse = object.getEllipse();
			MapProperties properties = object.getProperties();
			float x = ellipse.x + ellipse.width / 2;
			float y = ellipse.y + ellipse.height / 2;
			float radius = (ellipse.width + ellipse.height) / 4;
			Entity planetEntity = entityFactory.createPlanetEntity(engine, world, x, y, radius);
			PlanetComponent planetComponent = planetEntity.getComponent(PlanetComponent.class);
			setCustomProperty(properties, "size", INTEGERER, planetComponent::setSize);
			setCustomProperty(properties, "maxSize", INTEGERER, planetComponent::setMaxSize);
			setCustomProperty(properties, "minSize", INTEGERER, planetComponent::setMinSize);
		}
	}

	private static void createGoals(Engine engine, World world, TiledMap map, EntityFactory entityFactory) {
		for (EllipseMapObject object : map.getLayers().get(TiledConstants.GOAL_LAYER).getObjects().getByType(EllipseMapObject.class)) {
			Ellipse ellipse = object.getEllipse();
			MapProperties properties = object.getProperties();
			float x = ellipse.x + ellipse.width / 2;
			float y = ellipse.y + ellipse.height / 2;
			float radius = (ellipse.width + ellipse.height) / 4;
			Entity goalEntity = entityFactory.createGoalEntity(engine, world, x, y, radius);
			GoalComponent goalComponent = goalEntity.getComponent(GoalComponent.class);
			setCustomProperty(properties, "maxTimeBetween", FLOATER, goalComponent::setMaxTimeBetween);
			setCustomProperty(properties, "reduceScale", FLOATER, goalComponent::setReduceScale);
		}
	}

	private static <T> void setCustomProperty(MapProperties properties, String name, Converter<T> converter, Consumer<T> setter) {
		if (!properties.containsKey(name)) {
			return;
		}
		setter.accept(converter.convert(properties.get(name)));
	}

	private static void setCustomVectorProperty(MapProperties properties, String nameX, String nameY, Consumer<Vector2> setter) {
		float x = properties.containsKey(nameX) ? FLOATER.convert(properties.get(nameX)) : 0;
		float y = properties.containsKey(nameY) ? FLOATER.convert(properties.get(nameY)) : 0;
		setter.accept(new Vector2(x, y));
	}

	@FunctionalInterface
	private interface Converter<T> {
		T convert(Object object);
	}
}
