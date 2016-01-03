package dawid.orbitprototype.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dawid.orbitprototype.MyGdxGame;

public class LevelSelectScreen extends ScreenAdapter {

	private final OrthographicCamera gameCam = new OrthographicCamera(1280, 720);
	private final Viewport gamePort = new FitViewport(MyGdxGame.scaleDown(1280), MyGdxGame.scaleDown(720), gameCam);
	private final BitmapFont font;
	private final SpriteBatch batch = new SpriteBatch();
	private final FileHandle[] presentLevels;
	private final MyGdxGame game;

	public LevelSelectScreen(MyGdxGame game) {
		this.game = game;
		gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		presentLevels = Gdx.files.internal("levels").list();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/coolvetica rg.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.borderWidth = 1;
		font = generator.generateFont(parameter);
		generator.dispose();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		batch.begin();
		for (int i = 0; i < presentLevels.length; i++) {
			font.draw(batch, presentLevels[i].name(), 500, 720 - (30 * i + 20));
		}
		batch.end();
		handleInput();
	}

	private void handleInput() {
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			gameCam.unproject(touchPos);
			float y = 720 - (touchPos.y + 720 / 2);
			y = Math.round((y - 20) / 30);
			if (presentLevels.length >= y + 1) {
				game.loadLevel(presentLevels[(int) y]);
			}
		}
	}
}
