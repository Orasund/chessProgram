package chessProgram;

public interface Player
{
    Move chooseMove(Board b,BlackWhite color,int milliseconds, java.util.Random random);
    double getFitness(Board b, BlackWhite color); 
}