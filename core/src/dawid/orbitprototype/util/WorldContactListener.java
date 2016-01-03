package dawid.orbitprototype.util;

import com.badlogic.gdx.physics.box2d.*;
import dawid.orbitprototype.entities.BodyEntity;
import dawid.orbitprototype.entities.GoalEntity;

import static dawid.orbitprototype.util.CollisionBits.BODY_BIT;
import static dawid.orbitprototype.util.CollisionBits.GOAL_BIT;

public class WorldContactListener implements ContactListener {
	@Override
	public void beginContact(Contact contact) {
		short collisionDefinition = (short)(contact.getFixtureA().getFilterData().categoryBits | contact.getFixtureB().getFilterData().categoryBits);

		switch (collisionDefinition) {
			case BODY_BIT | GOAL_BIT: {
				Fixture[] fixtures = sortFixturesByCategoryBit(contact, BODY_BIT);
				((BodyEntity) fixtures[0].getUserData()).arrivedAtGoal();
				((GoalEntity) fixtures[1].getUserData()).bodyArrived();
				break;
			}
		}
	}

	private Fixture[] sortFixturesByCategoryBit(Contact contact, short categoryBit) {
		Fixture[] fixtures = new Fixture[2];
		if (contact.getFixtureA().getFilterData().categoryBits == categoryBit) {
			fixtures[0] = contact.getFixtureA();
			fixtures[1] = contact.getFixtureB();
		} else {
			fixtures[0] = contact.getFixtureB();
			fixtures[1] = contact.getFixtureA();
		}
		return fixtures;
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}
