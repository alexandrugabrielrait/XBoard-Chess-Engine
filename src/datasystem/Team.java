package datasystem;

import java.util.ArrayList;
import java.util.Collections;

public class Team extends ArrayList<Piece> implements Cloneable {
	public King king;

	/**
	 * Adauga o piesa in echipa. Daca este rege pastreaza referinta la ea
	 *
	 * @return daca piesa a fost adaugata cu succes
	 */
	@Override
	public boolean add(Piece piece) {
		if (!super.add(piece))
			return false;
		if (piece instanceof King)
			king = (King) piece;
		return true;
	}

	@Override
	public Team clone() {
		Team copy = new Team();
		for (Piece p : this) {
			if (p instanceof King) {
				copy.king = (King) (p.clone());
				copy.add(copy.king);
			} else
				copy.add(p.clone());
		}
		return copy;
	}

	public boolean isKingDead() {
		return !(contains(king));
	}

	public void sort() {
		Collections.sort(this);
	}
}
