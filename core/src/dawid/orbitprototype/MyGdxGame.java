package dawid.orbitprototype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	public static SpriteBatch batch;

	public static float scaleDown(float f) {
		return f / 100;
	}

	public static float scaleUp(float f) {
		return f * 100;
	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainScreen());
	}
}
