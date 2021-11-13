package com.jae.connect4minmax.Models.Utils;

import com.jae.connect4minmax.Controllers.GameController;
import com.jae.connect4minmax.Main;
import com.jae.connect4minmax.Models.Data.GameData;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;

public class GameScene implements Renderable {

    private final GameData gameData;

    private final int canvasWidth;
    private final int canvasHeight;

    private final int topOffset;
    private final int leftOffset;

    private final int cellSize;

    private final Color backgroundColor = new Color(0, 0, 0, 1);
    private final Color cellDefaultColor = new Color(.3, .3, 1, 1);

    private final Color player1 = new Color(1, .3, .3, 1);
    private final Color player2 = new Color(1, 1, .3, 1);

    private final Font regularFont;
    private final Font biggerFont;

    private final GraphicsContext ctx;

    private final String FONT_NAME = "ARCADECLASSIC.TTF";

    public GameScene(GraphicsContext ctx, int resX, int resY)
    {

        //GameData.getInstance().createNewGame(7, 6);

        this.ctx = ctx;

        this.gameData = GameData.getInstance();

        this.topOffset = 175;

        int gameHeight = resY - this.topOffset;

        this.cellSize = Math.min(resX / this.gameData.game.getWidth() ,gameHeight / this.gameData.game.getHeight());

        this.leftOffset = (resX - this.cellSize * this.gameData.game.getWidth()) / 2;

        this.canvasWidth = resX;
        this.canvasHeight = resY;

        URL arcadeClassicURL = Main.class.getResource(FONT_NAME);

        this.regularFont = Font.loadFont(arcadeClassicURL != null ? arcadeClassicURL.toString() : "", 30);
        this.biggerFont = Font.loadFont(arcadeClassicURL != null ? arcadeClassicURL.toString() : "", 100);


        this.ctx.setFont(this.regularFont);

        this.gameData.on("TURN_FINISHED", this::render);

    }

    @Override
    public EventHandler<MouseEvent> onMouseMoved()
    {
        return mouseEvent -> {
            int x = (int) mouseEvent.getX();


            gameData.setSelectedCol(x / cellSize);

            render();

        };
    }

    @Override
    public EventHandler<MouseEvent> onMouseClicked()
    {
        return mouseEvent -> {

            int x = (int) mouseEvent.getX() / cellSize;

            if(gameData.game.isPlayerTurn() && !gameData.game.isGameOver() && gameData.game.canPlay(x))
            {
                gameData.playPlayer(x);
                gameData.playAI();
            }
        };
    }

    @Override
    public EventHandler<KeyEvent> onKeyPressed() {
        return keyEvent -> {
            KeyCode pressedKey = keyEvent.getCode();

            if(pressedKey == KeyCode.ESCAPE)
                GameController.getInstance().setScene(Scene.MENU);
        };
    }


    public void drawWinScreen()
    {
        int winner = this.gameData.game.playerWon;

        ctx.setFill(Color.BLUE);
        String winnerText = switch (winner) {
            case 0 -> "DRAW";
            case 1 -> "PLAYER WON";
            case 2 -> "CPU WON";
            default -> "";
        };

        ctx.setFill(new Color(0, 0, 0, 0.5));
        ctx.fillRect(0, (this.canvasHeight >> 1) - 100, this.canvasWidth, 400);

        ctx.setFont(this.biggerFont);
        ctx.setFont(new Font(ctx.getFont().getFamily(), 100));
        ctx.setFill(Color.WHITE);
        ctx.fillText(winnerText, (this.canvasWidth >> 1) - 180, this.canvasHeight >> 1);
        ctx.setFont(this.regularFont);
    }

    @Override
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

                ctx.fillRect(i * cellSize + 1 + this.leftOffset, j * cellSize + 1 + this.topOffset, cellSize - 2, cellSize - 2);

                ctx.setFill(this.backgroundColor);

                if(this.gameData.game.game_board[j][i] == CellState.RED)
                    ctx.setFill(this.player1);

                if(this.gameData.game.game_board[j][i] == CellState.YELLOW)
                    ctx.setFill(this.player2);

                ctx.fillRoundRect(i * cellSize + 1 + this.leftOffset, j * cellSize + 1 + this.topOffset, cellSize - 2, cellSize - 2, this.cellSize, this.cellSize);
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
