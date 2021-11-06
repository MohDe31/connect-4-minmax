package com.jae.connect4minmax.Models.Data;

import com.jae.connect4minmax.Models.Game;
import com.jae.connect4minmax.Models.Utils.EventEmitter;

import java.util.concurrent.*;

public class GameData extends EventEmitter {


    public Game game;

    public static GameData instance;

    private int selectedCol;

    public static GameData getInstance()
    {

        if(instance == null)
        {
            instance = new GameData();
        }

        return instance;
    }

    private void runAndEmitAsync(String eventName, Runnable runnable)
    {
        CompletableFuture.runAsync(runnable)
                .thenRun(() -> this.emit(eventName));
    }


    public void createNewGame(int w, int h)
    {
        this.runAndEmitAsync("GAME_CREATED", ()->this.game = new Game(w, h));
    }


    public void playPlayer(int x)
    {
        if( checkGameCreated() ) return;

        this.runAndEmitAsync("TURN_FINISHED", () -> this.game.place(x));
    }

    public void playAI()
    {
        if( checkGameCreated() ) return;

        this.runAndEmitAsync("TURN_FINISHED", this.game::generateComputerDecision);
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
