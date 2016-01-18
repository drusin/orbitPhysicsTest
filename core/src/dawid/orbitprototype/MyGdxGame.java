package dawid.orbitprototype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ArrayMap;
import dawid.orbitprototype.screens.LevelSelectScreen;
import dawid.orbitprototype.screens.MainScreen;
import lombok.Getter;

import java.util.Random;

public class MyGdxGame extends Game {
	public static final Random random = new Random();
	private final String[] arg;
	@Getter
	private ArrayMap<Integer, FileHandle> levels;
	private Screen currentScreen;

	public MyGdxGame(String[] arg) {
		this.arg = arg;
	}

	@Override
	public void create() {
		levels = new ArrayMap<>();
		FileHandle[] levelArray = Gdx.files.internal("levels").list();
		for (int i = 1; i <= levelArray.length; i++) {
			levels.put(i, levelArray[i-1]);
		}

		if (arg.length != 0) {
			setScreen(new MainScreen(arg[0], 0, this));
		}
		else {
			setScreen(new LevelSelectScreen(this));
		}
	}

	public void loadLevel(int levelNumber) {
		setScreen(new MainScreen(levels.get(levelNumber), levelNumber, this));
	}
	public static float scaleDown(float f) {
		return f / 100;
	}

	public static float scaleUp(float f) {
		return f * 100;
	}

	@Override
	public void setScreen(Screen screen) {
		if (currentScreen != null) {
			currentScreen.dispose();
		}
		currentScreen = screen;
		super.setScreen(screen);
	}
}
