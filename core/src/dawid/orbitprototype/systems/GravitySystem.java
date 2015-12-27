package dawid.orbitprototype.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
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
			Vector2 dynamicPosition = dynamic.getComponent(DynamicComponent.class).fixture.getBody().getWorldCenter();
			for (Entity planet : planets) {
				Shape planetShape = planet.getComponent(PlanetComponent.class).fixture.getShape();
				float planetRadius = planetShape.getRadius();
				Vector2 planetPosition = planet.getComponent(PlanetComponent.class).fixture.getBody().getWorldCenter();
				Vector2 planetDistance = new Vector2(0, 0);
				planetDistance.add(dynamicPosition);
				planetDistance.sub(planetPosition);
				float finalDistance = planetDistance.len();
				if (true) { //finalDistance <= planetRadius * 3
					planetDistance.rotate(180);
					float vecSum = Math.abs(planetDistance.x)+Math.abs(planetDistance.y);
					planetDistance.x = planetDistance.x * ((1/vecSum)*planetRadius/finalDistance);
					planetDistance.y = planetDistance.y * ((1/vecSum)*planetRadius/finalDistance);
					dynamic.getComponent(DynamicComponent.class).fixture.getBody().applyForceToCenter(planetDistance, true);
				}
			}
		}
	}
}
