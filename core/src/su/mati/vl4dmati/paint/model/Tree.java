package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Queue;

import java.util.Random;

public class Tree {

    private Queue<TreeBlock> tree;
    private Queue<TreeBranch> branches;
    private Random random;
    private final int size = 20;

    public Tree() {
        tree = new Queue<>();
        branches = new Queue<>();
        init();
    }

    private void createBlock(int position) {
        TreeBlock block = new TreeBlock(new Texture("treeBlock.jpg"), 0, 0);
        block.setPosition(GameCamera.center - block.getWidth()/2, block.getHeight() * position);
        tree.addLast(block);

        // random generate tree branch
        if ((tree.size > 2) && (branches.isEmpty() || !branches.last().getBlock().equals(tree.get(tree.size - 2)))) {
            if (random.nextBoolean()) {
                boolean left = random.nextBoolean();
                TreeBranch branch = new TreeBranch(new Texture("treeBranch.png"), 0, 0, left, block);
                branch.setPosition(GameCamera.center - block.getWidth() / 2 + (left ? -branch.getWidth() : block.getWidth()), block.getHeight() * position);
                if (!left) {
                    branch.flip(true, false);
                }
                branches.addLast(branch);
            }
        }
    }

    private void init() {
        random = new Random();
        for (int i = 0; i < size; i++) {
            createBlock(i);
        }
    }

    public void draw(SpriteBatch batch, float delta) {
        for (TreeBlock block : tree) {
            block.draw(batch, delta);
        }
        for (TreeBranch branch : branches) {
            branch.draw(batch, delta);
        }
    }

    public void update() {
        if (tree.size < size) {
            createBlock(size - 1);
        }
    }

    public void onTouchDown() {
        if (!tree.isEmpty() && !branches.isEmpty() && tree.first().equals(branches.first().getBlock())) {
            branches.removeFirst();
        }
        tree.removeFirst();
        for (TreeBlock block : tree) {
            block.setAnimation(block.getHeight() * 7, 0, -block.getHeight());
        }
        for (TreeBranch branch : branches) {
            branch.setAnimation(branch.getBlock().getHeight() * 7, 0, -branch.getBlock().getHeight());
        }
    }

    public boolean isGameOver(boolean personLeft) {
        if ((tree.size > 1) && (branches.size > 0) && (tree.first().equals(branches.first().getBlock()) || tree.get(1).equals(branches.first().getBlock()))) {
            if (personLeft == branches.first().left) {
                return true;
            }
        }
        return false;
    }

}
