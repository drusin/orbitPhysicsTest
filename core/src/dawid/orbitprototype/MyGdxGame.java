package dawid.orbitprototype;

import com.badlogic.gdx.Game;

import java.util.Random;

public class MyGdxGame extends Game {
	public static final Random random = new Random();

	@Override
	public void create () {
		setScreen(new MainScreen());
	}

	public static float scaleDown(float f) {
		return f / 100;
	}

	public static float scaleUp(float f) {
		return f * 100;
	}
}
