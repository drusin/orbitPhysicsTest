package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dawid.orbitprototype.components.DynamicComponent;
import dawid.orbitprototype.components.PlanetComponent;

public class GravitySystem extends EntitySystem {

	private ImmutableArray<Entity> dynamicEntities;
	private ImmutableArray<Entity> planets;

	@Override
	public void addedToEngine(Engine engine) {
		dynamicEntities = engine.getEntitiesFor(Family.all(DynamicComponent.class).get());
		planets = engine.getEntitiesFor(Family.all(PlanetComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (Entity body : dynamicEntities) {
			for (Entity planet : planets) {
				Body bodyBody = body.getComponent(DynamicComponent.class).fixture.getBody();
				Body planetBody = planet.getComponent(PlanetComponent.class).fixture.getBody();

				Vector2 distance = new Vector2(planetBody.getPosition()).sub(bodyBody.getPosition());
				distance.nor();
				float force = 50;
				distance.x = distance.x * planet.getComponent(PlanetComponent.class).fixture.getShape().getRadius() * force * deltaTime;
				distance.y = distance.y * planet.getComponent(PlanetComponent.class).fixture.getShape().getRadius() * force * deltaTime;

				bodyBody.applyLinearImpulse(distance, bodyBody.getLocalCenter(), true);
			}
		}
	}
}
