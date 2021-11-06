package com.jae.connect4minmax.Controllers;

import com.jae.connect4minmax.Models.Data.GameData;
import com.jae.connect4minmax.Models.Game;
import com.jae.connect4minmax.Models.Utils.Renderer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class GameController {

    @FXML
    protected Canvas gameCanvas;


    public void initialize()
    {
        GameData.getInstance().createNewGame(7, 6);


        Renderer renderer = new Renderer(gameCanvas.getGraphicsContext2D(), (int) gameCanvas.getWidth(), (int) gameCanvas.getHeight());


        gameCanvas.setOnMouseMoved(renderer.onMouseMoved());

        gameCanvas.setOnMouseClicked(renderer.onMouseClicked());
    }





}