package com.picis.piano.sheet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MusicDuration {
    FORTH_TO_FORTH("4/4");

    private final String duration;

    public static MusicDuration of(String duration) {
        if (duration.equalsIgnoreCase(FORTH_TO_FORTH.duration)) {
            return FORTH_TO_FORTH;
        }
        throw new IllegalArgumentException("MusicDuration");
    }
}
