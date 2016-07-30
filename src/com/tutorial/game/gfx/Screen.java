package com.tutorial.game.gfx;

/**
 * Created by Jcs on 30/7/2016.
 */
public class Screen {

    public final int width, height;
    public int[] pixels;

    private SpriteSheet sheet;

    public Screen(int w, int h, SpriteSheet s) {
        width = w;
        height = h;
        sheet = s;

        pixels = new int[width * height];
    }

    public void render(int xp, int yp, int sprite) {
        int xSprite = sprite % 32;
        int ySprite = sprite / 32;
        int sOffset = xSprite * 8 + ySprite * 8 * sheet.width;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                pixels[(x + xp) + (y + yp) * width] = sheet.pixels[x + y * sheet.width + sOffset];
            }
        }
    }

    public void clear() {
        clear(0);
    }

    public void clear(int color) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = color;
        }
    }
}
