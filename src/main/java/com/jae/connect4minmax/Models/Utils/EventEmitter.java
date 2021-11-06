package com.jae.connect4minmax.Models.Utils;

import java.util.HashMap;

public class EventEmitter {

    private final HashMap<String, Runnable> __eventStorage;

    public EventEmitter()
    {
        this.__eventStorage = new HashMap<>();
    }

    public void emit(String eventName)
    {
        this.__eventStorage.get(eventName).run();
    }

    public void on(String eventName, Runnable callback)
    {
        this.__eventStorage.put(eventName, callback);
    }

}
