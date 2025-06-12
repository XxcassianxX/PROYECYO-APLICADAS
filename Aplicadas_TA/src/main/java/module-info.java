module com.icesi.aplicadas_ta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens gui to javafx.fxml;
    opens controller to javafx.fxml;
    exports gui;
}

