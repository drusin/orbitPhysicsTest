package dawid.orbitprototype.util;

import com.badlogic.gdx.physics.box2d.*;

import static dawid.orbitprototype.MyGdxGame.scaleDown;

public class FixtureCreator {

	public static Fixture createFixture(World world, BodyDef.BodyType bodyType, float x, float y, float radius, short categoryBits, short maskBits) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(scaleDown(x), scaleDown(y));
		bodyDef.type = bodyType;
		Body body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(scaleDown(radius));

		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;

		fixtureDef.filter.categoryBits = categoryBits;
		fixtureDef.filter.maskBits = maskBits;

		return body.createFixture(fixtureDef);
	}
}
