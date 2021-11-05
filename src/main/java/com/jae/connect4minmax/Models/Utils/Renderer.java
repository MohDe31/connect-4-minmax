package com.jae.connect4minmax.Models.Utils;

import com.jae.connect4minmax.Main;
import com.jae.connect4minmax.Models.Data.GameData;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;

public class Renderer {

    private final GraphicsContext ctx;
    private final int cellSize;

    private final GameData gameData;

    private final int canvasWidth;
    private final int canvasHeight;

    private final int topOffset;

    private final Color backgroundColor = new Color(0, 0, 0, 1);
    private final Color cellDefaultColor = new Color(.3, .3, 1, 1);

    private final Color player1 = new Color(1, .3, .3, 1);
    private final Color player2 = new Color(1, 1, .3, 1);

    private final Font regularFont;
    private final Font biggerFont;

    private final String FONT_NAME = "ARCADECLASSIC.TTF";

    public Renderer(GraphicsContext ctx, int resX, int resY)
    {
        this.ctx = ctx;
        this.gameData = GameData.getInstance();


        this.cellSize = Math.min(resX / this.gameData.game.getWidth() ,resY / this.gameData.game.getHeight());

        this.canvasWidth = resX;
        this.canvasHeight = resY;

        // int gameWidth = this.cellSize * this.gameData.game.getWidth();
        int gameHeight = this.cellSize * this.gameData.game.getHeight();

        URL arcadeClassicURL = Main.class.getResource(FONT_NAME);

        this.regularFont = Font.loadFont(arcadeClassicURL != null ? arcadeClassicURL.toString() : "", 30);
        this.biggerFont = Font.loadFont(arcadeClassicURL != null ? arcadeClassicURL.toString() : "", 100);

        this.topOffset = this.canvasHeight - gameHeight;

        this.ctx.setFont(this.regularFont);

    }

    public EventHandler<MouseEvent> onMouseMoved()
    {
        return mouseEvent -> {
            int x = (int) mouseEvent.getX();


            gameData.setSelectedCol(x / cellSize);

            render();

        };
    }

    public EventHandler<MouseEvent> onMouseClicked()
    {
        Renderer renderer = this;
        return mouseEvent -> {

            int x = (int) mouseEvent.getX() / cellSize;

            if(gameData.game.isPlayerTurn())
            {
                gameData.game.place(x);
                render();
                gameData.playAI(renderer::render);
            }
        };
    }

    public void drawWinScreen()
    {
        int winner = this.gameData.game.playerWon;
        String winnerText = switch (winner) {
            case 0 -> "DRAW";
            case 1 -> "PLAYER WON";
            case 2 -> "CPU WON";
            default -> "";
        };


        this.ctx.setFont(this.biggerFont);
        ctx.setFont(new Font(ctx.getFont().getFamily(), 100));
        ctx.setFill(Color.WHITE);
        ctx.fillText(winnerText, (this.canvasWidth >> 1) - 180, this.canvasHeight >> 1);
        this.ctx.setFont(this.regularFont);
    }

    public void render()
    {
        ctx.setFill(this.backgroundColor);
        ctx.fillRect(0, 0, this.canvasWidth, this.canvasHeight);
        for(int i = 0; i < this.gameData.game.getWidth(); i+=1)
            for(int j = 0; j < this.gameData.game.getHeight(); j+=1)
            {
                ctx.setFill(this.cellDefaultColor);

                if(i == this.gameData.getSelectedCol())
                    ctx.setFill(Color.GRAY);

                ctx.fillRect(i * cellSize + 1, j * cellSize + 1 + this.topOffset, cellSize - 2, cellSize - 2);

                ctx.setFill(this.backgroundColor);

                if(this.gameData.game.game_board[j][i] == CellState.RED)
                    ctx.setFill(this.player1);

                if(this.gameData.game.game_board[j][i] == CellState.YELLOW)
                    ctx.setFill(this.player2);

                ctx.fillRoundRect(i * cellSize + 1, j * cellSize + 1 + this.topOffset, cellSize - 2, cellSize - 2, this.cellSize, this.cellSize);
            }



        ctx.setStroke(Color.WHITE);
        ctx.setLineWidth(10);
        ctx.strokeRect(10, 10, this.canvasWidth - 20, this.topOffset - 20);

        ctx.setFill(Color.WHITE);
        ctx.fillText("Current Turn ", 50, 70);

        boolean playerTurn = this.gameData.game.isPlayerTurn();
        ctx.setFill(playerTurn ? this.player1 : this.player2);
        ctx.fillText(playerTurn ? "Player" : "CPU", 250, 70);

        ctx.setFill(Color.WHITE);
        ctx.fillText("Last turn timestamp ", 50, 130);

        ctx.setFill(Color.GREEN);
        ctx.fillText(this.gameData.game.getLastTurnTime() + " MS", 350, 130);


        if(this.gameData.game.isGameOver())
        {
            drawWinScreen();
        }
    }

}
