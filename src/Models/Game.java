package Models;

import Models.Utils.State;

import java.util.Arrays;

public class GamePlay {
    public int row;
    public int col;
    private int value;

    public GamePlay() {
        row = -1;
        col = -1;
        value = 0;
    }//end Constructor

    public GamePlay moveDone(int row, int col) {
        GamePlay moveDone = new GamePlay();
        moveDone.row = row;
        moveDone.col = col;
        moveDone.value = -1;
        return moveDone;
    }

    public GamePlay possibleMove(int row, int col, int value) {
        GamePlay possibleMove = new GamePlay();
        possibleMove.row = row;
        possibleMove.col = col;
        possibleMove.value = value;
        return possibleMove;
    }


    public GamePlay moveToCompare(int value) {
        GamePlay moveToCompare = new GamePlay();
        moveToCompare.row = -1;
        moveToCompare.col = -1;
        moveToCompare.value = value;
        return moveToCompare;
    }

    public int getValue() {
        return value;
    }

    public void setRow(int aRow) {
        row = aRow;
    }

    public void setCol(int aCol) {
        col = aCol;
    }

    public void setValue(int aValue) {
        value = aValue;
    }
}
