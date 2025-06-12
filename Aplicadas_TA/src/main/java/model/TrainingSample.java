package model;

import image.ImageLoader;
import java.awt.image.BufferedImage;

public class TrainingSample {
    private final double[] input;
    private final double[] output;

    public TrainingSample(BufferedImage image, double[] output) {
        this.input = ImageLoader.imageToInputArray(image);
        this.output = output;
    }

    public double[] getInput() {
        return input;
    }

    public double[] getOutput() {
        return output;
    }
}
