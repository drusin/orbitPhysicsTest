package dawid.orbitprototype.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.MyGdxGame;
import dawid.orbitprototype.components.DestroyableComponent;
import dawid.orbitprototype.components.DynamicComponent;
import dawid.orbitprototype.components.LifespanComponent;

import static dawid.orbitprototype.util.CollisionBits.*;

public class BodyEntity extends Box2dEntity {

	private final DestroyableComponent destroyableComponent;

	public BodyEntity(World world, float x, float y, float minLifespan, float lifespanVar) {
		super(world, BodyDef.BodyType.DynamicBody, x, y, 1, BODY_BIT, (short)(PLANET_BIT | GOAL_BIT));
		DynamicComponent dynamic = new DynamicComponent(fixture);
		add(dynamic);
		LifespanComponent lifespan = new LifespanComponent(MyGdxGame.random.nextFloat() * lifespanVar + minLifespan);
		add(lifespan);
		destroyableComponent = new DestroyableComponent(fixture);
		add(destroyableComponent);
		fixture.setUserData(this);
	}

	public void setLinearVelocity(Vector2 velocity) {
		fixture.getBody().setLinearVelocity(velocity);
	}

	public void arrivedAtGoal() {
		destroyableComponent.destroy = true;
	}
}
