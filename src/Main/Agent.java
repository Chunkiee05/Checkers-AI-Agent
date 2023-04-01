package Main;

import java.util.*;

public class Agent {
	
	//MINIMAX ALGORITHM
	public Node minimax(Node current, int depth, boolean maximizer, double alpha, double beta) {
		//MAXIMIZER
		if (maximizer) {
			if (depth == 0 || current.state.endGame() == true) 
				return current;
			
			double max = Double.NEGATIVE_INFINITY;
			Node bestmove = null;
			
			ArrayList<Node> moves = getAllMoves(current.state, false);
			for (int i = 0; i < moves.size(); i++) {
				Node node = minimax(moves.get(i), depth - 1, !current.state.blackTurn, alpha, beta);
				max = Math.max(max, node.movevalue);
				alpha = Math.max(alpha, max);
				
				//CURRENT MOVE BECOMES BESTMOVE IF MOVEVALUE = MAX;
				if (max == node.movevalue)
					bestmove = moves.get(i);
				
				//PRUNING, SKIP NODES THAT HAVE LESS VALUES
				if (beta <= alpha)
					i = moves.size();
			}
			return bestmove;
			
		}
		
		//MINIMIZER
		else {
			if (depth == 0 || current.state.endGame() == true) 
				return current;
			
			double min = Double.POSITIVE_INFINITY;
			Node bestmove = null;
			
			ArrayList<Node> moves = getAllMoves(current.state, true);
			for (int i = 0; i < moves.size(); i++) {
				Node value = minimax(moves.get(i), depth - 1, !current.state.blackTurn, alpha, beta);
				min = Math.min(min, value.movevalue);
				beta = Math.min(beta, min);
				
				//CURRENT MOVE BECOMES BESTMOVE IF MOVEVALUE = MIN;
				if (min == value.movevalue)
					bestmove = moves.get(i);
				
				//PRUNING, SKIP NODES THAT HAVE LESS VALUES
				if (beta <= alpha) 
					i = moves.size();
			}
			return bestmove;
		}
	}
	
	//ORDERS NODE LIST BASED ON HEURISTIC VALUE (HIGHEST TO LOWEST)
	public void orderMoves(ArrayList<Node> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = i + 1; j < nodes.size(); j++) {
				if (nodes.get(i).movevalue < nodes.get(j).movevalue)
					Collections.swap(nodes, i, j);
			}
		}
	}
	
	//RETURNS A LIST OF NODES WITH ALL POSSIBLE GAME STATES AND THEIR HEURISTIC VALUE
	public ArrayList<Node> getAllMoves(Checkers current, boolean blackTurn) {
		 ArrayList<Node> nodes = new ArrayList<Node>();
		 
		for (int i = 0; i < current.getAllPieces(blackTurn).size(); i++) {
			String temppiece = current.getAllPieces(blackTurn).get(i);
			for (int j = 0; j < current.getCombinedMoves(temppiece).size(); j++) {
				String tempmove = current.getCombinedMoves(temppiece).get(j);
				if (!tempmove.isEmpty()) {
					Node newnode = new Node(current);
					newnode.doMove(temppiece, tempmove);
					nodes.add(newnode);
					
				}
			}
		}
		orderMoves(nodes);
		return nodes;
	}

}
