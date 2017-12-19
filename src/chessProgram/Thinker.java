package chessProgram;

import java.util.Date;
import java.util.LinkedList;

class Thinker implements Runnable
{
  
    Player player;
    Board b;
    BlackWhite color;
    Move best_move;
    java.util.Random random;
    int var_k = 0;
    
    /*****************************
     * Constructor
     ***************************** */
    Thinker(Board b_,BlackWhite color_,Player player_, java.util.Random random_)
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
      int level = 4;
      while(true)
      {
        var_k = 0;
        LinkedList<Move> moves = b.getValidMoves(color);
        LinkedList<Move> temp_best_moves = new LinkedList<Move>();
        double best_fitness = Double.POSITIVE_INFINITY;
        for(Move move_i:moves)
        {
            Board temp = b.cloneIncompletely();//corrent information+previous move
            temp.executeMove(move_i);
            double q = evaluate(temp,Board.FlipColor(color), level-1,-best_fitness); //apponant color
            //System.out.println(move_i.toString()+","+q);
            if(temp_best_moves.size() == 0 || q<best_fitness)
            {
              temp_best_moves = new LinkedList<Move>();
              temp_best_moves.add(move_i);
              best_fitness = q;
            }
            else if(q == best_fitness)
              temp_best_moves.add(move_i);
        }
        best_move = temp_best_moves.get(0);
        if(temp_best_moves.size() > 1)
        {
          moves = temp_best_moves;
          temp_best_moves = new LinkedList<Move>();
          best_fitness = Double.POSITIVE_INFINITY;
          for(Move move_i:moves)
          {
              Board temp = b.cloneIncompletely();//corrent information+previous move
              temp.executeMove(move_i);
              double q = evaluate(temp,Board.FlipColor(color), 0,-best_fitness); //apponant color
              if(q>best_fitness || temp_best_moves.size() == 0)
              {
                temp_best_moves = new LinkedList<Move>();
                temp_best_moves.add(move_i);
                best_fitness = q;
              }
              else if(q == best_fitness)
                temp_best_moves.add(move_i);
          }
          System.out.println("thinking...");
          if(temp_best_moves.size() > 1)
          {
            best_move = temp_best_moves.get(random.nextInt(temp_best_moves.size()));
            System.out.println("choosing a random move...");
            for(Move move:temp_best_moves)
              System.out.println("(*)"+(move.toString()));
          }
          else
            best_move = temp_best_moves.get(0);
        }
        System.out.println("var_k:"+var_k);
        System.out.println("level:"+level+", fit:"+best_fitness+", move:"+best_move.toString());
        level++;
      }
    }
    
    /*****************************
     * double fitness = evaluate(b, color,level);
     * evaluates (recursively) the state of the board
     ***************************** */
    private double evaluate(Board b, BlackWhite color, int level, double current_min)
    {
        if(level==0)
        {
          var_k++;
          double fitness = player.getFitness(b,color);
          if(fitness > 40000)
            return Double.POSITIVE_INFINITY;
          else if(fitness < -40000)
            return -Double.POSITIVE_INFINITY;
          return fitness;
        }
        else
        {
            LinkedList<Move> moves = b.getValidMoves(color);
            double min = Double.POSITIVE_INFINITY;
            for(Move move_i:moves)
            {
              Board temp = b.cloneIncompletely();
              temp.executeMove(move_i);
              double d = evaluate(temp,Board.FlipColor(color), level-1,-min);
              
              if(min == Double.POSITIVE_INFINITY)
              {
                min=d;
                continue;
              }
              else if(d<current_min) //alpha-beta-search
                return Double.POSITIVE_INFINITY;
              if(d < min)
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
