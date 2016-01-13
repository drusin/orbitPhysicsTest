package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class LifespanComponent implements Component, Pool.Poolable {
	public float lifespan = 0;
	public float currentTime = 0;

	@Override
	public void reset() {
		lifespan = 0;
		currentTime = 0;
	}
}
