package chessProgram;

import java.util.Random;

public class HumanPlayerGUI implements Player
{
  @Override
  public double getFitness(Board board, BlackWhite color)
  {
	return 0;
  }

  @Override
  public Move chooseMove(Board board, BlackWhite color, int milliSeconds, Random random)
  {
    PlayerGUI theGUI = new PlayerGUI(board, color);
    theGUI.setAlwaysOnTop(true);
    Thread thread = new Thread(new Runnable()
    {
  	  @Override
  	  public void run()
  	  {
  	    theGUI.setBounds(10, 10, 420, 180);
  	    theGUI.show();
  	  }
    });
    thread.start();
    while(theGUI.move == null)
    {
      try
      {
        Thread.sleep((int)50);
  	  }
      catch(Exception l) {};
    }
    theGUI.dispose();
    return theGUI.move;
  }
}
