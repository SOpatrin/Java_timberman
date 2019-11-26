package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.graphics.Texture;

public class TreeBlock extends GameObject {

    public TreeBlock(Texture texture, float x, float y) {
        super(texture, x, y);
        setScale(1.5f, 1);
    }

}
