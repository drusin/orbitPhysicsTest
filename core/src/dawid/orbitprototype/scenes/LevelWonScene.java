package dawid.orbitprototype.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import lombok.Getter;

public class LevelWonScene {

	@Getter
	private final Stage stage;
	private final Skin skin;

	public LevelWonScene() {
		this(1280, 720);
	}

	public LevelWonScene(int width, int height) {
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

		final TextButton nextLevelButton = new TextButton("Next Level!", skin);
		table.add(nextLevelButton).padBottom(10).width(200);
		table.row();

		final TextButton retryButton = new TextButton("Retry!", skin);
		table.add(retryButton).padBottom(10).width(200);
		table.row();

		final TextButton menuButton = new TextButton("Main Menu!", skin);
		table.add(menuButton).width(200);
		table.row();

		// Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
		// Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
		// ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
		// revert the checked state.
		retryButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + retryButton.isChecked());
				retryButton.setText("Good job!");
			}
		});
	}
}
