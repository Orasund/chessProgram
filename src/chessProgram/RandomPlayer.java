package chessProgram;

import java.util.LinkedList;

public class RandomPlayer implements Player
{
    /*****************************
     * Move b = getFitness(Board b, Color color);
     * returns the Fitness of a given board
     ***************************** */
    public double getFitness(Board b, BlackWhite color)
    {
        return 0;
    }
    
    /*****************************
     * Move m = chooseMove(b,color,milliseconds, random);
     * chooses a Move in a given time
     ***************************** */
    public Move chooseMove(Board b,BlackWhite color,int milliseconds, java.util.Random random)
    {
        LinkedList<Move> moves = b.getValidMoves(color);
        //break if no move is possible (throw exeption)
        if(moves.size()==0)
          throw new RuntimeException("no move to choose from");
        int k = random.nextInt(moves.size());
        return moves.get(k);
    }
}