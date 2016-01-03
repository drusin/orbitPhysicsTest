package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import dawid.orbitprototype.components.GoalComponent;

import static dawid.orbitprototype.MyGdxGame.scaleDown;
import static dawid.orbitprototype.MyGdxGame.scaleUp;

public class DrawPercentageSystem extends IteratingSystem {

	private final SpriteBatch batch;
	private final BitmapFont font;

	public DrawPercentageSystem(SpriteBatch batch) {
		super(Family.all(GoalComponent.class).get());
		this.batch = batch;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/coolvetica rg.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.borderWidth = 1;
		font = generator.generateFont(parameter);
		generator.dispose();
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		GoalComponent goalComponent = entity.getComponent(GoalComponent.class);
		Vector2 position = goalComponent.fixture.getBody().getPosition();
		float radius = goalComponent.fixture.getShape().getRadius() + scaleDown(10);
		font.draw(batch, String.valueOf(Math.round(goalComponent.allBodies)) + "%", scaleUp(position.x), scaleUp(position.y - radius));
	}
}
