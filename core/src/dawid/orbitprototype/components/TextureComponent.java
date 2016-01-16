package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import lombok.Setter;

@Setter
public class TextureComponent implements Component {
	public Texture texture;
}
