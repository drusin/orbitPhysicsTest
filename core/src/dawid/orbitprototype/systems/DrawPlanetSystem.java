package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dawid.orbitprototype.components.Box2dFixtureComponent;
import dawid.orbitprototype.components.PlanetComponent;
import dawid.orbitprototype.util.GameCamera;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

public class DrawPlanetSystem extends IteratingSystem {

	private final SpriteBatch batch;
	private final GameCamera gameCam;
	private final Texture eyewhite;
	private final Texture iris;
	private final ComponentMapper<PlanetComponent> planetMapper;
	private final ComponentMapper<Box2dFixtureComponent> fixtureMapper;

	public DrawPlanetSystem(SpriteBatch batch, GameCamera gameCam) {
		super(Family.all(PlanetComponent.class, Box2dFixtureComponent.class).get());
		this.batch = batch;
		this.gameCam = gameCam;
		eyewhite = new Texture("graphics/eyewhite.png");
		iris = new Texture("graphics/iris.png");
		planetMapper = ComponentMapper.getFor(PlanetComponent.class);
		fixtureMapper = ComponentMapper.getFor(Box2dFixtureComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		Vector3 mousePosition = new Vector3(x, y, 0);
		gameCam.unproject(mousePosition);
		PlanetComponent planetComponent = planetMapper.get(entity);
		Box2dFixtureComponent fixtureComponent = fixtureMapper.get(entity);
		Vector2 position = fixtureComponent.fixture.getBody().getPosition();
		position.x = scaleUp(position.x);
		position.y = scaleUp(position.y);
		float radius = scaleUp(fixtureComponent.fixture.getShape().getRadius());
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
