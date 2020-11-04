package datasystem;

public class Pawn extends Piece {

	/**
	 * Daca pionul a sarit doua patrate tura trecuta
	 */
	public boolean hasDoubled;

	public Pawn(int color, int x, int y) {
		super(color, x, y);
		value = PAWN_VALUE;
	}

	public Piece clone() {
		Pawn p = new Pawn(color, x, y);
		p.hasDoubled = hasDoubled;
		return p;
	}

	@Override
	public boolean canAttack(int x1, int y1) {
		if (!ChessBoard.isInsideBoard(x1, y1))
			return false;
		int dx = x1 - x;
		int dy = y1 - y;
		if (Math.abs(dx) == 1)
			return (dy == color);
		return false;
	}

	@Override
	public void addValidMoves(MoveList list) {
		if (ChessBoard.isEmpty(x, y + color)) {
			checkPromotion(x, y + color, list, false);
		}
		if (isInStartingPosition() && ChessBoard.isEmpty(x, y + color) && ChessBoard.isEmpty(x, y + 2 * color)) {
			list.create(this, x, y + 2 * color, 1);
		}
		Piece p = ChessBoard.getPiece(x + 1, y + color);
		if (p != null && isEnemy(p))
			checkPromotion(x + 1, y + color, list, true);
		p = ChessBoard.getPiece(x - 1, y + color);
		if (p != null && isEnemy(p))
			checkPromotion(x - 1, y + color, list, true);
		p = ChessBoard.getPiece(x + 1, y);
		if (ChessBoard.isEnPassant(x + 1, y) && isEnemy(p))
			list.create(this, x + 1, y + color, 2);
		p = ChessBoard.getPiece(x - 1, y);
		if (ChessBoard.isEnPassant(x - 1, y) && isEnemy(p))
			list.create(this, x - 1, y + color, 2);
	}

	private void checkPromotion(int x, int y, MoveList list, boolean attack) {
		Move m = new Move(this.x, this.y, x, y, 1);
		list.add(m);
	}

	private boolean isInStartingPosition() {
		return (color == 1 && y == 1) || (color == -1 && y == 6);
	}
}