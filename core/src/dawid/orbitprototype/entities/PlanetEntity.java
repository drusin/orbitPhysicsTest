package dawid.orbitprototype.entities;

import static dawid.orbitprototype.util.CollisionBits.BODY_BIT;
import static dawid.orbitprototype.util.CollisionBits.PLANET_BIT;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import dawid.orbitprototype.components.PlanetComponent;

public class PlanetEntity extends Box2dEntity {

	public PlanetEntity(World world, float x, float y, float radius, int size, int maxSize, int minSize) {
		super(world, BodyDef.BodyType.StaticBody, x, y, radius, PLANET_BIT, BODY_BIT);
		Texture texture = new Texture("graphics/planets/mars.png");
		PlanetComponent planet = new PlanetComponent(fixture, texture, size, maxSize, minSize);
		add(planet);
	}
}
