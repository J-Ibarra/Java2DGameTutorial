package com.jcs.entity;

import com.jcs.gfx.Screen;
import com.jcs.level.Level;

import java.util.Random;

public class Entity {

    protected final Random random = new Random();

    public int x, y;
    public Level level;

    public int tickTime = 0;

    public final void init(Level level) {
        this.level = level;
    }

    public void render(Screen screen) {
    }

    public void tick() {
        tickTime++;
    }

    public boolean move(int xa, int ya) {
        if (xa != 0 || ya != 0) {
            return move2(xa, 0) | move2(0, ya);
        }
        return false;

    }

    protected boolean move2(int xa, int ya) {
        if (xa != 0 && ya != 0)
            throw new IllegalArgumentException("Move2 can only move along one axis at a time!");
        if (!level.getTile((x + xa) >> 4, (y + ya) >> 4).isSolid()) {
            x += xa;
            y += ya;
            return true;
        } else {
            return false;
        }
    }
}
