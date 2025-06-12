package controller;

import image.ImageLoader;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.CancerStage;
import model.Entrenador;
import model.NeuralNetwork;

import java.awt.image.BufferedImage;

public class MainController {

    @FXML private Canvas canvas;
    @FXML private Button loadImageButton;
    @FXML private Button predictButton;
    @FXML private Label resultLabel;

    private NeuralNetwork network;
    private Stage stage;
    private double[] currentImage;

    @FXML
    public void initialize() {
        network = new NeuralNetwork(256, 64, 4);
        Entrenador.entrenar(network);

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void onLoadImage() {
        BufferedImage img = ImageLoader.loadImageWithFileChooser(stage);
        if (img != null) {
            currentImage = ImageLoader.imageToInputArray(img);
            draw(currentImage);
            resultLabel.setText("Imagen cargada. Lista para predecir.");
        } else {
            resultLabel.setText("Error al cargar la imagen.");
        }
    }


    @FXML
    public void onPredict() {
        if (currentImage == null) {
            resultLabel.setText("Primero debes cargar una imagen.");
            return;
        }
        int prediction = network.predict(currentImage);
        CancerStage stage = CancerStage.fromIndex(prediction);
        resultLabel.setText("Etapa estimada: " + stage.name());
    }

    private void draw(double[] image) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 256, 256);
        for (int i = 0; i < 256; i++) {
            int x = (i % 16) * 16;
            int y = (i / 16) * 16;
            double gray = image[i];
            gc.setFill(javafx.scene.paint.Color.gray(gray));
            gc.fillRect(x, y, 16, 16);
        }

    }
}
