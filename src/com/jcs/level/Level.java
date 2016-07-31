package com.jcs.level;

import com.jcs.entity.Entity;
import com.jcs.entity.Player;
import com.jcs.gfx.Screen;
import com.jcs.level.levelgen.LevelGen;
import com.jcs.level.levelgen.RandomLevel;
import com.jcs.level.tiles.Tile;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Level {

    public int w, h;

    private static boolean isShow = false;

    public byte[] tiles;
    public byte[] data;

    public List<Entity> entities = new ArrayList<Entity>();

    public Player player;

    public int grassColor = 141;
    public int dirtColor = 322;
    public int sandColor = 550;

    public Level(int w, int h) {
        this.w = w;
        this.h = h;

        tiles = RandomLevel.generateLevel(w, h, 4);

    }

    public Level(int w, int h, int s) {
        this.w = w;
        this.h = h;

        tiles = LevelGen.generateLevel(w, h, s);
    }

    public Tile getTile(int x, int y) {
        if (x <= 0 || y <= 0 || x >= w - 1 || y >= h - 1)
            return Tile.rock;
        return Tile.tiles[tiles[x + y * w]];
    }

    public void tick() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).tick();
        }

        if (isShow) {
            renderShowMap();
        }
    }

    public void renderBackground(Screen screen, int xScroll, int yScroll) {
        int xo = xScroll >> 4;
        int yo = yScroll >> 4;
        int w = (screen.w + 15) >> 4;
        int h = (screen.h + 15) >> 4;
        screen.setOffset(xScroll, yScroll);
        for (int y = yo; y <= h + yo; y++) {
            for (int x = xo; x <= w + xo; x++) {
                getTile(x, y).render(screen, this, x, y);
            }
        }
        screen.setOffset(0, 0);
    }

    public void renderSprites(Screen screen, int xScroll, int yScroll) {
        int xo = xScroll >> 4;
        int yo = yScroll >> 4;
        int w = (screen.w + 15) >> 4;
        int h = (screen.h + 15) >> 4;

        screen.setOffset(xScroll, yScroll);

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(screen);
        }

        screen.setOffset(0, 0);
    }

    public void add(Entity entity) {
        if (entity instanceof Player) {
            player = (Player) entity;
        }
        entities.add(entity);
        entity.init(this);
    }

    public void remove(Entity e) {
        entities.remove(e);
    }

    JFrame frame;

    public void showMap() {
        if (!isShow) {
            isShow = ShowLevel.showMap(this, frame = new JFrame());
        } else {
            if (frame != null)
                frame.dispose();
            isShow = false;
        }
    }

    private void renderShowMap() {
        isShow = ShowLevel.render(this);
    }

}
