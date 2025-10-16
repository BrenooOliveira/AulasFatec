package com.excrud;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Button botao = new Button("Clique em mim!");
        botao.setOnAction(e -> botao.setText("Clicado!"));

        StackPane root = new StackPane(botao);
        Scene scene = new Scene(root, 400, 250);

        stage.setTitle("JavaFX + Maven rodando");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
