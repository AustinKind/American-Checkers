import java.util.ArrayList;

public class Player {
	private ArrayList<CheckerPiece> pieces;
	private boolean isOpponent;
	
	// Initializes player
	public Player(boolean isOpponent) {
		pieces = new ArrayList<>();
		this.isOpponent = isOpponent;
	}
	
	// Adds piece to player's pieces
	public boolean addPiece(CheckerPiece piece) {
		if (pieces.add(piece))
			return true;
		else
			return false;
	}
	
	// Removes piece from player's pieces
	public boolean removePiece(CheckerPiece piece) {
		if (pieces.remove(piece))
			return true;
		else
			return false;
	}

	// Returns true if player is the opponent.
	public boolean isOpponent() {
		return isOpponent;
	}
}