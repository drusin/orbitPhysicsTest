package dawid.orbitprototype.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class LevelLoader {

	private final static TmxMapLoader mapLoader = new TmxMapLoader();

	public static void loadMap(String name, Engine engine, World world) {
		TiledMap map = mapLoader.load(name);

		createSpawners(engine, map);
		createPlanets(engine, world, map);
		createGoals(engine, world, map);
	}

	private static void createSpawners(Engine engine, TiledMap map) {
		for (RectangleMapObject object : map.getLayers().get(TiledConstants.SPAWNER_LAYER).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = object.getRectangle();
			MapProperties properties = object.getProperties();
			float timeToSpawn = getFloatProperty(0.1f, properties, "timeToSpawn");
			float x = rectangle.getX() + rectangle.getWidth() / 2;
			float y = rectangle.getY() + rectangle.getHeight() / 2;
			float vx = getFloatProperty(0f, properties, "vx");
			float vy = getFloatProperty(0f, properties, "vy");
			float minLifespan = getFloatProperty(5f, properties, "minLifespan");
			float lifespanVar = getFloatProperty(20f, properties, "lifespanVar");
			float spread = getFloatProperty(10, properties, "spread");
			EntityFactory.createSpawnerEntity(engine, timeToSpawn, new Vector2(x, y), new Vector2(vx, vy), minLifespan, lifespanVar, spread);
		}
	}

	private static void createPlanets(Engine engine, World world, TiledMap map) {
		for (EllipseMapObject object : map.getLayers().get(TiledConstants.PLANET_LAYER).getObjects().getByType(EllipseMapObject.class)) {
			Ellipse ellipse = object.getEllipse();
			MapProperties properties = object.getProperties();
			float x = ellipse.x + ellipse.width / 2;
			float y = ellipse.y + ellipse.height / 2;
			float radius = (ellipse.width + ellipse.height) / 4;
			int size = getIntProperty(0, properties, "size");
			int maxSize = getIntProperty(999, properties, "maxSize");
			int minSize = getIntProperty(-999, properties, "minSize");
			EntityFactory.createPlanetEntity(engine, world, x, y, radius, size, maxSize, minSize);
		}
	}

	private static void createGoals(Engine engine, World world, TiledMap map) {
		for (EllipseMapObject object : map.getLayers().get(TiledConstants.GOAL_LAYER).getObjects().getByType(EllipseMapObject.class)) {
			Ellipse ellipse = object.getEllipse();
			MapProperties properties = object.getProperties();
			float maxTimeBetween = getFloatProperty(0.3f, properties, "maxTimeBetween");
			float reduceScale = getFloatProperty(10f, properties, "reduceScale");
			float x = ellipse.x + ellipse.width / 2;
			float y = ellipse.y + ellipse.height / 2;
			float radius = (ellipse.width + ellipse.height) / 4;
			EntityFactory.createGoalEntity(engine, world, x, y, radius, maxTimeBetween, reduceScale);
		}
	}

	private static float getFloatProperty(float defaultValue, MapProperties properties, String property) {
		return properties.containsKey(property)
				? Float.valueOf((String) properties.get(property))
				: defaultValue;
	}

	private static int getIntProperty(int defaultValue, MapProperties properties, String property) {
		return properties.containsKey(property)
				? Integer.valueOf((String) properties.get(property))
				: defaultValue;
	}
}
