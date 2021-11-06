package com.jae.connect4minmax.Models.Utils;

import com.jae.connect4minmax.Controllers.GameController;
import com.jae.connect4minmax.Main;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;

public class MenuScene implements Renderable{

    private final GraphicsContext ctx;


    private final int canvasWidth;
    private final int canvasHeight;


    private final Font regularFont;

    private final String FONT_NAME = "ARCADECLASSIC.TTF";

    private final String[] menuItems = new String[]{
            "NEW GAME", "QUIT"
    };

    private final Runnable[] menuItemActions = new Runnable[]
            {
                    () -> GameController.getInstance().setScene(Scene.GAME),
                    () -> System.exit(0)
            };

    private int selectedItem = 0;


    public MenuScene(GraphicsContext ctx, int resX, int resY)
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
            this.ctx.fillText(this.menuItems[i], 0, i * 50 + 50);
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
                case ENTER -> this.select();
            }

            if(pressedKey != KeyCode.ENTER)
                this.render();
        };

    }
}
