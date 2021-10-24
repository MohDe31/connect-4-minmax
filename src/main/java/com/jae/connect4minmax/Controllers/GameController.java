package com.jae.connect4minmax.Controllers;

import com.jae.connect4minmax.Models.Data.GameData;
import com.jae.connect4minmax.Models.Utils.Renderer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class GameController {

    @FXML
    protected Canvas gameCanvas;


    private Renderer renderer;


    public void initialize()
    {
        GameData.getInstance().createNewGame(7, 6);


        this.renderer = new Renderer(gameCanvas.getGraphicsContext2D(), 500, 500);


        gameCanvas.setOnMouseMoved(this.renderer.onMouseMoved());

        gameCanvas.setOnMouseClicked(this.renderer.onMouseClicked());
    }





}