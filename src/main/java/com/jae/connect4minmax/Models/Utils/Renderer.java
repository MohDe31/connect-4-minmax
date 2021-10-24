package com.jae.connect4minmax.Models.Utils;

import com.jae.connect4minmax.Models.Data.GameData;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Renderer {

    private GraphicsContext ctx;
    private int cellSize;

    private GameData gameData;



    public Renderer(GraphicsContext ctx, int resX, int resY)
    {
        this.ctx = ctx;
        this.gameData = GameData.getInstance();


        cellSize = Math.min(resX / this.gameData.game.getWidth() ,resY / this.gameData.game.getHeight());
    }

    public EventHandler<MouseEvent> onMouseMoved()
    {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int x = (int) mouseEvent.getX();


                gameData.setSelectedCol(x / cellSize);

                render();

            }
        };
    }

    public EventHandler<MouseEvent> onMouseClicked()
    {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                int x = (int) mouseEvent.getX() / cellSize;

                if(gameData.game.isPlayerTurn())
                {
                    gameData.game.place(x);
                    render();
                    gameData.playAI();
                    render();
                }
            }
        };
    }

    public void render()
    {

        for(int i = 0; i < this.gameData.game.getWidth(); i+=1)
            for(int j = 0; j < this.gameData.game.getHeight(); j+=1)
            {
                ctx.setFill(Color.BLACK);
                if(i == this.gameData.getSelectedCol())
                    ctx.setFill(Color.GRAY);

                if(this.gameData.game.game_board[j][i] == CellState.RED)
                    ctx.setFill(Color.RED);

                if(this.gameData.game.game_board[j][i] == CellState.YELLOW)
                    ctx.setFill(Color.YELLOW);

                ctx.fillRect(i * cellSize + 1, j * cellSize + 1, cellSize - 2, cellSize - 2);
            }
    }

}
