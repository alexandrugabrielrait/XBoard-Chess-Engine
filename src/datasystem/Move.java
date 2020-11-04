package datasystem;

import xboard.ChessEngine;
import xboard.Interpreter;

public class Move implements Comparable<Move>{
    public int x0;
    public int y0;
    public int x1;
    public int y1;
    public float value;
    public char promotion;

    public Move(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    public Move(int x0, int y0, int x1, int y1, float value) {
        this(x0, y0, x1, y1);
        this.value = value;
    }
    public Move(float value) {
        this.value = value;
    }

    public Move(int x0, int y0, int x1, int y1, float value, char promotion) {
        this(x0, y0, x1, y1, value);
        this.promotion = promotion;
    }

    @Override
    public int compareTo(Move o) {
        float c = value - o.value;
        if (c > 0)
            return 1;
        if (c < 0)
            return -1;
        return 0;
    }

    public boolean noAction() {
        return (x0 == 0) && (y1 == 0) && (x1 == 0) && (y1 == 0);
    }

    @Override
    public String toString() {
        return "Move{" +
                "x0=" + Interpreter.intToCharacter(x0) +
                ", y0=" + (y0 + 1) +
                ", x1=" + Interpreter.intToCharacter(x1) +
                ", y1=" + (y1 + 1) +
                ", value=" + value +
                ", promotion=" + promotion +
                ", piece=" + ChessBoard.getPiece(x0, y0) +
                '}';
    }
}
