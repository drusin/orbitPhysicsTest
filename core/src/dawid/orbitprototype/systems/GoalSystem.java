package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import dawid.orbitprototype.components.GoalComponent;
import dawid.orbitprototype.screens.MainScreen;

public class GoalSystem extends IteratingSystem {

	private final MainScreen mainScreen;
	private final ComponentMapper<GoalComponent> goalMapper;

	public GoalSystem(MainScreen mainScreen) {
		super(Family.all(GoalComponent.class).get());
		this.mainScreen = mainScreen;
		goalMapper = ComponentMapper.getFor(GoalComponent.class);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (getEntities().size() == 0) {
			return;
		}
		for (Entity e : getEntities()) {
			if (!goalMapper.get(e).fullfilled) {
				return;
			}
		}
		System.out.println("game is won!");
		mainScreen.levelIsWon();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		GoalComponent goalComponent = goalMapper.get(entity);
		if (goalComponent.newBodies == 0) {
			goalComponent.timer += deltaTime;
		}
		else {
			goalComponent.timer = 0;
		}
		if (goalComponent.timer > goalComponent.maxTimeBetween) {
			goalComponent.allBodies -= deltaTime * goalComponent.reduceScale;
			if (goalComponent.allBodies < 0) {
				goalComponent.allBodies = 0;
			}
		}
		goalComponent.allBodies += goalComponent.newBodies;
		goalComponent.newBodies = 0;
		if (goalComponent.allBodies >= 100) {
			goalComponent.allBodies = 100;
			goalComponent.fullfilled = true;
		}
		else {
			goalComponent.fullfilled = false;
		}
	}
}
