package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import dawid.orbitprototype.components.PlanetComponent;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

public class DrawPlanetSystem extends IteratingSystem {

	private final SpriteBatch batch;

	public DrawPlanetSystem(SpriteBatch batch) {
		super(Family.all(PlanetComponent.class).get());
		this.batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PlanetComponent planetComponent = entity.getComponent(PlanetComponent.class);
		float radius = planetComponent.fixture.getShape().getRadius();
		Vector2 position = planetComponent.fixture.getBody().getPosition();
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.BLUE);
		renderer.circle(scaleUp(position.x), scaleUp(position.y), scaleUp(radius));
		renderer.end();
	}
}
