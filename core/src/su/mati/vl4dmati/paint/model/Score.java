package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Score {

    private int value;

    public Score() {
        value = 0;
    }

    public int getValue() {
        return value;
    }

    public void saveScore() {
        FileHandle file = Gdx.files.local("files/score.txt");
        int oldScore = readScore();
        if (oldScore < value) {
            file.writeString(String.valueOf(value), false);
        }
    }

    public void clear() {
        FileHandle file = Gdx.files.local("files/score.txt");
        if (!file.exists()) {
            return;
        }
        file.writeString("0", false);
    }

    public int readScore() {
        FileHandle file = Gdx.files.local("files/score.txt");
        if (!file.exists()) {
            return 0;
        }
        int oldScore;
        try {
            oldScore = Integer.valueOf(file.readString());
        } catch (Exception e) {
            oldScore = 0;
        }
        return oldScore;
    }

    public void incrementScore() {
        value++;
    }
}
