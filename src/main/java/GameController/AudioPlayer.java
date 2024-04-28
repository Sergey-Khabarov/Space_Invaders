package GameController;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import java.net.URL;

public class AudioPlayer {
    private static final String BACKGROUND_MUSIC_FILE = "music.wav";
    private static final String EXPLOSION_SOUND_FILE = "explosion.wav";
    private final MediaPlayer musicPlayer;
    private final AudioClip explosionPlayer;
    private static final double EXPLOSION_SOUND_VOLUME = 0.1;
    private static final double MUSIC_SOUND_VOLUME = 0.1;

    public AudioPlayer() {
        this.musicPlayer = new MediaPlayer(loadAudioFile());
        this.explosionPlayer = new AudioClip(convertNameToUrl(EXPLOSION_SOUND_FILE));
    }

    public void playBackgroundMusic() {
        if (isPlayingBackgroundMusic()) {
            return;
        }
        this.musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        this.musicPlayer.setVolume(MUSIC_SOUND_VOLUME);
        this.musicPlayer.play();
    }

    public void stopBackgroundMusic() {
        if (isPlayingBackgroundMusic()) {
            this.musicPlayer.stop();
        }
    }

    public boolean isPlayingBackgroundMusic() {
        return MediaPlayer.Status.PLAYING.equals(this.musicPlayer.getStatus());
    }

    public void playExplosionSound() {
        explosionPlayer.play(EXPLOSION_SOUND_VOLUME);
    }

    private Media loadAudioFile() {
        return new Media(convertNameToUrl(AudioPlayer.BACKGROUND_MUSIC_FILE));
    }

    private String convertNameToUrl(String fileName) {
        URL musicSourceUrl = getClass().getClassLoader().getResource(fileName);
        if (musicSourceUrl == null) {
            throw new IllegalArgumentException(
                    "Please ensure that your resources folder contains the appropriate files for this exercise.");
        }
        return musicSourceUrl.toExternalForm();
    }
}
