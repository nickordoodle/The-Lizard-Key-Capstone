package org.lizard.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {


    private static Clip clip = null;

    private Music(){

    }


    public static void play(String soundFileName) {
        AudioInputStream audioInputStream = null;

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

    public static void stop(){
        clip.stop();
    }

    public static boolean isRunning(){
        try {
            return clip.isRunning();
        } catch (NullPointerException e) {
            return false;
        }
    }

}
