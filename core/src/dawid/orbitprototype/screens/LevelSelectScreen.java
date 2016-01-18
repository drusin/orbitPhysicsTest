package dawid.orbitprototype.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dawid.orbitprototype.MyGdxGame;

public class LevelSelectScreen extends ScreenAdapter {

	private final OrthographicCamera gameCam = new OrthographicCamera(1280, 720);
	private final Viewport gamePort = new FitViewport(1280, 720, gameCam);
	private final BitmapFont font;
	private final SpriteBatch batch = new SpriteBatch();
	private final MyGdxGame game;
	private final ArrayMap<Integer, FileHandle> levels;

	public LevelSelectScreen(MyGdxGame game) {
		this.game = game;
		gamePort.apply(true);

		levels = game.getLevels();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/coolvetica rg.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.borderWidth = 1;
		font = generator.generateFont(parameter);
		generator.dispose();
	}


	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(gameCam.combined);
		batch.begin();
		for (int i = 0; i < levels.size; i++) {
			font.draw(batch, levels.get(i +1).name(), 500, 720 - (30 * i + 20));
		}
		batch.end();
		handleInput();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	private void handleInput() {
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			gamePort.unproject(touchPos);
			float y = 720 - touchPos.y;
			y = Math.round((y - 20) / 30);
			if (levels.size >= y + 1) {
				game.loadLevel((int) y + 1);
			}
		}
	}
}
