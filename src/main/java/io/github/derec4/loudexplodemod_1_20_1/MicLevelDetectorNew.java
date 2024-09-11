package io.github.derec4.loudexplodemod_1_20_1;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class MicLevelDetectorNew {
    private double dbThreshold = Config.dbThreshold;  // Default from config
    private static final int BUFFER_SIZE = 4096;
    private static final int SAMPLE_RATE = 16000; // Adapted from AudioDetector
    private static final Logger LOGGER = LogUtils.getLogger();
    private volatile boolean micLevelHigh = false;
    private TargetDataLine microphone;

    public MicLevelDetectorNew() {
        startListening();
    }

    public MicLevelDetectorNew(double threshold) {
        dbThreshold = threshold;
        startListening();
    }

    private void startListening() {
        new Thread(() -> {
            AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
            try {
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                microphone = (TargetDataLine) AudioSystem.getLine(info);

                Mixer.Info[] mixers = AudioSystem.getMixerInfo();
                System.out.println("Available Audio Devices:");
                Arrays.stream(mixers).forEach(m -> System.out.println(m.getName()));
                System.out.println("------------------------\n");

                microphone.open(format);
                microphone.start();

                byte[] buffer = new byte[BUFFER_SIZE];
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                short[] shortBuffer;
                int numBytesRead;

                while (true) {
                    numBytesRead = microphone.read(buffer, 0, buffer.length);
                    shortBuffer = Utils.bytesToShorts(buffer);

                    double highestDb = Utils.getHighestAudioLevel(shortBuffer);

                    System.out.println("Your Volume: " + highestDb + " dB");
                    System.out.println("Threshold Value Set: " + dbThreshold);

                    micLevelHigh = highestDb >= dbThreshold;

                    out.reset(); // Reset buffer after each read
                }
            } catch (LineUnavailableException e) {
                LOGGER.error("Microphone line unavailable", e);
            }
        }).start();
    }

    public boolean isMicLevelHigh() {
        return micLevelHigh;
    }

    public void setDbThreshold(double threshold) {
        dbThreshold = threshold;
    }
}
