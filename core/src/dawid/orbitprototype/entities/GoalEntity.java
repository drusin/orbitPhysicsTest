package dawid.orbitprototype.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.components.GoalComponent;

import static dawid.orbitprototype.util.CollisionBits.BODY_BIT;
import static dawid.orbitprototype.util.CollisionBits.GOAL_BIT;

public class GoalEntity extends Box2dEntity {

	private final GoalComponent goalComponent;

	public GoalEntity(World world, float x, float y, float radius, float maxTimeBetween, float reduceScale) {
		super(world, BodyDef.BodyType.StaticBody, x, y, radius, GOAL_BIT, BODY_BIT);
		goalComponent = new GoalComponent(fixture, maxTimeBetween, reduceScale);
		add(goalComponent);
		fixture.setUserData(this);
	}

	public void bodyArrived() {
		goalComponent.newBodies ++;
	}
}
