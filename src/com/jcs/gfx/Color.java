package com.jcs.gfx;

public class Color {

    public static int[] colors = new int[6 * 6 * 6];

    static {
        int pp = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);
                    int mid = (rr * 30 + gg * 59 + bb * 11) / 100;

                    int r1 = ((rr + mid * 1) / 2) * 230 / 255 + 10;
                    int g1 = ((gg + mid * 1) / 2) * 230 / 255 + 10;
                    int b1 = ((bb + mid * 1) / 2) * 230 / 255 + 10;
                    colors[pp++] = r1 << 16 | g1 << 8 | b1;
                    //colors[pp++] = rr << 16 | gg << 8 | bb;
                }
            }
        }
    }

    public static int get(int a, int b, int c, int d) {
        return (get(d) << 24) + (get(c) << 16) + (get(b) << 8) + (get(a));
    }

    public static int get(int d) {
        if (d < 0)
            return 255;
        int r = d / 100 % 10;
        int g = d / 10 % 10;
        int b = d % 10;
        return r * 36 + g * 6 + b;
    }

}