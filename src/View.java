
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View extends JComponent{

	private Game game;
	private String text;
	private Point mousePoint;
	private JTextArea textBox;
	private final Board board;
	private final String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H"};
	
	public View(Board board, JTextArea textBox, Game game){
		this.text = "";
		this.game = game;
		this.board = board;
		this.textBox  = textBox;

		if(game.getMode() == 1){
			addMouseListener(new
					MouseAdapter(){
						public void mousePressed(MouseEvent event){
							mousePoint = event.getPoint(); 
							if(!View.this.game.getIsWon()){
								for(int i = 0; i<8; i++){ 
									for(int j = 0; j<8; j++){
										if(View.this.board.hasPiece(i, j)){
											Piece p = View.this.board.getSquare(i,j);
											if(p.contains(event.getPoint())){
												if(View.this.game.getTurn() == false && p.getColor() == false){
													p.setSelected(true); //set selected to true
													View.this.text += p.getType() + " selected! \n";
													View.this.textBox.setText(View.this.text);
												}
											}
										}
									}
								}
							}
						}
			});
		}

		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent event){
				Point lastMousePoint = mousePoint;
				mousePoint = event.getPoint();
				for(int i = 0; i<8; i++){ //nested for loops iterate through the whole board
					for(int j=0; j<8; j++){
						if(View.this.board.hasPiece(i, j)){
							if(View.this.board.getSquare(i,j).isSelected()){
								double dx = mousePoint.getX() - lastMousePoint.getX(); //calculate how much to change piece position
								double dy = mousePoint.getY() - lastMousePoint.getY();
								View.this.board.getSquare(i, j).translate((int)dx,(int)dy); //translate piece
							}
						}
					}
				}
				repaint(); //repaints the View everytime mouse is dragged
			}
		});
		/*
		 * This actionListener governs what happens when the mouse is released
		 */
		addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				boolean pieceSelected = false; //keeps track of whether or not a piece was selected
				Piece selectedPiece = null; //keeps track of the selected piece
				Piece eatenPiece = null; //keeps track of piece on square another piece was moved to
				int oldX = 0; //keeps track of prior position if piece
				int oldY = 0;
				
				//for loop cyles through board
				for(int i=0; i<8; i++){
					for(int j=0; j<8; j++){
						if(View.this.board.hasPiece(i, j)){ //checks if board square has a piece
							if(View.this.board.getSquare(i, j).isSelected()){ //then checks if the piece was selected
								pieceSelected = true;
								selectedPiece = View.this.board.getSquare(i,j); //records data about piece
								oldX = i;
								oldY = j;
							}
						}
					}
				}
				if(pieceSelected){
					if(selectedPiece.checkMoveIsLegal(e.getPoint(), View.this.board)){ //check legal moves
						int newX = ((int)e.getPoint().getX())/62; //record new point
						int newY = ((int)e.getPoint().getY())/62;
						/*
						 * Make the move
						 */
						//check for castling here
						if(selectedPiece.getType().equals("King") && newX-oldX == 2){
							if(selectedPiece.getColor() == false){ //case that piece is white
								View.this.board.clearSquare(oldX, oldY);
								View.this.board.setSquare(newX, newY, selectedPiece);
								selectedPiece.setLocation(newX, newY);
								Piece rook = View.this.board.getSquare(7, 7);
								View.this.board.setSquare(5,7,rook);
								rook.setLocation(5, 7);
								View.this.board.clearSquare(7, 7);
								View.this.text += "White castles. \n";
							}else{
								//case that piece is black
								View.this.board.clearSquare(oldX, oldY);
								View.this.board.setSquare(newX, newY, selectedPiece);
								selectedPiece.setLocation(newX, newY);
								Piece rook = View.this.board.getSquare(7, 0);
								View.this.board.setSquare(5,0,rook);
								rook.setLocation(5, 0);
								View.this.board.clearSquare(7, 0);
								View.this.text += "White castles. \n";
							}
						}else{
							if(View.this.board.hasPiece(newX, newY)){
								eatenPiece = View.this.board.getSquare(newX, newY);
							}
							View.this.board.clearSquare(oldX, oldY);
							View.this.board.setSquare(newX, newY, selectedPiece);
							//snap to square
							if((eatenPiece != null)){
								View.this.text += selectedPiece.getType() + " eats " + eatenPiece.getType() + "\n";
								View.this.textBox.setText(View.this.text);
							}
							View.this.text += selectedPiece.getType() + " was moved to: " + View.this.columns[newX] +  (newY+1) + "\n"; 
							System.out.println("Snapped to square");
						}
						//repaint();
						/*Note: newY+1 is used to 
						 * read in the console as if rows and cols start
						*from 1 instead of 0, as they do in the program*
						*/
						View.this.textBox.setText(View.this.text); //record to console
						
						View.this.game.checkIsWon(View.this.board);
						if(View.this.game.getIsWon()){
							if(View.this.game.getTurn() == false){
								View.this.text += "White has taken black's king and won the game!";
							}else{
								View.this.text += "Black has taken white's king and won the game!";
							}
							View.this.textBox.setText(View.this.text);
						}else{
							View.this.game.changeTurn();
							if(View.this.game.getMode() != 0){ //case that the game is playing with AI
								
								/*
								 * This Thread is what runs in teh background and computes the AI's best
								 * move and makes that move.  It also updates the textArea with the appropriate text
								 */
								Thread t = new Thread(new Runnable(){
									public void run(){
										View.this.text += View.this.game.getAI().makeMove(View.this.board); 
										View.this.textBox.setText(View.this.text);
										View.this.game.checkIsWon(View.this.board);
										if(View.this.game.getIsWon()){
											if(View.this.game.getTurn() == false){
												View.this.text += "White has taken black's king and won the game!";
											}else{
												View.this.text += "Black has taken white's king and won the game!";
											}
											View.this.textBox.setText(View.this.text);
										}else{
										View.this.game.changeTurn();
										}
										repaint();  //repaints board after AI makes move
									}
								});
								//Have AI make move if game is in AI mode
								t.start(); //begins thread

							}
						}
					}else{ //case that move was illegal
						//snap back to original square
						selectedPiece.setLocation(oldX, oldY);
						View.this.text += "Illegal move! \n";
						View.this.textBox.setText(View.this.text);
					}
					selectedPiece.setSelected(false);
				}
				repaint();
			}
		});
	}
	
	/**
	 * the paintComponent(Graphics g) method paints the view component.  It begins by
	 * painting the black and white squares, and then iterates throught the board to paint
	 * all the pieces on the board.  
	 */
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		boolean isBlack = false; //keeps track of whether a square should be painted black or white
		/*
		 * This for loop cycles through an 8x8 grid, alternating black and white squares
		 */
		for(int i = 0; i<8; i++){
			isBlack = !(isBlack);
			for(int j = 0; j<8; j++){
				isBlack = !(isBlack);
				Rectangle rect = new Rectangle(i*62,j*62,62,62);
				if(isBlack){	
					g2.setColor(Color.darkGray);
				}else{
					g2.setColor(Color.white);
				}
				g2.fill(rect);
			}
		}
		/*
		 * This for loop cycles through the board and for any board square with a piece,
		 * it paints draws the piece.  
		 */
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(board.hasPiece(i, j)){ //perform draw action if piece exists on board
					board.getSquare(i, j).draw(g2);
				}
			}
		}
		//draws selected pieces on top to ensure they are on the top layer
		for(int i=0; i<8; i++){
			for(int j =0; j<8; j++){
				if(board.hasPiece(i, j)){
					if(board.getSquare(i, j).isSelected()){
						board.getSquare(i,j).draw(g2);
					}
				}
			}
		}
	}
}
