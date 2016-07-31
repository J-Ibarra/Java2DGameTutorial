package com.jcs.entity;

import com.jcs.level.tiles.Tile;

public class Mob extends Entity {

    protected int dir = 0;
    protected int walkDist = 0;

    public void tick() {
        super.tick();
    }

    public boolean move(int xa, int ya) {

        if (xa != 0 || ya != 0) {
            if (xa < 0)
                dir = 2;
            if (xa > 0)
                dir = 3;
            if (ya < 0)
                dir = 1;
            if (ya > 0)
                dir = 0;

        }

        if (super.move(xa, ya)) {
            walkDist++;
            return true;
        } else {
            return false;
        }
    }

    protected boolean isSwimming() {
        Tile tile = level.getTile(x >> 4, y >> 4);
        return tile == Tile.water;
    }
}
