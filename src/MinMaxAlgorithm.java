
import java.util.*;
import java.awt.*;
	
public class MinMaxAlgorithm implements AI {
	
	private static final int DEPTH = 1;
	private int numTurns;
	
	/* Generates all the possible AI moves (AI: Black) 
	 * The board that scores the highest becomes the next move
	 */
	public String makeMove(Board b) {
		ArrayList<Board> possibleBoards = new ArrayList<Board>();
		ArrayList<Move> moves  = new ArrayList<Move>();
		
		Move bestMove; //keeps track of the best possible move AI has available
		int bestMoveScore; //score of that best move
		
		for(int i = 0; i<8; i++){
			for(int j=0; j<8; j++){
				if(b.hasPiece(i,j)){
					Piece piece = b.getSquare(i,j);
					if(piece.getColor() == true){
						for(int k=0; k<8; k++){
							for(int l=0; l<8; l++){
								if(piece.checkMoveIsLegal(new Point(k*62,l*62),b)){ //k and l and multiplied by 62 because checkLegalMove takes the pixel positions as parameters
									Move move = new Move(i,j,k,l,piece);
									moves.add(move);
									Board newBoard = new Board(b); //calls the copy constructor of the board clas
									doMove(newBoard, move); //performs move on the new board
									possibleBoards.add(newBoard);
								}
							}
						}
					}
				}
			}
		}
		bestMove = moves.get(0);
		bestMoveScore = evaluatePosition(possibleBoards.get(0), Integer.MIN_VALUE, Integer.MAX_VALUE, DEPTH, false);
		
		//call evaluateposition on each move
		//keep track of the move with the best score
		if(numTurns>0){
			for(int i = 1; i<possibleBoards.size(); i++){
				System.out.println("Evaluating move: " + moves.get(i).toString());
				/*
				 * calls evaluatePosition on each possible board and if the score is higher than previous,
				 * reset the bestMove
				 */
				int j = evaluatePosition(possibleBoards.get(i), Integer.MIN_VALUE, Integer.MAX_VALUE, DEPTH, false);
				if(j >= bestMoveScore){
					bestMove = moves.get(i);
					bestMoveScore = j;
				}
	
			}
		}else{
			Random generator = new Random();
			int index = generator.nextInt(moves.size());
			bestMove = moves.get(index);
		}
		System.out.println(bestMove.toString());
		numTurns++;
		return doMove(b, bestMove); //doMove performs the move on the original board and returns a string of that move

		
	}
	
	public String doMove(Board b, Move moveToMake){
		final String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H"};
		Piece pieceToMove = moveToMake.getPiece();
		
		if(pieceToMove.getType().equals("King")){
			if(pieceToMove.getColor() == false){//case that it is white king
				if(b.hasPiece(7, 7)){
					if(b.getSquare(7,7).getType().equals("Rook")){
						if(moveToMake.getOldX() == 4 && moveToMake.getOldY() == 7 && moveToMake.getNewX() == 6 && moveToMake.getNewY() ==7){
							//if conditions for castling are correct, moves the correct pieces
							b.clearSquare(moveToMake.getOldX(), moveToMake.getOldY());
							b.setSquare(moveToMake.getNewX(), moveToMake.getNewY(), pieceToMove);
							pieceToMove.setLocation(moveToMake.getNewX(), moveToMake.getNewY());
							Piece rook = b.getSquare(7, 7);
							b.setSquare(5,7,rook);
							rook.setLocation(5, 7);
							b.clearSquare(7, 7);
							String text = "White castles. \n";
							return text;
						}
					}
				}
			}else if(pieceToMove.getColor() == true){ //case that it is black king
				if(b.hasPiece(7, 0)){
					if(b.getSquare(7, 0).getType().equals("Rook")){
						if(moveToMake.getOldX() == 4 && moveToMake.getOldY() == 0 && moveToMake.getNewX() == 6 && moveToMake.getNewY() ==0){
							//if conditions for castling are correct, moves the correct pieces
							b.clearSquare(moveToMake.getOldX(), moveToMake.getOldY());
							b.setSquare(moveToMake.getNewX(), moveToMake.getNewY(), pieceToMove);
							pieceToMove.setLocation(moveToMake.getNewX(), moveToMake.getNewY());
							Piece rook = b.getSquare(7, 0);
							b.setSquare(5, 0, rook);
							rook.setLocation(5, 0);
							b.clearSquare(7, 0);
							String text = "Black castles. \n";
							return text;
						}
					}
				}
			}
		}
		//clear square and reset the new square with the piece to be moved
		b.clearSquare(moveToMake.getOldX(), moveToMake.getOldY());
		b.setSquare(moveToMake.getNewX(), moveToMake.getNewY(), pieceToMove);
		pieceToMove.setLocation(moveToMake.getNewX(), moveToMake.getNewY());
		
		/*
		 * Figures out what text to send back
		 */
		String text = pieceToMove.getType() + " was moved to: " + columns[moveToMake.getNewX()] +  (moveToMake.getNewY()+1) + "\n";
		return text;
	}
	
	/**
	 * Generates a game tree that it searches to figure out how advantageous a board
	 * MinMax algorithm with alpha-beta pruning
	 * Recursively evaluates itself it decreases the depth by 1
	 * When the depth=0, returns the result of running the evaluate; depth!=0, generates all possible moves
	 */
	public int evaluatePosition(Board b, int alpha, int beta, int depth, boolean color){ 
		System.out.println("Begin evaluating position: depth-" + depth + "for- "+ color);
		/*
		 * Base case: when depth is decremented to 0, evaluatePosition simply returns the result
		 * of the evaluate function
		 */
		if(depth == 0){
			int evaluation = evaluate(b);
			System.out.println("Evaluated to: " + evaluation);
			return evaluation;
		}
		
		if(color == false){ //minimizing player--sequence of events that occurs
			ArrayList<Move> moves = new ArrayList<Move>(); //this arraylist keeps track of possible moves from the given position
			/*
			 * Iterate through the board, collect all possible moves of the minimizing player
			 */
			for(int i = 0; i<8; i++){
				for(int j=0; j<8; j++){
					if(b.hasPiece(i,j)){
						if(b.getSquare(i,j).getColor() == color){
							Piece piece = b.getSquare(i,j);
							for(int k =0; k<8; k++){
								for(int l=0; l<8; l++){
									Point p = new Point(k*62,l*62);
									if(piece.checkMoveIsLegal(p, b)){
										moves.add(new Move(i,j,k,l,piece)); //adds moves to the arraylist as they are calculated
									}
									
								}
							}
						}
					}
				}
			}
			/*
			 * This for loop goes through all possible moves and calls evaluatePosition on them,
			 * changing the color.  Alpha-beta pruning is used here to remove obviously poor moves.
			 * These are determined by the variables alpha and beta.  All moves where the beta,
			 * which is the score of the minimizing (in this case white player) is less than or
			 * equal to alpha are discarded.  
			 */
			int newBeta = beta;
			for(Move move : moves){ //for child in node
				System.out.println("Move to be evaluated: " + move.toString());
				Board successorBoard = new Board(b); 
				doMove(successorBoard, move);
				newBeta = Math.min(newBeta, evaluatePosition(successorBoard, alpha, beta, depth -1, !color)); //think about how to change moves
				if(newBeta<= alpha) break;
			}
			return newBeta; //returns the highest score of the possible moves
		}else{ //maximizing player--this is the course of action determined if this is the maximizing player, or black
			ArrayList<Move> moves = new ArrayList<Move>();
			/*
			 * These for loops iterate through the board and add all possible pieces to the ArrayList of
			 * moves.  
			 */
			for(int i = 0; i<8; i++){
				for(int j=0; j<8; j++){
					if(b.hasPiece(i,j)){
						if(b.getSquare(i,j).getColor() == true){
							Piece piece = b.getSquare(i,j);
							for(int k =0; k<8; k++){
								for(int l=0; l<8; l++){
									Point p = new Point(k*62,l*62);
									if(piece.checkMoveIsLegal(p, b)){
										moves.add(new Move(i,j,k,l,piece)); //Check this statement 
									}
									
								}
							}
						}
					}
				}
		}
		/*
		 * This for loop cycles through all possible moves and 
		 * calculates a new alpha if the successor board evaluates
		 * to a higher number than what is currently the highest score,
		 * which is stored in alpha.  
		 */
		int newAlpha = alpha;
		for(Move move : moves){ //for child in node
			System.out.println("Move to be evaluated: " + move.toString());
			Board successorBoard = new Board(b); 
			doMove(successorBoard, move);
			newAlpha = Math.max(newAlpha, evaluatePosition(successorBoard, alpha, beta, depth -1, !color)); //think about how to change moves
			if(beta<= newAlpha) break;
		}
		return newAlpha; //returns the highest score of the possible moves
		}
	}
	
	/**
	 * Returns a number based on how advantageous a board is for the maximizing
	 * Kings has the highest number, queens the second, etc...
	 * Total white score = subtracted from the total black score
	 */
	public int evaluate(Board b){
		int whitescore = 0;
		int blackscore = 0;

		for(int i = 0; i<8; i++){
			for(int j=0; j<8; j++){
				if(b.hasPiece(i, j)){
					if(b.getSquare(i, j).getColor() == false){ //case that piece is white
						if(b.getSquare(i,j).getType() == "Queen"){
							whitescore += 9;
						}else if(b.getSquare(i,j).getType() == "Rook"){
							whitescore += 5;
						}else if(b.getSquare(i,j).getType() == "Knight" || b.getSquare(i,j).getType() == "Bishop"){
							whitescore += 3;
						}else if(b.getSquare(i,j).getType() == "Pawn"){
							whitescore += 1;
						}else if(b.getSquare(i,j).getType() == "King"){
							whitescore += 10000000;
						}
					}else if(b.getSquare(i,j).getColor() == true){ //case that piece is black
						if(b.getSquare(i,j).getType() == "Queen"){
							blackscore += 9;
						}else if(b.getSquare(i,j).getType() == "Rook"){
							blackscore += 5;
						}else if(b.getSquare(i,j).getType() == "Knight" || b.getSquare(i,j).getType() == "Bishop"){
							blackscore += 3;
						}else if(b.getSquare(i,j).getType() == "Pawn"){
							blackscore += 1;
						}else if(b.getSquare(i,j).getType() == "King"){
							blackscore += 10000000;
						}
					}
				}
			}
		}
		return blackscore-whitescore; //returns blackscore-whitescore, black player tries to maximize, white player tries to minimize
	}

}
