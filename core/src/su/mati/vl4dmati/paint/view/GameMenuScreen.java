package su.mati.vl4dmati.paint.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import su.mati.vl4dmati.paint.Paint;
import su.mati.vl4dmati.paint.model.Button;
import su.mati.vl4dmati.paint.model.GameCamera;

public class GameMenuScreen implements Screen {

    private SpriteBatch batch;
    private GameCamera camera;
    private BitmapFont font;
    private Texture background;
    private Button startButton;
    private Button exitButton;
    private float backgroundWidth;
    private float backgroundHeight;
    private float backgroundAspectRatio;
    private float backgroundX;
    private Paint game;

    public GameMenuScreen(final Paint game) {
        this.game = game;

        // init environment
        camera = new GameCamera();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.setColor(Color.RED);
        font.getData().setScale(0.3f);
        background = new Texture("background.jpg");
        startButton = new Button(new Texture("startButton.png"), 0, 0);
        startButton.setPosition(GameCamera.center - startButton.getWidth() / 2, GameCamera.center);
        exitButton = new Button(new Texture("exitButton.png"), 0, 0);
        exitButton.setPosition(GameCamera.center - startButton.getWidth() / 2, GameCamera.center);
    }

    @Override
    public void show() {
        backgroundAspectRatio = (float) background.getHeight() / background.getWidth();
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int x, int y, int pointer, int button) {
                startButton.onTouchDown(x, y);
                if (startButton.isPressed()) {
                    game.onStartGame();
                }
                exitButton.onTouchDown(x, y);
                if (exitButton.isPressed()) {
                    game.exit();
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
        startButton.setPosition(
                GameCamera.center - startButton.getWidth() / 2,
                GameCamera.width * GameCamera.getAspectRatio() - 200);
        startButton.draw(batch, delta);
        exitButton.setPosition(
                GameCamera.center - startButton.getWidth() / 2,
                GameCamera.width * GameCamera.getAspectRatio() - 250 - startButton.getHeight());
        exitButton.draw(batch, delta);
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

    }
}
