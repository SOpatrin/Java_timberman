package su.mati.vl4dmati.paint;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import su.mati.vl4dmati.paint.view.GameScreen;

public class Paint extends Game {

	Screen gameScreen;

	@Override
	public void create () {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

}
