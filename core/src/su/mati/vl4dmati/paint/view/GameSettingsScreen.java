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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import su.mati.vl4dmati.paint.Paint;
import su.mati.vl4dmati.paint.model.Button;
import su.mati.vl4dmati.paint.model.Config;
import su.mati.vl4dmati.paint.model.GameCamera;
import su.mati.vl4dmati.paint.model.GameObject;

public class GameSettingsScreen implements Screen {

    private SpriteBatch batch;
    private GameCamera camera;
    private BitmapFont font;
    private Texture background;
    private Button backButton;
    private Button saveButton;
    private Button plusButton;
    private Button minusButton;
    private float backgroundWidth;
    private float backgroundHeight;
    private float backgroundAspectRatio;
    private float backgroundX;
    private ShapeRenderer shapeRenderer;
    private Sound clickSound;
    private Paint game;
    private Config config;

    public GameSettingsScreen(final Paint game) {
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
        saveButton = new Button(new Texture("saveButton.png"), 0, 0);
        saveButton.setPosition(GameCamera.center - backButton.getWidth() / 2, GameCamera.center);
        plusButton = new Button(new Texture("plus.png"), 0, 0);
        plusButton.setPosition(GameCamera.center - backButton.getWidth() / 2, GameCamera.center);
        minusButton = new Button(new Texture("minus.png"), 0, 0);
        minusButton.setPosition(GameCamera.center - backButton.getWidth() / 2, GameCamera.center);

        plusButton.setScale(0.5f);
        minusButton.setScale(0.5f);


        clickSound = Gdx.audio.newSound(Gdx.files.internal("hat.mp3"));
        shapeRenderer = new ShapeRenderer();
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
                saveButton.onTouchDown(x, y);
                if (saveButton.isPressed()) {
                    clickSound.play(1f * config.getMasterVolume());
                    config.save();
                }
                plusButton.onTouchDown(x, y);
                if (plusButton.isPressed()) {
                    clickSound.play(1f * config.getMasterVolume());
                    config.increaseMasterVolume();
                }
                minusButton.onTouchDown(x, y);
                if (minusButton.isPressed()) {
                    clickSound.play(1f * config.getMasterVolume());
                    config.decreaseMasterVolume();
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
        shapeRenderer.setProjectionMatrix(camera.combined);

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
        backButton.setPosition(
                GameCamera.center - backButton.getWidth() - 25,
                100);
        backButton.draw(batch, delta);
        saveButton.setPosition(
                GameCamera.center + 25,
                100);
        saveButton.draw(batch, delta);
        plusButton.setPosition(
                GameCamera.center + GameObject.basicWidth + 30,
                GameCamera.width * GameCamera.getAspectRatio() - 200 - 15);
        plusButton.draw(batch, delta);
        minusButton.setPosition(
                GameCamera.center - GameObject.basicWidth - 30 - minusButton.getWidth(),
                GameCamera.width * GameCamera.getAspectRatio() - 200 - 15);
        minusButton.draw(batch, delta);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(204/255f, 204/255f, 0, 1);
        shapeRenderer.rect(
                GameCamera.center - GameObject.basicWidth - 10,
                GameCamera.width * GameCamera.getAspectRatio() - 200 - 10,
                GameObject.basicWidth * 2 + 20,
                GameObject.basicHeight/3 + 20);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(102/255f, 51/255f, 0, 1);
        shapeRenderer.rect(
                GameCamera.center - GameObject.basicWidth,
                GameCamera.width * GameCamera.getAspectRatio() - 200,
                GameObject.basicWidth * 2, GameObject.basicHeight/3);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(
                GameCamera.center - GameObject.basicWidth,
                GameCamera.width * GameCamera.getAspectRatio() - 200,
                config.getMasterVolume() * GameObject.basicWidth * 2f,
                GameObject.basicHeight / 3f);
        shapeRenderer.end();
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
