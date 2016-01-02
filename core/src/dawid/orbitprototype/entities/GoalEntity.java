package dawid.orbitprototype.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import static dawid.orbitprototype.util.CollisionBits.BODY_BIT;
import static dawid.orbitprototype.util.CollisionBits.GOAL_BIT;

public class GoalEntity extends Box2dEntity {

	public GoalEntity(World world, float x, float y, float radius) {
		super(world, BodyDef.BodyType.StaticBody, x, y, radius, GOAL_BIT, BODY_BIT);
		fixture.getBody().setUserData(this);
	}
}
