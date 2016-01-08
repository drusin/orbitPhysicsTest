package dawid.orbitprototype.systems;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dawid.orbitprototype.components.PlanetComponent;

public class DrawPlanetSystem extends IteratingSystem {

	private final SpriteBatch batch;
	private final Texture eyewhite;
	private final Texture iris;

	public DrawPlanetSystem(SpriteBatch batch) {
		super(Family.all(PlanetComponent.class).get());
		this.batch = batch;
		eyewhite = new Texture("graphics/eyewhite.png");
		iris = new Texture("graphics/iris.png");
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PlanetComponent planetComponent = entity.getComponent(PlanetComponent.class);
		Vector2 position = planetComponent.fixture.getBody().getPosition();
		float radius = scaleUp(planetComponent.fixture.getShape().getRadius());
		batch.draw(planetComponent.texture, scaleUp(position.x) - radius, scaleUp(position.y) - radius, radius * 2, radius * 2);
		batch.draw(eyewhite, scaleUp(position.x) - radius * 0.85f, scaleUp(position.y), radius * 0.75f, radius * 0.75f);
		batch.draw(eyewhite, scaleUp(position.x) + radius * 0.1f, scaleUp(position.y), radius * 0.75f, radius * 0.75f);

		batch.draw(iris, scaleUp(position.x) - radius * 0.6f, scaleUp(position.y) + radius * 0.25f, radius * 0.25f, radius * 0.25f);
		batch.draw(iris, scaleUp(position.x) + radius * 0.35f, scaleUp(position.y) + radius * 0.25f, radius * 0.25f, radius * 0.25f);
	}
}
