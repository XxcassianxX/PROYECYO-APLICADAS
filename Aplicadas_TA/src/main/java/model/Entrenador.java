package model;

import image.ImageLoader;
import java.awt.image.BufferedImage;

public class Entrenador {

    public static void entrenar(NeuralNetwork red) {
        TrainingSample[] muestras = new TrainingSample[4];

        BufferedImage img1 = ImageLoader.load("Etapas_de_mamografia_1.jpg");
        BufferedImage img2 = ImageLoader.load("Etapas_de_mamografia_2.jpg");
        BufferedImage img3 = ImageLoader.load("Etapas_de_mamografia_3.jpg");
        BufferedImage img4 = ImageLoader.load("Etapas_de_mamografia_4.jpg");

        if (img1 == null || img2 == null || img3 == null || img4 == null) {
            System.err.println("Error: una o más imágenes no se pudieron cargar. Revisa la carpeta resources/images.");
            return;
        }

        muestras[0] = new TrainingSample(img1, new double[]{1, 0, 0, 0}); // Etapa I
        muestras[1] = new TrainingSample(img2, new double[]{0, 1, 0, 0}); // Etapa II
        muestras[2] = new TrainingSample(img3, new double[]{0, 0, 1, 0}); // Etapa III
        muestras[3] = new TrainingSample(img4, new double[]{0, 0, 0, 1}); // Etapa IV

        for (int i = 0; i < 500; i++) {
            red.trainOnce(muestras, 0.1);
        }

        System.out.println("Red neuronal entrenada con éxito.");
    }
}
