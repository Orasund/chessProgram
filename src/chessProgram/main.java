//10h
package chessProgram;

public class main {

	public static void main(String[] args)
	{
	 try
	  {
	    /*Board b = new Board();
  	    Player p1 = new HumanPlayer();
  	    Player p2 = new MyPlayer();//RandomPlayer();
  	    Move m;
  	    int time = 6000;
  	    System.out.println(b.toString());
	    for(int i = 0; true; i++)
	    {
	      System.out.println("******** TURN "+i+" ********");
	      m = p1.chooseMove(b, Color.WHITE, time, new Random(845823893));
	      System.out.println("WHITE: "+m.toString());
	      b.executeMove(m);
	      System.out.println(b.toString());
	    
	      if(b.isMat(Color.BLACK))
	        break;
	      m = p2.chooseMove(b, Color.BLACK, time, new Random(845823893));
	      System.out.println("BLACK: "+m.toString());
	      b.executeMove(m);
          System.out.println(b.toString());
        
          if(b.isMat(Color.WHITE))
            break;
	    }*/
	    Game.main(args);
	  }
	  catch(Exception e)
	  {
	    System.out.println(e.getMessage());
	  }
	}

}
