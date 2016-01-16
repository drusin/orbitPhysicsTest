package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Fixture;
import lombok.Setter;

@Setter
public class Box2dFixtureComponent implements Component {
	public Fixture fixture = null;
}
