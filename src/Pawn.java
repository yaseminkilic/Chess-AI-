
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.*;

import javax.imageio.*;

import java.awt.image.BufferedImage;
import java.io.*;

public class Pawn extends Piece {
	
	private BufferedImage img = null;
	public Pawn(int x, int y, boolean color){
		super(x,y, color);
		boolean isBlack = super.getColor();
		try {
		    img = ImageIO.read(new File((isBlack) ? "blackpawn.png" : "whitepawn.png"));
		} catch (IOException e) {
			System.out.println("file not found");
		}
	}

	public void draw(Graphics g) { g.drawImage(img, super.getX(), super.getY(), null);}
	public String getType() { return "Pawn"; }
	
	public boolean checkMoveIsLegal(Point p, Board b) {
		int oldX = (int)super.getSquareOn().getX(), newX = (int)p.getX()/62;
		int oldY = (int)super.getSquareOn().getY(), newY = (int)p.getY()/62;
		boolean isBlack = super.getColor();
		
		if(b.hasPiece(newX, newY))
			if(b.getSquare(newX, newY).getColor() == isBlack)
				return false;
		
		if(!isBlack){
			if(oldY == 6) {
				if(Math.abs(newX - oldX) == 1 && newY == oldY -1 && b.hasPiece(newX, newY)){
					return true;
				}else if(newX == oldX && newY == oldY -1 && !b.hasPiece(newX, newY)){
					return true;
				}else if(newX == oldX && newY == oldY - 2 && !b.hasPiece(newX, newY)){
					return true;
				}
				return false;
			}else {
				if(Math.abs(newX - oldX) == 1 && newY == oldY -1 && b.hasPiece(newX, newY)){ 
					return true;
				}else if(newX == oldX && newY == oldY -1 && !b.hasPiece(newX, newY)){
					return true;
				}
				return false;
			}
		}else {
			if(oldY == 1){
				if(Math.abs(newX - oldX) == 1 && newY == oldY +1 && b.hasPiece(newX, newY)){
					System.out.println("Legal move: " + getType()  + " from: (" + oldX + "," + oldY + ") to (" + newX + "," + newY + ")");

					return true;
				}else if(newX == oldX && newY == oldY +1 && !b.hasPiece(newX, newY)){
					System.out.println("Legal move: " + getType()  + " from: (" + oldX + "," + oldY + ") to (" + newX + "," + newY + ")");

					return true;
				}else if(newX == oldX && newY == oldY + 2 && !b.hasPiece(newX, newY)){
					System.out.println("Legal move: " + getType()  + " from: (" + oldX + "," + oldY + ") to (" + newX + "," + newY + ")");

					return true;
				}
				return false;
			}else{
				if(Math.abs(newX - oldX) == 1 && newY == oldY +1 && b.hasPiece(newX, newY)){
					System.out.println("Legal move: " + getType()  + " from: (" + oldX + "," + oldY + ") to (" + newX + "," + newY + ")");

					return true;
				}else if(newX == oldX && newY == oldY +1 && !b.hasPiece(newX, newY)){
					System.out.println("Legal move: " + getType()  + " from: (" + oldX + "," + oldY + ") to (" + newX + "," + newY + ")");

					return true;
				}
				return false;
			}
		}
	}
	
	private boolean isWhite_NotEqual_6(Board b, int newX, int oldX, int newY, int oldY){
		if(Math.abs(newX - oldX) == 1 && newY == oldY -1 && b.hasPiece(newX, newY))
			return true;
		else if(newX == oldX && newY == oldY -1 && !b.hasPiece(newX, newY))
			return true;
		
		return false;
	}
	
	private boolean isBlack_NotEqual_1(Board b, int newX, int oldX, int newY, int oldY){
		if(Math.abs(newX - oldX) == 1 && newY == oldY +1 && b.hasPiece(newX, newY)){
			System.out.println("Legal move: " + getType()  + " from: (" + oldX + "," + oldY + ") to (" + newX + "," + newY + ")");
			return true;
		}else if(newX == oldX && newY == oldY +1 && !b.hasPiece(newX, newY)){
			System.out.println("Legal move: " + getType()  + " from: (" + oldX + "," + oldY + ") to (" + newX + "," + newY + ")");
			return true;
		}
		return false;
	}
}
