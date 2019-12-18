package su.mati.vl4dmati.paint;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import su.mati.vl4dmati.paint.view.GameMenuScreen;
import su.mati.vl4dmati.paint.view.GameOverScreen;
import su.mati.vl4dmati.paint.view.GameScoreScreen;
import su.mati.vl4dmati.paint.view.GameScreen;
import su.mati.vl4dmati.paint.view.GameSettingsScreen;

public class Paint extends Game {

	Screen gameScreen;
	Screen gameOverScreen;
	Screen gameMenuScreen;
	Screen gameScoreScreen;
	Screen gameSettingsScreen;

	@Override
	public void create () {
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		gameMenuScreen = new GameMenuScreen(this);
		gameScoreScreen = new GameScoreScreen(this);
		gameSettingsScreen = new GameSettingsScreen(this);
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

    public void onScore() {
		setScreen(gameScoreScreen);
	}

	public void onSettings() {
		setScreen(gameSettingsScreen);
	}

    public void exit() {
		Gdx.app.exit();
	}

}
