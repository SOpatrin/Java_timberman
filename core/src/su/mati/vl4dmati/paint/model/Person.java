package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Person extends GameObject {

    public boolean left;

    public Person(Texture texture, float x, float y) {
        super(texture, x, y);
        left = true;
        setScale(1.5f);
    }

    public void onTouchDown(int x, int y) {
        // check what half of display was tapped
        if (x >= Gdx.graphics.getWidth()/2f) {
            // if person on the left
            if (left) {
                flip(true, false);
                setPosition(GameCamera.center + 1, getBounds().getY());
                left = false;
            }
        } else {
            // if person on the right
            if (!left) {
                flip(true, false);
                setPosition(GameCamera.center - getWidth(), getBounds().getY());
                left = true;
            }
        }
    }

}
