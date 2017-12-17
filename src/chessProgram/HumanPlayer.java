package chessProgram;

import java.util.Scanner;

public class HumanPlayer implements Player
{
  Scanner scanner;
  
  /*****************************
   * Move b = getFitness(Board b, Color color);
   * returns the Fitness of a given board
   ***************************** */
  HumanPlayer()
  {
    scanner = new Scanner(System.in);
  }
  
  /*****************************
   * Move m = chooseMove(b,color,milliseconds, random);
   * chooses a Move in a given time
   ***************************** */
  public Move chooseMove(Board board, BlackWhite color, int milliSeconds, java.util.Random random)
  {
    System.out.println(">> PLEASE INPUT A COMMAND:");
    String input = scanner.next();
    Move out = new Move(input,board);
    return out;
  }
  public double getFitness(Board board, BlackWhite color) {
    return 0;
  }
}