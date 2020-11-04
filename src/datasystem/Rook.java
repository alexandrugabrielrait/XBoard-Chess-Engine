package datasystem;

public class Rook extends Piece {

    /**
     * Daca tura s-a mutat din pozitia initiala
     */
    public boolean hasMoved;

    public Rook(int color, int x, int y) {
        super(color, x, y);
        value = ROOK_VALUE;
    }

    public Piece clone() {
        Rook p = new Rook(color, x, y);
        p.hasMoved = hasMoved;
        return p;
    }

    @Override
    public boolean canAttack(int x1, int y1) {
        if (!ChessBoard.isInsideBoard(x1, y1))
            return false;
        if ((x1 != x && y1 != y) || (x1 == x && y1 == y))
            return false;
        if (y1 > y) {
            for (int i = y + 1; i < y1; i++)
                if (!ChessBoard.isEmpty(x, i))
                    return false;
        } else if (y1 < y) {
            for (int i = y - 1; i > y1; i--)
                if (!ChessBoard.isEmpty(x, i))
                    return false;
        } else if (x1 > x) {
            for (int i = x + 1; i < x1; i++)
                if (!ChessBoard.isEmpty(i, y))
                    return false;
        } else if (x1 < x) {
            for (int i = x - 1; i > x1; i--)
                if (!ChessBoard.isEmpty(i, y))
                    return false;
        }
        return true;
    }

    @Override
    public void addValidMoves(MoveList list) {
        for (int i = x + 1; i < 8; i++) {
            if (!ChessBoard.isInsideBoard(i, y))
                break;
            if (ChessBoard.isEmpty(i, y)) {
                list.create(this, i, y, 0.5f);
            } else {
                Piece p = ChessBoard.getPiece(i, y);
                if (isEnemy(p))
                    list.create(this, i, y, 1.5f + p.value - value);
                break;
            }
        }
        for (int i = x - 1; i >= 0; i--) {
            if (!ChessBoard.isInsideBoard(i, y))
                break;
            if (ChessBoard.isEmpty(i, y)) {
                list.create(this, i, y, 0.5f);
            } else {
                Piece p = ChessBoard.getPiece(i, y);
                if (isEnemy(p))
                    list.create(this, i, y, 1.5f + p.value - value);
                break;
            }
        }
        for (int i = y + 1; i < 8; i++) {
            if (!ChessBoard.isInsideBoard(x, i))
                break;
            if (ChessBoard.isEmpty(x, i)) {
                list.create(this, x, i, 1f);
            } else {
                Piece p = ChessBoard.getPiece(x, i);
                if (isEnemy(p))
                    list.create(this, x, i, 1.5f + p.value - value);
                break;
            }
        }
        for (int i = y - 1; i >= 0; i--) {
            if (!ChessBoard.isInsideBoard(x, i))
                break;
            if (ChessBoard.isEmpty(x, i)) {
                list.create(this, x, i, 1f);
            } else {
                Piece p = ChessBoard.getPiece(x, i);
                if (isEnemy(p))
                    list.create(this, x, i, 1.5f + p.value - value);
                break;
            }
        }
    }
}
