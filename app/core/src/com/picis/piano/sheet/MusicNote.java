package com.picis.piano.sheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class MusicNote {
    private final Note note;
    private final Octave octave;
    private final NoteLasting noteLasting;
    private final Shift shift;
    private final boolean isProlonged;

    @AllArgsConstructor
    @Getter
    public enum Shift {
        NONE(""),
        SHARP("#"),
        FLAT("b"),
        NATURAL("?");

        private final String shift;

        public static Shift of(String shift) {
            if (shift.equalsIgnoreCase(SHARP.shift)) {
                return SHARP;
            }
            if (shift.equalsIgnoreCase(FLAT.shift)) {
                return FLAT;
            }
            if (shift.equalsIgnoreCase(NATURAL.shift)) {
                return NATURAL;
            }
            return NONE;
        }
    }

    @AllArgsConstructor
    @Getter
    public enum Note {
        A("a"), B("b"), C("c"), D("d"), E("e"), F("f"), G("g");

        private final String note;

        public static Note of(String note) {
            if (note.equalsIgnoreCase(A.note)) {
                return A;
            }
            if (note.equalsIgnoreCase(B.note)) {
                return B;
            }
            if (note.equalsIgnoreCase(C.note)) {
                return C;
            }
            if (note.equalsIgnoreCase(D.note)) {
                return D;
            }
            if (note.equalsIgnoreCase(E.note)) {
                return E;
            }
            if (note.equalsIgnoreCase(F.note)) {
                return F;
            }
            if (note.equalsIgnoreCase(G.note)) {
                return G;
            }
            throw new IllegalArgumentException("Note");
        }
    }

    public static int BAR_SIZE = 16;

    @AllArgsConstructor
    @Getter
    public enum NoteLasting {
        WHOLE_NOTE("1", 16),
        HALF_NOTE("1/2", 8),
        QUARTER_NOTE("1/4", 4),
        EIGHT_NOTE("1/8", 2),
        SIXTEEN_NOTE("1/16", 1);

        private final String lasting;
        private final int impact;

        public static NoteLasting of(String duration) {
            if (duration.equalsIgnoreCase(WHOLE_NOTE.lasting)) {
                return WHOLE_NOTE;
            }
            if (duration.equalsIgnoreCase(HALF_NOTE.lasting)) {
                return HALF_NOTE;
            }
            if (duration.equalsIgnoreCase(QUARTER_NOTE.lasting)) {
                return QUARTER_NOTE;
            }
            if (duration.equalsIgnoreCase(EIGHT_NOTE.lasting)) {
                return EIGHT_NOTE;
            }
            if (duration.equalsIgnoreCase(SIXTEEN_NOTE.lasting)) {
                return SIXTEEN_NOTE;
            }
            throw new IllegalArgumentException("Duration");
        }
    }

    @AllArgsConstructor
    @Getter
    public enum Octave {
        CONTRA("C"), GREAT("G"), SMALL("S"), FIRST("1"), SECOND("2"), THIRD("3");

        private final String octave;

        public static Octave of(String octave) {
            if (octave.equalsIgnoreCase(CONTRA.octave)) {
                return CONTRA;
            }
            if (octave.equalsIgnoreCase(GREAT.octave)) {
                return GREAT;
            }
            if (octave.equalsIgnoreCase(SMALL.octave)) {
                return SMALL;
            }
            if (octave.equalsIgnoreCase(FIRST.octave)) {
                return FIRST;
            }
            if (octave.equalsIgnoreCase(SECOND.octave)) {
                return SECOND;
            }
            if (octave.equalsIgnoreCase(THIRD.octave)) {
                return THIRD;
            }
            throw new IllegalArgumentException("Octave");
        }
    }
}
