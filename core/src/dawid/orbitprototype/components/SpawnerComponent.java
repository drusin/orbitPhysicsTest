package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpawnerComponent implements Component {

	public final float timeToSpawn;
	public final Vector2 spawnLocation;
	public final Vector2 spawnVelocity;
	public final float minLifespan;
	public final float lifespanVar;
	public final float spread;

	public float timer = 0;
}
