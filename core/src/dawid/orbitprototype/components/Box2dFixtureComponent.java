package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pool;

public class Box2dFixtureComponent implements Component, Pool.Poolable {
	public Fixture fixture = null;

	@Override
	public void reset() {
		fixture = null;
	}
}
