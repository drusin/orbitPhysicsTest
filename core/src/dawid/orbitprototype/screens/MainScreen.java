package dawid.orbitprototype.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dawid.orbitprototype.IngameInputProcessor;
import dawid.orbitprototype.MyGdxGame;
import dawid.orbitprototype.scenes.LevelWonScene;
import dawid.orbitprototype.systems.*;
import dawid.orbitprototype.util.EntityFactory;
import dawid.orbitprototype.util.GameCamera;
import dawid.orbitprototype.util.LevelLoader;
import dawid.orbitprototype.util.WorldContactListener;
import lombok.Getter;

import static dawid.orbitprototype.MyGdxGame.scaleDown;

public class MainScreen extends ScreenAdapter {

	private final Engine engine;

	private final World world;
	private final GameCamera gameCamera;
	private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private final Viewport gamePort;
	private final Viewport physicsPort;
	private final SpriteBatch batch = new SpriteBatch();
	private final ParticleEffectPool particleEffectPool;
	@Getter
	private final int levelnumber;
	private final MyGdxGame game;
	private FPSLogger fpsLogger;
	private EntityFactory entityFactory;
	private LevelWonScene levelWon;
	private boolean levelIsWon = false;
	private GoalSystem goalSystem;

	public MainScreen(String level, int levelNumber, MyGdxGame game) {
		this(new FileHandle(level), levelNumber, game);
	}

	public MainScreen(FileHandle level, int levelnumber, MyGdxGame game) {
		this.levelnumber = levelnumber;
		this.game = game;
		engine = new Engine();
		world = new World(new Vector2(0, 0), true);
		OrthographicCamera guiCam = new OrthographicCamera(1280, 720);
		OrthographicCamera physicsCam = new OrthographicCamera(scaleDown(1280), scaleDown(720));
		gamePort = new FillViewport(1280, 720, guiCam);
		physicsPort = new FillViewport(scaleDown(1280), scaleDown(720), physicsCam);

		gameCamera = new GameCamera(gamePort, guiCam, physicsCam);

		gameCamera.guiCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		gameCamera.physicsCam.position.set(physicsPort.getWorldWidth() / 2, physicsPort.getWorldHeight() / 2, 0);

		ParticleEffect particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal("stardust__.particle"), Gdx.files.internal(""));
		particleEffectPool = new ParticleEffectPool(particleEffect, 100, 100);

		entityFactory = new EntityFactory();
		LevelLoader.loadMap(level.path(), engine, world, entityFactory);

		IngameInputProcessor ingameInputProcessor = new IngameInputProcessor(engine, gameCamera, world, game, this);
		Gdx.input.setInputProcessor(ingameInputProcessor);
		engine.addSystem(new GravitySystem());
		engine.addSystem(new LifespanSystem());
		engine.addSystem(new DestroySystem(engine, world));
		engine.addSystem(new SpawnerSystem(engine, world, particleEffectPool::obtain, entityFactory));
		goalSystem = new GoalSystem(this);
		engine.addSystem(goalSystem);
		engine.addSystem(new DrawPlanetSystem(batch, gameCamera));
		engine.addSystem(new DrawGoalSystem(batch));
		engine.addSystem(new DrawDustSystem(batch));
		engine.addSystem(new RemoveParticlesSystem());


		world.setContactListener(new WorldContactListener());
		fpsLogger = new FPSLogger();
	}

	@Override
	public void render(float delta) {
		fpsLogger.log();

		gameCamera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(delta, 6, 2);
		batch.setProjectionMatrix(gameCamera.guiCam.combined);
		batch.begin();
		engine.update(delta);
		batch.end();

		if (levelIsWon) {
			batch.setProjectionMatrix(levelWon.getStage().getCamera().combined);
			levelWon.getStage().draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		physicsPort.update(width, height);
		if (levelIsWon) {
			levelWon = new LevelWonScene(gamePort.getScreenWidth(), gamePort.getScreenHeight(), game, this);
		}
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		engine.removeAllEntities();
	}

	public void levelIsWon() {
		goalSystem.setProcessing(false);
		levelIsWon = true;
		levelWon = new LevelWonScene(gamePort.getScreenWidth(), gamePort.getScreenHeight(), game, this);
	}
}
