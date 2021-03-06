package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dawid.orbitprototype.components.Box2dFixtureComponent;
import dawid.orbitprototype.components.DynamicComponent;
import dawid.orbitprototype.components.ParticleComponent;
import dawid.orbitprototype.components.TextureComponent;

import static dawid.orbitprototype.MyGdxGame.scaleUp;

public class DrawDustSystem extends IteratingSystem {

	private final SpriteBatch spriteBatch;
	private final ComponentMapper<Box2dFixtureComponent> fixtureMapper;
	private final ComponentMapper<ParticleComponent> particleMapper;
	private final ComponentMapper<TextureComponent> textureMapper;

	public DrawDustSystem(SpriteBatch spriteBatch) {
		super(Family.all(DynamicComponent.class, ParticleComponent.class, Box2dFixtureComponent.class, TextureComponent.class).get());
		this.spriteBatch = spriteBatch;
		fixtureMapper = ComponentMapper.getFor(Box2dFixtureComponent.class);
		particleMapper = ComponentMapper.getFor(ParticleComponent.class);
		textureMapper = ComponentMapper.getFor(TextureComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		ParticleComponent particleComponent = particleMapper.get(entity);
		Box2dFixtureComponent fixtureComponent = fixtureMapper.get(entity);
		Vector2 position = fixtureComponent.fixture.getBody().getPosition();
		position.x = scaleUp(position.x);
		position.y = scaleUp(position.y);
		TextureComponent textureComponent = textureMapper.get(entity);
		spriteBatch.draw(textureComponent.texture, position.x - 2, position.y - 2);
		particleComponent.particle.setPosition(position.x, position.y);
		particleComponent.particle.draw(spriteBatch, deltaTime);
	}
}
