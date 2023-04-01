package Main;

import java.util.*;

public class Driver {
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Checkers game = new Checkers();
		Agent agent = new Agent();

		
		do {
			Methods.player(input, game);
			Node node = new Node(game);
			game = agent.minimax(node, 3, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).state;
		} while(!game.endGame());
		
		
		game.printWinner();
		
		
		input.close();
	}

}
