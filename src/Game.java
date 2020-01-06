
public class Game {
	public static void main (String[] args) {
		// Initialize the board, players, and starting pieces
		CheckerPiece[][] board = new CheckerPiece[8][4];
		Player player1 = new Player(false);
		Player player2 = new Player(true);
		for (int i = 5; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = new CheckerPiece(player1, i, j, board);
				player1.addPiece(board[i][j]);
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = new CheckerPiece(player2, i, j, board);
				player2.addPiece(board[i][j]);
			}
		}
		
		
	}
}
