package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
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
		for (Entity dynamic : dynamicEntities) {
			Fixture dynamicFixture = dynamic.getComponent(DynamicComponent.class).fixture;
			Vector2 dynamicPosition = dynamicFixture.getBody().getWorldCenter();
			for (Entity planet : planets) {
				Fixture planetFixture = planet.getComponent(PlanetComponent.class).fixture;
				Shape planetShape = planetFixture.getShape();
				float planetRadius = planetShape.getRadius();
				Vector2 planetPosition = planetFixture.getBody().getWorldCenter();
				Vector2 planetDistance = new Vector2(dynamicPosition).sub(planetPosition);
				float finalDistance = planetDistance.len();
				if (true) { //finalDistance <= planetRadius * 3
					planetDistance.rotate(180);
					float vecSum = Math.abs(planetDistance.x) + Math.abs(planetDistance.y);
					planetDistance.x = planetDistance.x * ((1 / vecSum) * planetRadius / finalDistance);
					planetDistance.y = planetDistance.y * ((1 / vecSum) * planetRadius / finalDistance);
					dynamicFixture.getBody().applyForceToCenter(planetDistance, true);
				}
			}
		}
	}
}
