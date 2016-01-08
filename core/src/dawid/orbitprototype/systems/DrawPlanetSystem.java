package dawid.orbitprototype.systems;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dawid.orbitprototype.components.PlanetComponent;

public class DrawPlanetSystem extends IteratingSystem {

	private final SpriteBatch batch;

	public DrawPlanetSystem(SpriteBatch batch) {
		super(Family.all(PlanetComponent.class).get());
		this.batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PlanetComponent planetComponent = entity.getComponent(PlanetComponent.class);
		Vector2 position = planetComponent.fixture.getBody().getPosition();
		float radius = scaleUp(planetComponent.fixture.getShape().getRadius());
		batch.draw(planetComponent.texture, scaleUp(position.x) - radius, scaleUp(position.y) - radius, radius * 2, radius * 2);
	}
}
