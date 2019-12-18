package su.mati.vl4dmati.paint.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import su.mati.vl4dmati.paint.Paint;
import su.mati.vl4dmati.paint.model.Button;
import su.mati.vl4dmati.paint.model.Config;
import su.mati.vl4dmati.paint.model.GameCamera;
import su.mati.vl4dmati.paint.model.Score;

public class GameScoreScreen implements Screen {

    private SpriteBatch batch;
    private GameCamera camera;
    private BitmapFont font;
    private Texture background;
    private Button backButton;
    private Button clearButton;
    private float backgroundWidth;
    private float backgroundHeight;
    private float backgroundAspectRatio;
    private float backgroundX;
    private Sound clickSound;
    private Score score;
    private Paint game;
    private Config config;

    public GameScoreScreen(final Paint game) {
        this.game = game;

        // init environment
        camera = new GameCamera();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.setColor(Color.WHITE);
        font.getData().setScale(0.3f);
        background = new Texture("background.jpg");

        backButton = new Button(new Texture("backButton.png"), 0, 0);
        backButton.setPosition(GameCamera.center - backButton.getWidth() / 2, GameCamera.center);
        clearButton = new Button(new Texture("clearButton.png"), 0, 0);
        clearButton.setPosition(GameCamera.center - backButton.getWidth() / 2, GameCamera.center);

        clickSound = Gdx.audio.newSound(Gdx.files.internal("hat.mp3"));
        score = new Score();
    }

    @Override
    public void show() {
        backgroundAspectRatio = (float) background.getHeight() / background.getWidth();
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        config = new Config();

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int x, int y, int pointer, int button) {
                backButton.onTouchDown(x, y);
                if (backButton.isPressed()) {
                    clickSound.play(1f * config.getMasterVolume());
                    game.onGameMenu();
                }
                clearButton.onTouchDown(x, y);
                if (clearButton.isPressed()) {
                    clickSound.play(1f * config.getMasterVolume());
                    score.clear();
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        // align background into the center with covered width and height
        if ((float) background.getHeight()/background.getWidth() > GameCamera.getAspectRatio()) {
            backgroundX = 0;
            backgroundWidth = GameCamera.width;
            backgroundHeight = GameCamera.width * backgroundAspectRatio;
        } else {
            backgroundX = GameCamera.center
                    - (GameCamera.width
                    * GameCamera.getAspectRatio()) / 2
                    / backgroundAspectRatio;
            backgroundWidth = (GameCamera.width
                    * GameCamera.getAspectRatio())
                    / backgroundAspectRatio;
            backgroundHeight = GameCamera.width * GameCamera.getAspectRatio();
        }

        batch.begin();
        batch.draw(background, backgroundX, 0, backgroundWidth, backgroundHeight);
        font.draw(
                batch,
                "Best score:",
                GameCamera.center - ("Best score:".length() * 36),
                GameCamera.width * GameCamera.getAspectRatio() - 200);
        font.draw(
                batch,
                String.valueOf(score.readScore()),
                GameCamera.center - (String.valueOf(score.readScore()).length() * 42),
                GameCamera.width * GameCamera.getAspectRatio() - 350);
        backButton.setPosition(
                GameCamera.center - backButton.getWidth() - 25,
                100);
        backButton.draw(batch, delta);
        clearButton.setPosition(
                GameCamera.center + 25,
                100);
        clearButton.draw(batch, delta);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.updateCamera();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        clickSound.dispose();
    }
}
