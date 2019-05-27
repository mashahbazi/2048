package com.example.my2048;

import java.util.Random;

public class MathUtils {
    private static MathUtils mathUtils;

    private Random rand;

    public MathUtils() {
        rand = new Random();
    }

    public static MathUtils getInstance() {
        if (mathUtils == null)
            mathUtils = new MathUtils();
        return mathUtils;
    }

    public int getRoundInt(int num) {
        return rand.nextInt(num + 1);
    }

    public int getRoundInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
