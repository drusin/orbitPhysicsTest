package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class GoalComponent implements Component, Pool.Poolable {
	public float maxTimeBetween = 0.3f;
	public float reduceScale = 10f;
	public float allBodies = 0;
	public int newBodies = 0;
	public float timer = 0;
	public boolean fullfilled = false;

	@Override
	public void reset() {
		maxTimeBetween = 0.3f;
		reduceScale = 10f;
		allBodies = 0;
		newBodies = 0;
		timer = 0;
		fullfilled = false;
	}
}
