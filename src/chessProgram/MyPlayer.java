package chessProgram;

public class MyPlayer implements Player
{
  private static short[][] pawnTable = new short[][]
  {
    {0,  0,  0,  0,  0,  0,  0,  0},
    {50, 50, 50, 50, 50, 50, 50, 50},
    {10, 10, 20, 30, 30, 20, 10, 10},
    {5,  5, 10, 27, 27, 10,  5,  5},
    {0,  0,  0, 25, 25,  0,  0,  0},
    {5, -5,-10,  0,  0,-10, -5,  5},
    {5, 10, 10,-25,-25, 10, 10,  5},
    {0,  0,  0,  0,  0,  0,  0,  0}
  };
  private static short[][] knightTable = new short[][]
  {
   {-50,-40,-30,-30,-30,-30,-40,-50},
   {-40,-20,  0,  0,  0,  0,-20,-40},
   {-30,  0, 10, 15, 15, 10,  0,-30},
   {-30,  5, 15, 20, 20, 15,  5,-30},
   {-30,  0, 15, 20, 20, 15,  0,-30},
   {-30,  5, 10, 15, 15, 10,  5,-30},
   {-40,-20,  0,  5,  5,  0,-20,-40},
   {-50,-40,-20,-30,-30,-20,-40,-50}
  };

  private static short[][] bishopTable = new short[][]
  {
   {-20,-10,-10,-10,-10,-10,-10,-20},
   {-10,  0,  0,  0,  0,  0,  0,-10},
   {-10,  0,  5, 10, 10,  5,  0,-10},
   {-10,  5,  5, 10, 10,  5,  5,-10},
   {-10,  0, 10, 10, 10, 10,  0,-10},
   {-10, 10, 10, 10, 10, 10, 10,-10},
   {-10,  5,  0,  0,  0,  0,  5,-10},
   {-20,-10,-40,-10,-10,-40,-10,-20},
  };

  private static short[][] kingTable = new short[][]
  {
    {-30, -40, -40, -50, -50, -40, -40, -30},
    {-30, -40, -40, -50, -50, -40, -40, -30},
    {-30, -40, -40, -50, -50, -40, -40, -30},
    {-30, -40, -40, -50, -50, -40, -40, -30},
    {-20, -30, -30, -40, -40, -30, -30, -20},
    {-10, -20, -20, -20, -20, -20, -20, -10}, 
    {20,  20,   0,   0,   0,   0,  20,  20},
    {20,  30,  10,   0,   0,  10,  30,  20}
  };
  
  /*****************************
   * Move b = getFitness(Board b, Color color);
   * returns the Fitness of a given board
   ***************************** */
  public double getFitness(Board b, BlackWhite color)
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
        int isWhite = (Figure.colorOf(fig)==BlackWhite.WHITE)?1:-1;
        switch(Figure.typeOf(fig))
        {
          case Figure.PAWN:
            sum += c*100;
            sum += c*pawnTable[(8-isWhite*j)%8][(8-isWhite*i)%8];
            break;
          case Figure.BISHOP:
            sum += c*325;
            sum += c*bishopTable[(8-isWhite*j)%8][(8-isWhite*i)%8];
            break;
          case Figure.KNIGHT:
            sum += c*320;
            sum += c*knightTable[(8-isWhite*j)%8][(8-isWhite*i)%8];
            break;
          case Figure.QUEEN:
            sum += c*975;
            break;
          case Figure.KING:
            sum += c*40000;
            sum += c*kingTable[(8-isWhite*j)%8][(8-isWhite*i)%8];
            break;
          case Figure.ROOK:
            sum += c*500;
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
  public Move chooseMove(Board board ,BlackWhite color ,int time_limit ,java.util.Random random)
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
