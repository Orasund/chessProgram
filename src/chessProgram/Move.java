package chessProgram;

//TODO:not yet ready
public class Move
{
  private Color color;
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
  public Move(String s)
  {
    //spliting
    //we need to split the string into the "Source" and the "Destination" part.
    //while doing so we should optain if this is a Hit or not
    
    //isHit?
    String[] parts = s.split("-");
    if(parts.length==0)
      isHit = true;
    else
    {
      isHit = false;
      parts = s.split("x");
    }
    
    //castle?
    if(parts[0].length()==1)
    {
      //this is actually a castle
      type = Figure.KING;
      sourceCol = 4;
      //TODO:Color Black?
      //TODO:Finish this part!!!
      return;
    }
    
    //type
    if(parts[0].length()==3)
    {
      type = (short)(parts[0].charAt(0));
      parts[0] = parts[0].substring(1,2);
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
      parts[0] = parts[0].substring(0,1);
    }
    else
      newType = 0;
    
    //dest
    coords = Board.StringToCoords(parts[1]);
    destCol = coords[0];
    destRow = coords[1];
  }
  
  /*****************************
   * Copy-Constructor
   ***************************** */
  public Move(Color color_, short type_, short sourceCol_, short sourceRow_, short destCol_, short destRow_, boolean isHit_, short newType_)
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
   *
   * returns the String-representation
   ***************************** */
  public String toString()
  {
    if(type == Figure.KING && sourceCol == 4 && destCol == 6)
      return "0-0"; //small castle
    if(type == Figure.KING && sourceCol == 4 && destCol == 2)
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
   * Getters
   ***************************** */
  public Color getColor()
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
