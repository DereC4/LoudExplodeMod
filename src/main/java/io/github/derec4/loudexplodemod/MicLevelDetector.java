package io.github.derec4.loudexplodemod;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class MicLevelDetector {
    private static final double DB_THRESHOLD = 80.0;
    private static final int BUFFER_SIZE = 1024;
    private static final Logger LOGGER = LogUtils.getLogger();
    private volatile boolean micLevelHigh = false;
    private TargetDataLine microphone;

    public MicLevelDetector() {
        startListening();
    }

    private void startListening() {
        new Thread(() -> {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
            try {
                microphone = AudioSystem.getTargetDataLine(format);
                microphone.open(format);
                microphone.start();

                byte[] buffer = new byte[BUFFER_SIZE];
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int numBytesRead;

                while (true) {
                    numBytesRead = microphone.read(buffer, 0, buffer.length);
                    out.write(buffer, 0, numBytesRead);

                    double rms = calculateRMS(buffer, numBytesRead);
                    double db = 20 * Math.log10(rms);

                    micLevelHigh = db > DB_THRESHOLD;

                    out.reset();
                }
            } catch (LineUnavailableException e) {
                LOGGER.error("Microphone line unavailable", e);
            }
        }).start();
    }

    private double calculateRMS(byte[] buffer, int numBytesRead) {
        long sum = 0;
        for (int i = 0; i < numBytesRead; i += 2) {
            int sample = buffer[i + 1] << 8 | buffer[i] & 0xFF;
            sum += (long) sample * sample;
        }
        double mean = sum / (numBytesRead / 2.0);
        return Math.sqrt(mean);
    }

    public boolean isMicLevelHigh() {
        return micLevelHigh;
    }
}
