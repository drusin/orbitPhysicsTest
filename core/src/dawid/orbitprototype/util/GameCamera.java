package dawid.orbitprototype.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.RequiredArgsConstructor;

import static dawid.orbitprototype.MyGdxGame.scaleDown;

@RequiredArgsConstructor
public class GameCamera {

	public final Viewport guiViewport;
	public final OrthographicCamera guiCam;
	public final OrthographicCamera physicsCam;

	public void unproject(Vector3 input) {
		guiViewport.unproject(input);
	}

	public void translate(float x, float y) {
		guiCam.translate(x, y);
		physicsCam.translate(scaleDown(x), scaleDown(y));
	}

	public void translate(Vector2 vector) {
		translate(vector.x * guiCam.zoom, vector.y * guiCam.zoom);
	}

	public void update() {
		System.out.println(guiCam.zoom);
		guiCam.update();
		physicsCam.update();
	}

	public void zoom(float zoom) {
		if (guiCam.zoom + zoom > 4f || guiCam.zoom + zoom < 0.4f) {
			return;
		}
		guiCam.zoom += zoom;
		physicsCam.zoom = guiCam.zoom;
	}
}