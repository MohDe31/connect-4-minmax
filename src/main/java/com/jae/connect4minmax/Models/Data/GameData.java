package com.jae.connect4minmax.Models.Data;

import com.jae.connect4minmax.Controllers.PlayAI;
import com.jae.connect4minmax.Models.Game;
import javafx.scene.canvas.GraphicsContext;

public class GameData {


    public Game game;

    public static GameData instance;

    PlayAI aiThread;

    private int selectedCol;

    public static GameData getInstance()
    {

        if(instance == null)
        {
            instance = new GameData();
        }

        return instance;
    }


    public void createNewGame(int w, int h)
    {
        this.game = new Game(w, h);
    }

    public void playAI()
    {
        if( checkGameCreated() ) return;

        this.aiThread = new PlayAI(this.game::generateComputerDecision);
        this.aiThread.start();
    }


    private boolean checkGameCreated()
    {
        if(this.game == null)
        {
            System.out.println("GAME INSTANCE NOT CREATED");
            return false;
        }

        return false;
    }


    public void setSelectedCol(int v)
    {
        this.selectedCol = v;
    }


    public int getSelectedCol()
    {
        return this.selectedCol;
    }
}
