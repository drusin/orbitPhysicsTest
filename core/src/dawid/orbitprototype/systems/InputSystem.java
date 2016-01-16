package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import dawid.orbitprototype.components.Box2dFixtureComponent;
import dawid.orbitprototype.components.PlanetComponent;
import dawid.orbitprototype.util.FixtureCreator;
import dawid.orbitprototype.util.GameCamera;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

public class InputSystem extends IteratingSystem implements InputProcessor {

	private final GameCamera gameCam;
	private final World world;
	private final ComponentMapper<PlanetComponent> planetMapper;
	private final ComponentMapper<Box2dFixtureComponent> fixtureMapper;
	private Vector2 startPos = null;

	public InputSystem(GameCamera gameCam, World world) {
		super(Family.all(PlanetComponent.class, Box2dFixtureComponent.class).get());
		this.gameCam = gameCam;
		this.world = world;
		planetMapper = ComponentMapper.getFor(PlanetComponent.class);
		fixtureMapper = ComponentMapper.getFor(Box2dFixtureComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			gameCam.unproject(touchPos);
			float x = touchPos.x;
			float y = touchPos.y;

			PlanetComponent planetComponent = planetMapper.get(entity);
			Box2dFixtureComponent fixtureComponent = fixtureMapper.get(entity);
			Vector2 position = fixtureComponent.fixture.getBody().getPosition();
			position.x = scaleUp(position.x);
			position.y = scaleUp(position.y);
			float radius = scaleUp(fixtureComponent.fixture.getShape().getRadius());
			if (x > position.x - radius && x < position.x + radius
					&& y > position.y - radius && y < position.y + radius) {
				if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && planetComponent.size > planetComponent.minSize && radius > 30) {
					System.out.println(radius);
					FixtureCreator.resize(fixtureComponent, -10f, world);
					planetComponent.size --;
				}
				else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && planetComponent.size < planetComponent.maxSize){
					FixtureCreator.resize(fixtureComponent, 10f, world);
					planetComponent.size ++;
				}
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
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
