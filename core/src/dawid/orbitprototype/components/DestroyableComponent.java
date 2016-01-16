package dawid.orbitprototype.components;

import com.badlogic.ashley.core.Component;
import lombok.Setter;

@Setter
public class DestroyableComponent implements Component{
	public boolean destroy = false;
}
