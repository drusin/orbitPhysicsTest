package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Fixture;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DestroyableComponent implements Component {
	public final Fixture fixture;
	public boolean destroy = false;
}
