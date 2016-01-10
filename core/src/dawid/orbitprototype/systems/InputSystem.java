package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import dawid.orbitprototype.components.PlanetComponent;
import dawid.orbitprototype.entities.PlanetEntity;

import static dawid.orbitprototype.MyGdxGame.scaleDown;

public class InputSystem extends IteratingSystem {

	private final OrthographicCamera gameCam;

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
	}
}
