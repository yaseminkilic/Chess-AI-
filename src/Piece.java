
import java.awt.*;
import java.awt.geom.*;

public abstract class Piece {
	
	private int x;
	private int y;
	private boolean isBlack;
	private boolean isSelected;
	private Point squarePieces_On;
	
	public Piece(int x, int y, boolean color){
		this.squarePieces_On = new Point(x,y);
		this.x = x*62;
		this.y = y*62;
		this.isSelected = false;
		this.isBlack = color;
	}

	public int getX(){ return x; }
	public int getY(){ return y; }
	public Point getLocation(){ return new Point(x,y); }
	public boolean isSelected(){ return this.isSelected; }
	public Point getSquareOn(){ return this.squarePieces_On; }
	public void setSelected(boolean selected){ this.isSelected =  selected; }

	public boolean contains(Point2D p){
		return x<= p.getX() && (x + 62) >= p.getX() && y<= p.getY() && (y+62) >= p.getY();
	}
	
	public void translate(int dx, int dy){
		x += dx;
		y += dy;
	}
	
	public void setLocation(int row, int col){
		x = row*62;
		y  = col*62;
		squarePieces_On.setLocation(row, col);
	}
	
	public boolean getColor(){ return this.isBlack; }
	public abstract String getType();
	public abstract void draw(Graphics g);
	public abstract boolean checkMoveIsLegal(Point p, Board b);
}
