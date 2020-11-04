package datasystem;

public class Knight extends Piece {

    public Knight(int color, int x, int y) {
        super(color, x, y);
        value = KNIGHT_VALUE;
    }

    public Piece clone() {
        return new Knight(color, x, y);
    }

    @Override
    public boolean canAttack(int x1, int y1) {
        if (!ChessBoard.isInsideBoard(x1, y1))
            return false;
        int dx = Math.abs(x1 - x);
        int dy = Math.abs(y1 - y);
        if ((Math.max(dx, dy) == 2) && (Math.min(dx, dy) == 1))
            return true;
        return false;
    }

    /**
     * Genereaza lista mutarilor posibile fara a le valida
     *
     * @return lista mutarilor
     */
    private MoveList generateMoves() {
        MoveList list = new MoveList();
        list.create(this, this.x + 1, this.y + 2);
        list.create(this, this.x + 2, this.y + 1);
        list.create(this, this.x + 1, this.y - 2);
        list.create(this, this.x + 2, this.y - 1);
        list.create(this, this.x - 1, this.y + 2);
        list.create(this, this.x - 2, this.y + 1);
        list.create(this, this.x - 1, this.y - 2);
        list.create(this, this.x - 2, this.y - 1);
        return list;
    }

    @Override
    public void addValidMoves(MoveList list) {
        MoveList newList = generateMoves();
        for (Move m : newList) {
            if (ChessBoard.isInsideBoard(m.x1, m.y1)) {
                if (ChessBoard.isEmpty(m.x1, m.y1)) {
                    list.add(m);
                } else {
                    Piece target = ChessBoard.getPiece(m.x1, m.y1);
                    if (isEnemy(target)) {
                        list.add(m);
                    }
                }
            }
        }
    }


}
