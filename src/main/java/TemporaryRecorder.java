import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import io.github.derec4.loudexplodemod_1_20_1.Utils;

public class TemporaryRecorder {

    private static final double THRESHOLD_DB = -40.0;
    private static final int BUFFER_SIZE = 4096;
    private static final String OUTPUT_FILE = "output_with_threshold.wav";
    private static final int SAMPLE_RATE = 16000;
    private static final int SILENCE_DURATION = 5000;

    public static void main(String[] args) {
        try {
            AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);

            microphone.open(format);
            microphone.start();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            boolean recording = false;
            long silenceStart = -1;

            System.out.println("Listening for audio above threshold...");

            while (true) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);
                short[] audioSamples = Utils.bytesToShorts(buffer);

                if (Utils.isAboveThreshold(audioSamples, THRESHOLD_DB)) {
                    if (!recording) {
                        System.out.println("Threshold reached! Starting to record.");
                        recording = true;
                    }
                    out.write(buffer, 0, bytesRead);
                    silenceStart = -1;
                } else if (recording) {

                    if (silenceStart == -1) {
                        silenceStart = System.currentTimeMillis();
                    } else if (System.currentTimeMillis() - silenceStart > SILENCE_DURATION) {
                        System.out.println("Silence detected, stopping recording.");
                        break;
                    }
                }
            }

            microphone.close();
            byte[] audioData = out.toByteArray();
            saveToFile(audioData, format);

        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveToFile(byte[] audioData, AudioFormat format) throws IOException {
        File outputFile = new File(OUTPUT_FILE);
        try (AudioInputStream audioStream = new AudioInputStream(
                new ByteArrayInputStream(audioData), format, audioData.length / format.getFrameSize())) {
            AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, outputFile);
        }
        System.out.println("Recording saved as " + OUTPUT_FILE);
    }
}
