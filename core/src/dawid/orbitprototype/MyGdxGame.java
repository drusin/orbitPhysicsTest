package dawid.orbitprototype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	public static SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainScreen());
	}
}
