package gui;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Clasificador de Etapas - Cáncer de Mama");
        stage.show();

        MainController controller = loader.getController();
        controller.setStage(stage); // para cargar imagen
    }

    public static void main(String[] args) {
        launch(args);
    }
}
