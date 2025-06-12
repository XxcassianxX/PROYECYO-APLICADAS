package image;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {

    public static BufferedImage load(String resourceName) {
        InputStream is = ImageLoader.class.getResourceAsStream("/images/" + resourceName);
        if (is == null) {
            System.err.println("❌ NO SE ENCUENTRA: images/" + resourceName);
            return null;
        }
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedImage lastLoadedImage;

    public static BufferedImage loadImageWithFileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.jpeg", "*.png")
        );
        java.io.File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                lastLoadedImage = ImageIO.read(file); // GUARDAMOS LA IMAGEN
                return lastLoadedImage;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static BufferedImage getLastLoadedImage() {
        return lastLoadedImage;
    }


    public static double[] convertToInputArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[] input = new double[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                double gray = (r + g + b) / 3.0;
                input[y * width + x] = gray / 255.0;
            }
        }
        return input;
    }
    public static double[] imageToInputArray(BufferedImage original) {
        BufferedImage resized = new BufferedImage(16, 16, BufferedImage.TYPE_BYTE_GRAY);
        resized.getGraphics().drawImage(original.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH), 0, 0, null);

        double[] input = new double[256];
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int rgb = resized.getRGB(x, y) & 0xFF;
                input[y * 16 + x] = rgb / 255.0;
            }
        }
        return input;
    }


}
