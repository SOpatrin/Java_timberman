package su.mati.vl4dmati.paint.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import su.mati.vl4dmati.paint.Paint;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Paint";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new Paint(), config);
	}
}
