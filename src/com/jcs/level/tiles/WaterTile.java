package com.jcs.level.tiles;

import com.jcs.gfx.Color;
import com.jcs.gfx.Screen;
import com.jcs.gfx.TilesIndex;
import com.jcs.level.Level;

import java.util.Random;

public class WaterTile extends Tile {

    public WaterTile(int id) {
        super(id);
        connectsToWater = true;
        connectsToSand = true;
    }

    private Random wRandom = new Random();

    @Override
    public void render(Screen screen, Level level, int x, int y) {

        wRandom.setSeed(tickCount / 20);

        int col = Color.get(005, 005, 225, 225);
        int transitionColor1 = Color.get(3, 005, level.dirtColor - 111, level.dirtColor);
        int transitionColor2 = Color.get(3, 005, level.sandColor - 111, level.sandColor);

        boolean u = !level.getTile(x, y - 1).connectsToWater;
        boolean d = !level.getTile(x, y + 1).connectsToWater;
        boolean l = !level.getTile(x - 1, y).connectsToWater;
        boolean r = !level.getTile(x + 1, y).connectsToWater;

        boolean su = u && level.getTile(x, y - 1).connectsToSand;
        boolean sd = d && level.getTile(x, y + 1).connectsToSand;
        boolean sl = l && level.getTile(x - 1, y).connectsToSand;
        boolean sr = r && level.getTile(x + 1, y).connectsToSand;

        int xoff = -14;
        int yoff = 6;

        int x0 = 14 + xoff;
        int x1 = 15 + xoff;
        int x2 = 16 + xoff;

        int y0 = 0 + yoff;
        int y1 = 1 + yoff;
        int y2 = 2 + yoff;

        if (!u && !l) {
            screen.render(x * 16 + 0, y * 16 + 0, wRandom.nextInt(4) + TilesIndex.basic, col, wRandom.nextInt(4));
        } else
            screen.render(x * 16 + 0, y * 16 + 0, (l ? x0 : x1) + (u ? y0 : y1) * 32,
                    (su || sl) ? transitionColor2 : transitionColor1, 0);

        if (!u && !r) {
            screen.render(x * 16 + 8, y * 16 + 0, wRandom.nextInt(4) + TilesIndex.basic, col, wRandom.nextInt(4));
        } else
            screen.render(x * 16 + 8, y * 16 + 0, (r ? x2 : x1) + (u ? y0 : y1) * 32,
                    (su || sr) ? transitionColor2 : transitionColor1, 0);

        if (!d && !l) {
            screen.render(x * 16 + 0, y * 16 + 8, wRandom.nextInt(4) + TilesIndex.basic, col, wRandom.nextInt(4));
        } else
            screen.render(x * 16 + 0, y * 16 + 8, (l ? x0 : x1) + (d ? y2 : y1) * 32,
                    (sd || sl) ? transitionColor2 : transitionColor1, 0);
        if (!d && !r) {
            screen.render(x * 16 + 8, y * 16 + 8, wRandom.nextInt(4) + TilesIndex.basic, col, wRandom.nextInt(4));
        } else
            screen.render(x * 16 + 8, y * 16 + 8, (r ? x2 : x1) + (d ? y2 : y1) * 32,
                    (sd || sr) ? transitionColor2 : transitionColor1, 0);

    }

}