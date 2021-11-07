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

public class NewGameScene implements Renderable {

    private final GraphicsContext ctx;


    private final int canvasWidth;
    private final int canvasHeight;


    private final Font regularFont;

    private final String FONT_NAME = "ARCADECLASSIC.TTF";

    private int width = 7;
    private int height = 6;

    private int difficulty = 8;

    private final String[] menuItems = new String[]{
            "START", "WIDTH    " + width, "HEIGHT    " + height, "DIFFICULTY    " + difficulty, "BACK"
    };

    private final Runnable[] menuItemActions = new Runnable[]
            {
                    () -> {
                        GameData.getInstance().createNewGame(this.width, this.height, this.difficulty);
                        GameController.getInstance().setScene(Scene.GAME);
                    },
                    () -> {}, () -> {}, () -> {},
                    () -> GameController.getInstance().setScene(Scene.MENU)
            };

    private int selectedItem = 0;

    public NewGameScene(GraphicsContext ctx, int resX, int resY)
    {
        this.ctx = ctx;

        this.canvasWidth = resX;
        this.canvasHeight = resY;

        URL arcadeClassicURL = Main.class.getResource(FONT_NAME);

        this.regularFont = Font.loadFont(arcadeClassicURL != null ? arcadeClassicURL.toString() : "", 30);

        this.ctx.setFont(this.regularFont);
    }



    public void clear()
    {
        this.ctx.setFill(Color.BLACK);
        this.ctx.fillRect(0, 0, this.canvasWidth, this.canvasHeight);
    }

    public void drawMenu()
    {

        for(int i = 0; i < this.menuItems.length; i+=1)
        {
            this.ctx.setFill(Color.GRAY);
            if(this.selectedItem == i) this.ctx.setFill(Color.WHITE);
            this.ctx.fillText(this.menuItems[i], (this.canvasWidth >> 1) - this.menuItems[i].length() * 7.5, i * 50 + 350);
        }
    }

    @Override
    public void render() {
        this.clear();

        this.drawMenu();

    }

    public void select()
    {
        this.menuItemActions[this.selectedItem].run();
    }

    public void move_(int side)
    {

        switch (this.selectedItem)
        {
            case 1 -> this.width = Math.max(5, Math.min(this.width + side, 9));
            case 2 -> this.height = Math.max(5, Math.min(this.height + side, 9));
            case 3 -> this.difficulty = Math.max(1, Math.min(this.difficulty + side, 8));
        }


        this.menuItems[1] = "WIDTH    " + width;
        this.menuItems[2] = "HEIGHT    " + height;
        this.menuItems[3] = "DIFFICULTY    " + difficulty;

    }

    @Override
    public EventHandler<MouseEvent> onMouseMoved() {
        return null;
    }

    @Override
    public EventHandler<MouseEvent> onMouseClicked() {
        return null;
    }

    @Override
    public EventHandler<KeyEvent> onKeyPressed() {
        return keyEvent -> {
            KeyCode pressedKey = keyEvent.getCode();

            switch (pressedKey)
            {
                case UP -> this.selectedItem = this.selectedItem == 0 ? this.menuItems.length - 1 : this.selectedItem - 1;
                case DOWN -> this.selectedItem = (this.selectedItem + 1)  % this.menuItems.length;
                case LEFT -> this.move_(-1);
                case RIGHT -> this.move_(1);
                case ENTER -> this.select();
            }

            if(pressedKey != KeyCode.ENTER)
                this.render();
        };

    }
}
