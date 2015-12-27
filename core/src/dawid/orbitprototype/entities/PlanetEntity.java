package dawid.orbitprototype.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.components.PlanetComponent;

public class PlanetEntity extends Box2dEntity {

	public PlanetEntity(Engine engine, World world, float x, float y, float radius) {
		super(world, BodyDef.BodyType.StaticBody, x, y, radius);
		PlanetComponent planet = new PlanetComponent(fixture);
		add(planet);
		engine.addEntity(this);
	}
}
