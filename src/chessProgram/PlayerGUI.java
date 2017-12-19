package chessProgram;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.LinkedList;

public class PlayerGUI extends JFrame
{
  private static final long serialVersionUID = 1L;
  private Board board;
  BlackWhite myColor;
  Move move = null;
	
  final int CELLSIZE = 72;
  final int RIGHTOFFSET = 8;
  final int LEFTOFFSET = 8;
  final int TOPOFFSET = 31;
  final int BOTTOMOFFSET = 8;
  	
  //Constructor
  public PlayerGUI(Board board, BlackWhite color)
  {
    this.board = board;
  	myColor = color;
  	this.getContentPane().setLayout(null);
  	this.initWindow();
  }
	
  protected void initWindow()
  {
	this.setTitle("Schach");
	this.addWindowListener(new WindowListener()
	{

      @Override
      public void windowActivated(WindowEvent e) {}
      @Override
      public void windowClosed(WindowEvent e)
      {
      }
      @Override
      public void windowClosing(WindowEvent e) {
        CloseWindow();
      }
      @Override
      public void windowDeactivated(WindowEvent e) {}
      @Override
      public void windowDeiconified(WindowEvent e) {}
      @Override
      public void windowIconified(WindowEvent e) {}
      @Override
      public void windowOpened(WindowEvent e) {}
    });
	this.addMouseListener(new MouseListener()
	{
	  @Override
	  public void mouseClicked(MouseEvent arg0) {}
	  @Override
	  public void mouseEntered(MouseEvent e) {}
	  @Override
	  public void mouseExited(MouseEvent e) {}
	  @Override
	  public void mousePressed(MouseEvent e) {}
	  @Override
	  public void mouseReleased(MouseEvent e)
	  {
		reactOnClick(e.getX(), e.getY());
	  }
	});
  }
	
  public void paint(Graphics g)
  {
	setSize(CELLSIZE*8+RIGHTOFFSET+LEFTOFFSET,CELLSIZE*8+TOPOFFSET+BOTTOMOFFSET);
	int w = getWidth(),  h = getHeight();
	g.setColor(Color.BLACK);
	g.fillRect(1, 1, w, h);
	g.setColor(Color.WHITE);
	for(int i = 0; i < 8; i += 2)
	{
	  for(int j = 0; j < 8; j += 2)
	  {
		g.fillRect(LEFTOFFSET + j * CELLSIZE, TOPOFFSET + i * CELLSIZE, CELLSIZE, CELLSIZE);
		g.fillRect(LEFTOFFSET + (j + 1) * CELLSIZE, TOPOFFSET + (i + 1) * CELLSIZE, CELLSIZE, CELLSIZE);
	  }
	}
	URL u = getClass().getProtectionDomain().getCodeSource().getLocation();
	String directoryname = u.getPath() + "img/";
	directoryname = directoryname.substring(1);
	directoryname = directoryname.replace("%20", " ");
	for(int row = 0; row < 8; row++)
	  for(int col = 0; col < 8; col++)
      {
        short figureIndex = board.getFigures()[col][row];
        if(figureIndex == 0)
          continue;
        
        int figureType = Figure.typeOf(figureIndex);
        BlackWhite figureColor = Figure.colorOf(figureIndex);
        BlackWhite cellColor;
        if((row+col+1)%2==0)
          cellColor = BlackWhite.WHITE;
        else
          cellColor = BlackWhite.BLACK;
        String filename = directoryname;
        switch(figureType)
        {
          case Figure.PAWN:
            filename += "pawn";
            break;
          case Figure.KNIGHT:
            filename += "knight";
            break;
          case Figure.BISHOP:
            filename += "bishop";
            break;
          case Figure.KING:
            filename += "king";
            break;
          case Figure.QUEEN:
            filename += "queen";
            break;
          case Figure.ROOK:
            filename += "rook";
            break;
        }
        filename += figureColor==BlackWhite.WHITE?"_white":"_black";
        filename += cellColor==BlackWhite.WHITE?"_white":"_black";
        filename += ".gif";
        Image pic = getToolkit().getImage(filename);
        g.drawImage(pic, LEFTOFFSET + col * CELLSIZE, TOPOFFSET + (7 - row) * CELLSIZE, this);
      }
  }
	
  boolean notYetClicked = true;
  short sourceCol;
  short sourceRow;
	
  public void reactOnClick(int x, int y)
  {
	short col = (short)((x - LEFTOFFSET)/CELLSIZE);
	short row = (short)(7 - (y - TOPOFFSET)/CELLSIZE);
	if(notYetClicked)
	{
	  //firstClick
	  short figureIndex = board.getFigures()[col][row];
	  if(figureIndex == 0)
	    return;
	  
	  BlackWhite figureColor = Figure.colorOf(figureIndex);
	  if(figureColor != myColor)
	    return;
	  
	  sourceCol = col;
      sourceRow = row;
      notYetClicked = false;
      System.out.println("clicked:"+col+","+row);
	}
	else
	{
	  //secondClick
	  short figureIndex = board.getFigures()[col][row];
	  
	  LinkedList<Move> validMoves = Figure.getValidMoves(board, sourceCol, sourceRow);
	  short newType;
	  if((Figure.typeOf(board.getFigures()[sourceCol][sourceRow]) == Figure.PAWN) && (row == 0 || row == 7))
	    newType = Figure.QUEEN;
	  else
	    newType = (short)0;
	  Move temp_move = new Move(myColor, Figure.typeOf(board.getFigures()[sourceCol][sourceRow]), sourceCol, sourceRow, col, row, !(figureIndex==0), newType);
	  if(Move.MovesListIncludesMove(validMoves, temp_move))
	    move = temp_move;
	  else
	    notYetClicked = true;
	}
  }
	
  public void CloseWindow()
  {
    System.exit(-1);
  }
}
