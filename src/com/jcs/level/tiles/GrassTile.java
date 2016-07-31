package com.jcs.level.tiles;

import com.jcs.gfx.Color;
import com.jcs.gfx.Screen;
import com.jcs.gfx.TilesIndex;
import com.jcs.level.Level;

public class GrassTile extends Tile {

    public GrassTile(int id) {
        super(id);
        connectsToGrass = true;
    }

    public void render(Screen screen, Level level, int x, int y) {

        int col = Color.get(level.grassColor, level.grassColor, level.grassColor + 111, level.grassColor + 111);
        int transitionColor = Color.get(level.grassColor - 111, level.grassColor, level.grassColor + 111,
                level.dirtColor);

        boolean u = !level.getTile(x, y - 1).connectsToGrass;
        boolean d = !level.getTile(x, y + 1).connectsToGrass;
        boolean l = !level.getTile(x - 1, y).connectsToGrass;
        boolean r = !level.getTile(x + 1, y).connectsToGrass;

        int x0 = 0;
        int x1 = 1;
        int x2 = 2;

        int y0 = 6;
        int y1 = 7;
        int y2 = 8;

        if (!u && !l) {
            screen.render(x * 16 + 0, y * 16 + 0, 0 + TilesIndex.basic, col);
        } else
            screen.render(x * 16 + 0, y * 16 + 0, (l ? x0 : x1) + (u ? y0 : y1) * 32, transitionColor);

        if (!u && !r) {
            screen.render(x * 16 + 8, y * 16 + 0, 1 + TilesIndex.basic, col);
        } else
            screen.render(x * 16 + 8, y * 16 + 0, (r ? x2 : x1) + (u ? y0 : y1) * 32, transitionColor);

        if (!d && !l) {
            screen.render(x * 16 + 0, y * 16 + 8, 2 + TilesIndex.basic, col);
        } else
            screen.render(x * 16 + 0, y * 16 + 8, (l ? x0 : x1) + (d ? y2 : y1) * 32, transitionColor);
        if (!d && !r) {
            screen.render(x * 16 + 8, y * 16 + 8, 3 + TilesIndex.basic, col);
        } else
            screen.render(x * 16 + 8, y * 16 + 8, (r ? x2 : x1) + (d ? y2 : y1) * 32, transitionColor);
    }

}
