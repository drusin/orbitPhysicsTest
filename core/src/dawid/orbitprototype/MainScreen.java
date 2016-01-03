package dawid.orbitprototype;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dawid.orbitprototype.systems.*;
import dawid.orbitprototype.util.LevelLoader;
import dawid.orbitprototype.util.WorldContactListener;

public class MainScreen extends ScreenAdapter {

	private final World world = new World(new Vector2(0, 0), true);
	private final Engine engine = new Engine();
	private final OrthographicCamera gameCam = new OrthographicCamera();
	private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private final Viewport gamePort = new FillViewport(MyGdxGame.scaleDown(1280), MyGdxGame.scaleDown(720), gameCam);
	private final SpriteBatch batch = new SpriteBatch();

	public MainScreen() {
		gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		engine.addSystem(new GravitySystem());
		engine.addSystem(new InputSystem());
		engine.addSystem(new LifespanSystem());
		engine.addSystem(new DestroySystem(engine, world));
		engine.addSystem(new SpawnerSystem(engine, world));
		engine.addSystem(new GoalSystem());
		engine.addSystem(new DrawPercentageSystem(batch));

		LevelLoader.loadMap("levels/test.tmx", engine, world);

		world.setContactListener(new WorldContactListener());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(delta, 6, 2);
		engine.update(delta);

		debugRenderer.render(world, gameCam.combined);
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
