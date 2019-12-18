package su.mati.vl4dmati.paint.model;

public class DeathScore {

    float value;
    float speed;

    public DeathScore() {
        value = 100f;
        speed = 7f;
    }

    public void update(float delta) {
        if (value > 0) {
            value -= speed * delta;
        }
    }

    public void onTouchDown() {
        if (value < 150) {
            value++;
        }
    }

    public int getValue() {
        return Math.round(value);
    }
}
