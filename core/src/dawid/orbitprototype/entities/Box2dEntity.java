package dawid.orbitprototype.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import dawid.orbitprototype.MyGdxGame;

public abstract class Box2dEntity extends Entity {

	protected final Fixture fixture;

	public Box2dEntity (World world, BodyDef.BodyType bodyType, float x, float y, float radius) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(MyGdxGame.scaleDown(x), MyGdxGame.scaleDown(y));
		bodyDef.type = bodyType;
		Body body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(MyGdxGame.scaleDown(radius));

		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;
		fixture = body.createFixture(fixtureDef);
	}
}
