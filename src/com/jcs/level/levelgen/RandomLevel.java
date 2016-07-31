package com.jcs.level.levelgen;

import java.util.Random;

public class RandomLevel {

    public static byte[] generateLevel(int w, int h, int noTiles) {
        byte[] data = new byte[w * h];
        Random random = new Random();
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                data[i + j * w] = (byte) random.nextInt(noTiles);
            }
        }
        return data;
    }
}
