package com.picis.piano;

import com.picis.piano.sheet.GuidoExtracter;
import com.picis.piano.sheet.MusicBar;
import com.picis.piano.sheet.MusicClef;
import com.picis.piano.sheet.MusicDuration;
import com.picis.piano.sheet.MusicKey;
import com.picis.piano.sheet.MusicNote;
import com.picis.piano.sheet.MusicPiece;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GuidoExtracterTest {
    private final GuidoExtracter guidoExtracter = new GuidoExtracter();

    @Test
    public void test_case_when_parsed_correctly() {
        String testData = "[ \\clef<\"treble\"> \\key<\"D\"> \\meter<\"4/4\">" +
            "a_1_1/2 b_1_1/2 " +
            "a_1_1/4_. g_1_1/8 f_1_1/4_# g_1_1/4 " +
            "a_1_1/2 b_1_1/2 " +
            "a_1_1/4_. g_1_1/8 f_1_1/4_# g_1_1/4 " +
            "a_1_1/2 a_1_1/2 " +
            "b_1_1/2 c_2_1/4_# d_2_1/4 " +
            "c_2_1/2 b_1_1/2 " +
            "a_1_1 ]";

        String name = "name";
        MusicPiece musicPiece = guidoExtracter.transformToPiece(testData, name);

        MusicBar musicBar1 = musicPiece.getStaff1().get(0);

        Assertions.assertEquals(MusicClef.TREBLE, musicBar1.getClef());
        Assertions.assertEquals(MusicDuration.FORTH_TO_FORTH, musicBar1.getDuration());
        Assertions.assertEquals(MusicKey.D, musicBar1.getKey());
        Assertions.assertEquals(name, musicPiece.getName());

        Assertions.assertEquals(1, musicBar1.getId());
        Assertions.assertEquals(2, musicBar1.getNotes().size());
        Assertions.assertEquals(MusicNote.Note.A, musicBar1.getNotes().get(0).getNote());
        Assertions.assertEquals(MusicNote.Note.B, musicBar1.getNotes().get(1).getNote());
        Assertions.assertEquals(MusicNote.Octave.FIRST, musicBar1.getNotes().get(0).getOctave());
        Assertions.assertEquals(MusicNote.Octave.FIRST, musicBar1.getNotes().get(1).getOctave());
        Assertions.assertEquals(MusicNote.Shift.NONE, musicBar1.getNotes().get(0).getShift());
        Assertions.assertEquals(MusicNote.Shift.NONE, musicBar1.getNotes().get(1).getShift());
        Assertions.assertEquals(MusicNote.NoteLasting.HALF_NOTE, musicBar1.getNotes().get(0).getNoteLasting());
        Assertions.assertEquals(MusicNote.NoteLasting.HALF_NOTE, musicBar1.getNotes().get(1).getNoteLasting());
        Assertions.assertFalse(musicBar1.getNotes().get(0).isProlonged());
        Assertions.assertFalse(musicBar1.getNotes().get(1).isProlonged());

        MusicBar musicBar2 = musicPiece.getStaff1().get(1);
        Assertions.assertEquals(4, musicBar2.getNotes().size());
        Assertions.assertEquals(2, musicBar2.getId());
        Assertions.assertEquals(MusicNote.Note.A, musicBar2.getNotes().get(0).getNote());
        Assertions.assertEquals(MusicNote.Note.G, musicBar2.getNotes().get(1).getNote());
        Assertions.assertEquals(MusicNote.Octave.FIRST, musicBar2.getNotes().get(0).getOctave());
        Assertions.assertEquals(MusicNote.Octave.FIRST, musicBar2.getNotes().get(1).getOctave());
        Assertions.assertEquals(MusicNote.Shift.NONE, musicBar2.getNotes().get(0).getShift());
        Assertions.assertEquals(MusicNote.Shift.NONE, musicBar2.getNotes().get(1).getShift());
        Assertions.assertEquals(MusicNote.NoteLasting.QUARTER_NOTE, musicBar2.getNotes().get(0).getNoteLasting());
        Assertions.assertEquals(MusicNote.NoteLasting.EIGHT_NOTE, musicBar2.getNotes().get(1).getNoteLasting());
        Assertions.assertTrue(musicBar2.getNotes().get(0).isProlonged());
        Assertions.assertFalse(musicBar2.getNotes().get(1).isProlonged());
    }
}