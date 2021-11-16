package com.picis.piano.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioRecorder;

public class RecorderManger {
    private final int samples;
    private final boolean isMono;
//    private final AudioRecorder recorder;

    public RecorderManger(int samples, boolean isMono) {
        this.samples = samples;
        this.isMono = isMono;
//        recorder = Gdx.audio.newAudioRecorder(samples, isMono);
    }

    public short[] record(int millis) {
        final short[] data = new short[(int) (samples * ((float) millis / 1000f))];
//        recorder.read(data, 0, data.length);
        return data;
    }
}
