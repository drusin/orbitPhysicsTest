package dawid.orbitprototype;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.entities.BodyEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Spawner {

	private final float TIME_TO_SPAWN = 0.1f;

	private final Vector2 spawnLocation;
	private final Vector2 spawnVelocity;
	private final Engine engine;
	private final World world;

	private float timer = 0;

	public void update(float delta) {
		timer += delta;
		if (timer > TIME_TO_SPAWN) {
			timer = 0;
			float i = (MyGdxGame.random.nextInt(200) - 100) / 10f;
			System.out.println(i);
			new BodyEntity(engine, world, spawnLocation.x + i, spawnLocation.y + i).setLinearVelocity(spawnVelocity);
		}
	}
}
