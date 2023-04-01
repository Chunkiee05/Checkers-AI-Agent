package Main;
import java.util.*;

public class Methods {
	
	//CHECKS IF NUMBER IS EVEN
	public static boolean isEven(int number) {
		return number % 2 == 0;
	}
	
	//CONVERTS STRING INPUT INTO INT ARRAY
	public static int[] convertInput(String input) {
		int[] converted = new int[2];
		StringTokenizer str = new StringTokenizer(input);
		try {
		converted[0] = Integer.parseInt(str.nextToken(","));
		converted[1] = Integer.parseInt(str.nextToken(","));
		} catch (Exception e) {
			System.out.println("INCORRECT FORMAT\n");
			return null;
		}
		return converted;
	}
	
	//CHECKS IF NUMBER IS IN BOARD INDEX RANGE
	public static boolean inIndexRange(int number) {
		if (number <= 8 && number >= 1)
			return true;
		else 
			return false;
	}
	
	//MANUALLY COPIES GAME STATE 
	public static void copyGame(Checkers source, Checkers copy) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				try {
					if (source.Board[i][j] != null)
						copy.Board[i][j] = (Piece)source.Board[i][j].clone();
					else
						copy.Board[i][j] = null;
				}catch(CloneNotSupportedException c) {}
			}
		}
		source.blackTurn = copy.blackTurn;
	}
	
	//CALCULATES UTILITY VALUE
	public static double calculateUtility(Checkers game){
		return (((game.countKingPieces(false) * 2) + game.countRegularPieces(false)) - 
				((game.countKingPieces(true) * 2) + game.countRegularPieces(true)));
	}
	
	//PLAYER INPUTS
	public static void player(Scanner input, Checkers game) {
		boolean endTurn = false;
		String select, move;
		
		if (game.blackTurn)
			do {
					game.printBoard();
					//ASK PIECE
					System.out.print("\nBLACK TURN, Pick a Piece to Move (ROW,COL): ");
					select = input.nextLine();
					//ASK MOVE
					
					if (game.isValidPiece(select)){
						System.out.print("\nBLACK TURN, Where to move (ROW,COL): ");
						move = input.nextLine();
						//PERFORM MOVE
						if (game.isValidSimple(select, move) || game.isValidJump(select, move)) {
							game.doMove(select, move);
							endTurn = true;
						}
					}
			} while (endTurn == false);
		}
		
}
