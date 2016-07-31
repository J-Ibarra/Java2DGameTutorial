package com.jcs.level;

import com.jcs.level.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class ShowLevel {

    public static Canvas canvas;
    public static BufferedImage img;

    public static boolean render(Level level) {

        try {
            BufferStrategy bs = canvas.getBufferStrategy();
            if (bs == null) {
                canvas.createBufferStrategy(3);
                return true;
            }

            int x = (level.player.lastX >> 4);
            int y = (level.player.lastY >> 4);

            //for (int y = yo - 8; y < yo + 8; y++) {
            //for (int x = xo - 1; x < xo + 1; x++) {
            int i = x + y * level.w;
            if (level.tiles[i] == Tile.water.id)
                img.setRGB(x, y, 0x000080);
            if (level.tiles[i] == Tile.grass.id)
                img.setRGB(x, y, 0x208020);
            if (level.tiles[i] == Tile.rock.id)
                img.setRGB(x, y, 0xa0a0a0);
            if (level.tiles[i] == Tile.dirt.id)
                img.setRGB(x, y, 0x604040);
            if (level.tiles[i] == Tile.sand.id)
                img.setRGB(x, y, 0xa0a040);
            //}
            //s}

            img.setRGB(level.player.x >> 4, level.player.y >> 4, Color.RED.getRGB());

            Graphics g = bs.getDrawGraphics();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            g.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight(), null);

            g.dispose();
            bs.show();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean showMap(Level level, JFrame frame) {

        int[] pixels = new int[level.w * level.h];
        for (int y = 0; y < level.h; y++) {
            for (int x = 0; x < level.w; x++) {
                int i = x + y * level.w;
                if (level.tiles[i] == Tile.water.id)
                    pixels[i] = 0x000080;
                if (level.tiles[i] == Tile.grass.id)
                    pixels[i] = 0x208020;
                if (level.tiles[i] == Tile.rock.id)
                    pixels[i] = 0xa0a0a0;
                if (level.tiles[i] == Tile.dirt.id)
                    pixels[i] = 0x604040;
                if (level.tiles[i] == Tile.sand.id)
                    pixels[i] = 0xa0a040;
            }
        }

        pixels[(level.player.x >> 4) + (level.player.y >> 4) * level.w] = 0xFFFFFF;

        img = new BufferedImage(level.w, level.h, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, level.w, level.h, pixels, 0, level.w);

        canvas = new Canvas();
        Dimension dimension = new Dimension(level.w * 1, level.h * 1);
        canvas.setMinimumSize(dimension);
        canvas.setMaximumSize(dimension);
        canvas.setPreferredSize(dimension);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.setFocusable(false);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);

        return true;
    }

}
