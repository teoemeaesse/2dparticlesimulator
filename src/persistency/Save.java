package persistency;

import java.io.Serializable;

public class Save implements Serializable {
    private float[][][] playback;
    private String title;

    public Save(float[][][] playback, String title) {
        this.playback = playback;
        this.title = title;
    }

    public float[][][] getPlayback() {
        return playback;
    }

    public String getTitle() {
        return title;
    }
}
