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
//0 1 2 3 4 5 6 7
//
//Ideal: one may use one array

//TODO:not yet ready
public class Board
{
  private short[][] figures;
  private boolean whiteCanCastleQueenSide;
  private boolean whiteCanCastleKingSide;
  private boolean blackCanCastleQueenSide;
  private boolean blackCanCastleKingSide;
  private ArrayList<Move> history;
	
  public Board()
  {
    figures = new short[8][8];
    Reset();
  }
  
  //public short[][] getFigures()
  //public void SetFigure(int row, int column, short figure)

  private void Reset()
  {
	//Place special Figures for WHITE
	figures[0][0]= (short)(Figure.ROOK + Figure.WHITE_OFFSET); // a number
	figures[1][0]=(short)(Figure.KNIGHT+Figure.WHITE_OFFSET);  //  a offset number like =1
	figures[2][0]=(short)(Figure.BISHOP+Figure.WHITE_OFFSET);
	figures[3][0]=(short)(Figure.KING+Figure.WHITE_OFFSET);
	figures[4][0]=(short)(Figure.QUEEN+Figure.WHITE_OFFSET);
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
    figures[3][7]=(short)(Figure.KING+Figure.BLACK_OFFSET);
    figures[4][7]=(short)(Figure.QUEEN+Figure.BLACK_OFFSET);
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
	whiteCanCastleQueenSide=true; //Private
	whiteCanCastleKingSide = true; //Private
	blackCanCastleQueenSide = true;
	blackCanCastleKingSide = true;
	history = new ArrayList<Move>();
	//TODO:more parameters to set
  }
  
  //public Board CloneIncompletely()
  //public List<Move> getValidMoves()
  //public List<Move> getValidMoves(int color)
  //public List<Move> getHistory()
  
  public boolean execute_move(Move move) //dont validate!
  {
    short fig = figures[move.getSourceCol()][move.getDestRow()];
    figures[move.getDestCol()][move.getDestRow()] = fig;
    figures[move.getSourceCol()][move.getSourceRow()] = 0;
    //TODO
    //castleing
    //promoting
    //add to history   History.add(m);
    //En Passant
    //and more... like setting canCastle to false
    return true;
  }
  
  //public Boolean WhiteCanCastle()
  //public Boolean BlackCanCastle()
  //public boolean isMat(int color)
  
  public String toString() //on the console
  {
    String out = "  -------------------------------";
    out += '\n';
    for(int i=7; i>=0; i--)
    {
      out += (int)RowName(i) + "|";
      for(int j=7; j>=0; j--)
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
  
  //public static int FlipColor(int color)
  //public static String ColorToString(int color)
  
  public static String CoordsToString(int col, int row)
  {
    return "" + ColumnName(col) + RowName(row);
  }
  
  public static int[] StringToCoords(String s)
  {
    int[] out = {ColumnIndex(s.charAt(0)),RowIndex(s.charAt(1))};
    return out;
  }
  
  public static char ColumnName(int index)
  {
    return (char)('a'+index);
  }
  
  public static int ColumnIndex(char name)
  {
    return name-'a';
  }
  
  public static char RowName(int index)
  {
    return (char)(index+1);
  }
  
  public static int RowIndex(char name)
  {
    return name-1;
  }
}