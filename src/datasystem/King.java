package datasystem;

public class King extends Piece {

    /**
     * Daca regele s-a mutat din pozitia initiala
     */
    public boolean hasMoved;

    public King(int color, int x, int y) {
        super(color, x, y);
        value = KING_VALUE;
    }

    public Piece clone() {
        King p = new King(color, x, y);
        p.hasMoved = hasMoved;
        return p;
    }

    @Override
    public boolean canAttack(int x1, int y1) {
        if (!ChessBoard.isInsideBoard(x1, y1) || ((x == x1) && (y == y1)))
            return false;
        if ((Math.abs(x1 - x) > 1) || (Math.abs(y1 - y) > 1))
            return false;
        return true;
    }

    /**
     * Genereaza lista mutarilor posibile fara a le valida
     *
     * @return lista mutarilor
     */
    private MoveList generateMoves() {
        MoveList list = new MoveList();
        list.create(this, this.x + 1, this.y);
        list.create(this, this.x - 1, this.y);
        list.create(this, this.x, this.y + 1);
        list.create(this, this.x, this.y - 1);
        list.create(this, this.x + 1, this.y + 1);
        list.create(this, this.x + 1, this.y - 1);
        list.create(this, this.x - 1, this.y + 1);
        list.create(this, this.x - 1, this.y - 1);
        return list;
    }

    @Override
    public void addValidMoves(MoveList list) {
        MoveList newList = generateMoves();
        // Normal Moves
        for (Move m : newList) {
            if (ChessBoard.isInsideBoard(m.x1, m.y1)) {
                if (ChessBoard.isEmpty(m.x1, m.y1)) {
                        list.add(m);
                } else {
                    Piece target = ChessBoard.getPiece(m.x1, m.y1);
                    if (isEnemy(target)){
                        list.add(m);
                    }
                }
            }
        }
        // Castling
        if (!hasMoved && !isInCheck()) {
            // Short Castle
            Piece p = ChessBoard.getPiece(x + 3, y);
            if (p instanceof Rook && !isEnemy(p)) {
                Rook r = (Rook) p;
                if (!r.hasMoved && ChessBoard.isEmpty(x + 1, y) && ChessBoard.isEmpty(x + 2, y)
                        && !ChessBoard.canBeAttacked(x + 1, y, -color)
                        && !ChessBoard.canBeAttacked(x + 2, y, -color))
                    list.create(this, x + 2, y, 3);
            }
            // Long Castle
            p = ChessBoard.getPiece(x - 4, y);
            if (p instanceof Rook && !isEnemy(p)) {
                Rook r = (Rook) p;
                if (!r.hasMoved && ChessBoard.isEmpty(x - 1, y) && ChessBoard.isEmpty(x - 2, y)
                        && ChessBoard.isEmpty(x - 3, y)
                        && !ChessBoard.canBeAttacked(x - 1, y, -color)
                        && !ChessBoard.canBeAttacked(x - 2, y, -color))
                    list.create(this, x - 2, y, 3);
            }
        }
    }

    /**
     * Verifica daca regele este in sah
     */
    public boolean isInCheck() {
        return ChessBoard.canBeAttacked(x, y, -color);
    }
}
