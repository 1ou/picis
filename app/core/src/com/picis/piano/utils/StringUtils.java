package com.picis.piano.utils;

public class StringUtils {
    public static String between(String s, String left, String right) {
        s = s.substring(s.indexOf(left) + left.length());
        s = s.substring(0, s.indexOf(right));
        return s;
    }
}
