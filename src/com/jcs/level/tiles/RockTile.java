package com.jcs.level.tiles;

import com.jcs.gfx.Color;
import com.jcs.gfx.Screen;
import com.jcs.gfx.TilesIndex;
import com.jcs.level.Level;

public class RockTile extends Tile {

    public RockTile(int id) {
        super(id);
    }

    @Override
    public void render(Screen screen, Level level, int x, int y) {
        int col = Color.get(444, 444, 333, 333);
        int transitionColor = Color.get(111, 444, 555, level.dirtColor);

        boolean u = level.getTile(x, y - 1) != this;
        boolean d = level.getTile(x, y + 1) != this;
        boolean l = level.getTile(x - 1, y) != this;
        boolean r = level.getTile(x + 1, y) != this;

        boolean ul = level.getTile(x - 1, y - 1) != this;
        boolean dl = level.getTile(x - 1, y + 1) != this;
        boolean ur = level.getTile(x + 1, y - 1) != this;
        boolean dr = level.getTile(x + 1, y + 1) != this;

        int xoff = -4;
        int yoff = 10;
        int x0 = 4 + xoff;
        int x1 = 5 + xoff;
        int x2 = 6 + xoff;
        int x3 = 7 + xoff;
        int x4 = 8 + xoff;

        int y0 = 0 + yoff;
        int y1 = 1 + yoff;
        int y2 = 2 + yoff;

        if (!u && !l) {
            if (!ul)
                screen.render(x * 16 + 0, y * 16 + 0, 0 + TilesIndex.basic, col, 0);
            else
                screen.render(x * 16 + 0, y * 16 + 0, x3 + (y0 * 32), transitionColor, 3);
        } else
            screen.render(x * 16 + 0, y * 16 + 0, (l ? x2 : x1) + ((u ? y2 : y1) * 32), transitionColor, 3);

        if (!u && !r) {
            if (!ur)
                screen.render(x * 16 + 8, y * 16 + 0, 1 + TilesIndex.basic, col, 0);
            else
                screen.render(x * 16 + 8, y * 16 + 0, x4 + (y0 * 32), transitionColor, 3);
        } else
            screen.render(x * 16 + 8, y * 16 + 0, (r ? x0 : x1) + ((u ? y2 : y1) * 32), transitionColor, 3);

        if (!d && !l) {
            if (!dl)
                screen.render(x * 16 + 0, y * 16 + 8, 2 + TilesIndex.basic, col, 0);
            else
                screen.render(x * 16 + 0, y * 16 + 8, x3 + (y1 * 32), transitionColor, 3);
        } else
            screen.render(x * 16 + 0, y * 16 + 8, (l ? x2 : x1) + ((d ? y0 : y1) * 32), transitionColor, 3);
        if (!d && !r) {
            if (!dr)
                screen.render(x * 16 + 8, y * 16 + 8, 3 + TilesIndex.basic, col, 0);
            else
                screen.render(x * 16 + 8, y * 16 + 8, x4 + (y1 * 32), transitionColor, 3);
        } else
            screen.render(x * 16 + 8, y * 16 + 8, (r ? x0 : x1) + ((d ? y0 : y1) * 32), transitionColor, 3);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
