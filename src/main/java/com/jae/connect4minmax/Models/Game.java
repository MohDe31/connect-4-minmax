package com.jae.connect4minmax.Models;

import com.jae.connect4minmax.Models.Utils.Board;
import com.jae.connect4minmax.Models.Utils.CellState;

import java.util.Arrays;

public class Game {
    private int width, height;
    private int searchDepth = 8;
    private int score = 100000;

    private boolean gameOver;

    private Board board;

    public int iterations;
    public CellState[][] game_board;

    /*


    public int[] columnHeight;
*/

    public Game(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.game_board = new CellState[this.height][this.width];

        for (int i = 0; i < this.height; i += 1) {
            Arrays.fill(this.game_board[i], 0, this.width, CellState.EMPTY);
        }


        this.board = new Board(this, game_board, CellState.RED);
    }

    public int getWidth() { return this.width; }

    public int getHeight() { return this.height; }

    public int getScore() { return this.score; }

    public int getIterations() { return this.iterations; }

    public boolean isPlayerTurn() { return this.board.isPlayerTurn(); }

    public boolean isGameOver() { return this.gameOver; }



    public void generateComputerDecision()
    {
        if(this.board.getScore() != this.score && this.board.getScore() != -this.score && !this.board.isFull())
        {
            this.iterations = 0;

            long currentTime = System.nanoTime();

            ComputerPlayResult ai_move = this.maximizePlay(this.board, this.searchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);

            System.out.println("Score: " + ai_move.score);

            this.place(ai_move.column);


            System.out.println("Thinking time: " + (System.nanoTime() - currentTime) / 1000000 + "MS");
        }
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

                this.iterations++; // Debug

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

                this.iterations++;

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

    public boolean place(int column)
    {
        // If not finished
        if (this.board.getScore() != this.score && this.board.getScore() != -this.score && !this.board.isFull())
        {
            if(!this.board.place(column))
                return false;

            this.updateStatus();

            return true;
        }

        return false;
    }


    public void updateStatus()
    {

        // Human won
        if (this.board.getScore() == -this.score) {
            this.gameOver = true;
        }

        // Computer won
        if (this.board.getScore() == this.score) {
            this.gameOver = true;
        }

        // Tie
        if (this.board.isFull()) {
            this.gameOver = true;
        }
    }

    public String toString() {
        String out = "";

        //for(int j = this.width - 1; j >= 0; j-=1)
        for(int i = 0; i < this.height; i+=1)
        {
            for(int j = 0; j < this.width; j+=1)
            {
                switch (this.game_board[i][j])
                {
                    case EMPTY -> {
                        out += " ";
                    }

                    case RED -> {
                        out += "X";
                    }

                    case YELLOW -> {
                        out += "O";
                    }
                }
            }

            out += "\n";
        }


        return out;
    }

    public CellState switchRound(CellState player)
    {
        if(player == CellState.RED)
            return CellState.YELLOW;

        return CellState.RED;
    }
}
