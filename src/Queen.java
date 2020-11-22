
import java.awt.*;

import javax.imageio.*;

import java.awt.image.BufferedImage;
import java.io.*;

public class Queen extends Piece {
	
	private BufferedImage img = null;
	public Queen(int x, int y, boolean color){
		super(x,y, color);
		boolean isBlack = super.getColor();
		try {
		    img = ImageIO.read(new File((isBlack) ? "blackqueen.png" : "whitequeen.png"));
		} catch (IOException e) {
			System.out.println("file not found");
		}
	}
	
	public void draw(Graphics g){ g.drawImage(img, super.getX(), super.getY(), null); }
	public String getType(){ return "Queen"; }
	
	public boolean checkMoveIsLegal(Point p, Board b){
		int oldX = (int)super.getSquareOn().getX(), newX = (int)p.getX()/62;
		int oldY = (int)super.getSquareOn().getY(), newY = (int)p.getY()/62;
		if(b.hasPiece(newX, newY))
			if(b.getSquare(newX, newY).getColor() == super.getColor())
				return false;

		if((newX >= 0 && newX <= 7) && (newY >= 0 && newY <= 7)){
			if(oldX == newX){
				if(oldY == newY) return true;
				for(int i = 1; i< Math.abs(newY-oldY) ; i++){
					if(b.hasPiece(oldX, Math.min(oldY, newY) + i))
						return false;
				}
				return true;
			}
			
			if((oldY == newY)) {
				if(oldX == newX) return true;
				for(int i = 1; i< Math.abs(newX-oldX) ; i++){
					if(b.hasPiece((Math.min(oldX, newX) + i), oldY))
						return false;
				}
				return true;
			}
			
			if(Math.abs(newX - oldX) == Math.abs(newY - oldY)){
				boolean x_newIsBigger = (newX > oldX), y_newIsBigger = (newY > oldY);
				if((x_newIsBigger && y_newIsBigger) || (!x_newIsBigger && !y_newIsBigger)){
					for(int i= 1; i< Math.abs(newX-oldX); i++){
						if(b.hasPiece(Math.min(oldX, newX) + i,Math.min(oldY, newY) + i)){
							return false;
						}
					}
					return true;
				} 
				else if(x_newIsBigger && !y_newIsBigger) {
					for(int i= 1; i< Math.abs(newX-oldX); i++){
						if(b.hasPiece(oldX + i, oldY - i)){
							return false;
						}
					}
					return true;
				}
				else if(!x_newIsBigger && y_newIsBigger) {
					for(int i= 1; i< Math.abs(newX-oldX); i++){
						if(b.hasPiece(oldX -i, oldY + i)){
							return false;
						}
					}
					return true;
				}
				return true;
			}
		}
		return false;
	}
}
