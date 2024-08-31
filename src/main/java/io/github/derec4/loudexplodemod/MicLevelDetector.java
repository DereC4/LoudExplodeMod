package io.github.derec4.loudexplodemod;

import javax.sound.sampled.*;

public class MicLevelDetector {
    private static final float THRESHOLD = 0.8f; // Adjust threshold as needed

    public boolean isMicLevelHigh() {
        try {
            AudioFormat format = new AudioFormat(16000, 8, 1, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            byte[] buffer = new byte[1024];
            int bytesRead = microphone.read(buffer, 0, buffer.length);
            float[] samples = new float[bytesRead / 2];

            for (int i = 0, s = 0; i < bytesRead; ) {
                int sample = 0;

                sample |= buffer[i++] & 0xFF; // (reverse these two lines
                sample |= buffer[i++] << 8;   //  if the format is wrong)

                samples[s++] = sample / 32768f;
            }

            float level = 0;
            for (float sample : samples) {
                level += sample * sample;
            }

            level = (float) Math.sqrt(level / samples.length);

            return level > THRESHOLD;
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isMicLevelHigh() {
        return micLevelHigh;
    }
}
