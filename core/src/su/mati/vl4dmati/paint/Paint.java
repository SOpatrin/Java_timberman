package su.mati.vl4dmati.paint;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import su.mati.vl4dmati.paint.view.GameOverScreen;
import su.mati.vl4dmati.paint.view.GameScreen;

public class Paint extends Game {

	Screen gameScreen;
	Screen gameOverScreen;

	@Override
	public void create () {
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		setScreen(gameScreen);
	}

	public void onGameOver() {
        setScreen(gameOverScreen);
    }

    public void onStartGame() {
	    setScreen(gameScreen);
    }

}
