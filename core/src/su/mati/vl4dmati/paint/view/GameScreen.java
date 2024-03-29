package su.mati.vl4dmati.paint.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import su.mati.vl4dmati.paint.Paint;
import su.mati.vl4dmati.paint.model.Config;
import su.mati.vl4dmati.paint.model.DeathScore;
import su.mati.vl4dmati.paint.model.GameCamera;
import su.mati.vl4dmati.paint.model.GameObject;
import su.mati.vl4dmati.paint.model.Person;
import su.mati.vl4dmati.paint.model.Score;
import su.mati.vl4dmati.paint.model.Tree;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private GameCamera camera;
    private Tree tree;
    private Person person;
    private BitmapFont font;
    private Texture background;
    private Array<Sound> woodSounds;
    private Sound gameOverSound;
    private Music backgroundSound;
    private float backgroundWidth;
    private float backgroundHeight;
    private float backgroundAspectRatio;
    private float backgroundX;
    private Score score;
    private DeathScore deathScore;
    private ShapeRenderer shapeRenderer;
    private Paint game;
    private Config config;

    public GameScreen(Paint game) {
        this.game = game;

        // init environment
        camera = new GameCamera();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.setColor(Color.WHITE);
        font.getData().setScale(0.3f);
        background = new Texture("background.jpg");


        woodSounds = new Array<>();
        woodSounds.add(Gdx.audio.newSound(Gdx.files.internal("wood1.mp3")),
                Gdx.audio.newSound(Gdx.files.internal("wood2.mp3")),
                Gdx.audio.newSound(Gdx.files.internal("wood3.mp3")),
                Gdx.audio.newSound(Gdx.files.internal("wood4.mp3")));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("classic_hurt.mp3"));
        backgroundSound = Gdx.audio.newMusic(Gdx.files.internal("Static-X - The Only.mp3"));

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        backgroundAspectRatio = (float) background.getHeight() / background.getWidth();
        score = new Score();
        deathScore = new DeathScore();

        // init objects
        tree = new Tree();
        person = new Person(new Texture("Idle.gif"), 0, 0);
        person.setPosition(GameCamera.center - person.getWidth(), 0);
        config = new Config();
        backgroundSound.setVolume(0.05f * config.getMasterVolume());
        backgroundSound.play();

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int x, int y, int pointer, int button) {
                if (pointer > 0 || button > 0) return false;
                person.onTouchDown(x, y);
                if (tree.isGameOver(person.left)) {
                    gameOverSound.play(1f * config.getMasterVolume());
                    score.saveScore();
                    game.onGameOver();
                }
                tree.onTouchDown();
                woodSounds.random().play(1f * config.getMasterVolume());
                score.incrementScore();
                deathScore.onTouchDown();
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

        update(delta);

        batch.begin();
        batch.draw(background, backgroundX, 0, backgroundWidth, backgroundHeight);
        tree.draw(batch, delta);
        person.draw(batch, delta);
        font.draw(
                batch,
                String.valueOf(score.getValue()),
                GameCamera.center - (String.valueOf(score.getValue()).length() * 42),
                GameCamera.width * GameCamera.getAspectRatio() - 200);
        batch.end();

        // render death score bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(204/255f, 204/255f, 0, 1);
        shapeRenderer.rect(
                GameCamera.center - GameObject.basicWidth/2 - 10,
                GameCamera.width * GameCamera.getAspectRatio() - 100 - 10,
                GameObject.basicWidth + 20,
                GameObject.basicHeight/5 + 20);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(102/255f, 51/255f, 0, 1);
        shapeRenderer.rect(
                GameCamera.center - GameObject.basicWidth/2,
                GameCamera.width * GameCamera.getAspectRatio() - 100,
                GameObject.basicWidth, GameObject.basicHeight/5);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(
                GameCamera.center - GameObject.basicWidth/2,
                GameCamera.width * GameCamera.getAspectRatio() - 100,
                (deathScore.getValue() / 150f) * GameObject.basicWidth,
                GameObject.basicHeight / 5);
        shapeRenderer.end();
    }

    public void update(float delta) {
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
            backgroundWidth = (GameCamera.width * GameCamera.getAspectRatio()) / backgroundAspectRatio;
            backgroundHeight = GameCamera.width * GameCamera.getAspectRatio();
        }
        tree.update();
        deathScore.update(delta);
        if (deathScore.getValue() <= 0) {
            gameOverSound.play(1f * config.getMasterVolume());
            score.saveScore();
            game.onGameOver();
        }
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
        backgroundSound.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        for (Sound sound : woodSounds) {
            sound.dispose();
        }
        gameOverSound.dispose();
        backgroundSound.dispose();
    }

}
