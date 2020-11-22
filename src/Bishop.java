
import java.awt.*;
import javax.imageio.*;

import java.awt.image.BufferedImage;
import java.io.*;

public class Bishop extends Piece {
	
	private BufferedImage img = null;
	public Bishop(int x, int y, boolean color){
		super(x,y, color);
		boolean isBlack = super.getColor();
		try {
		    img = ImageIO.read(new File((isBlack) ? "blackbishop.png" : "whitebishop.png"));
		} catch (IOException e) {
			System.out.println("file not found");
		}
	}

	public void draw(Graphics g){ g.drawImage(img, super.getX(), super.getY(), null); }
	public String getType(){ return "Bishop"; }
	
	public boolean checkMoveIsLegal(Point p, Board b){
		int oldX = (int)super.getSquareOn().getX(), newX = (int)p.getX()/62;;
		int oldY = (int)super.getSquareOn().getY(), newY = (int)p.getY()/62;
		if(b.hasPiece(newX, newY))
			if(b.getSquare(newX, newY).getColor() == super.getColor())
				return false;

		/* checks that the piece is still on the board */
		if(Math.abs(newX - oldX) == Math.abs(newY - oldY) && (newX >= 0 && newX <= 7) && (newY >= 0 && newY <= 7)) {
			/* checks to the left and up */
			if(((newX - oldX) < 0) && ((newY - oldY) < 0)) {
				for(int i = 1; i < (oldX - newX); i++) {
					if(b.hasPiece(newX + i, newY + i))
						return false;
				}
				return true;
			}
			/* checks to the left and down */
			if(((newX - oldX) < 0) && ((newY - oldY) > 0)) {
				for(int i = 1; i < Math.abs(newX - oldX); i++) {
					if(b.hasPiece(oldX -i, oldY + i))
						return false;
				}
				return true;
			}
			/* checks to the right and up */
			if(((newX - oldX) > 0) && ((newY - oldY) < 0)) {
				for(int i = 1; i < newX - oldX; i++) {
					if(b.hasPiece(oldX + i, oldY - i))
						return false;
				}
				return true;
			}
			/* checks to the right and down */
			if(((newX - oldX) > 0) && ((newY - oldY) > 0)) { 
				for(int i = 1; i < newX - oldX; i++) {
					if(b.hasPiece(oldX + i,oldY + i))
						return false;
				}
				return true;
			}
			return true;
		}
		return false;
	}
}
