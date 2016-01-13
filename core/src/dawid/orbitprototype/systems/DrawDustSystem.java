package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dawid.orbitprototype.components.DynamicComponent;
import dawid.orbitprototype.components.ParticleComponent;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

public class DrawDustSystem extends IteratingSystem {

	private final SpriteBatch spriteBatch;

	public DrawDustSystem(SpriteBatch spriteBatch) {
		super(Family.all(DynamicComponent.class, ParticleComponent.class).get());
		this.spriteBatch = spriteBatch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		DynamicComponent dynamicComponent = entity.getComponent(DynamicComponent.class);
		ParticleComponent particleComponent = entity.getComponent(ParticleComponent.class);
		Vector2 position = dynamicComponent.fixture.getBody().getPosition();
		position.x = scaleUp(position.x);
		position.y = scaleUp(position.y);
		particleComponent.particle.setPosition(position.x, position.y);
		particleComponent.particle.draw(spriteBatch, deltaTime);
	}
}
