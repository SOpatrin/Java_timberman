package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import su.mati.vl4dmati.paint.Paint;

public class Button extends GameObject {

    private boolean pressed;

    public Button(Texture texture, float x, float y) {
        super(texture, x, y);
        setScale(2f);
        pressed = false;
    }

    public void onTouchDown(float x, float y) {
        x = (x / Gdx.graphics.getWidth()) * GameCamera.width;
        y = (GameCamera.width * GameCamera.getAspectRatio()) - (y / Gdx.graphics.getHeight()) * (GameCamera.width * GameCamera.getAspectRatio());
        if (((x >= getBounds().getX()) && (x <= (getBounds().getX() + getWidth())))
            && ((y >= getBounds().getY()) && (y <= (getBounds().getY() + getHeight())))) {
            pressed = true;
        } else {
            pressed = false;
        }
    }

    public boolean isPressed() {
        return pressed;
    }
}
