package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.graphics.Texture;

public class TestObject extends GameObject {
    public TestObject(float x, float y) {
        super(new Texture("badlogic.jpg"), x - 2f, y - 2f);
    }
}
