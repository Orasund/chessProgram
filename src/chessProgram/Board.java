package chessProgram;

import java.util.ArrayList;

//points on the board will be represented as [col][row]
//
//7               y   y = [7][7]
//6
//5
//4
//3
//2
//1 
//0   x               x = [1][0]
//  0 1 2 3 4 5 6 7
//
//Ideal: one may use one array

public class Board
{
  private short[][] figures;
  private boolean whiteCanCastleQueenSide;
  private boolean whiteCanCastleKingSide;
  private boolean blackCanCastleQueenSide;
  private boolean blackCanCastleKingSide;
  private ArrayList<Move> history;

  /*****************************
   * Constructor
   ***************************** */
  public Board()
  {
    figures = new short[8][8];
    history = new ArrayList<Move>();
    reset();
  }
  
  /*****************************
   * Copy-Constructor
   ***************************** */
  public Board(Board board)
  {
    figures = board.getFigures();
    whiteCanCastleQueenSide = board.getWhiteCanCastleQueenSide();
    whiteCanCastleKingSide = board.getWhiteCanCastleKingSide();
    blackCanCastleQueenSide = board.getBlackCanCastleQueenSide();
    blackCanCastleKingSide = board.getBlackCanCastleKingSide();
    history = new ArrayList<Move>();
    ArrayList<Move> old_history = board.getHistory();
    int n = old_history.size();
    if(n>=2)
      history.add(old_history.get(n-1-1));
    if(n>=1)
      history.add(old_history.get(n-1));
  }

  /*****************************
   * b.Reset();
   * resets the board
   ***************************** */
  private void reset()
  {
	//Place special Figures for WHITE
	figures[0][0]= (short)(Figure.ROOK + Figure.WHITE_OFFSET); // a number
	figures[1][0]=(short)(Figure.KNIGHT+Figure.WHITE_OFFSET);  //  a offset number like =1
	figures[2][0]=(short)(Figure.BISHOP+Figure.WHITE_OFFSET);
	figures[3][0]=(short)(Figure.QUEEN+Figure.WHITE_OFFSET);
	figures[4][0]=(short)(Figure.KING+Figure.WHITE_OFFSET);
	figures[5][0]=(short)(Figure.BISHOP+Figure.WHITE_OFFSET);
	figures[6][0]=(short)(Figure.KNIGHT+Figure.WHITE_OFFSET); 
	figures[7][0]=(short)(Figure.ROOK + Figure.WHITE_OFFSET);
	
	//Pawns
	for(int i = 0; i<=7; i++)
	{
	  figures[i][1]=(short)(Figure.PAWN + Figure.WHITE_OFFSET);
	  figures[i][6]=(short)(Figure.PAWN + Figure.BLACK_OFFSET);
	}
	
	//Place special Figures for BLACK
    figures[0][7]=(short)(Figure.ROOK + Figure.BLACK_OFFSET);
    figures[1][7]=(short)(Figure.KNIGHT+Figure.BLACK_OFFSET);
    figures[2][7]=(short)(Figure.BISHOP+Figure.BLACK_OFFSET);
    figures[3][7]=(short)(Figure.QUEEN+Figure.BLACK_OFFSET);
    figures[4][7]=(short)(Figure.KING+Figure.BLACK_OFFSET);
    figures[5][7]=(short)(Figure.BISHOP+Figure.BLACK_OFFSET);
    figures[6][7]=(short)(Figure.KNIGHT+Figure.BLACK_OFFSET); 
    figures[7][7]=(short)(Figure.ROOK + Figure.BLACK_OFFSET);
    
    //clear all other spaces
    for(int i=0; i<=7; i++)
      for(int j=2; j<=5; j++)
      {
        figures[i][j]=(short)(Figure.EMPTY); //0
      }
    
    //reset all parameters
	whiteCanCastleQueenSide = true; //Private
	whiteCanCastleKingSide = true; //Private
	blackCanCastleQueenSide = true;
	blackCanCastleKingSide = true;
	history = new ArrayList<Move>();
  }
  
  /*****************************
   * Board new_b = b.CloneIncompletely();
   * clones everything but the history.
   ***************************** */
  public Board cloneIncompletely()
  {
    return new Board(this);
  }
  
  public ArrayList<Move> getValidMoves(BlackWhite color)
  {
    ArrayList<Move> moves = new ArrayList<Move>();
    if(isMat()==true)
      return moves;
          
    for(int i = 0; i<figures.length; i++)
      for(int j = 0; j<figures.length; j++)
      {
        short f = figures[i][j];
        if(f == 0)
          continue;
        if(Figure.colorOf(f)==color)
          moves.addAll(Figure.getValidMoves(this,(short)i,(short)j));
      }
    return moves;
  }
  
  /*****************************
   * ArrayList<Move> history = getHistory();
   * returns a clone of the history
   ***************************** */
  @SuppressWarnings("unchecked")
  public ArrayList<Move> getHistory()
  {
    return (ArrayList<Move>)history.clone();
  }
  
  /*****************************
   * ArrayList<Move> history = getHistory();
   * returns a clone of the history
   ***************************** */
  public boolean executeMove(Move move) //dont validate!
  {
    history.add(move);
    
    //king moved -> can't Castle
    if(move.getType() == Figure.KING)
    {
      if(move.getColor()==BlackWhite.WHITE)
      {
        whiteCanCastleQueenSide = false;
        whiteCanCastleKingSide = false;
      }
      else
      {
        blackCanCastleQueenSide = false;
        blackCanCastleKingSide = false;
      }
    }
    
    //Castleing
    int isCastle = move.isCastle();
    if(isCastle != 0)
    {
      short offset = (move.getColor()==BlackWhite.WHITE) ? Figure.WHITE_OFFSET:Figure.BLACK_OFFSET;
      figures[move.getDestCol()][move.getDestRow()] = (short)(Figure.KING+offset);
      figures[move.getSourceCol()][move.getSourceRow()] = 0;
      
      if(isCastle ==  1)
      {
        figures[5][move.getDestRow()] = (short)(Figure.ROOK+offset);
        figures[7][move.getDestRow()] = 0;
      }
      else //if(isCastle == 2)
      {
        figures[3][move.getDestRow()] = (short)(Figure.ROOK+offset);
        figures[0][move.getDestRow()] = 0;
      }
      return true;
    }
    
    short fig = figures[move.getSourceCol()][move.getSourceRow()];
    
    short newType = move.getNewType();
    if(move.getNewType()!=0) //promoting
    {
      if(move.getColor()==BlackWhite.WHITE)
        fig = newType;
      else
        fig = (short)(newType+Figure.BLACK_OFFSET);
    }
    
    //En Passant
    if(Figure.typeOf(fig) == Figure.PAWN && move.getIsHit() && figures[move.getDestCol()][move.getDestRow()]==0)
    {
      figures[move.getDestCol()][move.getSourceRow()] = 0;
    }
    
    figures[move.getDestCol()][move.getDestRow()] = fig;
    figures[move.getSourceCol()][move.getSourceRow()] = 0;
    
    //Rook moves -> cant castle
    if(move.getType() == Figure.ROOK)
    {
      if(move.getColor()==BlackWhite.WHITE)
      {
        if(move.getSourceCol() == 0)
          whiteCanCastleQueenSide = false;
        else
          whiteCanCastleKingSide = false;
      }
      else
      {
        if(move.getSourceCol() == 0)
          blackCanCastleQueenSide = false;
        else
          blackCanCastleKingSide = false;
      }
    }
    return true;
  }
  
  /*****************************
   * whiteCanCastle = b.WhiteCanCastle();
   * returns if white can Castle
   ***************************** */
  public Boolean WhiteCanCastle()
  {
    return whiteCanCastleKingSide && whiteCanCastleQueenSide;
  }
  
  /*****************************
   * Boolean blackCanCastle = b.BlackCanCastle();
   * returns if black can Castle
   ***************************** */
  public Boolean BlackCanCastle()
  {
    return blackCanCastleKingSide && blackCanCastleQueenSide;
  }
  
  /*****************************
   * Boolean mat = isMat();
   * returns if a player has lost
   ***************************** */
  public boolean isMat()
  {
    for(int i = 0; i<8; i++)
      for(int j = 0; j<8; j++)
      {
        if(Figure.typeOf(figures[i][j])==Figure.KING)
          return false;
      }
    
    return true;
  }
  
  /*****************************
   * Boolean whiteHasLost = isMat(Color.WHITE);
   * returns if the color has its King
   ***************************** */
  public boolean isMat(BlackWhite color)
  {
    if(color == BlackWhite.WHITE)
      for(int i = 0; i<8; i++)
        for(int j = 0; j<8; j++)
        {
          if(Figure.typeOf(figures[i][j])==Figure.KING && Figure.colorOf(figures[i][j])==color)
            return false;
        }
    else
      for(int i = 7; i>=0; i--)
        for(int j = 7; j>=0; j--)
        {
          if(Figure.typeOf(figures[i][j])==Figure.KING && Figure.colorOf(figures[i][j])==color)
            return false;
        }
    return true;
  }
  
  /*****************************
   * String out = b.toString();
   * returns a printable representation
   ***************************** */
  public String toString() //on the console
  {
    String out = "  -------------------------------   waiting for:"+currentColor();
    out += '\n';
    for(int i=7; i>=0; i--)
    {
      out += (int)RowName(i) + "|";
      for(int j=0; j<=7; j++)
      {
        out += " " + Figure.toString(figures[j][i]) + " ";
        out +="|";
      }
      out += '\n';
      out += " |---|---|---|---|---|---|---|---|";
      out += '\n';
    }
    out += "  ";
    for(int j=0; j<=7; j++)
    {
      out += " " + ColumnName(j) + "  ";
    }
    return out;
  }
  
  /*****************************
   * Color c = currentColor();
   * returns the color of the next move
   ***************************** */
  public BlackWhite currentColor()
  {
    if(history.size()==0)
      return BlackWhite.WHITE;
    
    Move last = history.get(history.size()-1);
    return FlipColor(last.getColor());
  }
  
  /*****************************
   * Setters
   ***************************** */
  public void SetFigure(int column, int row, short figure)
  {
    figures[column][row] = figure;
  }
  
  /*****************************
   * Getters
   ***************************** */
  public short[][] getFigures()
  {
    short[][] out = new short[8][8];
    for(int i = 0; i<=7; i++)
      for(int j = 0; j<=7; j++)
      {
        out[i][j] = figures[i][j];
      }
    return out;
  }
  
  public short getFigure(int column, int row)
  {
    return figures[column][row];
  }
  
  public boolean getWhiteCanCastleQueenSide()
  {
    return whiteCanCastleQueenSide;
  }
  
  public boolean getWhiteCanCastleKingSide()
  {
    return whiteCanCastleKingSide;
  }
  
  public boolean getBlackCanCastleQueenSide()
  {
    return blackCanCastleQueenSide;
  }
  
  public boolean getBlackCanCastleKingSide()
  {
    return blackCanCastleKingSide;
  }
  
  /*****************************
   * 
   * STATIC FUNCTIONS
   * 
   ***************************** */
  
  /*****************************
   * black = Board.FlipColor(Color.WHITE);
   * flips a color
   ***************************** */
  public static BlackWhite FlipColor(BlackWhite color)
  {
    if(color == BlackWhite.WHITE)
      return BlackWhite.BLACK;
    return BlackWhite.WHITE;
  }
  
  /*****************************
   * String w = ColorToString(Color.White)
   * returns "w" or "b"
   ***************************** */
  public static String ColorToString(BlackWhite color)
  {
    if(color == BlackWhite.WHITE)
      return "w";
    return "b";
  }
  
  /*****************************
   * String out = CoordsToString(0,0);
   * returns a printable version of Coords
   ***************************** */
  public static String CoordsToString(int col, int row)
  {
    return "" + ColumnName(col) + (int)RowName(row);
  }
  
  /*****************************
   * int[] coords = StringToCoords("a1");
   * returns the coords of the String-representation
   ***************************** */
  public static int[] StringToCoords(String s)
  {
    int[] out = {ColumnIndex(s.charAt(0)),RowIndex(s.charAt(1))};
    return out;
  }
  
  /*****************************
   * a == ColumnName(0)
   * returns the printable Representation of the input
   ***************************** */
  public static char ColumnName(int index)
  {
    return (char)('a'+index);
  }
  
  /*****************************
   * int 0 = ColumnIndex('a');
   * return the coord of the String-representation
   ***************************** */
  public static int ColumnIndex(char name)
  {
    return name-'a';
  }
  
  /*****************************
   * 1 == ColumnName(0)
   * returns the printable Representation of the input
   ***************************** */
  public static char RowName(int index)
  {
    return (char)(index+1);
  }
  
  /*****************************
   * int 0 = RowIndex(1);
   * return the row of the String-representation
   ***************************** */
  public static int RowIndex(char name)
  {
    return name-1-48;
  }
}