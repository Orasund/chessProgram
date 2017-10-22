package chessProgram;

//TODO:not yet ready
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
   *
   * returns the String-representation of a Figure
   ***************************** */
  static String toString(short type)
  {
    if(type == EMPTY)
      return " ";
    
    //the figures are actually already chars
    return Character.toString((char)type);
  }
}
