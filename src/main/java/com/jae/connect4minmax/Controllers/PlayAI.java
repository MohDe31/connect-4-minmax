package com.jae.connect4minmax.Controllers;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PlayAI extends Service<Void> {
    public PlayAI instance;

    private Runnable aiFunction;
    private Runnable renderFunc;

    public PlayAI(Runnable aiFunction, Runnable renderFunc)
    {
        if(this.instance == null)
        {
            this.instance = this;
            this.aiFunction = aiFunction;

            this.renderFunc = renderFunc;
        }
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                aiFunction.run();

                renderFunc.run();

                return null;
            }
        };
    }
}
