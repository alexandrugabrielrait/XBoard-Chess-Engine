package datasystem;

public class Bishop extends Piece {

    public Bishop(int color, int x, int y) {
        super(color, x, y);
        value = BISHOP_VALUE;
    }

    public Piece clone() {
        return new Bishop(color, x, y);
    }

    @Override
    public boolean canAttack(int x1, int y1) {
        if (!ChessBoard.isInsideBoard(x1, y1))
            return false;
        if ((x1 == x) || (y1 == y) || Math.abs(x1 - x) != Math.abs(y1 - y))
            return false;
        if (y1 > y) {
            if (x1 > x) {
                // greater x, greater y
                for (int i = 1; y + i < y1; i++) {
                    if (!ChessBoard.isEmpty(x + i, y + i))
                        return false;
                }
            } else {
                // lesser x, greater y
                for (int i = 1; y + i < y1; i++) {
                    if (!ChessBoard.isEmpty(x - i, y + i))
                        return false;
                }
            }
        } else {
            if (x1 > x) {
                // greater x, lesser y
                for (int i = 1; y - i > y1; i++) {
                    if (!ChessBoard.isEmpty(x + i, y - i))
                        return false;
                }
            } else {
                // lesser x, lesser y
                for (int i = 1; y - i > y1; i++) {
                    if (!ChessBoard.isEmpty(x - i, y - i))
                        return false;
                }
            }
        }
        return true;
    }

    @Override
    public void addValidMoves(MoveList list) {
        for (int i = 1; i <= 7; i++) {
            if (!ChessBoard.isInsideBoard(x + i, y + i))
                break;
            if (ChessBoard.isEmpty(x + i, y + i)) {
                list.create(this, x + i, y + i, 1);
            } else {
                Piece p = ChessBoard.getPiece(x + i, y + i);
                if (isEnemy(p))
                    list.create(this, x + i, y + i, 1.5f + p.value - value);
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (!ChessBoard.isInsideBoard(x + i, y - i))
                break;
            if (ChessBoard.isEmpty(x + i, y - i)) {
                list.create(this, x + i, y - i, 1);
            } else {
                Piece p = ChessBoard.getPiece(x + i, y - i);
                if (isEnemy(p))
                    list.create(this, x + i, y - i, 1.5f + p.value - value);
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (!ChessBoard.isInsideBoard(x - i, y + i))
                break;
            if (ChessBoard.isEmpty(x - i, y + i)) {
                list.create(this, x - i, y + i, 1);
            } else {
                Piece p = ChessBoard.getPiece(x - i, y + i);
                if (isEnemy(p))
                    list.create(this, x - i, y + i, 1.5f + p.value - value);
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (!ChessBoard.isInsideBoard(x - i, y - i))
                break;
            if (ChessBoard.isEmpty(x - i, y - i)) {
                list.create(this, x - i, y - i, 1);
            } else {
                Piece p = ChessBoard.getPiece(x - i, y - i);
                if (isEnemy(p))
                    list.create(this, x - i, y - i, 1.5f + p.value - value);
                break;
            }
        }
    }
}
