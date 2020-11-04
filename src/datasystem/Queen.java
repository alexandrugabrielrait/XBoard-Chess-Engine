package datasystem;

import java.util.Queue;

public class Queen extends Piece {

    public Queen(int color, int x, int y) {
        super(color, x, y);
        value = QUEEN_VALUE;
    }

    public Piece clone() {
        return new Queen(color, x, y);
    }

    @Override
    public boolean canAttack(int x1, int y1) {
        if (!ChessBoard.isInsideBoard(x1, y1))
            return false;
        Piece rook = new Rook(color, x, y);
        Piece bishop = new Bishop(color, x, y);
        if (rook.canAttack(x1, y1) || bishop.canAttack(x1, y1))
            return true;
        return false;
    }

    @Override
    public void addValidMoves(MoveList list) {
        Piece dummy = new Rook(color, x, y);
        dummy.addValidMoves(list);
        dummy = new Bishop(color, x, y);
        dummy.addValidMoves(list);
    }
}