package dawid.orbitprototype.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.MyGdxGame;
import dawid.orbitprototype.components.DynamicComponent;
import dawid.orbitprototype.components.LifespanComponent;

public class BodyEntity extends Box2dEntity {

	public BodyEntity(Engine engine, World world, float x, float y) {
		super(world, BodyDef.BodyType.DynamicBody, x, y, 10, (short) 4, (short) 2);
		DynamicComponent dynamic = new DynamicComponent(fixture);
		add(dynamic);
		LifespanComponent lifespan = new LifespanComponent(MyGdxGame.random.nextInt(10) + 5);
		add(lifespan);
		engine.addEntity(this);
	}
}
