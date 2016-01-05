package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Fixture;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlanetComponent implements Component {
	public final Fixture fixture;
	public int size;
	public final int maxSize;
	public final int minSize;
}
