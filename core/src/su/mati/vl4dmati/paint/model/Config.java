package su.mati.vl4dmati.paint.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Config {

    FileHandle file;
    float masterVolume;

    public Config() {
        file = Gdx.files.local("files/config.txt");
        readMasterVolume();
    }

    public void save() {
        file.writeString(String.valueOf(masterVolume), false);
    }

    public void readMasterVolume() {
        try {
            masterVolume = Float.valueOf(file.readString());
        } catch (Exception e) {
            masterVolume = 0.5f;
        }
    }

    public void increaseMasterVolume() {
        if (masterVolume < 1f) {
            masterVolume += 0.1f;
        } else {
            masterVolume = 1f;
        }
    }

    public void decreaseMasterVolume() {
        if (masterVolume > 0f) {
            masterVolume -= 0.1f;
        } else {
            masterVolume = 0f;
        }
    }

    public float getMasterVolume() {
        return masterVolume;
    }
}
