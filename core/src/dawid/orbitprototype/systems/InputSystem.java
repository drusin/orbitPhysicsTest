package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import dawid.orbitprototype.components.PlanetComponent;
import dawid.orbitprototype.entities.PlanetEntity;

import static dawid.orbitprototype.MyGdxGame.scaleDown;

public class InputSystem extends IteratingSystem implements InputProcessor {

	private final OrthographicCamera gameCam;
	private Vector2 startPos = null;

	public InputSystem(OrthographicCamera gameCam) {
		super(Family.all(PlanetComponent.class).get());
		this.gameCam = gameCam;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			gameCam.unproject(touchPos);
			float x = touchPos.x;
			float y = touchPos.y;

			PlanetComponent planetComponent = entity.getComponent(PlanetComponent.class);
			Fixture f = planetComponent.fixture;
			Vector2 position = f.getBody().getPosition();
			float radius = f.getShape().getRadius();
			if (x > position.x - radius && x < position.x + radius
					&& y > position.y - radius && y < position.y + radius) {
				if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && planetComponent.size > planetComponent.minSize && f.getShape().getRadius() > scaleDown(10)) {
					((PlanetEntity)entity).resize(-10f);
					planetComponent.size --;
				}
				else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && planetComponent.size < planetComponent.maxSize){
					((PlanetEntity)entity).resize(10f);
					planetComponent.size ++;
				}
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			gameCam.translate(-deltaTime, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			gameCam.translate(0 , deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			gameCam.translate(deltaTime, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			gameCam.translate(0, -deltaTime);
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
			startPos = new Vector2(scaleDown(screenX), scaleDown(screenY));
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
			Vector2 dragVec = new Vector2(scaleDown(screenX), scaleDown(screenY));
			startPos.sub(dragVec);
			startPos.y = -startPos.y;
			gameCam.translate(startPos);
			startPos.x = scaleDown(screenX);
			startPos.y = scaleDown(screenY);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		gameCam.zoom += amount * 0.2f;
		return false;
	}
}
