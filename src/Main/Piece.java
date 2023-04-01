package Main;

public class Piece implements Cloneable{
	private boolean isBlack; //DETERMINES PIECE COLOR (BLACK OR WHITE)
	protected boolean isKing; //DETERMINES PIECE TYPE (KING OR REGULAR)
	
	//INSTANTIATES PIECE
	public Piece(boolean isBlack) {
		this.isBlack = isBlack;

		this.isKing = false;
	}
	
	//RETURNS PIECE COLOR
	public boolean isBlack() {
		return this.isBlack;
	}
	
	//SETS KING VALUE
	public void setKing(boolean isKing) {
		this.isKing = isKing;
	}
	
	//CREATES A NEW COPY OF THE PIECE
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
