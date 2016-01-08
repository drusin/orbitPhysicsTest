package dawid.orbitprototype.systems;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import dawid.orbitprototype.components.GoalComponent;

public class DrawGoalSystem extends IteratingSystem {

	private final SpriteBatch batch;
	Array<Texture> sprites = new Array<>();

	public DrawGoalSystem(SpriteBatch batch) {
		super(Family.all(GoalComponent.class).get());
		this.batch = batch;
		sprites.add(new Texture("graphics/goal/000.png"));
		sprites.add(new Texture("graphics/goal/020.png"));
		sprites.add(new Texture("graphics/goal/040.png"));
		sprites.add(new Texture("graphics/goal/060.png"));
		sprites.add(new Texture("graphics/goal/080.png"));
		sprites.add(new Texture("graphics/goal/100.png"));
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		GoalComponent goalComponent = entity.getComponent(GoalComponent.class);
		int spriteNum = (int) Math.floor(goalComponent.allBodies / 20);
		float alpha = (goalComponent.allBodies % 20) / 20f;
		Vector2 position = goalComponent.fixture.getBody().getPosition();
		float radius = scaleUp(goalComponent.fixture.getShape().getRadius());
		batch.draw(sprites.get(spriteNum), scaleUp(position.x) - radius, scaleUp(position.y) - radius, radius * 2, radius * 2);
		if (spriteNum < sprites.size -1) {
			batch.setColor(1f, 1f, 1f, alpha);
			batch.draw(sprites.get(spriteNum +1), scaleUp(position.x) - radius, scaleUp(position.y) - radius, radius * 2, radius * 2);
			batch.setColor(1f, 1f, 1f, 1f);
		}
	}
}
