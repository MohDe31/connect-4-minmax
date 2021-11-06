package com.jae.connect4minmax.Models.Utils;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public interface Renderable {
    void render();

    EventHandler<MouseEvent> onMouseMoved();
    EventHandler<MouseEvent> onMouseClicked();

    EventHandler<KeyEvent> onKeyPressed();
}
