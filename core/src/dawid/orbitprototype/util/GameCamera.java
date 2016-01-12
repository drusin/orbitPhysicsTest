package dawid.orbitprototype.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import lombok.RequiredArgsConstructor;

import static dawid.orbitprototype.MyGdxGame.scaleDown;

@RequiredArgsConstructor
public class GameCamera {

	public final OrthographicCamera guiCam;
	public final OrthographicCamera physicsCam;

	public void unproject(Vector3 input) {
		guiCam.unproject(input);
	}

	public void translate(float x, float y) {
		guiCam.translate(x, y);
		physicsCam.translate(scaleDown(x), scaleDown(y));
	}

	public void translate(Vector2 vector) {
		translate(vector.x, vector.y);
	}

	public void update() {
		guiCam.update();
		physicsCam.update();
	}

	public void zoom(float zoom) {
		guiCam.zoom += zoom;
		physicsCam.zoom += zoom;
	}
}