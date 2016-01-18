package dawid.orbitprototype.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import dawid.orbitprototype.MyGdxGame;
import dawid.orbitprototype.screens.LevelSelectScreen;
import dawid.orbitprototype.screens.MainScreen;
import lombok.Getter;

public class LevelWonScene {

	@Getter
	private final Stage stage;
	private final Skin skin;

	public LevelWonScene(int width, int height, MyGdxGame game, MainScreen mainScreen) {
		stage = new Stage(new FillViewport(width, height));
		Gdx.input.setInputProcessor(stage);

		TextureAtlas atlas = new TextureAtlas("tiny_giant_planets.pack");
		skin = new Skin(new FileHandle("skin.json"), atlas);

		NinePatchDrawable menu_background = new NinePatchDrawable(new NinePatch(new Texture("menu_background.png"), 68, 69, 19, 35));

		Table table = new Table();
		table.setBackground(menu_background);
		table.setFillParent(false);

		Table backgroundTable = new Table();
		backgroundTable.setFillParent(true);
		backgroundTable.add();
		backgroundTable.add(table).width(250);
		backgroundTable.add();

		stage.addActor(backgroundTable);

		Label success = new Label("Success!", skin, "title-text");
		table.add(success).padBottom(20).padTop(10);
		table.row();
		HorizontalGroup movesGroup = new HorizontalGroup();
		Label movesLabel = new Label("Moves: ", skin);
		movesGroup.addActor(movesLabel);
		Label movesAmount = new Label("0", skin);
		movesGroup.addActor(movesAmount);
		table.add(movesGroup).padBottom(10);
		table.row();

		HorizontalGroup timeGroup = new HorizontalGroup();
		Label timeLabel = new Label("Time: ", skin);
		timeGroup.addActor(timeLabel);
		Label tineAmount = new Label("00:00", skin);
		timeGroup.addActor(tineAmount);
		table.add(timeGroup).padBottom(20);
		table.row();

		FileHandle nextLevel = game.getLevels().get(mainScreen.getLevelnumber() + 1);

		final TextButton nextLevelButton = new TextButton("Next Level!", skin);
		nextLevelButton.setDisabled(nextLevel == null);
		table.add(nextLevelButton).padBottom(10).width(200);
		table.row();

		nextLevelButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				int nextLevelNumber = mainScreen.getLevelnumber() + 1;
				game.loadLevel(nextLevelNumber);
			}
		});

		final TextButton retryButton = new TextButton("Retry!", skin);
		table.add(retryButton).padBottom(10).width(200);
		table.row();

		retryButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.loadLevel(mainScreen.getLevelnumber());
			}
		});

		final TextButton menuButton = new TextButton("Main Menu!", skin);
		table.add(menuButton).width(200);
		table.row();

		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new LevelSelectScreen(game));
			}
		});
	}
}
