package Main;

public class Node {
	protected Checkers state; //GAME STATE
	protected double movevalue; //UTILITY VALUE
	
	//INSTANTIATE NODE WITH CURRENT GAME STATE
	public Node(Checkers curr) {
		this.state = new Checkers();
		Methods.copyGame(curr, this.state);
		this.movevalue = Methods.calculateUtility(this.state);
	}
	
	//PERFORMS MOVE
	public void doMove(String selection, String move) {
		this.state.doMove(selection, move);
		this.updateValue();
	}
	
	//UPDATES UTILITY VALUE
	public void updateValue() {
		this.movevalue = Methods.calculateUtility(this.state);
	}
	
	
}
