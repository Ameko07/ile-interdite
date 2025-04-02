import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Musique {
    private Clip clip;

    public void jouerMusique(String filePath) {
        try {
            File file = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Joue en boucle
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopperMusique() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }


}
