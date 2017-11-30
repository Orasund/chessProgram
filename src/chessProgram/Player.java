package chessProgram;

public interface Player
{
    Move chooseMove(Board b,Color color,int milliseconds, java.util.Random random);
    double getFitness(Board b, Color color); 
}