package com.picis.piano.sheet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MusicKey {
    D("D");

    private final String key;

    public static MusicKey of(String key) {
        if (key.equalsIgnoreCase(D.key)) {
            return D;
        }
        throw new IllegalArgumentException("MusicKey");
    }
}
