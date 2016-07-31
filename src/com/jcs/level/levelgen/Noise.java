package com.jcs.level.levelgen;

public class Noise {

    private int width = 128;
    private int heigh = 128;
    private int featureSize = 32;

    private double[] data;

    public Noise() {
        data = Gen(width, heigh, featureSize);
    }

    public Noise(int width, int heigh, int featureSize) {
        this.width = width;
        this.heigh = heigh;
        this.featureSize = featureSize;
        data = Gen(width, heigh, featureSize);
    }

    private double[] Gen(int w, int h, int featureSize) {

        double[] data = new double[w * h];

        for (int y = 0; y < h; y += featureSize) {
            for (int x = 0; x < w; x += featureSize) {
                double dato = Math.random() * 2 - 1;
                data[getIndex(x, y)] = dato;
            }
        }

        int stepSize = featureSize;
        double scale = 1.0 / w;
        double scaleMod = 1;

        do {
            int halfStep = stepSize / 2;
            for (int y = 0; y < w; y += stepSize) {
                for (int x = 0; x < w; x += stepSize) {
                    double a = getData(x, y, data);
                    double b = getData(x + stepSize, y, data);
                    double c = getData(x, y + stepSize, data);
                    double d = getData(x + stepSize, y + stepSize, data);

                    double E = (a + b + c + d) / 4.0 + (Math.random() * 2 - 1) * stepSize * scale;
                    data[getIndex(x + halfStep, y + halfStep)] = E;
                }
            }

            for (int y = 0; y < w; y += stepSize) {
                for (int x = 0; x < w; x += stepSize) {
                    double a = getData(x, y, data);
                    double b = getData(x + stepSize, y, data);
                    double c = getData(x, y + stepSize, data);
                    double d = getData(x + halfStep, y + halfStep, data);
                    double e = getData(x + halfStep, y - halfStep, data);
                    double f = getData(x - halfStep, y + halfStep, data);

                    double H = (a + b + d + e) / 4.0 + (Math.random() * 10 - 5) * stepSize * scale * 0.5;
                    data[getIndex(x + halfStep, y)] = H;

                    double G = (a + c + d + f) / 4.0 + (Math.random() * 10 - 5) * stepSize * scale * 0.5;
                    data[getIndex(x, y + halfStep)] = G;
                }
            }

            stepSize /= 2;
            scale *= (scaleMod + 0.8);
            scaleMod *= 0.3;

            Thread.yield();
        } while (stepSize > 1);
        return data;
    }

    private double getData(int x, int y, double[] data) {
        return data[(x & (width - 1)) + (y & (heigh - 1)) * width];
    }

    private int getIndex(int x, int y) {
        return (x & (width - 1)) + (y & (heigh - 1)) * width;
    }

    private int[] Scale(double[] data) {

        double max = 0, min = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] < min)
                min = data[i];

            if (data[i] > max)
                max = data[i];
        }

        int[] iData = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            double dato = data[i];
            iData[i] = (int) ((dato - min) / (max - min) * 255.0);

        }

        max = 0;
        min = 0;

        for (int i = 0; i < data.length; i++) {
            if (iData[i] < min)
                min = data[i];

            if (iData[i] > max)
                max = data[i];
        }

        return iData;
    }

    public double getData(int x, int y) {
        return this.data[getIndex(x, y)];
    }

    public void setData(int x, int y, double val) {
        this.data[getIndex(x, y)] = val;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigh() {
        return heigh;
    }

    public double[] getData() {
        return data;
    }

    public int[] getDataScale() {
        return Scale(data);
    }
}
