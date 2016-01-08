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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import dawid.orbitprototype.MyGdxGame;
import dawid.orbitprototype.systems.DestroySystem;
import dawid.orbitprototype.systems.DrawGoalSystem;
import dawid.orbitprototype.systems.DrawPercentageSystem;
import dawid.orbitprototype.systems.DrawPlanetSystem;
import dawid.orbitprototype.systems.GoalSystem;
import dawid.orbitprototype.systems.GravitySystem;
import dawid.orbitprototype.systems.InputSystem;
import dawid.orbitprototype.systems.LifespanSystem;
import dawid.orbitprototype.systems.SpawnerSystem;
import dawid.orbitprototype.util.LevelLoader;
import dawid.orbitprototype.util.WorldContactListener;

public class MainScreen extends ScreenAdapter {

	private static final World world = new World(new Vector2(0, 0), true);
	private static final Engine engine = new Engine();

	private final OrthographicCamera gameCam = new OrthographicCamera(1280, 720);
	private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private final Viewport gamePort = new FitViewport(MyGdxGame.scaleDown(1280), MyGdxGame.scaleDown(720), gameCam);
	private final SpriteBatch batch = new SpriteBatch();

	public MainScreen(String level) {
		this(new FileHandle(level));
	}

	public MainScreen(FileHandle level) {
		gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		engine.addSystem(new GravitySystem());
		engine.addSystem(new InputSystem(gameCam));
		engine.addSystem(new LifespanSystem());
		engine.addSystem(new DestroySystem(engine, world));
		engine.addSystem(new SpawnerSystem(engine, world));
		engine.addSystem(new GoalSystem());
		engine.addSystem(new DrawPercentageSystem(batch));
		engine.addSystem(new DrawPlanetSystem(batch));
		engine.addSystem(new DrawGoalSystem(batch));

		LevelLoader.loadMap(level.path(), engine, world);

		world.setContactListener(new WorldContactListener());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(delta, 6, 2);
		debugRenderer.render(world, gameCam.combined);

		batch.begin();
		engine.update(delta);
		batch.end();

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
