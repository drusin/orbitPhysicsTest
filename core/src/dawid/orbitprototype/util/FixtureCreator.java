package dawid.orbitprototype.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import dawid.orbitprototype.components.Box2dFixtureComponent;

import static dawid.orbitprototype.MyGdxGame.scaleDown;
import static dawid.orbitprototype.MyGdxGame.scaleUp;

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

	public static void resize(Box2dFixtureComponent fixtureComponent, float f, World world) {
		float newRadius = scaleUp(fixtureComponent.fixture.getShape().getRadius()) + f;
		Vector2 position = fixtureComponent.fixture.getBody().getPosition();
		BodyDef.BodyType bodyType = fixtureComponent.fixture.getBody().getType();
		short categoryBits = fixtureComponent.fixture.getFilterData().categoryBits;
		short maskBits = fixtureComponent.fixture.getFilterData().maskBits;
		world.destroyBody(fixtureComponent.fixture.getBody());

		fixtureComponent.fixture = createFixture(world, bodyType, scaleUp(position.x), scaleUp(position.y), newRadius, categoryBits, maskBits);
	}
}
