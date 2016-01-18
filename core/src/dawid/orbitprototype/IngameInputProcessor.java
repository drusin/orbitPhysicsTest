package dawid.orbitprototype;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.components.Box2dFixtureComponent;
import dawid.orbitprototype.components.PlanetComponent;
import dawid.orbitprototype.screens.LevelSelectScreen;
import dawid.orbitprototype.screens.MainScreen;
import dawid.orbitprototype.util.FixtureCreator;
import dawid.orbitprototype.util.GameCamera;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

public class IngameInputProcessor implements InputProcessor {

	private final GameCamera gameCam;
	private final World world;
	private final MyGdxGame game;
	private final MainScreen mainScreen;
	private final ComponentMapper<PlanetComponent> planetMapper;
	private final ComponentMapper<Box2dFixtureComponent> fixtureMapper;
	private final ImmutableArray<Entity> planetEntities;
	private final Assets assets;
	private Vector2 startPos = null;

	public IngameInputProcessor(Engine engine, GameCamera gameCam, World world, MyGdxGame game, MainScreen mainScreen, Assets assets) {
		this.assets = assets;
		planetEntities = engine.getEntitiesFor(Family.all(PlanetComponent.class, Box2dFixtureComponent.class).get());
		this.gameCam = gameCam;
		this.world = world;
		this.game = game;
		this.mainScreen = mainScreen;
		planetMapper = ComponentMapper.getFor(PlanetComponent.class);
		fixtureMapper = ComponentMapper.getFor(Box2dFixtureComponent.class);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.ESCAPE) {
			game.setScreen(new LevelSelectScreen(game, assets));
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.MIDDLE) {
			startPos = new Vector2(screenX, screenY);
		}
		Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		gameCam.unproject(touchPos);

		float x = touchPos.x;
		float y = touchPos.y;

		for (Entity entity : planetEntities) {
			PlanetComponent planetComponent = planetMapper.get(entity);
			Box2dFixtureComponent fixtureComponent = fixtureMapper.get(entity);
			Vector2 position = fixtureComponent.fixture.getBody().getPosition();
			position.x = scaleUp(position.x);
			position.y = scaleUp(position.y);
			float radius = scaleUp(fixtureComponent.fixture.getShape().getRadius());
			if (x > position.x - radius && x < position.x + radius
					&& y > position.y - radius && y < position.y + radius) {
				if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && planetComponent.size > planetComponent.minSize && radius > 30) {
					FixtureCreator.resize(fixtureComponent, -10f, world);
					planetComponent.size--;
				} else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && planetComponent.size < planetComponent.maxSize) {
					FixtureCreator.resize(fixtureComponent, 10f, world);
					planetComponent.size++;
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		startPos = null;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (startPos != null) {
			Vector2 dragVec = new Vector2(screenX, screenY);
			startPos.sub(dragVec);
			startPos.y = -startPos.y;
			gameCam.translate(startPos);
			startPos.x = screenX;
			startPos.y = screenY;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		gameCam.zoom(amount * 0.2f);
		return false;
	}
}
