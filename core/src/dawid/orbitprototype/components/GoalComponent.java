package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Fixture;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoalComponent implements Component {
	public static final float MAX_TIME_BETWEEN = 0.3f;
	public final Fixture fixture;
	public int neededBodies = 100;
	public float allBodies = 0;
	public int newBodies = 0;
	public float timer = 0;
	public boolean fullfilled = false;
}
