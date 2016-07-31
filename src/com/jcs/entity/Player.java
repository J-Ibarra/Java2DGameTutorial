package com.jcs.entity;

import com.jcs.Game;
import com.jcs.InputHandler;
import com.jcs.gfx.Color;
import com.jcs.gfx.Screen;
import com.jcs.gfx.TilesIndex;
import com.jcs.level.Level;
import com.jcs.level.tiles.Tile;

public class Player extends Mob {

    private Game game;
    private InputHandler key;
    private int attackDir;

    private boolean attack = false;

    public int lastX, lastY;

    public Player(Game game, InputHandler key) {
        this.game = game;
        this.key = key;
    }

    public void tick() {
        super.tick();

        int xa = 0;
        int ya = 0;
        if (key.up.down) ya--;
        if (key.down.down) ya++;
        if (key.left.down) xa--;
        if (key.right.down) xa++;

        lastX = x;
        lastY = y;


        attackDir = dir;

        move(xa, ya);
    }

    public boolean findStartPos(Level level) {
        while (true) {
            int x = random.nextInt(level.w);
            int y = random.nextInt(level.h);
            if (level.getTile(x, y) == Tile.grass) {
                this.x = x * 16 + 8;
                this.y = y * 16 + 8;
                return true;
            }
        }
    }

    @Override
    public void render(Screen screen) {

        int xt = 0;
        int yt = 0;

        int flip1 = (walkDist >> 3) & 1;
        int flip2 = (walkDist >> 3) & 1;

        if (dir == 1) {
            xt += 2;
        }
        if (dir > 1) {
            flip1 = 0;
            flip2 = ((walkDist >> 4) & 1);
            if (dir == 2) {
                flip1 = 1;
            }
            xt += 4 + ((walkDist >> 3) & 1) * 2;
        }

        int xo = x - 8;
        int yo = y - 11;

        int col = Color.get(-1, 100, 220, 532);
        /*
		 * if (hurtTime > 0) { col = Color.get(-1, 555, 555, 555); }
		 */

        if (isSwimming()) {
            yo += 4;
            int waterColor = Color.get(-1, -1, 115, 335);
            if (tickTime / 8 % 2 == 0) {
                waterColor = Color.get(-1, 335, 5, 115);
            }
            screen.render(xo + 0, yo + 3, TilesIndex.swiming, waterColor, 0);
            screen.render(xo + 8, yo + 3, TilesIndex.swiming, waterColor, 1);
        } else {
            screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2);
            screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2);
        }

        screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1);
        screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1);


        screen.render(xo + 0, yo + 0, 31, Color.get(-1, -1, 555, -1), 0);
        screen.render(xo + 8, yo + 0, 31, Color.get(-1, -1, 555, -1), 1);
        screen.render(xo + 0, yo + 8, 31, Color.get(-1, -1, 555, -1), 2);
        screen.render(xo + 8, yo + 8, 31, Color.get(-1, -1, 555, -1), 3);

    }

}
