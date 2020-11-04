package datasystem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Piece implements Comparable<Piece>, Cloneable {
    public int color;
    int x;
    int y;
    protected float value;
    public static final float PAWN_VALUE = 1;
    public static final float KNIGHT_VALUE = 3;
    public static final float BISHOP_VALUE = 3.25f;
    public static final float ROOK_VALUE = 5;
    public static final float QUEEN_VALUE = 9.5f;
    public static final float KING_VALUE = 1000;

    public Piece(int color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public abstract Piece clone();

    /**
     * Intoarce true daca piesa poate ataca patratul de coordonate x, y.
     * Nu ia in considerare daca patratul este ocupat sau nu, de aliat sau de inamic
     */
    public abstract boolean canAttack(int x1, int y1);

    public float getValue() {
        return value;
    }

    /**
     * Adauga miscarile valide ale piesei la lista data
     *
     * @param list lista de mutari
     */
    public abstract void addValidMoves(MoveList list);

    /**
     * Intoarce true daca piesa respective este un inamic al acestei piese
     *
     * @param piece checked piece
     */
    public boolean isEnemy(Piece piece) {
        return (color != piece.color);
    }

    /**
     * Compara doua piese astfel incat pionii sa fie intotdeauna primii in lista
     */
    public int compareTo(Piece p) {
        return  Float.compare(value, p.value);
    }
}