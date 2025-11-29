package com.biblioteca;

import com.biblioteca.util.DatabaseSetup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Inicializa as tabelas do SQLite
        DatabaseSetup.inicializar();

        FXMLLoader loader =
            new FXMLLoader(getClass().getResource("/com/biblioteca/view/HomeView.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Biblioteca - Home");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
