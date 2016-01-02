package dawid.orbitprototype.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import dawid.orbitprototype.components.SpawnerComponent;

public class SpawnerEntity extends Entity {

	public SpawnerEntity(Vector2 spawnLocation, Vector2 spawnVelocity) {
		SpawnerComponent spawnerComponent = new SpawnerComponent(spawnLocation, spawnVelocity);
		add(spawnerComponent);
	}
}
