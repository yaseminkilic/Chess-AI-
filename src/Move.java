
public class Move {
	
	private int oldX, oldY, newX, newY;
	private Piece copyOfPiece;
	
	public Move(int oldX, int oldY, int newX, int newY, Piece piece){
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
		
		boolean state = piece.getColor();
		String color = piece.getType();
		
		if(color.equalsIgnoreCase("King")){
			copyOfPiece = new King((piece.getX()/62), (piece.getY()/62), state);
		} 
		else if(color.equalsIgnoreCase("Queen")){
			copyOfPiece = new Queen((piece.getX()/62), (piece.getY()/62), state);
		}
		else if(color.equalsIgnoreCase("Rook")){
			copyOfPiece = new Rook((piece.getX()/62), (piece.getY()/62), state);
		}
		else if(color.equalsIgnoreCase("Bishop")){
			copyOfPiece = new Bishop((piece.getX()/62), (piece.getY()/62), state);
		}
		else if(color.equalsIgnoreCase("Knight")){
			copyOfPiece = new Knight((piece.getX()/62), (piece.getY()/62), state);
		}
		else if(color.equalsIgnoreCase("Pawn")){
			copyOfPiece = new Pawn((piece.getX()/62), (piece.getY()/62), state);
		}
	}
	
	public int getOldX(){ return oldX; }
	public int getOldY(){ return oldY; }
	public int getNewX(){ return newX; }
	public int getNewY(){ return newY; }
	public Piece getPiece(){ return copyOfPiece; }
	public String toString(){
		return (copyOfPiece.getType() + " moved from (" + oldX + "," + oldY + ") to (" + newX + "," + newY + ")");
	}
}
