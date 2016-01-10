package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dawid.orbitprototype.components.PlanetComponent;

public class DrawPlanetSystem extends IteratingSystem {

	private final SpriteBatch batch;
	private final OrthographicCamera gameCam;
	private final Texture eyewhite;
	private final Texture iris;

	public DrawPlanetSystem(SpriteBatch batch, OrthographicCamera gameCam) {
		super(Family.all(PlanetComponent.class).get());
		this.batch = batch;
		this.gameCam = gameCam;
		eyewhite = new Texture("graphics/eyewhite.png");
		iris = new Texture("graphics/iris.png");
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		Vector3 mousePosition = new Vector3(x, y, 0);
		gameCam.unproject(mousePosition);
		PlanetComponent planetComponent = entity.getComponent(PlanetComponent.class);
		Vector2 position = planetComponent.fixture.getBody().getPosition();
		float radius = planetComponent.fixture.getShape().getRadius();
		batch.draw(planetComponent.texture, position.x - radius, position.y - radius, radius * 2, radius * 2);
		batch.draw(eyewhite, position.x - radius * 0.85f, position.y, radius * 0.75f, radius * 0.75f);
		batch.draw(eyewhite, position.x + radius * 0.1f, position.y, radius * 0.75f, radius * 0.75f);

		float xCenter = position.x;
		float xLeftCenter = position.x - radius * 0.6f;
		float xRightCenter = position.x + radius * 0.35f;
		float yCenter = position.y + radius * 0.25f;
		float mouseX = mousePosition.x;
		float mouseY = mousePosition.y;

		Vector2 eye = new Vector2(xCenter - mouseX, yCenter - mouseY);
		eye.nor();

		batch.draw(iris, xLeftCenter - eye.x * radius * 0.2f, yCenter - eye.y * radius * 0.2f, radius * 0.25f, radius * 0.25f);
		batch.draw(iris, xRightCenter - eye.x * radius * 0.2f, yCenter - eye.y * radius * 0.2f, radius * 0.25f, radius * 0.25f);
	}
}
