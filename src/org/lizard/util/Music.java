package org.lizard.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Music {


    public static Clip clip;

    private static Music musicHandler = new Music();


    private Music() {
        loadTrack("princeofdarkness.wav");
    }

    private Music(List<String> listOfTracks) {
        //TODO Implement a multi song library
    }

    private static void loadTrack(String fileName) {
        AudioInputStream audioInputStream = null;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());

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
    }

    public static void play() {
        clip.start();
    }

    public static void stop() {
        clip.stop();
    }

    // Amount is in decibels.  Enter negative amount to reduce volume.
    // Enter positive amount to raise volume.
    public static void adjustVolume(float amount, FloatControl gainControl) {

        try {

            gainControl.setValue(amount); //Change volume by the amount

        } catch (NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static boolean isRunning() {
        try {
            return clip.isRunning();
        } catch (NullPointerException e) {
            return false;
        }
    }

}
