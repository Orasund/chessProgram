package chessProgram;

import java.util.LinkedList;

public class Move
{
  private BlackWhite color;
  private short type;
  private int sourceCol;
  private int sourceRow;
  private int destCol;
  private int destRow;
  private boolean isHit;
  private short newType; //maybe sometimes useless, give it a default value for now
  
  /*****************************
   * Constructor
   ***************************** */
  public Move(String s,Board b)
  {
    //spliting
    //we need to split the string into the "Source" and the "Destination" part.
    //while doing so we should optain if this is a Hit or not
    
    if(s == "null")
    {
      type = 0;
      color = BlackWhite.WHITE;
      sourceCol = 0;
      sourceRow = 0;
      destCol = 0;
      destRow = 0;
      isHit = false;
      newType = 0;
      return;
    }
    
    //isHit?
    String[] parts = s.split("-");
    if(parts.length==1)
    {
      isHit = true;
      parts = s.split("x");
    }
    else
      isHit = false;
    
    //castle?
    if(parts[0].length()==1)
    {
      //this is actually a castle
      type = Figure.KING;
      sourceCol = 4;
      color = b.currentColor();
      if(color == BlackWhite.WHITE)
      {
        sourceRow = 0;
        destRow = 0;
      }
      else
      {
        sourceRow = 7;
        destRow = 7;
      }
      
      //big or small castle?
      if(parts.length == 2)
      {
        //small
        destCol = 6;
      }
      else
      {
        //big
        destCol = 2;
      }
      
      newType=0;
      isHit=false;
      return;
    }
    
    //type
    if(parts[0].length()==3)
    {
      type = (short)(parts[0].charAt(0));
      parts[0] = parts[0].substring(1,3);
    }
    else
      type = Figure.PAWN;
    
    //source
    int[] coords = Board.StringToCoords(parts[0]);
    sourceCol = coords[0];
    sourceRow = coords[1];
    
    //newType
    if(parts[1].length()==3)
    {
      newType = (short)(parts[1].charAt(2));
      parts[0] = parts[0].substring(0,2);
    }
    else
      newType = 0; //default
    
    //dest
    coords = Board.StringToCoords(parts[1]);
    destCol = coords[0];
    destRow = coords[1];
    
    //color
    color = Figure.colorOf(b.getFigure(sourceCol, sourceRow));
  }
  
  /*****************************
   * Copy-Constructor
   ***************************** */
  public Move(BlackWhite color_, short type_, short sourceCol_, short sourceRow_, short destCol_, short destRow_, boolean isHit_, short newType_)
  {
    color = color_;
    type = type_;
    sourceCol = sourceCol_;
    sourceRow = sourceRow_;
    destCol = destCol_;
    destRow = destRow_;
    isHit = isHit_;
    newType = newType_;
  }

  /*****************************
   * String s = figure.toString();
   * returns the String-representation
   ***************************** */
  public String toString()
  {
    int c = isCastle();
    if(c==1)
      return "0-0"; //small castle
    if(c==2)
      return "0-0-0"; //big castle
      
    
    String s;
    //pawn must have an intern representation for the display of our board,
    //therefore we may NOT do this if-condition in Figure.toString
    if(type == Figure.PAWN)
      s = "";
    else
      s = Figure.toString(type);
    
    s += Board.CoordsToString(sourceCol, sourceRow);
    s += isHit?"x":"-";
    s += Board.CoordsToString(destCol, destRow);
    if(newType >= 0)
    {
      s += Figure.toString(newType);
    }
    return s;
  }
  
  /*****************************
   * m.equals(m2);
   * returns the String-representation
   ***************************** */
  public boolean equals(Move move)
  {
    return 
      color == move.color && type == move.type && sourceCol == move.sourceCol
      && sourceRow == move.sourceRow && destCol == move.destCol &&  destRow == move.destRow
      && isHit == move.isHit && newType == move.newType;
  }
  
  /*****************************
   * MovesListIncludesMove(moves,move)
   * returns if moves contains move
   ***************************** */
  public static Boolean MovesListIncludesMove(LinkedList<Move> moves, Move move)
  {
    for(Move move_i:moves)
      if(move_i.equals(move))
        return true;
    return false;
  }
  
  public int isCastle()
  {
    if(type != Figure.KING || sourceCol != 4)
      return 0;
    
    if(destCol == 6)
    {
      return 1;
    }
    else if(destCol == 2)
    {
      return 2;
    }
    else
      return 0;
  }
  
  /*****************************
   * Setters
   *****************************/
  public void setColor(BlackWhite color_)
  {
    color = color_;
  }
  
  public void setHit(boolean hit)
  {
    isHit = hit;
  }
  
  /*****************************
   * Getters
   ***************************** */
  public BlackWhite getColor()
  {
    return color;
  }
  
  public short getType()
  {
    return type;
  }
  
  public int getSourceCol()
  {
    return sourceCol;
  }
  
  public int getSourceRow()
  {
    return sourceRow;
  }
  
  public int getDestCol()
  {
    return destCol;
  }
  
  public int getDestRow()
  {
    return destRow;
  }
  
  public boolean getIsHit()
  {
    return isHit;
  }
  
  public short getNewType()
  {
    return newType;
  }
}
