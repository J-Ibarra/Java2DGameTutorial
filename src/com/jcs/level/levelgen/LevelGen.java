package com.jcs.level.levelgen;

import com.jcs.level.tiles.Tile;

public class LevelGen {

    public static byte[] generateLevel(int w, int h, int s) {
        byte[] data = new byte[w * h];

        Noise mnoise1 = new Noise(w, h, s);
        Noise mnoise2 = new Noise(w, h, s);
        Noise mnoise3 = new Noise(w, h, s);
        Noise mnoise4 = new Noise(w, h, s);

        Noise noise1 = new Noise(w, h, s);
        Noise noise2 = new Noise(w, h, s);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                double val = Math.abs(noise1.getData(x, y) - noise2.getData(x, y)) * 4 - 2;
                double mval = mnoise1.getData(x, y) - mnoise2.getData(x, y);
                mval = mval - mnoise3.getData(x, y) - mnoise4.getData(x, y);
                mval = Math.abs(mval) * 4 - 2;

                data[x + y * w] = Tile.grass.id;

                if (val < -0.5) {
                    data[x + y * w] = Tile.water.id;
                }

                if (val > 0.5 && mval < -1.0) {
                    data[x + y * w] = Tile.rock.id;
                }
            }
        }

        return data;
    }
}
