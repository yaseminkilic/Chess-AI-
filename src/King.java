
import java.awt.*;

import javax.imageio.*;

import java.awt.image.BufferedImage;
import java.io.*;

public class King extends Piece {
	
	private BufferedImage img = null;
	public King(int x, int y, boolean color){
		super(x,y, color);
		boolean isBlack = super.getColor();
		try {
		    img = ImageIO.read(new File((isBlack) ? "blackking.png" : "whiteking.png"));
		} catch (IOException e) {
			System.out.println("file not found");
		}
	}

	public void draw(Graphics g){ g.drawImage(img, super.getX(), super.getY(), null); }
	public String getType(){ return "King"; }

	public boolean checkMoveIsLegal(Point p, Board b){
		int oldX = (int)super.getSquareOn().getX(), newX = (int)p.getX()/62;
		int oldY = (int)super.getSquareOn().getY(), newY = (int)p.getY()/62;
		if(b.hasPiece(newX, newY))
			if(b.getSquare(newX, newY).getColor() == super.getColor())
				return false;

		if(Math.abs(newX - oldX) <= 1 && Math.abs(newY - oldY) <=1 && (newX >= 0 && newX <= 7) && (newY >= 0 && newY <= 7))
			return true;	

		boolean isBlack = super.getColor();
		int index = (isBlack) ? 0 : 7;
		if(b.hasPiece(7, index)){
			if(b.getSquare(7,index).getType().equals("Rook")){
				if(oldX == 4 && oldY == 7 && newX == 6 && newY == index){
					if(!b.hasPiece(5, index) && !b.hasPiece(6,index)){
						return true;
					}
				}
			}
		}
		return false;
	}
}
