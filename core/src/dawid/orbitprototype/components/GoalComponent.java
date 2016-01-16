package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import lombok.Setter;

@Setter
public class GoalComponent implements Component {
	public float maxTimeBetween = 0.3f;
	public float reduceScale = 10f;
	public float allBodies = 0;
	public int newBodies = 0;
	public float timer = 0;
	public boolean fullfilled = false;
}
