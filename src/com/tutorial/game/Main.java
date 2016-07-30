package com.tutorial.game;

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
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    private JFrame frame;

    public Main() {

        Dimension dimension = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        frame = new JFrame(TITTLE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void init() {

    }

    int updateCount = 0;

    public void update() {
        updateCount++;

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = i + updateCount;
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
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
        init();
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
        new Main().start();
    }
}
