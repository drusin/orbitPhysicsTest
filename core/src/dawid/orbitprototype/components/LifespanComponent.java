package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LifespanComponent implements Component {

	public final float lifespan;
	public float currentTime = 0;
}
