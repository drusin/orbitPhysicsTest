package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import dawid.orbitprototype.components.PlanetComponent;
import dawid.orbitprototype.entities.PlanetEntity;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

public class InputSystem extends IteratingSystem {

	public InputSystem() {
		super(Family.all(PlanetComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		if (Gdx.input.isTouched()) {
			int x = Gdx.input.getX();
			int y = 720 - Gdx.input.getY();

			Fixture f = entity.getComponent(PlanetComponent.class).fixture;
			Vector2 position = f.getBody().getPosition();
			float radius = f.getShape().getRadius();
			if (x > scaleUp(position.x) - scaleUp(radius) && x < scaleUp(position.x) + scaleUp(radius)
					&& y > scaleUp(position.y) - scaleUp(radius) && y < scaleUp(position.y) + scaleUp(radius)) {
				if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
					((PlanetEntity)entity).resize(-0.5f);
				}
				else {
					((PlanetEntity)entity).resize(0.5f);
				}
			}
		}
	}
}
