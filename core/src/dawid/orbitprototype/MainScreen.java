package dawid.orbitprototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements Screen {

	private final World world = new World(new Vector2(0, 0), true);
	private final OrthographicCamera gameCam = new OrthographicCamera();
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private final Viewport gamePort = new FillViewport(scaleDown(1280), scaleDown(720), gameCam);

	private Array<Fixture> planets = new Array<>();
	private Array<Fixture> bodies = new Array<>();

	public MainScreen() {
		gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		planets.add(createPlanet(320, 360, 20));
		planets.add(createPlanet(960, 360, 20));
		planets.add(createPlanet(50, 50, 20));
		planets.add(createPlanet(800, 100, 20));
		planets.add(createPlanet(900, 650, 20));
	}

	private Fixture createPlanet(float x, float y, float radius) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(scaleDown(x), scaleDown(y));
		bodyDef.type = BodyDef.BodyType.StaticBody;
		Body body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(scaleDown(radius));

		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;
		fixtureDef.filter.maskBits = 0;
		return body.createFixture(fixtureDef);
	}

	private Fixture createBody(float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(scaleDown(x), scaleDown(y));
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		Body body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(scaleDown(10));

		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;
		fixtureDef.filter.maskBits = 0;
		return body.createFixture(fixtureDef);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		applyForces(delta);
		handleInput();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(delta, 6, 2);
		MyGdxGame.batch.setProjectionMatrix(gameCam.combined);
		MyGdxGame.batch.begin();
		MyGdxGame.batch.end();
		debugRenderer.render(world, gameCam.combined);
	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			bodies.add(createBody(640, 360));
		}
		if (Gdx.input.isTouched()) {
			int x = Gdx.input.getX();
			int y = 720 - Gdx.input.getY();

			for (Fixture f : planets) {
				Vector2 position = f.getBody().getPosition();
				float radius = f.getShape().getRadius();
				if (x > scaleUp(position.x) - scaleUp(radius) && x < scaleUp(position.x) + scaleUp(radius)
						&& y > scaleUp(position.y) - scaleUp(radius) && y < scaleUp(position.y) + scaleUp(radius)) {
					f.getShape().setRadius(radius + scaleDown(1));
				}
			}
		}
	}

	private void applyForces(float delta) {
		for (Fixture body : bodies) {
			for (Fixture planet : planets) {
				Body bodyBody = body.getBody();
				Body planetBody = planet.getBody();

				Vector2 distance = new Vector2(planetBody.getPosition()).sub(bodyBody.getPosition());
				distance.nor();
				float force = 50;
				distance.x = distance.x * planet.getShape().getRadius() * force * delta;
				distance.y = distance.y * planet.getShape().getRadius() * force * delta;

				bodyBody.applyLinearImpulse(distance, bodyBody.getLocalCenter(), true);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	float scaleDown(float f) {
		return f / 100;
	}

	float scaleUp(float f) {
		return f * 100;
	}
}
