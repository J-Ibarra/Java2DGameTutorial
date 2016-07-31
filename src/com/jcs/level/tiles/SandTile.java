package com.jcs.level.tiles;

import com.jcs.gfx.Color;
import com.jcs.gfx.Screen;
import com.jcs.gfx.TilesIndex;
import com.jcs.level.Level;

public class SandTile extends Tile {

    public SandTile(int id) {
        super(id);
        connectsToSand = true;
    }

    @Override
    public void render(Screen screen, Level level, int x, int y) {

        int col = Color.get(level.sandColor + 2, level.sandColor, level.sandColor - 110, level.sandColor - 110);
        int transitionColor = Color.get(level.sandColor - 110, level.sandColor, level.sandColor - 110, level.dirtColor);

        boolean u = !level.getTile(x, y - 1).connectsToSand;
        boolean d = !level.getTile(x, y + 1).connectsToSand;
        boolean l = !level.getTile(x - 1, y).connectsToSand;
        boolean r = !level.getTile(x + 1, y).connectsToSand;

        int x0 = 0;
        int x1 = 1;
        int x2 = 2;

        int y0 = 6;
        int y1 = 7;
        int y2 = 8;

        if (!u && !l) {
            screen.render(x * 16 + 0, y * 16 + 0, 0 + TilesIndex.basic, col, 0);
        } else
            screen.render(x * 16 + 0, y * 16 + 0, (l ? x0 : x1) + (u ? y0 : y1) * 32, transitionColor);

        if (!u && !r) {
            screen.render(x * 16 + 8, y * 16 + 0, 1 + TilesIndex.basic, col, 0);
        } else
            screen.render(x * 16 + 8, y * 16 + 0, (r ? x2 : x1) + (u ? y0 : y1) * 32, transitionColor);

        if (!d && !l) {
            screen.render(x * 16 + 0, y * 16 + 8, 2 + TilesIndex.basic, col, 0);
        } else
            screen.render(x * 16 + 0, y * 16 + 8, (l ? x0 : x1) + (d ? y2 : y1) * 32, transitionColor);

        if (!d && !r) {
            screen.render(x * 16 + 8, y * 16 + 8, 3 + TilesIndex.basic, col, 0);
        } else
            screen.render(x * 16 + 8, y * 16 + 8, (r ? x2 : x1) + (d ? y2 : y1) * 32, transitionColor);
    }

}
