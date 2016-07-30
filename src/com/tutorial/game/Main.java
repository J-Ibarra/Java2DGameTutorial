package com.tutorial.game;

import com.tutorial.game.gfx.Screen;
import com.tutorial.game.gfx.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by Jcs on 30/7/2016.
 */
public class Main extends Canvas implements Runnable {

    private boolean running = false;

    public static final String TITTLE = "Java 2D Game Tutorial";
    public static final int SCALE = 3;
    public static final int WIDTH = 800 / SCALE;
    public static final int HEIGHT = 600 / SCALE;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    private SpriteSheet sheet;
    private Screen screen;
    private  InputHandler key;


    private JFrame frame;

    public Main() throws Exception {

        Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        init();

        frame = new JFrame(TITTLE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void init() throws Exception {
        sheet = new SpriteSheet("SpriteSheet.png");
        screen = new Screen(WIDTH, HEIGHT, sheet);
        key = new InputHandler(this);
    }

    int x, y;

    public void update() {
        if(key.up.isDown())
            y--;
        if(key.down.isDown())
            y++;
        if(key.left.isDown())
            x--;
        if(key.right.isDown())
            x++;
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        screen.clear(0xff00ff);

        screen.render(x, y, 31);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                pixels[x + y * WIDTH] = screen.pixels[x + y * screen.width];
            }
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    public void oneSecond(int ups, int fps) {
        frame.setTitle(TITTLE + " | ups: " + ups + ", fps: " + fps);
    }

    @Override
    public void run() {
        double nUps = 1_000_000_000d / 60d;
        int fps = 0;
        int ups = 0;
        double delta = 0;

        long lastTime = System.nanoTime();
        long lastTimer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nUps;
            lastTime = now;

            while (delta > 1d) {
                ups++;
                update();
                delta -= 1d;
            }

            fps++;
            render();

            if (System.currentTimeMillis() - lastTimer > 1000d) {
                lastTimer += 1000d;
                oneSecond(ups, fps);
                fps = ups = 0;
            }
        }
    }

    public void start() {
        running = true;
        new Thread(this, "Java Game").start();
    }

    public static void main(String arg[]) {
        try {
            new Main().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
