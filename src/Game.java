
import javax.swing.*;

public class Game {
	
	private AI player;
	private JLabel label;	// label that keeps track of who's turn it is
	private int mode;		// 0 => player & AI, 1 => AI & AI
	private boolean isBlackTurn;
	private boolean isWon;
	
	private String ai_1;
	private String ai_2;
	
	public Game(int mode, JLabel label){
		this.mode = mode;
		this.label = label;
		this.isWon = false;
		this.isBlackTurn = false;

		if(mode == 1){
			player = new MinMaxAlgorithm();
		}
	}
	
	public Game(int mode, String player1, String player2, JLabel label){
		this.mode = mode;
		this.label = label;
		this.isWon = false;
		this.isBlackTurn = false;
		this.ai_1 = player1;
		this.ai_2 = player2;
	}
	
	public AI getAI(){ return player; }
	public boolean getIsWon(){ return isWon;}
	public int getMode(){ return mode; }
	public boolean getTurn(){ return isBlackTurn; }

	public void changeTurn(){
		isBlackTurn = !isBlackTurn;
		if(mode == 0){
			label.setText( ((isBlackTurn) ? ai_2 : ai_1) + "'s Turn");
		}else if(mode == 1){
			label.setText( ((isBlackTurn) ? "Minimax's turn!" : "Your turn!") );
		}
	}
	
	public void checkIsWon(Board b){
		isWon = true; //victory defaults to true if no king is found
		
		for(int i = 0; i<8; i++){
			for(int j=0; j<8; j++){
				if(b.hasPiece(i, j)){
					if(b.getSquare(i,j).getType().equalsIgnoreCase("King")){ //checks if a king is on the square
						boolean state = b.getSquare(i, j).getColor();
						if(isBlackTurn == true && state == false){
							isWon =  false;
						}
						else if(isBlackTurn == false && state == true){
							isWon = false;
						}
					}
				}
			}
		}
	}
}
