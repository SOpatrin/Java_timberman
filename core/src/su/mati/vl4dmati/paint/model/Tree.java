package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Queue;

public class Tree {

    private Queue<TreeBlock> tree;
    private final float size = 20;

    public Tree() {
        tree = new Queue<>();
        init();
    }

    private void init() {
        for (int i = 0; i < size; i++) {
            TreeBlock block = new TreeBlock(new Texture("treeBlock.jpg"), 0, 0);
            block.setPosition(GameCamera.center - block.getWidth()/2, block.getHeight() * i);
            tree.addLast(block);
        }
    }

    public void draw(SpriteBatch batch, float delta) {
        for (TreeBlock block : tree) {
            block.draw(batch, delta);
        }
    }

    public void update() {
        if (tree.size < size) {
            TreeBlock block = new TreeBlock(new Texture("treeBlock.jpg"), 0, 0);
            block.setPosition(GameCamera.center - block.getWidth()/2, block.getHeight() * (size - 1));
            tree.addLast(block);
        }
    }

    public void onTouchDown() {
        tree.removeFirst();
        for (TreeBlock block : tree) {
            block.setAnimation(block.getHeight() * 7, 0, -block.getHeight());
        }
    }

}
