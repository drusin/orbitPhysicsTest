package dawid.orbitprototype;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dawid.orbitprototype.components.DynamicComponent;
import dawid.orbitprototype.components.PlanetComponent;
import dawid.orbitprototype.systems.GravitySystem;
import dawid.orbitprototype.systems.InputSystem;

public class MainScreen implements Screen {

	private final World world = new World(new Vector2(0, 0), true);
	private final OrthographicCamera gameCam = new OrthographicCamera();
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private final Viewport gamePort = new FillViewport(MyGdxGame.scaleDown(1280), MyGdxGame.scaleDown(720), gameCam);

	private final Engine engine;

	public MainScreen() {
		gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		engine = new Engine();
		engine.addSystem(new GravitySystem());
		engine.addSystem(new InputSystem());

		createPlanet(320, 360, 20);
		createPlanet(960, 360, 20);
		createPlanet(50, 50, 20);
		createPlanet(800, 100, 20);
		createPlanet(900, 650, 20);
	}

	private void createPlanet(float x, float y, float radius) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(MyGdxGame.scaleDown(x), MyGdxGame.scaleDown(y));
		bodyDef.type = BodyDef.BodyType.StaticBody;
		Body body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(MyGdxGame.scaleDown(radius));

		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;
		fixtureDef.filter.maskBits = 0;
		Fixture fixture = body.createFixture(fixtureDef);
		Entity entity = new Entity();
		PlanetComponent component = new PlanetComponent();
		component.fixture = fixture;
		entity.add(component);
		engine.addEntity(entity);
	}

	private void createBody(float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(MyGdxGame.scaleDown(x), MyGdxGame.scaleDown(y));
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		Body body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(MyGdxGame.scaleDown(10));

		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;
		fixtureDef.filter.maskBits = 0;
		Fixture fixture = body.createFixture(fixtureDef);
		Entity entity = new Entity();
		DynamicComponent component = new DynamicComponent();
		component.fixture = fixture;
		entity.add(component);
		engine.addEntity(entity);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		handleInput();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(delta, 6, 2);
		engine.update(delta);
		MyGdxGame.batch.setProjectionMatrix(gameCam.combined);
		MyGdxGame.batch.begin();
		MyGdxGame.batch.end();
		debugRenderer.render(world, gameCam.combined);
	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			createBody(640, 360);
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
}
