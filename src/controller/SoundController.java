package controller;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundController {

    private static void playWav(String fileName) {
        try {
            URL soundURL = SoundController.class.getResource(fileName);
            if (soundURL == null) {
                System.err.println("Sound file not found: " + fileName);
                return;
            }

            // Load âm thanh
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Phát âm thanh (non-blocking)
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playClick() {
        playWav("/sound1.wav");
    }

    public static void playTick() {
        playWav("/sound2.wav");
    }
}
