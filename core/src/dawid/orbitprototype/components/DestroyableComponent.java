package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class DestroyableComponent implements Component, Pool.Poolable {
	public boolean destroy = false;

	@Override
	public void reset() {
		destroy = false;
	}
}
