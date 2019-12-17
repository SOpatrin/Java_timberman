package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.graphics.Texture;

public class TreeBranch extends GameObject {

    boolean left;
    TreeBlock block;

    public TreeBranch(Texture texture, float x, float y, boolean left, TreeBlock block) {
        super(texture, x, y);
        setScale(2f, 2f);
        this.left = left;
        this.block = block;
    }

    public TreeBlock getBlock() {
        return block;
    }
}
