package su.mati.vl4dmati.paint.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import su.mati.vl4dmati.paint.model.GameCamera;
import su.mati.vl4dmati.paint.model.Person;
import su.mati.vl4dmati.paint.model.Tree;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private GameCamera camera;
    private Tree tree;
    private Person person;
    private BitmapFont font;
    private Texture background;
    private float backgroundWidth;
    private float backgroundHeight;
    private float backgroundAspectRatio;
    private float backgroundX;
    private int score = 0;

    @Override
    public void show() {
        // init environment
        camera = new GameCamera();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.setColor(Color.WHITE);
        font.getData().setScale(0.3f);
        background = new Texture("background.jpeg");
        backgroundAspectRatio = (float) background.getHeight() / background.getWidth();
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // init objects
        tree = new Tree();
        person = new Person(new Texture("Idle.gif"), 0, 0);
        person.setPosition(GameCamera.center - person.getWidth(), 0);

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int x, int y, int pointer, int button) {
                if (pointer > 0 || button > 0) return false;
                person.onTouchDown(x, y);
                tree.onTouchDown();
                score++;
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        update();

        batch.begin();
        batch.draw(background, backgroundX, 0, backgroundWidth, backgroundHeight);
        tree.draw(batch, delta);
        person.draw(batch, delta);
        font.draw(batch, String.valueOf(score), GameCamera.center - (String.valueOf(score).length() * 42), GameCamera.width * GameCamera.getAspectRatio() - 100);
        batch.end();
    }

    public void update() {
        // align background into the center with covered width and height
        if ((float) background.getHeight()/background.getWidth() > GameCamera.getAspectRatio()) {
            backgroundX = 0;
            backgroundWidth = GameCamera.width;
            backgroundHeight = GameCamera.width * backgroundAspectRatio;
        } else {
            backgroundX = GameCamera.center - (GameCamera.width * GameCamera.getAspectRatio())/2 / backgroundAspectRatio;
            backgroundWidth = (GameCamera.width * GameCamera.getAspectRatio()) / backgroundAspectRatio;
            backgroundHeight = GameCamera.width * GameCamera.getAspectRatio();
        }
        tree.update();
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
    }

}
