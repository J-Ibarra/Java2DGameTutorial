package com.jcs.level.tiles;

import com.jcs.gfx.Screen;
import com.jcs.level.Level;

import java.util.Random;

public class Tile {
    public static int tickCount = 0;
    protected Random random = new Random();

    public static Tile[] tiles = new Tile[256];

    public static Tile grass = new GrassTile(0);
    public static Tile water = new WaterTile(1);
    public static Tile rock = new RockTile(2);
    public static Tile sand = new SandTile(3);
    public static Tile dirt = new DirtTile(4);

    public boolean connectsToGrass = false;
    public boolean connectsToSand = false;
    public boolean connectsToLava = false;
    public boolean connectsToWater = false;

    public final byte id;

    public Tile(int id) {
        this.id = (byte) id;
        if (tiles[id] != null)
            throw new RuntimeException("Duplicate tile ids!");
        tiles[id] = this;
    }

    public void render(Screen screen, Level level, int x, int y) {
    }

    public boolean isSolid() {
        return false;
    }
}
