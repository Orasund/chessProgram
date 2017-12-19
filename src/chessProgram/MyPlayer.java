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
    {5, -5, 10,  0,  0, 10, -5,  5},
    {5, 10,  0,-25,-25,  0, 10,  5},
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
    {20,  20,  50,   10,   0,  10,  50,  20}
  };
  
  private static short[][] kingTableEndGame = new short[][]
  {
      {-50,-40,-30,-20,-20,-30,-40,-50},
      {-30,-20,-10,  0,  0,-10,-20,-30},
      {-30,-10, 20, 30, 30, 20,-10,-30},
      {-30,-10, 30, 40, 40, 30,-10,-30},
      {-30,-10, 30, 40, 40, 30,-10,-30},
      {-30,-10, 20, 30, 30, 20,-10,-30},
      {-30,-30,  0,  0,  0,  0,-30,-30},
      {-50,-30,-30,-30,-30,-30,-30,-50}
  };
  
  /*****************************
   * Move b = getFitness(Board b, Color color);
   * returns the Fitness of a given board
   ***************************** */
  public double getFitness(Board b, BlackWhite color)
  {
    //idea sum up figure qualities
    //pawn:1,rook: 5,bishop=knight=3.3, Queen:9,king:10000
    double[] sums = {0,0,0};
    int[][] kings = {{0,0},{0,0}};
    double sum = 0;
    for(int i = 0; i<8; i++)
      for(int j = 0; j<8; j++)
      {
        short fig = b.getFigure(i, j);
        if(fig == 0)
          continue;
        
        //if(Figure.colorOf(fig)!=color)
        //  continue;
          
        int c = (Figure.colorOf(fig)==color) ? 1 : -1;
        int y = (Figure.colorOf(fig)==BlackWhite.WHITE)?7-j:j;
        int x = (Figure.colorOf(fig)==BlackWhite.WHITE)?i:7-i;
        switch(Figure.typeOf(fig))
        {
          case Figure.PAWN:
            sums[1+c] += 100;
            sums[1] += c*pawnTable[y][x];
            break;
          case Figure.BISHOP:
            sums[1+c] += 325;
            sums[1] += c*bishopTable[y][x];
            break;
          case Figure.KNIGHT:
            sums[1+c] += 320;
            sums[1] += c*knightTable[y][x];
            break;
          case Figure.QUEEN:
            sums[1+c] += 975;
            break;
          case Figure.KING:
            sums[1] += c*40000;
            int temp_c = (Figure.colorOf(fig)==color)?0:1;
            kings[temp_c][0] = y;
            kings[temp_c][1] = x;
            break;
          case Figure.ROOK:
            sums[1+c] += 500;
            break;
          default:
            throw(new RuntimeException("getFitness is floored!"));
            
        }
      }
    sum = (sums[2]-sums[0])+sums[1];
    if(sums[0]<=1395 && sums[2]<=1395)
      sum += kingTableEndGame[kings[0][0]][kings[0][1]] - kingTableEndGame[kings[1][0]][kings[1][1]];
    else
      sum += kingTable[kings[0][0]][kings[0][1]] - kingTable[kings[1][0]][kings[1][1]];
    sum += 1*evalFigures(b,color);
    return sum;
  }
  
  
  public double evalFigures(Board b,BlackWhite mycolor)
  {
    int movement = 0;
    int attack = 0;
    int guard = 0;
    
    short temp_row;
    short temp_col;
    for(int col = 0; col<8; col++)
      for(int row = 0; row<8; row++)
      {
        short fig =  b.getFigure(col,row);
        if(fig==0)// || Figure.colorOf(fig) != color)
          continue;
        BlackWhite color = Figure.colorOf(fig);
        int c = (color==mycolor) ? 1 : -1;
        
        short type = Figure.typeOf(fig);
        if(type == Figure.PAWN)
        {
          // X
          // o
          temp_row = (color == BlackWhite.WHITE)?(short)(row+1):(short)(row-1);
          if(
            ((color == BlackWhite.WHITE)? temp_row <= 7 : temp_row >= 0)
            && b.getFigure(col, temp_row)==0
          )
          {
            movement+=2*c;
            // X
            // |
            // o
            temp_row = (color == BlackWhite.WHITE)?(short)(row+2):(short)(row-2);
            if(
              ((color == BlackWhite.WHITE)? row == 1 : row == 6)
              && b.getFigure(col, temp_row)==0
            )
              movement+=c;
          }
          
          // X
          //   o
          int[][] pos = (color == BlackWhite.WHITE)?new int[][]{{-1,1},{1,1}}:new int[][]{{-1,-1},{1,-1}};
          for(int[] coord:pos)
          {
            temp_col = (short)(col+coord[0]);
            temp_row = (short)(row+coord[1]);
            if(temp_col<0 || temp_col>=8 || temp_row <0 || temp_row >=8)
              continue;
            
            short sqaure = b.getFigure(temp_col, temp_row);
            if(sqaure!=0)
            {
              if(Figure.colorOf(sqaure)==color)
                guard+=2*c;
              else
                attack+=c;
            }
          }
        }
        else if (type == Figure.KNIGHT)
        {
          //   X   X
          // X   |   X
          //   - N -
          // X   |   X
          //   X   X
          int[][] pos = {
            {-1,2},{-2,1},{-1,-2},{-2,-1},{1,2},{2,1},{1,-2},{2,-1}
          };
          for(int[] coord:pos)
          {
            temp_col = (short)(col+coord[0]);
            temp_row = (short)(row+coord[1]);
            if(temp_col<0 || temp_col>=8 || temp_row <0 || temp_row >=8)
              continue;
            
            short sqaure = b.getFigure(temp_col, temp_row);
            if(sqaure==0)
              movement+=c;
            else if(Figure.colorOf(sqaure)==color)
              guard+=c;
            else
              attack+=c;
          }
        }
        else if(type == Figure.KING)
        {
          // 
          //  X X X
          //  X K X
          //  X X X
          //  
          int[][] pos = {
            {-1,1},{-1,-1},{1,1},{1,-1},{0,1},{0,-1},{1,0},{-1,0}
          };
          for(int[] coord:pos)
          {
            temp_col = (short)(col+coord[0]);
            temp_row = (short)(row+coord[1]);
            if(temp_col<0 || temp_col>=8 || temp_row <0 || temp_row >=8)
              continue;
            
            short sqaure = b.getFigure(temp_col, temp_row);
            if(sqaure==0)
              movement+=c;
            else if(Figure.colorOf(sqaure)==color)
              guard+=c;
            else
              attack+=c;
          }
        }
        else if (type == Figure.ROOK || type == Figure.QUEEN)
        {
          //     X
          //     X
          // X X R X X
          //     X
          //     X
          int[][] pos = {
            {0,1},{0,-1},{1,0},{-1,0}
          };
          for(int[] coord:pos)
          {
            for(int j = 1; true; j++)
            {
              temp_col = (short)(col+j*coord[0]);
              temp_row = (short)(row+j*coord[1]);
              if(temp_col<0 || temp_col>=8 || temp_row <0 || temp_row >=8)
                break;
              
              short sqaure = b.getFigure(temp_col, temp_row);
              if(sqaure==0)
              {
                movement+=c;
                continue;
              }
              
              if(Figure.colorOf(sqaure)==color)
                guard+=c;
              else
                attack+=c;
            }
          }
        }
        if(type == Figure.BISHOP || type == Figure.QUEEN)
        {
          // X       X
          //   X   X
          //     B
          //   X   X
          // X       X
          int[][] pos = {
              {-1,1},{-1,-1},{1,1},{1,-1}
          };
          for(int[] coord:pos)
          {
            for(int j = 1; true; j++)
            {
              temp_col = (short)(col+j*coord[0]);
              temp_row = (short)(row+j*coord[1]);
              if(temp_col<0 || temp_col>=8 || temp_row <0 || temp_row >=8)
                break;
              
              short sqaure = b.getFigure(temp_col, temp_row);
              if(sqaure==0)
              {
                movement+=c;
                continue;
              }
              
              if(Figure.colorOf(sqaure)==color)
                guard+=c;
              else
                attack+=c;
            }
          }
        }
      }
    
    
    
    return 2*attack+3*guard+movement;
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
