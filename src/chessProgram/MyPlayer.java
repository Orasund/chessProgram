package chessProgram;

public class MyPlayer implements Player
{
  /*****************************
   * Move b = getFitness(Board b, Color color);
   * returns the Fitness of a given board
   ***************************** */
  public double getFitness(Board b, Color color)
  {
    //idea sum up figure qualities
    //pawn:1,rook: 5,bishop=knight=3.3, Queen:9,king:10000
    double sum = 0;
    for(int i = 0; i<8; i++)
      for(int j = 0; j<8; j++)
      {
        short fig = b.getFigure(i, j);
        if(fig == 0)
          continue;
          
        int c = (Figure.colorOf(fig)==color) ? 1 : -1;
        switch(Figure.typeOf(fig))
        {
          case Figure.PAWN:
            sum += c*1;
            break;
          case Figure.BISHOP:
            sum += c*3.3;
            break;
          case Figure.KNIGHT:
            sum += c*3.3;
            break;
          case Figure.QUEEN:
            sum += c*9;
            break;
          case Figure.KING:
            sum += c*10000;
            break;
          case Figure.ROOK:
            sum += c*5;
            break;
          default:
            throw(new RuntimeException("getFitness is floored!"));
            
        }
      }
    return sum;
  }
    
  /*****************************
   * Move m = chooseMove(b,color,milliseconds, random);
   * chooses a Move in a given time
   ***************************** */
  public Move chooseMove(Board board ,Color color ,int time_limit ,java.util.Random random)
  {
      Thinker thinker = new Thinker(board,color, this, random); //interface runnable!!!!
      Thread t = new Thread(thinker);
      t.start();
      try
      {
        Thread.sleep(time_limit);
      }catch(Exception e){
        System.out.println(e.getMessage());
      }

      t.stop();
      return thinker.getBestMove();
  }
}
