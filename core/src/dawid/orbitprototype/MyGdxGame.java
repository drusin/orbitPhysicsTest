package dawid.orbitprototype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.files.FileHandle;
import dawid.orbitprototype.screens.LevelSelectScreen;
import dawid.orbitprototype.screens.MainScreen;

import java.util.Random;

public class MyGdxGame extends Game {
	public static final Random random = new Random();
	private final String[] arg;

	public MyGdxGame(String[] arg) {
		this.arg = arg;
	}

	@Override
	public void create() {
		if (arg.length != 0) {
			setScreen(new MainScreen(arg[0]));
		}
		else {
			setScreen(new LevelSelectScreen(this));
		}
	}

	public void loadLevel(FileHandle level) {
		setScreen(new MainScreen(level));
	}

	public static float scaleDown(float f) {
		return f / 100;
	}

	public static float scaleUp(float f) {
		return f * 100;
	}
}
