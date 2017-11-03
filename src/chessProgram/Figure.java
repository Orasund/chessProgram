package chessProgram;

import java.util.ArrayList;

public class Figure
{
  static short EMPTY = 0;
  static short PAWN = 'P';
  static short ROOK = 'R';
  static short KNIGHT = 'N';
  static short BISHOP = 'B';
  static short QUEEN = 'Q';
  static short KING = 'K';
  static short WHITE_OFFSET = 0; //because we store as char, no offset is needed
  static short BLACK_OFFSET = 32; //ASCII offset of big letters to small letters
  
  /*****************************
   * String s = Figure.toString(type);
   * returns the String-representation of a Figure
   ***************************** */
  public static String toString(short type)
  {
    if(type == EMPTY)
      return " ";
    
    //the figures are actually already chars
    return Character.toString((char)type);
  }
  
  /*****************************
   * String s = Figure.toString(BLACK,type);
   * returns the String-representation of a Figure
   ***************************** */
  public static String toString(Color color, short type)
  {
    if(color == Color.BLACK)
      return toString((short)(type+BLACK_OFFSET));
    return toString(type);
  }
  
  /*****************************
   * short bishop = Figure.fromString('B');
   * returns the figure from a String
   ***************************** */
  //TODO:Pls delete in exercise3, this is just useless
  public static short fromString(char str)
  {
    return (short)str;
  }
  
  public static ArrayList<Move> getValidMoves(Board board, int col, int row)
  {
    ArrayList<Move> moves = new ArrayList<Move>();
    short[][] figures = board.getFigures();
    short figureIndex = figures[col][row];
    short figureType = typeOf(figureIndex);
    if (figureIndex != 0)
    {
      // TODO
      if (figureType == PAWN)
      {
      // TODO
      }
      if (figureType == ROOK || figureType == QUEEN)
      {
      // TODO
      }
      if (figureType == KNIGHT)
      {
      // TODO
      }
      if(figureType == BISHOP || figureType == QUEEN)
      {
      // TODO
      }
      if (figureType == KING)
      {
      // TODO
      }
    }
    return moves;
  }

  private static boolean isValidDestination(Board board, int color, int col, int row)
  {
  // TODO
    return true;
  }
  
  /*****************************
   * isFree(b,0,0)
   * returns if the square is free.
   ***************************** */
  //TODO: Pls move to Board in exercise3, if still not needed!
  private static boolean isFree(Board board, int col, int row)
  {
    return board.getFigure(col, row)==0;
  }
  
  /*****************************
   * Color black = colorOf(Figure.PAWN+Figure.BLACK_OFFSET);
   * returns the color of a Figure
   ***************************** */
  public static Color colorOf(short figure)
  {
    if(figure >'A'+BLACK_OFFSET)
      return Color.BLACK;
    return Color.WHITE;
  }
  
  /*****************************
   * short pawn = typeOf(Figure.PAWN+Figure.BLACK_OFFSET);
   * returns the type of a Figure
   ***************************** */
  public static short typeOf(short figure)
  {
    if(colorOf(figure)==Color.BLACK)
      return (short)(figure - BLACK_OFFSET);
    return figure;
  }
}
