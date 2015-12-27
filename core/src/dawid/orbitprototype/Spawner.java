package dawid.orbitprototype;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.entities.BodyEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Spawner {

	private final float TIME_TO_SPAWN = 1;

	private final Engine engine;
	private final World world;

	private float timer = 0;

	public void update(float delta) {
		timer += delta;
		if (timer > TIME_TO_SPAWN) {
			timer = 0;
			new BodyEntity(engine, world, 50, 650);
		}
	}
}
