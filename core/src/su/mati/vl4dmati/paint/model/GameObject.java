package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

public abstract class GameObject {

    Polygon bounds;
    Sprite object;
    private float width, height;
    public static final float basicWidth = 200f, basicHeight = 200f;
    private float xScale = 1f, yScale = 1f;
    private float animationX = 0;
    private float animationY = 0;
    private float speed = 0;
    private float animationTime = 0;


    public GameObject(Texture texture, float x, float y) {
        // setup texture
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        width = basicWidth;
        height = basicHeight * (float) texture.getWidth()/texture.getHeight();
        object = new Sprite(texture);
        object.setOrigin(width / 2f, height / 2f);
        object.setPosition(x, y);

        // setup bounds
        bounds = new Polygon(new float[] {0f, 0f, width, 0f, width, height, 0f, height});
        bounds.setOrigin(width / 2f, height / 2f);
        bounds.setPosition(x, y);

        setSize();

    }

    private void setSize() {
        // resize object
        float width = this.width * xScale;
        float height = this.height * yScale;
        object.setSize(width, height);
    }

    public void draw(SpriteBatch batch, float delta){
        animate(delta);
        object.setPosition(bounds.getX(), bounds.getY());
        object.setRotation(bounds.getRotation());
        object.draw(batch);
    }

    public Polygon getBounds() {
        return bounds;
    }

    public void setScale(float scale) {
        xScale = scale;
        yScale = scale;
        setSize();
    }

    public void setScale(float x, float y) {
        xScale = x;
        yScale = y;
        setSize();
    }

    public void flip(boolean x, boolean y) {
        object.flip(x, y);
    }

    public void setAnimation(float speed, float x, float y) {
        if (this.speed != 0) {
            endAnimate();
        }
        if (speed != 0) {
            this.speed = speed;
            animationX = x;
            animationY = y;
            animationTime = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) / speed;
        }
    }

    public float getWidth() {
        return width * xScale;
    }

    public float getHeight() {
        return height * yScale;
    }

    public void setPosition(float x, float y) {
        bounds.setPosition(x, y);
    }

    private void animate(float delta) {
        if (speed != 0) {
            if (animationTime > delta) {
                float deltaX = speed * delta * (animationX / (float) Math.sqrt(Math.pow(animationX, 2) + Math.pow(animationY, 2)));
                float deltaY = speed * delta * (animationY / (float) Math.sqrt(Math.pow(animationX, 2) + Math.pow(animationY, 2)));
                bounds.translate(deltaX, deltaY);
                animationTime -= delta;
                animationX -= deltaX;
                animationY -= deltaY;
            } else {
                endAnimate();
            }
        }
    }

    private void endAnimate() {
        bounds.translate(animationX, animationY);
        speed = 0;
        animationX = 0;
        animationY = 0;
    }

}
