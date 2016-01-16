package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import lombok.Setter;

@Setter
public class PlanetComponent implements Component {
	public Texture texture = null;
	public int size = 0;
	public int maxSize = 999;
	public int minSize = -999;
}
