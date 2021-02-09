package org.lizard.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {

    public static void playMusic(String soundFileName) {
        AudioInputStream audioInputStream = null;
        Clip clip = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundFileName).getAbsoluteFile());
        } catch (UnsupportedAudioFileException | IOException unsupportedAudioFileException) {
            unsupportedAudioFileException.printStackTrace();
        }

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException lineUnavailableException) {
            lineUnavailableException.printStackTrace();
        }
        try {
            assert clip != null;
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException lineUnavailableException) {
            lineUnavailableException.printStackTrace();
        }

        clip.start();
    }

}
