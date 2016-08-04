package com.jcs;

import com.jcs.entity.Player;
import com.jcs.gfx.Color;
import com.jcs.gfx.Font;
import com.jcs.gfx.Screen;
import com.jcs.gfx.SpriteSheet;
import com.jcs.level.Level;
import com.jcs.level.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1;

    private boolean running = false;

    public static final int WIDTH = 26 * 8;
    public static final int HEIGHT = 20 * 8;
    public static final int SCALE = 3;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    SpriteSheet sheet;
    Screen screen;

    InputHandler key;

    Level level;
    Player player;

    public Game() throws Exception {
        Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);

        init();
    }

    public void init() throws Exception {
        key = new InputHandler(this);

        sheet = new SpriteSheet("SpriteSheet.png");
        screen = new Screen(WIDTH, HEIGHT, sheet);

        level = new Level(512, 512, 32);
        player = new Player(this, key);
        player.x = 20;
        player.y = 20;
        player.findStartPos(level);
        level.add(player);
    }

    private void tick() {
        key.tick();
        level.tick();

        if (key.map.clicked) {
            level.showMap();
        }

        Tile.tickCount++;
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            requestFocus();
            return;
        }

        int xScroll = player.x - screen.w / 2;
        int yScroll = player.y - (screen.h - 8) / 2;
        if (xScroll < 16)
            xScroll = 16;
        if (yScroll < 16)
            yScroll = 16;
        if (xScroll > level.w * 16 - screen.w - 16)
            xScroll = level.w * 16 - screen.w - 16;
        if (yScroll > level.h * 16 - screen.h - 16)
            yScroll = level.h * 16 - screen.h - 16;

        //screen.clear(0);

        level.renderBackground(screen, xScroll, yScroll);
        level.renderSprites(screen, xScroll, yScroll);

        Font.draw("x: " + (player.x >> 4) + ", y: " + (player.y >> 4), screen, 1, 1, Color.get(-1, -1, -1, 222));

        for (int y = 0; y < screen.h; y++) {
            for (int x = 0; x < screen.w; x++) {
                int cc = screen.pixels[x + y * screen.w];
                if (cc < 6 * 6 * 6)
                    pixels[x + y * WIDTH] = Color.colors[cc];
            }
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();

    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPertick = 1_000_000_000D / 60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPertick;
            lastTime = now;

            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
            }

            frames++;
            render();

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                //System.out.println("Ups: " + ticks + ", Fps: " + frames);
                ticks = frames = 0;
            }
        }

        System.exit(-1);

    }

    public static void main(String[] args) {

        try {
            Game game = new Game();
            JFrame frame = new JFrame();
            frame.setTitle("Jcs 2D Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(game, BorderLayout.CENTER);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);

            frame.setVisible(true);

            game.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        running = true;
        new Thread(this, "Jcs Game Engine").start();
    }

    public void stop() {
        running = false;
        Thread.yield();
    }

}
