package com.tutorial.game.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by Jcs on 30/7/2016.
 */
public class SpriteSheet {

    public final int width, height;
    public int[] pixels;

    public SpriteSheet(String path) throws Exception{
        BufferedImage image = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));

        width = image.getWidth();
        height = image.getHeight();

        pixels = image.getRGB( 0, 0, width,height, null, 0, width);

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = pixels[i] & 0xFFFFFF;
        }
    }

}
