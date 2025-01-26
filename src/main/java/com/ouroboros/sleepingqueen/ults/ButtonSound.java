/**
 * ButtonSound.java
 * <p>
 * This class is used to play the button click sound.
 *
 * @Author Hai Long Mac
 */

package com.ouroboros.sleepingqueen.ults;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class ButtonSound {
    public static void playButtonClickSound() {
        String soundFile = Objects.requireNonNull(ButtonSound.class.getResource("/com/ouroboros/sleepingqueen/music/buttonSound.mp3")).toExternalForm();
        Media sound = new Media(soundFile);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}

