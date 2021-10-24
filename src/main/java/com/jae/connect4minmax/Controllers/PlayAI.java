package com.jae.connect4minmax.Controllers;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;

public class PlayAI extends Service<Void> {
    public PlayAI instance;

    private Runnable aiFunction;

    public PlayAI(Runnable aiFunction)
    {
        if(this.instance == null)
        {
            this.instance = this;
            this.aiFunction = aiFunction;
        }
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                aiFunction.run();
                return null;
            }
        };
    }
}
