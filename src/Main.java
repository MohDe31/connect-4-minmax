import Models.Game;

import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        //launch(args);

        Game game = new Game(7, 6);
        Scanner myObj = new Scanner(System.in);


        while(!game.isGameOver()) {
            System.out.println("Enter a column");

            int col = Integer.parseInt(myObj.nextLine());



            game.place(col);

            System.out.println(game);

            game.generateComputerDecision();

            System.out.println(game);
        }
    }

}
