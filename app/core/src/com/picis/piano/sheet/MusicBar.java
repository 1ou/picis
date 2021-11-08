package com.picis.piano.sheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MusicBar {
    private final int id;
    private final MusicClef clef;
    private final MusicKey key;
    private final MusicDuration duration;
    private List<MusicNote> notes;
}
