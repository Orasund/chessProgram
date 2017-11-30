package chessProgram;

import java.util.ArrayList;

class Thinker implements Runnable
{
  
    Player player;
    Board b;
    Color color;
    Move best_move;
    java.util.Random random;
    
    /*****************************
     * Constructor
     ***************************** */
    Thinker(Board b_,Color color_,Player player_, java.util.Random random_)
    {
      b = b_;
      color = color_;
      player = player_;
      random = random_;
    }

    /*****************************
     * thinker.run();
     * implemented run() function from Runnable
     ***************************** */
    public void run()
    {
      int level = 1;
      while(true)
      {
          ArrayList<Move> moves = b.getValidMoves(color);
          int n = moves.size();
          Move temp_best_move = null;
          double best_fitness = 0;
          for(int i = 0; i< n; i++)
          {
              Board temp = b.cloneIncompletely();//corrent information+previous move
              temp.executeMove(moves.get(i));
              double q = evaluate(temp,Board.FlipColor(color), level-1); //apponant color
              if(i == 0 || q>best_fitness || (q == best_fitness && random.nextInt(2)==0))
              {
                temp_best_move = moves.get(i);
                best_fitness = q;
              }
          }
          best_move = temp_best_move;
          System.out.println("level:"+level+", fit:"+best_fitness);
          level++;
      }
    }
    
    /*****************************
     * double fitness = evaluate(b, color,level);
     * evaluates (recursively) the state of the board
     ***************************** */
    private double evaluate(Board b, Color color, int level)
    {
        if(level==0)
        {
            double myFitness = player.getFitness(b,color);
            double oppFitness = player.getFitness(b,Board.FlipColor(color));
            return oppFitness-myFitness;
        }
        else
        {
            ArrayList<Move> moves = b.getValidMoves(color);
            int n = moves.size();
            double min = 0;
            for(int i=0; i<n;i++)
            {
                Board temp = b.cloneIncompletely();
                temp.executeMove(moves.get(i));
                double d = evaluate(temp,Board.FlipColor(color), level-1);
                if(i == 0)
                  min = d;
                else if(d < min)
                  min=d;
            }
            return -min;
        }
        //if two moves have same quality,
        //evaluate them again with level = 0;
        //be greedy
        //if again same, choose random
    }
    
    /*****************************
     * Move best = getBestMove();
     * gives back the currently best possible move.
     ***************************** */
    public Move getBestMove()
    {
      return best_move;
    }
}
