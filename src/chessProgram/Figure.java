package chessProgram;

import java.util.ArrayList;

public class Figure
{
  final static short EMPTY = 0;
  final static short PAWN = 'P';
  final static short ROOK = 'R';
  final static short KNIGHT = 'N';
  final static short BISHOP = 'B';
  final static short QUEEN = 'Q';
  final static short KING = 'K';
  final static short WHITE_OFFSET = 0; //because we store as char, no offset is needed
  final static short BLACK_OFFSET = 32; //ASCII offset of big letters to small letters
  
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
  
  /*****************************
   * ArrayList<Move> moves = getValidMoves(b,col,row)
   * returns a list of all valid moves
   ***************************** */
  public static ArrayList<Move> getValidMoves(Board board, short col, short row)
  {
    ArrayList<Move> moves = new ArrayList<Move>();
    short figureIndex = board.getFigure(col,row);
    short figure_type = typeOf(figureIndex);
    Color figure_color = Figure.colorOf(figureIndex);
    short temp_col;
    short temp_row;
    if (figureIndex != 0)
    {
      if (figure_type == PAWN)
      {
        if(figure_color == Color.WHITE)
        {
          if(figure_color == Color.WHITE)
            temp_row = (short)(row+1);
          else
            temp_row = (short)(row-1);
          
          if(
            ((figure_color == Color.WHITE && temp_row <= 7)
              || (figure_color == Color.BLACK && temp_row >= 0)
            )
            && board.getFigure(col,temp_row) == 0
          )
          {
            // X
            // o
            if(
              (figure_color == Color.WHITE && temp_row ==7)
              || (figure_color == Color.BLACK && temp_row ==0)
            )
            {
              //add moves with promotion! queen, knight, bishop, rook
              moves.add(new Move(figure_color,Figure.PAWN,col,row,col,temp_row,false,Figure.BISHOP));
              moves.add(new Move(figure_color,Figure.PAWN,col,row,col,temp_row,false,Figure.KNIGHT));
              moves.add(new Move(figure_color,Figure.PAWN,col,row,col,temp_row,false,Figure.QUEEN));
              moves.add(new Move(figure_color,Figure.PAWN,col,row,col,temp_row,false,Figure.ROOK));
            }
            else
              moves.add(new Move(figure_color,Figure.PAWN,col,row,col,temp_row,false,(short)0));
            
            // X
            // |
            // o
            if(figure_color == Color.WHITE)
              temp_row = (short)(row+2);
            else
              temp_row = (short)(row-2);
            if(
              (
                (figure_color == Color.WHITE && row==1)
                || (figure_color == Color.BLACK && row==6)
              )
              && board.getFigure(col, temp_row)==0
            )
               moves.add(new Move(figure_color,Figure.PAWN,col,row,col,temp_row,false,(short)0));
          }  
          // X
          //   o
          int[][] pos;
          if(figure_color == Color.WHITE)
            pos = new int[][]{{-1,1},{1,1}};
          else
            pos = new int[][]{{-1,-1},{1,-1}};
            
          for(int i = 0; i<2; i++)
          {
            temp_col = (short)(col+pos[i][0]);
            temp_row = (short)(row+pos[i][1]);
            if(temp_col<0 || temp_col>=8 || temp_row <0 || temp_row >=8)
              continue;
            
            if(board.getFigure(temp_col, temp_row) != 0)
            {
              if(isValidDestination(board,figure_color,temp_col,temp_row))
              {
                if(
                  (figure_color == Color.WHITE && temp_row ==7)
                  || (figure_color == Color.BLACK && temp_row ==0)
                )
                {
                  //add moves with promotion! queen, knight, bishop, rook
                  moves.add(new Move(figure_color,Figure.PAWN,col,row,temp_col,temp_row,true,Figure.BISHOP));
                  moves.add(new Move(figure_color,Figure.PAWN,col,row,temp_col,temp_row,true,Figure.KNIGHT));
                  moves.add(new Move(figure_color,Figure.PAWN,col,row,temp_col,temp_row,true,Figure.QUEEN));
                  moves.add(new Move(figure_color,Figure.PAWN,col,row,temp_col,temp_row,true,Figure.ROOK));
                }
                else
                  moves.add(new Move(figure_color,Figure.PAWN,col,row,temp_col,temp_row,true,(short)0));
              }
                
            }
            else
            {
              //
              //   *
              //   X
              // o .
              if(figure_color == Color.WHITE)
                temp_row = (short)(row+2);
              else
                temp_row = (short)(row-2);
              ArrayList<Move> history = board.getHistory();
              if(history.size()>0)
              {
                Move lastMove = history.get(history.size()-1);
                if(
                  lastMove.getType() == Figure.PAWN
                  && lastMove.getSourceCol() == temp_col
                  && lastMove.getSourceRow() == temp_row
                  && lastMove.getDestCol() == temp_col
                  && lastMove.getDestRow() == row
                )
                  moves.add(new Move(figure_color,Figure.PAWN,col,row,temp_col,temp_row,true,(short)0));
              }
            }
          }   
        }
      }
      if (figure_type == ROOK || figure_type == QUEEN)
      {
        //     X
        //     X
        // X X R X X
        //     X
        //     X
        int[][] pos = {
            {0,1},{0,-1},{1,0},{-1,0}
        };
        for(int i = 0; i<pos.length; i++)
          for(int j = 1; true; j++)
          {
            temp_col = (short)(col+j*pos[i][0]);
            temp_row = (short)(row+j*pos[i][1]);
            if(temp_col<0 || temp_col>=8 || temp_row <0 || temp_row >=8)
              break;
            
            if(isValidDestination(board,figure_color,temp_col,temp_row))
            {
              boolean isHit = (board.getFigure(temp_col,temp_row) != 0);
              moves.add(new Move(figure_color,figure_type,col,row,temp_col,temp_row,isHit,(short)0));
              if(isHit)
                break;
            }
            else
              break;
          }
      }
      if (figure_type == KNIGHT)
      {
        //   X   X
        // X   |   X
        //   - N -
        // X   |   X
        //   X   X
        int[][] pos = {
          {-1,2},{-2,1},{-1,-2},{-2,-1},{1,2},{2,1},{1,-2},{2,-1}
        };
        for(int i = 0; i <pos.length; i++)
        {
          temp_col = (short)(col+pos[i][0]);
          temp_row = (short)(row+pos[i][1]);
          if(temp_col<0 || temp_col>=8 || temp_row <0 || temp_row >=8)
            continue;
          
          if(isValidDestination(board,figure_color,temp_col,temp_row))
          {
            boolean isHit = (board.getFigure(temp_col, temp_row) != 0);
            moves.add(new Move(figure_color,figure_type,col,row,temp_col,temp_row,isHit,(short)0));
          }
        }
      }
      if(figure_type == BISHOP || figure_type == QUEEN)
      {
        // X       X
        //   X   X
        //     B
        //   X   X
        // X       X
        int[][] pos = {
            {-1,1},{-1,-1},{1,1},{1,-1}
        };
        for(int i = 0; i<pos.length; i++)
          for(int j = 1; true; j++)
          {
            temp_col = (short)(col+j*pos[i][0]);
            temp_row = (short)(row+j*pos[i][1]);
            if(temp_col<0 || temp_col>=8 || temp_row <0 || temp_row >=8)
              break;
            
            if(!isValidDestination(board,figure_color,temp_col,temp_row))
              break;
            
            boolean isHit = (board.getFigure(temp_col,temp_row) != 0);
            moves.add(new Move(figure_color,figure_type,col,row,temp_col,temp_row,isHit,(short)0));
            if(isHit)
              break;
          }
      }
      if (figure_type == KING)
      {
        // 
        //  X X X
        //  X K X
        //  X X X
        //   
        
        /*** Normal moves ***/
        int[][] pos = {
            {-1,1},{-1,-1},{1,1},{1,-1},{0,1},{0,-1},{1,0},{-1,0}
        };
        for(int i = 0; i <pos.length; i++)
        {
          temp_col = (short)(col+pos[i][0]);
          temp_row = (short)(row+pos[i][1]);
          if(temp_col<0 || temp_col>=8 || temp_row <0 || temp_row >=8)
            continue;
          
          if(isValidDestination(board,figure_color,temp_col,temp_row))
          {
            boolean isHit = (board.getFigure(temp_col, temp_row) != 0);
            moves.add(new Move(figure_color,figure_type,col,row,temp_col,temp_row,isHit,(short)0));
          }
        }
        
        /*** Castle ***/
        if(!canBeHit(board, col,row, figure_color))
        {
          if(figure_color == Color.WHITE)
          {
            if(board.getWhiteCanCastleQueenSide() && board.getFigure(1, 0) == 0 && board.getFigure(2, 0) == 0 && board.getFigure(3, 0) == 0 && Figure.typeOf(board.getFigure(0, 0)) == Figure.ROOK)
              moves.add(new Move("0-0-0",board));
            else if(board.getWhiteCanCastleKingSide() && board.getFigure(5, 0) == 0 && board.getFigure(6, 0) == 0 && Figure.typeOf(board.getFigure(7, 0)) == Figure.ROOK)
              moves.add(new Move("0-0",board));
          }
          else
          {
            if(board.getBlackCanCastleQueenSide() && board.getFigure(1, 7) == 0 && board.getFigure(2, 7) == 0 && board.getFigure(3, 7) == 0 && Figure.typeOf(board.getFigure(0, 7)) == Figure.ROOK)
              moves.add(new Move("0-0-0",board));
            else if(board.getBlackCanCastleKingSide() && board.getFigure(5, 7) == 0 && board.getFigure(6, 7) == 0 && Figure.typeOf(board.getFigure(7, 7)) == Figure.ROOK)
              moves.add(new Move("0-0",board));
          }
        }
      }
    }
    return moves;
  }
  
  /*****************************
   * boolean b = canBeHit(b,col,row,c)
   * returns if an enemy can hit this spot
   ***************************** */
  private static boolean canBeHit(Board b, int col, int row, Color c)
  {
    for(int i = 0; i < 8; i++)
      for(int j = 0; j<8; j++)
      {
        short fig = b.getFigure(i, j);
        if(fig == 0)
          continue;
        
        Color color = colorOf(fig);
        if(color == c)
          continue;
        
        short type = typeOf(fig);
        if(type == KING)
        {
          if(Math.abs(i-col)<=1 && Math.abs(i-row)<=1)
            return true;
          continue;
        }
        
        ArrayList<Move> moves = getValidMoves(b, (short)i,(short)j);
        for(int k = 0; k<moves.size(); k++)
        {
          Move m = moves.get(k);
          if(m.getIsHit() ==true && m.getDestCol() == col && m.getDestRow() == row)
            return true;
        }
      }
    return false;
  }
  
  /*****************************
   * isValidDestination(b,color,0,0)
   * returns if the pot is free or occupied by an enemy
   ***************************** */
  private static boolean isValidDestination(Board board, Color color, int col, int row)
  {
    short fig = board.getFigure(col,row);
    return fig==0 || Figure.colorOf(fig)!=color;
  }
  
  /*****************************
   * Color black = colorOf(Figure.PAWN+Figure.BLACK_OFFSET);
   * returns the color of a Figure
   ***************************** */
  public static Color colorOf(short figure)
  {
    if(figure >='A'+BLACK_OFFSET)
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
  
  /*****************************
   * int seven = relativeIndex(0, Color.WHITE);
   * returns the type of a Figure
   ***************************** */
  public static int relativeIndex(int i, Color c)
  {
    if(c == Color.WHITE)
      return i;
    
    return 7-i;
  }
}
