package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class SpawnerComponent implements Component, Pool.Poolable {
	public float timeToSpawn = 0.1f;
	public Vector2 spawnLocation = new Vector2();
	public Vector2 spawnVelocity = new Vector2();
	public float minLifespan = 5f;
	public float lifespanVar = 20f;
	public float spread = 10f;
	public float timer = 0;

	@Override
	public void reset() {
		timeToSpawn = 0.1f;
		spawnLocation.set(0, 0);
		spawnVelocity.set(0, 0);
		minLifespan = 5f;
		lifespanVar = 20f;
		spread = 10f;
		timer = 0;
	}
}
