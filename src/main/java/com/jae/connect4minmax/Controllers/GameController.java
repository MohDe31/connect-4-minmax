package com.jae.connect4minmax.Controllers;

import com.jae.connect4minmax.Models.Data.GameData;
import com.jae.connect4minmax.Models.Utils.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class GameController {

    @FXML
    protected Canvas gameCanvas;

    private Scene activeSceneIdx;

    private Renderable activeScene;

    static private GameController instance;


    public static GameController getInstance()
    {
        return instance;
    }


    public void initialize()
    {
        instance = this;
    }

    public void setScene(Scene scene)
    {
        if(activeSceneIdx == scene)
            return;


        switch (scene) {
            case GAME -> activeScene = new GameScene(gameCanvas.getGraphicsContext2D(), (int) gameCanvas.getWidth(), (int) gameCanvas.getHeight());
            case MENU -> activeScene = new MenuScene(gameCanvas.getGraphicsContext2D(), (int) gameCanvas.getWidth(), (int) gameCanvas.getHeight());
            default -> throw new IllegalStateException("Unexpected value: " + scene);
        }

        gameCanvas.setOnMouseMoved(activeScene.onMouseMoved());
        gameCanvas.setOnMouseClicked(activeScene.onMouseClicked());

        gameCanvas.getScene().setOnKeyPressed(activeScene.onKeyPressed());

        activeSceneIdx = scene;

        activeScene.render();
    }
}