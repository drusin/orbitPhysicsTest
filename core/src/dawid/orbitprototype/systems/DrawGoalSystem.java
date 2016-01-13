package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dawid.orbitprototype.components.Box2dFixtureComponent;
import dawid.orbitprototype.components.GoalComponent;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

public class DrawGoalSystem extends IteratingSystem {

	private final SpriteBatch batch;
	private final Array<Texture> sprites = new Array<>();
	private final ComponentMapper<GoalComponent> goalMapper;
	private final ComponentMapper<Box2dFixtureComponent> fixtureMapper;

	public DrawGoalSystem(SpriteBatch batch) {
		super(Family.all(GoalComponent.class, Box2dFixtureComponent.class).get());
		this.batch = batch;
		sprites.add(new Texture("graphics/goal/000.png"));
		sprites.add(new Texture("graphics/goal/020.png"));
		sprites.add(new Texture("graphics/goal/040.png"));
		sprites.add(new Texture("graphics/goal/060.png"));
		sprites.add(new Texture("graphics/goal/080.png"));
		sprites.add(new Texture("graphics/goal/100.png"));
		goalMapper = ComponentMapper.getFor(GoalComponent.class);
		fixtureMapper = ComponentMapper.getFor(Box2dFixtureComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		GoalComponent goalComponent = goalMapper.get(entity);
		Box2dFixtureComponent fixtureComponent = fixtureMapper.get(entity);
		int spriteNum = (int) Math.floor(goalComponent.allBodies / 20);
		float alpha = (goalComponent.allBodies % 20) / 20f;
		Vector2 position = fixtureComponent.fixture.getBody().getPosition();
		position.x = scaleUp(position.x);
		position.y = scaleUp(position.y);
		float radius = scaleUp(fixtureComponent.fixture.getShape().getRadius());
		batch.draw(sprites.get(spriteNum), position.x - radius, position.y - radius, radius * 2, radius * 2);
		if (spriteNum < sprites.size -1) {
			batch.setColor(1f, 1f, 1f, alpha);
			batch.draw(sprites.get(spriteNum +1), position.x - radius, position.y - radius, radius * 2, radius * 2);
			batch.setColor(1f, 1f, 1f, 1f);
		}
	}
}
