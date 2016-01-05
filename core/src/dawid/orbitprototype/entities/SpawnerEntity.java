package dawid.orbitprototype.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import dawid.orbitprototype.components.SpawnerComponent;

public class SpawnerEntity extends Entity {

	public SpawnerEntity(float timeToSpawn, Vector2 spawnLocation, Vector2 spawnVelocity, float minLifespan, float lifespanVar) {
		SpawnerComponent spawnerComponent = new SpawnerComponent(timeToSpawn, spawnLocation, spawnVelocity, minLifespan, lifespanVar);
		add(spawnerComponent);
	}
}
