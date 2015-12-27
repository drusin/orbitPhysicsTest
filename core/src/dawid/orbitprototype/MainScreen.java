package dawid.orbitprototype;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dawid.orbitprototype.entities.BodyEntity;
import dawid.orbitprototype.entities.PlanetEntity;
import dawid.orbitprototype.systems.GravitySystem;
import dawid.orbitprototype.systems.InputSystem;
import dawid.orbitprototype.systems.LifespanSystem;

public class MainScreen extends ScreenAdapter {

	private final World world = new World(new Vector2(0, 0), true);
	private final Engine engine = new Engine();
	private final OrthographicCamera gameCam = new OrthographicCamera();
	private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private final Viewport gamePort = new FillViewport(MyGdxGame.scaleDown(1280), MyGdxGame.scaleDown(720), gameCam);
	private final Spawner spawner;

	public MainScreen() {
		gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		engine.addSystem(new GravitySystem());
		engine.addSystem(new InputSystem());
		engine.addSystem(new LifespanSystem(engine, world));

		new PlanetEntity(engine, world, 320, 360, 20);
		new PlanetEntity(engine, world, 960, 360, 20);
		new PlanetEntity(engine, world, 50, 50, 20);
		new PlanetEntity(engine, world, 800, 100, 20);
		new PlanetEntity(engine, world, 900, 650, 20);

		spawner = new Spawner(engine, world);
	}

	@Override
	public void render(float delta) {
		handleInput();
		spawner.update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(delta, 6, 2);
		engine.update(delta);
		debugRenderer.render(world, gameCam.combined);
	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			new BodyEntity(engine, world, 640, 360);
		}
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
	}
}
