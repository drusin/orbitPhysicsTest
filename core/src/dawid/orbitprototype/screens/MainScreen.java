package dawid.orbitprototype.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dawid.orbitprototype.systems.*;
import dawid.orbitprototype.util.GameCamera;
import dawid.orbitprototype.util.LevelLoader;
import dawid.orbitprototype.util.WorldContactListener;

import static dawid.orbitprototype.MyGdxGame.scaleDown;

public class MainScreen extends ScreenAdapter {

	private static final World world = new World(new Vector2(0, 0), true);
	private static final Engine engine = new Engine();

	private final GameCamera gameCamera;
	private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private final Viewport gamePort;
	private final Viewport physicsPort;
	private final SpriteBatch batch = new SpriteBatch();

	public MainScreen(String level) {
		this(new FileHandle(level));
	}

	public MainScreen(FileHandle level) {
		OrthographicCamera guiCam = new OrthographicCamera(1280, 720);
		OrthographicCamera physicsCam = new OrthographicCamera(scaleDown(1280), scaleDown(720));
		gamePort = new FillViewport(1280, 720, guiCam);
		physicsPort = new FillViewport(scaleDown(1280), scaleDown(720), physicsCam);

		gameCamera = new GameCamera(guiCam, physicsCam);

		gameCamera.guiCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		gameCamera.physicsCam.position.set(physicsPort.getWorldWidth() / 2, physicsPort.getWorldHeight() / 2, 0);

		engine.addSystem(new GravitySystem());
		InputSystem inputSystem = new InputSystem(gameCamera);
		engine.addSystem(inputSystem);
		Gdx.input.setInputProcessor(inputSystem);
		engine.addSystem(new LifespanSystem());
		engine.addSystem(new DestroySystem(engine, world));
		engine.addSystem(new SpawnerSystem(engine, world));
		engine.addSystem(new GoalSystem());
		engine.addSystem(new DrawPlanetSystem(batch, gameCamera));
		engine.addSystem(new DrawGoalSystem(batch));

		LevelLoader.loadMap(level.path(), engine, world);

		world.setContactListener(new WorldContactListener());
	}

	@Override
	public void render(float delta) {
		gameCamera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(delta, 6, 2);
		debugRenderer.render(world, gameCamera.physicsCam.combined);

		batch.setProjectionMatrix(gameCamera.guiCam.combined);
		batch.begin();
		engine.update(delta);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		physicsPort.update(width, height);
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
	}
}
