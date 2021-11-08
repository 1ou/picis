package com.picis.piano.sheet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MusicClef {
    BASS("bass"),
    TREBLE("treble");

    private final String clef;

    public static MusicClef of(String clef) {
        if (clef.equalsIgnoreCase(BASS.clef)) {
            return BASS;
        }
        if (clef.equalsIgnoreCase(TREBLE.clef)) {
            return TREBLE;
        }
        throw new IllegalArgumentException("MusicClef");
    }
}
