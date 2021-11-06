package com.jae.connect4minmax.Models;

import com.jae.connect4minmax.Models.Utils.Board;
import com.jae.connect4minmax.Models.Utils.CellState;

import java.util.Arrays;

public class Game {
    private final int width, height;
    private final int searchDepth = 9;
    private final int score = 100000;

    private long lastTurnTime = 0;

    private boolean gameOver;
    public int playerWon;

    private final Board board;

    public CellState[][] game_board;

    private int[] col_height;

    public Game(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.col_height = new int[width];
        this.game_board = new CellState[this.height][this.width];

        for (int i = 0; i < this.height; i += 1) {
            Arrays.fill(this.game_board[i], 0, this.width, CellState.EMPTY);
        }

        this.board = new Board(this, game_board, CellState.RED);
    }

    public int getWidth() { return this.width; }

    public int getHeight() { return this.height; }

    public int getScore() { return this.score; }

    public boolean isPlayerTurn() { return this.board.isPlayerTurn(); }

    public boolean isGameOver() { return this.gameOver; }



    public void generateComputerDecision()
    {
        if(this.board.getScore() != this.score && this.board.getScore() != -this.score && !this.board.isFull())
        {

            long currentTime = System.nanoTime();

            ComputerPlayResult ai_move = this.maximizePlay(this.board, this.searchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);

            System.out.println("Score: " + ai_move.score);

            this.place(ai_move.column);

            this.lastTurnTime = (System.nanoTime() - currentTime) / 1000000;
            System.out.println("Thinking time: " + this.lastTurnTime + "MS");
        }
    }

    public long getLastTurnTime()
    {
        return lastTurnTime;
    }

    class ComputerPlayResult
    {
        public int column;
        public int score;

        public ComputerPlayResult(int column, int score)
        {
            this.column = column;
            this.score = score;
        }
    }

    public ComputerPlayResult maximizePlay(Board board, int depth, int alpha, int beta)
    {
        // Call score of our board
        int score = board.getScore();

        // Break
        if (board.isFinished(depth, score)) return new ComputerPlayResult(-1, score);

        // Column, Score
        ComputerPlayResult max = new ComputerPlayResult(-1, -99999);

        // For all possible moves
        for (var column = 0; column < this.width; column++) {
            Board new_board = board.copy(); // Create new board

            if (new_board.place(column)) {

                ComputerPlayResult next_move = this.minimizePlay(new_board, depth - 1, alpha, beta); // Recursive calling

                // Evaluate new move
                if (max.column == -1 || next_move.score > max.score) {
                    max.column = column;
                    max.score = next_move.score;
                    alpha = next_move.score;
                }

                if (alpha >= beta) return max;
            }
        }

        return max;
    }

    private ComputerPlayResult minimizePlay(Board board, int depth, int alpha, int beta) {
        int score = board.getScore();

        if (board.isFinished(depth, score)) return new ComputerPlayResult(-1, score);

        // Column, score
        ComputerPlayResult min = new ComputerPlayResult(-1, 99999);

        for (var column = 0; column < this.width; column++) {
            var new_board = board.copy();

            if (new_board.place(column)) {

                ComputerPlayResult next_move = this.maximizePlay(new_board, depth - 1, alpha, beta);

                if (min.column == -1 || next_move.score < min.score) {
                    min.column = column;
                    min.score = next_move.score;
                    beta = next_move.score;
                }

                if (alpha >= beta) return min;

            }
        }
        return min;
    }


    public boolean canPlay(int col)
    {
        return this.col_height[col] < this.height;
    }

    public void place(int column)
    {
        // If not finished
        if (this.board.getScore() != this.score && this.board.getScore() != -this.score && !this.board.isFull() && this.canPlay(column))
        {
            if(!this.board.place(column))
                return;

            this.col_height[column]++;
            this.updateStatus();

        }

    }


    public void updateStatus()
    {

        // Human won
        if (this.board.getScore() == -this.score) {
            this.gameOver = true;
            this.playerWon = 1;
        }

        // Computer won
        if (this.board.getScore() == this.score) {
            this.gameOver = true;
            this.playerWon = 2;
        }

        // Tie
        if (this.board.isFull()) {
            this.gameOver = true;
            this.playerWon = 0;
        }
    }

    public String toString() {
        StringBuilder out = new StringBuilder();

        for(int i = 0; i < this.height; i+=1)
        {
            for(int j = 0; j < this.width; j+=1)
            {
                switch (this.game_board[i][j])
                {
                    case EMPTY -> out.append(" ");

                    case RED -> out.append("X");

                    case YELLOW -> out.append("O");
                }
            }

            out.append("\n");
        }


        return out.toString();
    }

    public CellState switchRound(CellState player)
    {
        if(player == CellState.RED)
            return CellState.YELLOW;

        return CellState.RED;
    }
}
