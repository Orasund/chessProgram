package chessProgram;

public class Testing
{
  public static void BoardNewMove(String s,Board b)
  {
    System.out.println('('+s+')');
    b.executeMove(new Move(s,b));
  }
  public static void BoardDebug(Board b)
  {
    System.out.println(b.toString());
    System.out.println("(*) b.getWhiteCanCastleQueenSide()="+b.getWhiteCanCastleQueenSide());
    System.out.println("(*) b.getWhiteCanCastleKingSide()="+b.getWhiteCanCastleKingSide());
    System.out.println("(*) b.getBlackCanCastleQueenSide()="+b.getBlackCanCastleQueenSide());
    System.out.println("(*) b.getBlackCanCastleKingSide()="+b.getBlackCanCastleKingSide());
    System.out.println("(*) b.currentColor()="+b.currentColor());
  }
  public static void BoardClass()
  {
    System.out.println("************************");
    System.out.println("START testing Board...");
    
    Board b = new Board();
    System.out.println("Board b = new Board();");
    BoardDebug(b);
    
    b.executeMove(new Move("g2-g4",b));
    System.out.println("b.execute_move(new Move(\"g2-g4\",b));");
    BoardDebug(b);
    
    BoardNewMove("a7-a5",b);
    BoardNewMove("Ng1-h3",b);
    BoardNewMove("a5-a4",b);
    BoardNewMove("Bf1-g2",b);
    BoardNewMove("b2-b4",b);
    BoardNewMove("a4xb3",b);
    System.out.println("EN PASSANT");
    BoardDebug(b);
    
    BoardNewMove("0-0",b);
    System.out.println("SMALL CASTLE");
    BoardDebug(b);
    
    BoardNewMove("b3-b2",b);
    BoardNewMove("Nb1-a3",b);
    BoardNewMove("b2-b1Q",b);
    System.out.println("PROMOTING");
    BoardDebug(b);
    
    System.out.println("Board b2 = b.cloneIncompletely();");
    Board b2 = b.cloneIncompletely();
    BoardDebug(b2);
    System.out.println("b2.isMat(Color.BLACK)="+b2.isMat(BlackWhite.BLACK));
    
    System.out.println("END testing Board...");
  }
  
  /*****************************
   * MoveDebug(a);
   * gives a Debug-representation of a
   ***************************** */
  public static void MoveDebug(Move a)
  {
    System.out.println(
      "(*) a.toString():"+a.toString()
    );
    System.out.println(
      "(*) a.getColor():"+a.getColor()
    );
    System.out.println(
      "(*) a.getType():"+a.getType()
    );
    System.out.println(
      "(*) a.getSourceCol():"+a.getSourceCol()
    );
    System.out.println(
      "(*) a.getSourceRow():"+a.getSourceRow()
    );
    System.out.println(
      "(*) a.getDestCol():"+a.getDestCol()
    );
    System.out.println(
      "(*) a.getDestRow():"+a.getDestRow()
    );
    System.out.println(
      "(*) a.getIsHit():"+a.getIsHit()
    );
    System.out.println(
      "(*) a.getNewType():"+a.getNewType()
    );
  }
  
  /*****************************
   * Testing.MoveClass();
   * tests the Move Class.
   ***************************** */
  public static void MoveClass()
  {
    System.out.println("************************");
    System.out.println("START testing Move...");
    
    Board b = new Board();
    Move a;
    
    a = new Move("a2-a4",b);
    System.out.println("new Move(\"a2-a4\",b)");
    MoveDebug(a);
    
    a = new Move("Ra2xa4",b);
    System.out.println("Ra2xa4="+a.toString());
    
    a = new Move("a2-a4R",b);
    System.out.println("a2-a4R="+a.toString());
    
    a = new Move("0-0",b);
    System.out.println("0-0="+a.toString());
    
    a = new Move("0-0-0",b);
    System.out.println("new Move(\"0-0-0\",b);");
    MoveDebug(a);
    
    System.out.println("END testing Move...");
  }
  
  /*****************************
   * Testing.FigureClass();
   * tests the Figure Class.
   ***************************** */
  public static void FigureClass()
  {
    System.out.println("************************");
    System.out.println("START testing Figure...");
    
    System.out.println(
      "(*) Figure.toString(Figure.PAWN)):"
      +Figure.toString(Figure.PAWN)
    );
    System.out.println(
      "(*) Figure.toString(Figure.ROOK)):"
      +Figure.toString(Figure.ROOK)
    );
    System.out.println(
      "(*) Figure.toString(Figure.KNIGHT)):"
      +Figure.toString(Figure.KNIGHT)
    );
    System.out.println(
      "(*) Figure.toString(Figure.BISHOP)):"
      +Figure.toString(Figure.BISHOP)
    );
    System.out.println(
      "(*) Figure.toString(Figure.QUEEN)):"
      +Figure.toString(Figure.QUEEN)
    );
    System.out.println(
      "(*) Figure.toString(Figure.KING)):"
      +Figure.toString(Figure.KING)
    );
    System.out.println(
      "(*) Figure.colorOf(Figure.ROOK+Figure.WHITE_OFFSET):"
      +Figure.colorOf((short)(Figure.ROOK+Figure.WHITE_OFFSET))
    );
    System.out.println(
      "(*) Figure.colorOf(Figure.ROOK+Figure.BLACK_OFFSET):"
      +Figure.colorOf((short)(Figure.ROOK+Figure.BLACK_OFFSET))
    );
    System.out.println("END testing Figure...");
  }
  
  /*****************************
   * Testing.PlayerClass();
   * tests the Player Class.
   ***************************** */
  public static void PlayerClass()
  {
    System.out.println("************************");
    System.out.println("START testing Player Classes...");
    Player p1 = new HumanPlayer();
    Player p2 = new RandomPlayer();
    long seed = 845823893;
    int MAX_TIME = 6000;
    Game.runningGame(p1, p2, seed, MAX_TIME);
    System.out.println("END testing Player Classes...");
  }
}
