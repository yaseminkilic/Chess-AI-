
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.*;

import javax.imageio.*;

import java.awt.image.BufferedImage;
import java.io.*;

public class Knight extends Piece {
	
	private BufferedImage img = null;
	public Knight(int x, int y, boolean color){
		super(x,y, color);
		boolean isBlack = super.getColor();
		try {
		    img = ImageIO.read(new File((isBlack) ? "darkknight.png" : "whiteknight.png"));
		} catch (IOException e) {
			System.out.println("file not found");
		}
	}
	
	public void draw(Graphics g) { g.drawImage(img, super.getX(), super.getY(), null);}
	public String getType() { return "Knight"; }

	public boolean checkMoveIsLegal(Point p, Board b) {
		int oldX = (int)super.getSquareOn().getX(), newX = (int)p.getX()/62;
		int oldY = (int)super.getSquareOn().getY(), newY = (int)p.getY()/62;
		if(b.hasPiece(newX, newY))
			if(b.getSquare(newX, newY).getColor() == super.getColor())
				return false;

		if(Math.abs(newX - oldX) == 2 && Math.abs(newY - oldY) == 1 && (newX >= 0 && newX <= 7) && (newY >= 0 && newY <= 7))
			return true;
		else if(Math.abs(newX -oldX) == 1 && Math.abs(newY - oldY) == 2 && (newX >= 0 && newX <= 7) && (newY >= 0 && newY <= 7))
			return true;
		else
			return false;
	}
}
