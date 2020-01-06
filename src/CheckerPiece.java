
public class CheckerPiece {
	public Player owner;
	private boolean isKing;
	private int x;
	private int y;
	private CheckerPiece[][] board;
	
	// Instantiates a checker piece.
	public CheckerPiece(Player owner, int x, int y, CheckerPiece[][] board) {
		this.owner = owner;
		this.isKing = false;
		this.x = x;
		this.y = y;
		this.board = board;
	}
	
	// Determines if this piece has a valid move.
	public boolean hasMove() {
		boolean isOdd = x % 2 == 1;
		if (isKing || owner.isOpponent()) {
			// Move bottom left
			if (x < 7) {
				if (isOdd && y > 0) {
					if (board[x+1][y-1] == null)
						return true;
				} else if (!isOdd) {
					if (board[x+1][y] == null)
						return true;
				}
				// Move bottom right
				if (!isOdd && y < 3) {
					if (board[x+1][y+1] == null)
						return true;
				} else if (isOdd) {
					if (board[x+1][y] == null)
						return true;
				}
			}
			// Move top left
			if (x > 0) {
				if (isOdd && y > 0) {
					if (board[x-1][y-1] == null)
						return true;
				} else if (!isOdd) {
					if (board[x-1][y] == null)
						return true;
				}
				// Move top right
				if (!isOdd && y < 3) {
					if (board[x-1][y+1] == null)
						return true;
				} else if (isOdd) {
					if (board[x-1][y] == null)
						return true;
				}
			}
			
			// Jump bottom left
			if (x < 6 && y > 0) {
				if (isOdd && board[x+1][y-1] != null && board[x+1][y-1].owner.isOpponent()) {
					if (board[x+2][y-1] == null)
						return true;
				} else if (!isOdd && board[x+1][y] != null && board[x+1][y].owner.isOpponent()) {
					if (board[x+2][y-1] == null)
						return true;
				}
			}
			// Jump bottom right
			if (x < 6 && y < 3) {
				if (isOdd && board[x+1][y] != null && board[x+1][y].owner.isOpponent()) {
					if (board[x+2][y+1] == null)
						return true;
				} else if (!isOdd && board[x+1][y+1] != null && board[x+1][y+1].owner.isOpponent()) {
					if (board[x+2][y+1] == null)
						return true;
				}
			}
		}
		if (isKing || !owner.isOpponent()) {
			// Jump top left
			if (x > 1 && y > 0) {
				if (isOdd && board[x-1][y-1] != null && board[x-1][y-1].owner.isOpponent()) {
					if (board[x-2][y-1] == null)
						return true;
				} else if (!isOdd && board[x-1][y] != null && board[x-1][y].owner.isOpponent()) {
					if (board[x-2][y-1] == null)
						return true;
				}
			}
			// Jump top right
			if (x > 1 && y < 3) {
				if (isOdd && board[x-1][y] != null && board[x-1][y].owner.isOpponent()) {
					if (board[x-2][y+1] == null)
						return true;
				} else if (!isOdd && board[x-1][y+1] != null && board[x-1][y+1].owner.isOpponent()) {
					if (board[x-2][y+1] == null)
						return true;
				}
			}
		}
		return false;
	}
	
	// Determines if this piece has a valid jump.
	public boolean hasJump() {
		boolean isOdd = x % 2 == 1;
		if (isKing || owner.isOpponent()) {
			if (x < 6 && y > 0) {
				if (isOdd && board[x+1][y-1] != null && board[x+1][y-1].owner.isOpponent()) {
					if (board[x+2][y-1] == null)
						return true;
				} else if (!isOdd && board[x+1][y] != null && board[x+1][y].owner.isOpponent()) {
					if (board[x+2][y-1] == null)
						return true;
				}
			}
			if (x < 6 && y < 3) {
				if (isOdd && board[x+1][y] != null && board[x+1][y].owner.isOpponent()) {
					if (board[x+2][y+1] == null)
						return true;
				} else if (!isOdd && board[x+1][y+1] != null && board[x+1][y+1].owner.isOpponent()) {
					if (board[x+2][y+1] == null)
						return true;
				}
			}
		}
		if (isKing || !owner.isOpponent()) {
			if (x > 1 && y > 0) {
				if (isOdd && board[x-1][y-1] != null && board[x-1][y-1].owner.isOpponent()) {
					if (board[x-2][y-1] == null)
						return true;
				} else if (!isOdd && board[x-1][y] != null && board[x-1][y].owner.isOpponent()) {
					if (board[x-2][y-1] == null)
						return true;
				}
			}
			if (x > 1 && y < 3) {
				if (isOdd && board[x-1][y] != null && board[x-1][y].owner.isOpponent()) {
					if (board[x-2][y+1] == null)
						return true;
				} else if (!isOdd && board[x-1][y+1] != null && board[x-1][y+1].owner.isOpponent()) {
					if (board[x-2][y+1] == null)
						return true;
				}
			}
		}
		return false;
	}
	
	// Removes piece from the board.
	public void deletePiece(Player player) {
		board[x][y] = null;
		player.removePiece(this);
	}
	
	// Attempt to move piece.
	// If mustJump is true, move will return false for any move other than a jump.
	public boolean move(int x, int y, boolean mustJump) {
		boolean isOdd = x % 2 == 1;
		if (board[x][y] != null)
			return false;
		if (isKing || !owner.isOpponent()) {
			// Checks if this is a move
			if (x == this.x - 1 && !mustJump) {
				if (isOdd && (y == this.y - 1 || y == this.y)) {
					board[x][y] = this;
					board[this.x][this.y] = null;
					this.x = x;
					this.y = y;
					promoteIfAble();
					return true;
				} else if (y == this.y || y == this.y + 1) {
					board[x][y] = this;
					board[this.x][this.y] = null;
					this.x = x;
					this.y = y;
					promoteIfAble();
					return true;
				}
			}
			// Checks if this is a jump
			if (x == this.x - 2) {
				if (y == this.y - 1) {
					if (isOdd && board[this.x-1][this.y-1] != null && board[this.x-1][this.y-1].owner.isOpponent()) {
						board[x][y] = this;
						board[this.x][this.y] = null;
						board[this.x-1][this.y-1].deletePiece(board[this.x-1][this.y-1].owner);
						promoteIfAble();
						return true;
					} else if (!isOdd && board[this.x-1][this.y] != null && board[this.x-1][this.y].owner.isOpponent()) {
						board[x][y] = this;
						board[this.x][this.y] = null;
						board[this.x-1][this.y].deletePiece(board[this.x-1][this.y].owner);
						promoteIfAble();
						return true;
					}
				} else if (y == this.y + 1) {
					if (isOdd && board[this.x-1][this.y] != null && board[this.x-1][this.y].owner.isOpponent()) {
						board[x][y] = this;
						board[this.x][this.y] = null;
						board[this.x-1][this.y].deletePiece(board[this.x-1][this.y].owner);
						promoteIfAble();
						return true;
					} else if (!isOdd && board[this.x-1][this.y+1] != null && board[this.x-1][this.y+1].owner.isOpponent()) {
						board[x][y] = this;
						board[this.x][this.y] = null;
						board[this.x-1][this.y+1].deletePiece(board[this.x-1][this.y+1].owner);
						promoteIfAble();
						return true;
					}
				}
			}
		}
		if (isKing || owner.isOpponent()) {
			// Checks if this is a move
			if (x == this.x + 1 && !mustJump) {
				if (isOdd && (y == this.y - 1 || y == this.y)) {
					board[x][y] = this;
					board[this.x][this.y] = null;
					this.x = x;
					this.y = y;
					promoteIfAble();
					return true;
				} else if (y == this.y || y == this.y + 1) {
					board[x][y] = this;
					board[this.x][this.y] = null;
					this.x = x;
					this.y = y;
					promoteIfAble();
					return true;
				}
			}
			// Checks if this is a jump
			if (x == this.x + 2) {
				if (y == this.y - 1) {
					if (isOdd && board[this.x+1][this.y-1] != null && board[this.x+1][this.y-1].owner.isOpponent()) {
						board[x][y] = this;
						board[this.x][this.y] = null;
						board[this.x+1][this.y-1].deletePiece(board[this.x+1][this.y-1].owner);
						promoteIfAble();
						return true;
					} else if (!isOdd && board[this.x+1][this.y] != null && board[this.x+1][this.y].owner.isOpponent()) {
						board[x][y] = this;
						board[this.x][this.y] = null;
						board[this.x+1][this.y].deletePiece(board[this.x+1][this.y].owner);
						promoteIfAble();
						return true;
					}
				} else if (y == this.y + 1) {
					if (isOdd && board[this.x+1][this.y] != null && board[this.x+1][this.y].owner.isOpponent()) {
						board[x][y] = this;
						board[this.x][this.y] = null;
						board[this.x+1][this.y].deletePiece(board[this.x+1][this.y].owner);
						promoteIfAble();
						return true;
					} else if (!isOdd && board[this.x+1][this.y+1] != null && board[this.x+1][this.y+1].owner.isOpponent()) {
						board[x][y] = this;
						board[this.x][this.y] = null;
						board[this.x+1][this.y+1].deletePiece(board[this.x+1][this.y+1].owner);
						promoteIfAble();
						return true;
					}
				}
			}
		}
		return false;
	}

	private void promoteIfAble() {
		if ((owner.isOpponent() && x == 7) || (!owner.isOpponent() && x == 0))
			isKing = true;
	}
}
