package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class GameCamera extends OrthographicCamera {

    // number of horizontal parts of display (10 by default)
    public static final float width = 1000f;
    public static final float center = width / 2;
    private static float aspectRatio = (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public GameCamera(){
        super(width, aspectRatio);
        updateAspectRatio();
    }

    private void updateAspectRatio() {
        aspectRatio = (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
    }

    public void updateCamera() {
        updateAspectRatio();
        setToOrtho(false, width, width * aspectRatio);
        update();
    }

    public static float getAspectRatio() {
        return aspectRatio;
    }

}
