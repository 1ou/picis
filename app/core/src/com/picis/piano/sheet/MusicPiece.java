package com.picis.piano.sheet;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MusicPiece {
    private final String name;
    private final String tempo;
    private final List<MusicBar> staff1;
    private final List<MusicBar> staff2;
}
