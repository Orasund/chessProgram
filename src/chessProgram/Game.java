package chessProgram;

import java.util.*;
public class Game
{
  /*****************************
   * runningGame(p1,p2,42,1337);
   * starts a game
   ***************************** */
  public static void runningGame(Player whitePlayer, Player blackPlayer, long seed, int MAX_TIME)
  {
    Random random = new Random(seed);
    System.out.println("random Seed: "+seed);
    Board board = new Board();
    boolean whiteMat = false;
    boolean blackMat = false;
    boolean remis = false;
    int round = 1;
    int MAX_ROUNDS = 200;
    
    System.out.println(board.toString());
    Move m;
    while (!whiteMat && !blackMat && !remis && round < MAX_ROUNDS)
    {
      System.out.println("******** ROUND "+round+" ********");
      m = whitePlayer.chooseMove(board, Color.WHITE, MAX_TIME, random);
      System.out.println("WHITE: "+m.toString());
      board.executeMove(m);
      System.out.println(board.toString());
      
      if(board.isMat(Color.BLACK))
      {
        blackMat = true;
        break;
      }
        
      m = blackPlayer.chooseMove(board, Color.BLACK, MAX_TIME, random);
      System.out.println("BLACK: "+m.toString());
      board.executeMove(m);
      System.out.println(board.toString());
    
      if(board.isMat(Color.WHITE))
      {
        whiteMat = true;
        break;
      }
      
      if(MAX_TIME>=500)
        remis = true;
        
      round++;
    }
    if (whiteMat)
    System.out.println("Result: BLACK wins");
    if (blackMat)
    System.out.println("Result: WHITE wins");
    if (remis || round == MAX_ROUNDS)
    System.out.println("Result: REMIS");
  }
  
  /*****************************
   * main function
   * the main function of a game
   ***************************** */
  public static void main(String[] args)
  {
    long seed = (new Date()).getTime();
    Player whitePlayer = new HumanPlayer();
    Player blackPlayer = new MyPlayer();
    // Player whitePlayer = new MyPlayer();
    // Player blackPlayer = new HumanPlayerGUI();
    int MAX_TIME = 500;
    runningGame(whitePlayer, blackPlayer, seed, MAX_TIME);
  }
}