package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import lombok.Setter;

@Setter
public class SpawnerComponent implements Component {
	public float timeToSpawn = 0.1f;
	public Vector2 spawnLocation = new Vector2();
	public Vector2 spawnVelocity = new Vector2();
	public float minLifespan = 5f;
	public float lifespanVar = 20f;
	public float spread = 10f;
	public float timer = 0;
}
