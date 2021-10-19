package com.jae.connect4minmax.Models.Utils;

import com.jae.connect4minmax.Models.Game;

import java.util.Arrays;

public class Board {

    private Game game;
    private CellState[][] board;
    private CellState player;


    public Board(Game game, CellState[][] board, CellState player)
    {
        this.game = game;
        this.board = board;
        this.player = player;
    }

    public boolean place(int column)
    {
        // Check if column valid
        // 1. not empty 2. not exceeding the board size
        if (this.board[0][column] == CellState.EMPTY && column >= 0 && column < this.game.getWidth()) {
            // Bottom to top
            for (var y = this.game.getHeight() - 1; y >= 0; y--) {
                if (this.board[y][column] == CellState.EMPTY) {
                    this.board[y][column] = this.player; // Set current player coin
                    break; // Break from loop after inserting
                }
            }
            this.player = this.game.switchRound(this.player);
            return true;
        } else {
            return false;
        }
    }

    public int getScore()
    {
        int points;

        int vertical_points = 0;
        int horizontal_points = 0;
        int diagonal_points1 = 0;
        int diagonal_points2 = 0;

        // Board-size: 7x6 (height x width)
        // Array indices begin with 0
        // => e.g. height: 0, 1, 2, 3, 4, 5

        // Vertical points
        // Check each column for vertical score
        //
        // Possible situations
        //  0  1  2  3  4  5  6
        // [x][ ][ ][ ][ ][ ][ ] 0
        // [x][x][ ][ ][ ][ ][ ] 1
        // [x][x][x][ ][ ][ ][ ] 2
        // [x][x][x][ ][ ][ ][ ] 3
        // [ ][x][x][ ][ ][ ][ ] 4
        // [ ][ ][x][ ][ ][ ][ ] 5
        for (int row = 0; row < this.game.getHeight() - 3; row++) {
            for (int column = 0; column < this.game.getWidth(); column++) {

                int score = this.scorePosition(row, column, 1, 0);

                if (score == this.game.getScore()) return this.game.getScore();
                if (score == -this.game.getScore()) return -this.game.getScore();

                vertical_points += score;
            }
        }

        // Horizontal points
        // Check each row's score
        //
        // Possible situations
        //  0  1  2  3  4  5  6
        // [x][x][x][x][ ][ ][ ] 0
        // [ ][x][x][x][x][ ][ ] 1
        // [ ][ ][x][x][x][x][ ] 2
        // [ ][ ][ ][x][x][x][x] 3
        // [ ][ ][ ][ ][ ][ ][ ] 4
        // [ ][ ][ ][ ][ ][ ][ ] 5
        for (var row = 0; row < this.game.getHeight(); row++) {
            for (var column = 0; column < this.game.getWidth() - 3; column++) {
                int score = this.scorePosition(row, column, 0, 1);
                if (score == this.game.getScore()) return this.game.getScore();
                if (score == -this.game.getScore()) return -this.game.getScore();
                horizontal_points += score;
            }
        }



        // Diagonal points 1 (left-bottom)
        //
        // Possible situation
        //  0  1  2  3  4  5  6
        // [x][ ][ ][ ][ ][ ][ ] 0
        // [ ][x][ ][ ][ ][ ][ ] 1
        // [ ][ ][x][ ][ ][ ][ ] 2
        // [ ][ ][ ][x][ ][ ][ ] 3
        // [ ][ ][ ][ ][ ][ ][ ] 4
        // [ ][ ][ ][ ][ ][ ][ ] 5
        for (var row = 0; row < this.game.getHeight() - 3; row++) {
            for (var column = 0; column < this.game.getWidth() - 3; column++) {
                int score = this.scorePosition(row, column, 1, 1);
                if (score == this.game.getScore()) return this.game.getScore();
                if (score == -this.game.getScore()) return -this.game.getScore();
                diagonal_points1 += score;
            }
        }

        // Diagonal points 2 (right-bottom)
        //
        // Possible situation
        //  0  1  2  3  4  5  6
        // [ ][ ][ ][x][ ][ ][ ] 0
        // [ ][ ][x][ ][ ][ ][ ] 1
        // [ ][x][ ][ ][ ][ ][ ] 2
        // [x][ ][ ][ ][ ][ ][ ] 3
        // [ ][ ][ ][ ][ ][ ][ ] 4
        // [ ][ ][ ][ ][ ][ ][ ] 5
        for (var row = 3; row < this.game.getHeight(); row++) {
            for (var column = 0; column <= this.game.getWidth() - 4; column++) {
                int score = this.scorePosition(row, column, -1, +1);
                if (score == this.game.getScore()) return this.game.getScore();
                if (score == -this.game.getScore()) return -this.game.getScore();
                diagonal_points2 += score;
            }

        }

        points = horizontal_points + vertical_points + diagonal_points1 + diagonal_points2;

        return points;
    }

    private int scorePosition(int row, int column, int delta_y, int delta_x) {
        int human_points = 0;
        int computer_points = 0;

        // Determine score through amount of available chips
        for (var i = 0; i < 4; i++) {

            if (this.board[row][column] == CellState.RED) {
                //this.game.winning_array_human.push([row, column]);
                human_points++; // Add for each human chip
            } else if (this.board[row][column] == CellState.YELLOW) {
                //this.game.winning_array_cpu.push([row, column]);
                computer_points++; // Add for each computer chip
            }

            // Moving through our board
            row += delta_y;
            column += delta_x;
        }

        // Marking winning/returning score
        if (human_points == 4) {
            //this.game.winning_array = this.game.winning_array_human;
            // Computer won (100000)
            return -this.game.getScore();
        } else if (computer_points == 4) {
            //this.game.winning_array = this.game.winning_array_cpu;
            // Human won (-100000)
            return this.game.getScore();
        } else {
            // Return normal points
            return computer_points;
        }
    }

    public boolean isFull()
    {
        for (int i = 0; i < this.game.getWidth(); i++) {
            if (this.board[0][i] == CellState.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public boolean isFinished(int depth, int score)
    {
        if (depth == 0 || score == this.game.getScore() || score == -this.game.getScore() || this.isFull()) {
            return true;
        }
        return false;
    }

    public Board copy()
    {
        CellState[][] new_board = new CellState[this.game.getWidth()][this.game.getHeight()];
        for (var i = 0; i < this.board.length; i++) {
            new_board[i] = Arrays.copyOf(this.board[i], this.board[i].length);
        }

        return new Board(this.game, new_board, this.player);
    }
}
