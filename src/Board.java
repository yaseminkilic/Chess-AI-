
public class Board {
	
	private Piece[][] grid;
	
	public Board(){
		
		this.grid = new Piece[8][8];
		for(int i = 0; i<8; i++)
			for(int j = 0; j<8; j++)
				this.grid[i][j] = null;

		setSquare(0,0,new Rook(0,0,true));		// Black Rook
		setSquare(0,7,new Rook(0,7,false));		// White Rook

		setSquare(1,0,new Knight(1,0,true)); 	// Black Knight
		setSquare(1,7,new Knight(1,7,false));	// White Knight
		
		setSquare(2,0,new Bishop(2,0,true)); 	// Black Bishop
		setSquare(2,7,new Bishop(2,7,false));	// White Bishop
		
		setSquare(3,0,new Queen(3,0,true)); 	// Black Queen
		setSquare(3,7,new Queen(3,7,false));	// White Queen

		setSquare(4,0,new King(4,0,true)); 		// Black King
		setSquare(4,7,new King(4,7,false));		// White King

		setSquare(5,0,new Bishop(5,0,true)); 	// Black Bishop
		setSquare(5,7, new Bishop(5,7,false)); 	// White Bishop

		setSquare(6,0,new Knight(6,0,true)); 	// Black Knight
		setSquare(6,7, new Knight(6,7,false));	// White Knight

		setSquare(7,0, new Rook(7,0,true)); 	// Black Rook
		setSquare(7,7,new Rook(7,7,false));		// White Rook
		
		for(int i=0;i<8;i++) {
			setSquare(i,1,new Pawn(i,1,true)); // Black
			setSquare(i,6, new Pawn(i,6,false)); // White
		}
	}

	public Board(Board b){
		this.grid = new Piece[8][8];
		for(int i = 0; i<8; i++)
			for(int j = 0; j<8; j++)
				this.grid[i][j] = null;
		
		for(int i = 0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(b.hasPiece(i,j)) {
					
					boolean state = b.getSquare(i, j).getColor();
					String color = b.getSquare(i, j).getType();
					
					if(color.equalsIgnoreCase("King")){
						this.grid[i][j] = new King(i,j,state);
					} 
					else if(color.equalsIgnoreCase("Queen")){
						this.grid[i][j] = new Queen(i,j,state);
					}
					else if(color.equalsIgnoreCase("Rook")){
						this.grid[i][j] = new Rook(i,j,state);
					}
					else if(color.equalsIgnoreCase("Bishop")){
						this.grid[i][j] = new Bishop(i,j,state);
					}
					else if(color.equalsIgnoreCase("Knight")){
						this.grid[i][j] = new Knight(i,j,state);
					}
					else if(color.equalsIgnoreCase("Pawn")){
						this.grid[i][j] = new Pawn(i,j,state);
					}
				}
			}
		}
	}

	public Piece getSquare(int row, int col){ return this.grid[row][col]; }
	public void setSquare(int row, int col, Piece piece){ this.grid[row][col] = piece; }
	public void clearSquare(int row, int col){ this.grid[row][col] = null; }
	public boolean hasPiece(int row, int col){ return (getSquare(row,col) != null) ? true : false; }	
}
