package dawid.orbitprototype.components;

import lombok.AllArgsConstructor;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Fixture;

@AllArgsConstructor
public class PlanetComponent implements Component {
	public final Fixture fixture;
	public final Texture texture;
	public int size;
	public final int maxSize;
	public final int minSize;
}
