package com.picis.piano.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Staff implements UiObject {
    private final Line line1;
    private final Line line2;
    private final Line line3;
    private final Line line4;
    private final Line line5;

    public static Staff generate(int width, int height) {
        int leftShift = width / 15;
        int rightShift = width / 15;

        int topShift = height / 10;
        int downShift = height / 10;
        int sizeBetweenLines = height / 17;

        return Staff.builder()
            .line1(new Line(leftShift, height - topShift, width - rightShift, height - topShift))
            .line2(new Line(leftShift, height - topShift - sizeBetweenLines , width - rightShift, height - topShift - sizeBetweenLines))
            .line3(new Line(leftShift, height - topShift - sizeBetweenLines * 2, width - rightShift, height - topShift - sizeBetweenLines * 2))
            .line4(new Line(leftShift, height - topShift - sizeBetweenLines * 3, width - rightShift, height - topShift - sizeBetweenLines * 3))
            .line5(new Line(leftShift, height - topShift - sizeBetweenLines * 4, width - rightShift, height - topShift - sizeBetweenLines * 4))
            .build();
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class Line implements UiObject {
        private final int x1;
        private final int y1;
        private final int x2;
        private final int y2;
    }
}
