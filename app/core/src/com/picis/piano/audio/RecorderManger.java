package com.picis.piano.audio;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.backends.lwjgl.audio.JavaSoundAudioRecorder;
import lombok.SneakyThrows;

public class RecorderManger {
    private final int samples;
    private final boolean isMono;
    private final AudioRecorder recorder;
    private final AudioDispatcher audioDispatcher;

    @SneakyThrows
    public RecorderManger(int sampleRate, boolean isMono) {
        this.samples = sampleRate;
        this.isMono = isMono;
        audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, 1024, 0);
        recorder = new JavaSoundAudioRecorder(sampleRate, isMono);

        PitchDetectionHandler pdh = (res, e) -> {
            final float pitchInHz = res.getPitch();
            if (pitchInHz != -1.0) {
                processPitch(pitchInHz);
            }
        };
        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        audioDispatcher.addAudioProcessor(pitchProcessor);

        Thread audioThread = new Thread(audioDispatcher, "Audio Thread");
        audioThread.start();
    }

    public void processPitch(float pitchInHz) {
        String pitch = String.valueOf(pitchInHz);
        String note = "";

        if (pitchInHz >= 110 && pitchInHz < 123.47) {
            note = "A1";
        } else if (pitchInHz >= 123.47 && pitchInHz < 130.81) {
            note = "B1";
        } else if (pitchInHz >= 130.81 && pitchInHz < 146.83) {
            note = "C1";
        } else if (pitchInHz >= 146.83 && pitchInHz < 164.81) {
            note = "D1";
        } else if (pitchInHz >= 164.81 && pitchInHz <= 174.61) {
            note = "E1";
        } else if (pitchInHz >= 174.61 && pitchInHz < 185) {
            note = "F1";
        } else if (pitchInHz >= 185 && pitchInHz < 196) {
            note = "G1";
        }

        System.out.println("HZ: " + pitch + " Note: " + note);
    }

    public short[] record(int millis) {
        final short[] data = new short[(int) (samples * ((float) millis / 1000f))];
        recorder.read(data, 0, data.length);
        for (short datum : data) {
//            System.out.print(datum + " ");
        }
        return data;
    }
}
