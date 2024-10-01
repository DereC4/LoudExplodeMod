package io.github.derec4.loudexplodemod_1_20_1;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import javax.sound.sampled.*;
import java.util.Arrays;

public class MicLevelDetectorNew {
    private double dbThreshold = Config.dbThreshold;  // Default from config
    private static final int BUFFER_SIZE = 1024;
    private static final int SAMPLE_RATE = 44100; // Updated to match the example
    private static final Logger LOGGER = LogUtils.getLogger();
    private volatile boolean micLevelHigh = false;
    private volatile boolean running = true;
    private TargetDataLine microphone;
    private Thread listeningThread;

    public MicLevelDetectorNew() {
        startListening();
    }

    public MicLevelDetectorNew(double threshold) {
        dbThreshold = threshold;
        startListening();
    }

    private void startListening() {
        listeningThread = new Thread(() -> {
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

                byte[] buffer = new byte[BUFFER_SIZE       ];
                int numBytesRead;
                while (true) {
                    numBytesRead = microphone.read(buffer, 0, buffer.length);
                    double maxDb = Utils.calculateMaxDb(buffer, numBytesRead);
                    micLevelHigh = maxDb >= translateThreshold(dbThreshold);

                    LOGGER.info("Your Volume: {} dB", maxDb);
                    LOGGER.info("Threshold Value Set: {}", translateThreshold(dbThreshold));
                }
            } catch (LineUnavailableException e) {
                LOGGER.error("Microphone line unavailable", e);
            } finally {
                if (microphone != null && microphone.isOpen()) {
                    microphone.close();
                }
            }
        });
        listeningThread.start();
    }

    public boolean isMicLevelHigh() {
        return micLevelHigh;
    }

    public void setDbThreshold(double threshold) {
        dbThreshold = threshold;
    }

    public void stopListening() {
        running = false;
        if (microphone != null && microphone.isOpen()) {
            microphone.close();
        }
        try {
            listeningThread.join();
        } catch (InterruptedException e) {
            LOGGER.error("Error stopping the listening thread", e);
            Thread.currentThread().interrupt();
        }
    }

    private double translateThreshold(double userFriendlyThreshold) {
        // Translate user-friendly threshold (0 to 30) to actual decibel value (-30 to 0)
        return (userFriendlyThreshold / 100.0) * 60.0 - 30.0;
    }
}