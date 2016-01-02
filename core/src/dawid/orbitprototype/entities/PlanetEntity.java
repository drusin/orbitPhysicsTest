package dawid.orbitprototype.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.components.PlanetComponent;

import static dawid.orbitprototype.util.CollisionBits.BODY_BIT;
import static dawid.orbitprototype.util.CollisionBits.PLANET_BIT;

public class PlanetEntity extends Box2dEntity {

	public PlanetEntity(World world, float x, float y, float radius) {
		super(world, BodyDef.BodyType.StaticBody, x, y, radius, PLANET_BIT, BODY_BIT);
		PlanetComponent planet = new PlanetComponent(fixture);
		add(planet);
	}
}
