package com.picis.piano.sheet;

import com.picis.piano.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.picis.piano.sheet.MusicNote.BAR_SIZE;

public class GuidoExtracter {
    public MusicPiece transformToPiece(String guidoFormatText, String name) {
        String body = StringUtils.between(guidoFormatText, "[", "]");
        String clef = StringUtils.between(body, "\\clef<\"", "\">");
        String key = StringUtils.between(body, "\\key<\"", "\">");
        String duration = StringUtils.between(body, "\\meter<\"", "\">");

        int lastBrake = body.lastIndexOf(">");
        String[] notes = body.substring(lastBrake + 1).split(" ");

        MusicClef musicClef = MusicClef.of(clef);
        MusicKey musicKey = MusicKey.of(key);
        MusicDuration musicDuration = MusicDuration.of(duration);
        List<MusicBar> musicBars = extractNotes(notes, musicClef, musicKey, musicDuration);

        return new MusicPiece(name, "", musicBars, Collections.<MusicBar>emptyList());
    }

    private List<MusicBar> extractNotes(String[] notes, MusicClef clef, MusicKey key, MusicDuration duration) {
        List<MusicNote> musicNotes = new ArrayList<>();
        List<MusicBar> musicBars = new ArrayList<>();
        int index = 1;
        MusicBar musicBar = new MusicBar(index, clef, key, duration);
        int currSize = 0;
        for (String note : notes) {
            String trimmedNote = note.trim();
            String[] params = trimmedNote.split("_");
            MusicNote.Note n = MusicNote.Note.of(params[0]);
            MusicNote.Octave octave = MusicNote.Octave.of(params[1]);
            MusicNote.NoteLasting noteLasting = MusicNote.NoteLasting.of(params[2]);
            MusicNote.Shift shift = MusicNote.Shift.NONE;

            int impact = noteLasting.getImpact();
            boolean isProlonged = false;

            if (params.length > 3 && params[3].equalsIgnoreCase(".")) {
                isProlonged = true;
                impact += impact / 2;
            }

            if (params.length > 4 && params[4].equalsIgnoreCase(".")) {
                isProlonged = true;
                impact += impact / 2;
            }

            if (params.length > 3 && !params[3].equalsIgnoreCase(".")) {
                shift = MusicNote.Shift.of(params[3]);
            }
            musicNotes.add(new MusicNote(n, octave, noteLasting, shift, isProlonged));

            currSize += impact;

            if (currSize == BAR_SIZE) {
                musicBar.setNotes(musicNotes);
                musicBars.add(musicBar);
                musicBar = new MusicBar(++index, clef, key, duration);
                musicNotes = new ArrayList<>();
                currSize = 0;
            }
        }

        return musicBars;
    }
}
