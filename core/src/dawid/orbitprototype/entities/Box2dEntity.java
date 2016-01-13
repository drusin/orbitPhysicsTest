package dawid.orbitprototype.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static dawid.orbitprototype.MyGdxGame.scaleDown;

public abstract class Box2dEntity extends Entity {

	private final World world;
	private final BodyDef.BodyType bodyType;

	protected Fixture fixture;


	public Box2dEntity (World world, BodyDef.BodyType bodyType, float x, float y, float radius, short categoryBits, short maskBits) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(scaleDown(x), scaleDown(y));
		this.bodyType = bodyType;
		bodyDef.type = bodyType;
		this.world = world;
		Body body = world.createBody(bodyDef);

		createFixture(scaleDown(radius), categoryBits, maskBits, body);
	}

	public void resize(float f) {
		float newRadius = fixture.getShape().getRadius() + scaleDown(f);
		Vector2 position = fixture.getBody().getPosition();
		world.destroyBody(fixture.getBody());
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(position);
		bodyDef.type = bodyType;
		Body body = world.createBody(bodyDef);

		CircleShape shape = new CircleShape();
		shape.setRadius(newRadius);

		createFixture(newRadius, fixture.getFilterData().categoryBits, fixture.getFilterData().maskBits, body);
	}

	private void createFixture(float radius, short categoryBits, short maskBits, Body body) {
		FixtureDef fixtureDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);

		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;

		fixtureDef.filter.categoryBits = categoryBits;
		fixtureDef.filter.maskBits = maskBits;

		fixture = body.createFixture(fixtureDef);
	}
}
