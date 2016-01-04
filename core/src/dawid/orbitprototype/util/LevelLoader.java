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
import dawid.orbitprototype.entities.GoalEntity;
import dawid.orbitprototype.entities.PlanetEntity;
import dawid.orbitprototype.entities.SpawnerEntity;

import static dawid.orbitprototype.MyGdxGame.scaleDown;

public class LevelLoader {

	private final static TmxMapLoader mapLoader = new TmxMapLoader();

	public static void loadMap(String name, Engine engine, World world) {
		TiledMap map = mapLoader.load(name);

		for (RectangleMapObject object : map.getLayers().get(TiledConstants.SPAWNER_LAYER).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = object.getRectangle();
			MapProperties properties = object.getProperties();
			float timeToSpawn = properties.containsKey("timeToSpawn")
					? Float.valueOf((String) properties.get("timeToSpawn"))
					: 0.1f;
			float x = rectangle.getX() + rectangle.getWidth() / 2;
			float y = rectangle.getY() + rectangle.getHeight() / 2;
			float vx = properties.containsKey("vx")
					? Float.valueOf((String) properties.get("vx"))
					: 0f;
			float vy = properties.containsKey("vy")
					? Float.valueOf((String) properties.get("vy"))
					: 0;
			engine.addEntity(new SpawnerEntity(timeToSpawn, new Vector2(x, y), new Vector2(scaleDown(vx), scaleDown(vy))));
		}

		for (EllipseMapObject object : map.getLayers().get(TiledConstants.PLANET_LAYER).getObjects().getByType(EllipseMapObject.class)) {
			Ellipse ellipse = object.getEllipse();
			float x = ellipse.x + ellipse.width / 2;
			float y = ellipse.y + ellipse.height / 2;
			float radius = (ellipse.width + ellipse.height) / 4;
			engine.addEntity(new PlanetEntity(world, x, y, radius));
		}

		for (EllipseMapObject object : map.getLayers().get(TiledConstants.GOAL_LAYER).getObjects().getByType(EllipseMapObject.class)) {
			Ellipse ellipse = object.getEllipse();
			float x = ellipse.x + ellipse.width / 2;
			float y = ellipse.y + ellipse.height / 2;
			float radius = (ellipse.width + ellipse.height) / 4;
			engine.addEntity(new GoalEntity(world, x, y, radius));
		}
	}
}
