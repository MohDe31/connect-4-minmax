package com.jae.connect4minmax;

import com.jae.connect4minmax.Controllers.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("connect4-minmax.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        GameController.getInstance().setScene(com.jae.connect4minmax.Models.Utils.Scene.MENU);

        stage.setTitle("Coonect-4");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}