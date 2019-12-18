package su.mati.vl4dmati.paint;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import su.mati.vl4dmati.paint.view.GameMenuScreen;
import su.mati.vl4dmati.paint.view.GameOverScreen;
import su.mati.vl4dmati.paint.view.GameScreen;

public class Paint extends Game {

	Screen gameScreen;
	Screen gameOverScreen;
	Screen gameMenuScreen;

	@Override
	public void create () {
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		gameMenuScreen = new GameMenuScreen(this);
		setScreen(gameMenuScreen);
	}

	public void onGameOver() {
        setScreen(gameOverScreen);
    }

    public void onStartGame() {
	    setScreen(gameScreen);
    }

    public void onGameMenu() {
	    setScreen(gameMenuScreen);
    }

    public void exit() {
		Gdx.app.exit();
	}

}
