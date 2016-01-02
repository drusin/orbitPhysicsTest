package dawid.orbitprototype.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
			float x = rectangle.getX() + rectangle.getWidth() / 2;
			float y = rectangle.getY() + rectangle.getHeight() / 2;
			int vx = Integer.valueOf((String)object.getProperties().get("vx"));
			int vy = Integer.valueOf((String)object.getProperties().get("vy"));
			engine.addEntity(new SpawnerEntity(new Vector2(x, y), new Vector2(scaleDown(vx), scaleDown(vy))));
		}

		for (RectangleMapObject object : map.getLayers().get(TiledConstants.PLANET_LAYER).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = object.getRectangle();
			float x = rectangle.getX() + rectangle.getWidth() / 2;
			float y = rectangle.getY() + rectangle.getHeight() / 2;
			float radius = (rectangle.getWidth() + rectangle.getHeight()) / 4;
			engine.addEntity(new PlanetEntity(world, x, y, radius));
		}

		for (RectangleMapObject object : map.getLayers().get(TiledConstants.GOAL_LAYER).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = object.getRectangle();
			float x = rectangle.getX() + rectangle.getWidth() / 2;
			float y = rectangle.getY() + rectangle.getHeight() / 2;
			float radius = (rectangle.getWidth() + rectangle.getHeight()) / 4;
			engine.addEntity(new GoalEntity(world, x, y, radius));
		}
	}
}
