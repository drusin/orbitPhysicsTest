package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Fixture;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DynamicComponent implements Component {
	public final Fixture fixture;
}
