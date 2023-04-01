package Main;
import java.util.*;

public class Checkers {
	protected Piece[][] Board = new Piece[8][8]; //8 x 8 Board
	protected boolean blackTurn; //DETERMINES WHOS TURN
	
	//DEFAULT GAME SETUP
	public Checkers() {
		blackTurn = true;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i < 3) {
					if ((Methods.isEven(i) && !Methods.isEven(j)) || (!Methods.isEven(i) && Methods.isEven(j)))
						this.Board[i][j] = new Piece(false);
				}
				else if (i > 4) {
					if ((Methods.isEven(i) && !Methods.isEven(j)) || (!Methods.isEven(i) && Methods.isEven(j)))
						this.Board[i][j] = new Piece(true);
				}
			}
		}
	}
	
	//PRINTS CURRENT BOARD
	public void printBoard() {
		for (int i = 0; i < 8; i++) {
			System.out.print(i+1);
			for (int j = 0; j < 8; j++) {
				if (this.Board[i][j] != null) {
					if (Board[i][j].isBlack() == false)
						if (Board[i][j].isKing == true)
							System.out.print("|W");
						else
							System.out.print("|w");
					else
						if (Board[i][j].isKing == true)
							System.out.print("|B");
						else
							System.out.print("|b");
				}
				else {
					System.out.print("| ");
				}
			}
			
			System.out.printf("|\n");
		}
		System.out.println("  1 2 3 4 5 6 7 8");
	}
	
	//SWITCHES TURN
	public void switchTurn() {
		this.blackTurn = !this.blackTurn;
	}
	
	//CHECKS AND TURNS PIECE INTO KING
	public void becomeKing(int[] move) {
		if (this.Board[move[0] - 1][move[1] - 1].isBlack()) {
			if (this.Board[move[0] - 1][move[1] - 1].isKing == false && move[0] == 1)
				this.Board[move[0] - 1][move[1] - 1].setKing(true);
		}
		else {
			if (this.Board[move[0] - 1][move[1] - 1].isKing == false && move[0] == 8)
				this.Board[move[0] - 1][move[1] - 1].setKing(true);
		}
	}
	
	//CHECKS IF LAST MOVE WAS A JUMP
	public boolean lastisJump(String selectionstring, String movestring) {
		int[] selection = Methods.convertInput(selectionstring); 
		int[] move = Methods.convertInput(movestring);
		
		if (move[0] - selection[0] == 2 || selection[0] - move[0] == 2)
			return true;
		else
			return false;
	}

	//RETURN NUMBER OF TOTAL REGULAR PIECES FOR A COLOR
	public int countRegularPieces(boolean isBlack) {
		int count = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.Board[i][j] != null && this.Board[i][j].isKing == false && this.Board[i][j].isBlack() == isBlack)
					count++;
			}
		}
		return count;
	}

	//RETURN NUMBER OF TOTAL KING PIECES FOR A COLOR
	public int countKingPieces(boolean isBlack) {
		int count = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.Board[i][j] != null && this.Board[i][j].isKing == true && this.Board[i][j].isBlack() == isBlack)
					count++;
			}
		}
		return count;
	}
	
	//RETURN NUMBER OF TOTAL PIECES FOR A COLOR
	public int countPiecesLeft(boolean isBlack) {
		int count = 0;
		count = this.countKingPieces(isBlack) + this.countRegularPieces(isBlack);
		return count;
	}
	
	//CHECKS IF END GAME STATE IS MET
	public boolean endGame() {
		if (this.countPiecesLeft(true) == 0)
			return true;

		else if (this.countPiecesLeft(false) == 0) 
			return true;
		
		else
			return false;
	}
	
	//PRINTS COLOR WINNER
	public void printWinner() {
		if (this.countPiecesLeft(true) == 0) {
			this.printBoard();
			System.out.println("\nWHITE Wins!");
		}
		else if (this.countPiecesLeft(false) == 0) {
			this.printBoard();
			System.out.println("\nBLACK Wins!");
		}
	}
	
	//EXECUTES MULTIJUMP
	public void continuousJump(String selectionstring) {
		Random rnd = new Random();
		this.doMove(selectionstring, this.getValidJumps(selectionstring).get(rnd.nextInt(this.getValidJumps(selectionstring).size())));
	}

	//EXECUTES STATED MOVE
	public void doMove(String selectionstring, String movestring) {
		int[] selection = Methods.convertInput(selectionstring); 
		int[] move = Methods.convertInput(movestring);
		
		this.Board[move[0] - 1][move[1] - 1] = this.Board[selection[0] - 1][selection[1] - 1];
		this.Board[selection[0] - 1][selection[1] - 1] = null;
		if (move[1] - selection[1] == 2 || selection[1] - move[1] == 2) {
			this.Board[(move[0] + selection[0])/2 - 1][(move[1] + selection[1])/2 - 1] = null;
		}
		this.becomeKing(move);
		
		//CHECK IF THERE IS POSSIBLE MULTIJUMP
		if (this.lastisJump(selectionstring, movestring) && !this.getValidJumps(movestring).isEmpty()) {
			this.continuousJump(movestring);
		}
		
		else 
			this.switchTurn();
	}
	
	//RETURNS ALL PIECES FOR A COLOR
	public ArrayList<String> getAllPieces(boolean isBlack) {
		ArrayList<String> pieces = new ArrayList<String>();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.Board[i][j] != null && this.Board[i][j].isBlack() == isBlack)
					pieces.add((i+1) + "," + (j+1));
			}
		}
		
		return pieces;
	}
	
	//RETURNS ALL VALID SIMPLE MOVES FOR A PIECE
	public ArrayList<String> getValidSimple(String selectionString) {
		ArrayList<String> moves = new ArrayList<String>();
		int[] selection = Methods.convertInput(selectionString);
		Piece piece = this.Board[selection[0] - 1][selection[1] - 1];
		
		if (piece.isKing || piece.isBlack()) {
			//Simple move top-left diagonal
			if (this.possibleMove(selection, Methods.convertInput((selection[0] - 1) + "," + (selection[1] - 1))))
				if (Methods.inIndexRange(selection[0] - 1) && Methods.inIndexRange(selection[1] - 1))
					moves.add((selection[0] - 1) + "," + (selection[1] - 1));
			//Simple move top-right diagonal
			if (this.possibleMove(selection, Methods.convertInput((selection[0] - 1) + "," + (selection[1] + 1))))
				if (Methods.inIndexRange(selection[0] - 1) && Methods.inIndexRange(selection[1] + 1))
					moves.add((selection[0] - 1) + "," + (selection[1] + 1));
		}
		
		if (piece.isKing || !piece.isBlack()) {
			//Simple move down-left diagonal
			if (this.possibleMove(selection, Methods.convertInput((selection[0] + 1) + "," + (selection[1] - 1))))
				if (Methods.inIndexRange(selection[0] + 1) && Methods.inIndexRange(selection[1] - 1))
					moves.add((selection[0] + 1) + "," + (selection[1] - 1));
			//Simple move down-right diagonal
			if (this.possibleMove(selection, Methods.convertInput((selection[0] + 1) + "," + (selection[1] + 1))))
				if (Methods.inIndexRange(selection[0] + 1) && Methods.inIndexRange(selection[1] + 1))
					moves.add((selection[0] + 1) + "," + (selection[1] + 1));
		}
		
		return moves;
	}
	
	//RETURNS ALL VALID JUMP MOVES FOR A PIECE
	public ArrayList<String> getValidJumps(String selectionString) {
		ArrayList<String> moves = new ArrayList<String>();
		int[] selection = Methods.convertInput(selectionString);
		Piece piece = this.Board[selection[0] - 1][selection[1] - 1];
		
		if (piece.isKing || piece.isBlack()) {
			//Jump move top-left diagonal
			if (this.possibleMove(selection, Methods.convertInput((selection[0] - 2) + "," + (selection[1] - 2))))
				if (Methods.inIndexRange(selection[0] - 2) && Methods.inIndexRange(selection[1] - 2))
					moves.add((selection[0] - 2) + "," + (selection[1] - 2));
			//Jump move top-right diagonal
			if (this.possibleMove(selection, Methods.convertInput((selection[0] - 2) + "," + (selection[1] + 2))))
				if (Methods.inIndexRange(selection[0] - 2) && Methods.inIndexRange(selection[1] + 2))
					moves.add((selection[0] - 2) + "," + (selection[1] + 2));
		}
		
		if (piece.isKing || !piece.isBlack()) {
			//Jump move down-left diagonal
			if (this.possibleMove(selection, Methods.convertInput((selection[0] + 2) + "," + (selection[1] - 2))))
				if (Methods.inIndexRange(selection[0] + 2) && Methods.inIndexRange(selection[1] - 2))
					moves.add((selection[0] + 2) + "," + (selection[1] - 2));
			//Jump move down-right diagonal
			if (this.possibleMove(selection, Methods.convertInput((selection[0] + 2) + "," + (selection[1] + 2))))
				if (Methods.inIndexRange(selection[0] + 2) && Methods.inIndexRange(selection[1] + 2))
					moves.add((selection[0] + 2) + "," + (selection[1] + 2));
		}
		
		return moves;
	}
	
	//RETURNS ALL POSSIBLE MOVES FOR A PIECE
	public ArrayList<String> getCombinedMoves(String selectionString){
		ArrayList<String> moves = new ArrayList<String>();
		moves.addAll(this.getValidSimple(selectionString));
		moves.addAll(this.getValidJumps(selectionString));
		return moves;
	}
	
	//CHECKS IF SELECTED PIECE IS VALID
	public boolean isValidPiece(String selection) {
		
		if (this.getAllPieces(blackTurn).contains(selection))
			return true;
		else
			return false;
	}
	
	//CHECKS IF SIMPLE MOVE IS VALID FOR PIECE
	public boolean isValidSimple(String select, String move) {
		if (this.getValidSimple(select).contains(move))
			return true;
		else
			return false;
	}
	
	//CHECKS IF JUMP MOVE IS VALID FOR PIECE
	public boolean isValidJump(String select, String move) {
		if (this.getValidJumps(select).contains(move))
			return true;
		else
			return false;
	}
	
	//CHECKS IF MOVE IS VALID FOR A PIECE
	public boolean possibleMove(int[] selection, int[] move) {

		if (selection != null && move != null) {
			if (!Methods.inIndexRange(move[0]) || !Methods.inIndexRange(move[1])) 
				return false;
			
			else if (this.Board[move[0] - 1][move[1] - 1] == null) {
				//KING PIECE
				if (this.Board[selection[0] - 1][selection[1] - 1].isKing == true) {
					//Simple
					if (move[1] - selection[1] == 1 || selection[1] - move[1] == 1) {
						if (move[0] - selection[0] == -1 || move[0] - selection[0] == 1)
							return true;
					}
					//Jump
					else if (move[1] - selection[1] == 2 || selection[1] - move[1] == 2) {
						if ((move[0] - selection[0] == 2 || move[0] - selection[0] == -2) && this.Board[(move[0] + selection[0])/2 - 1][(move[1] + selection[1])/2 - 1] != null) 
							if (this.Board[(move[0] + selection[0])/2 - 1][(move[1] + selection[1])/2 - 1].isBlack() != this.Board[selection[0] - 1][selection[1] - 1].isBlack())
								return true;
					}
					
					
				}
				
				//BLACK PIECE
				else if (Board[selection[0] - 1][selection[1] - 1].isBlack()) {
					//Simple
					if (move[1] - selection[1] == 1 || selection[1] - move[1] == 1) {
						if (move[0] - selection[0] == -1)
							return true;
						else if (this.Board[selection[0] - 1][selection[1] - 1].isKing == true && move[0] - selection[0] == 1)
							return true;
					}
					//Jump
					else if (move[1] - selection[1] == 2 || selection[1] - move[1] == 2) {
						if (move[0] - selection[0] == -2 && this.Board[(move[0] + selection[0])/2 - 1][(move[1] + selection[1])/2 - 1] != null)
							if (this.Board[(move[0] + selection[0])/2 - 1][(move[1] + selection[1])/2 - 1].isBlack() == false)
								return true;
					}
				
				}
				
				//WHITE PIECE
				else {
					//Simple
					if (move[1] - selection[1] == 1 || selection[1] - move[1] == 1) {
						if (move[0] - selection[0] == 1)
							return true;
						else if (this.Board[selection[0] - 1][selection[1] - 1].isKing == true && move[0] - selection[0] == -1)
							return true;
					}
					//Jump
					else if (move[1] - selection[1] == 2 || selection[1] - move[1] == 2) {
						if (move[0] - selection[0] == 2 && Board[(move[0] + selection[0])/2 - 1][(move[1] + selection[1])/2 - 1] != null)
							if (this.Board[(move[0] + selection[0])/2 - 1][(move[1] + selection[1])/2 - 1].isBlack() == true)
								return true;
					}
				}
				
				
			}
		}
		return false;
		
	}
	

	

	

	

	


	




	
}
