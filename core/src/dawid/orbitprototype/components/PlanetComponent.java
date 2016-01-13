package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;

public class PlanetComponent implements Component, Pool.Poolable {
	public Texture texture = null;
	public int size = 0;
	public int maxSize = 999;
	public int minSize = -999;

	@Override
	public void reset() {
		texture = null;
		size = 0;
		maxSize = 999;
		minSize = -999;
	}
}
