package datasystem;

import utilities.Boards;

public class ChessBoard {
	public static BoardState state = new BoardState();

	static {
		resetBoard();
	}

	/**
	 * Reseteaza tabla la starea initiala
	 */
	public static void resetBoard() {
		state.board = new Piece[8][8];
		state.whiteTeam = new Team();
		state.blackTeam = new Team();

		setDefaultBoard();
	}

	static void setDefaultBoard() {
		for (int i = 0; i < 8; i++) {
			state.board[1][i] = new Pawn(1, i, 1);
			state.board[6][i] = new Pawn(-1, i, 6);
		}
		state.board[0][0] = new Rook(1, 0, 0);
		state.board[0][7] = new Rook(1, 7, 0);
		state.board[7][0] = new Rook(-1, 0, 7);
		state.board[7][7] = new Rook(-1, 7, 7);

		state.board[0][1] = new Knight(1, 1, 0);
		state.board[0][6] = new Knight(1, 6, 0);
		state.board[7][1] = new Knight(-1, 1, 7);
		state.board[7][6] = new Knight(-1, 6, 7);

		state.board[0][2] = new Bishop(1, 2, 0);
		state.board[0][5] = new Bishop(1, 5, 0);
		state.board[7][2] = new Bishop(-1, 2, 7);
		state.board[7][5] = new Bishop(-1, 5, 7);

		state.board[0][3] = new Queen(1, 3, 0);
		state.board[7][3] = new Queen(-1, 3, 7);

		state.board[0][4] = new King(1, 4, 0);
		state.board[7][4] = new King(-1, 4, 7);

		// Adaugam piesele in echipe, pionii fiind primii adaugati

		for (int i = 1; i >= 0; --i)
			for (Piece p : state.board[i])
				state.whiteTeam.add(p);
		state.whiteTeam.sort();

		for (int i = 6; i <= 7; ++i)
			for (Piece p : state.board[i])
				state.blackTeam.add(p);
		state.blackTeam.sort();
	}

	/**
	 * Realizeaza o mutare care se presupune adevarata
	 *
	 * @param move este o mutare
	 * @return killed piece
	 */
	static public Piece moveOnBoard(Move move) {
		Piece p = getPiece(move.x0, move.y0);
		if (p == null) {
			return null;
		}
		int i = 0;
		Team team = getTeam(p.color);
		Piece o = team.get(i);
		while (o instanceof Pawn) {
			((Pawn) o).hasDoubled = false;
			o = team.get(++i);
		}
		if (p instanceof King) {
			((King) p).hasMoved = true;
			int distance = move.x1 - move.x0;
			if (distance == 2) {
				// Short Castle
				moveOnBoard(new Move(p.x + 3, p.y, p.x + 1, p.y));
			} else if (distance == -2) {
				// Long Castle
				moveOnBoard(new Move(p.x - 4, p.y, p.x - 1, p.y));
			}
		}

		if (p instanceof Rook) {
			((Rook) p).hasMoved = true;
		}
		if (p instanceof Pawn) {
			int dy = move.y1 - move.y0;
			if (Math.abs(dy) == 2) {
				((Pawn) p).hasDoubled = true;
			}
			// promotion
			if (move.y1 == 0 || move.y1 == 7) {
				team.remove(p);
				switch (move.promotion) {
					case 'n':
						p = new Knight(p.color, move.x0, move.y0);
						break;
					case 'r':
						p = new Rook(p.color, move.x0, move.y0);
						break;
					case 'b':
						p = new Bishop(p.color, move.x0, move.y0);
						break;
					default:
						p = new Queen(p.color, move.x0, move.y0);
				}
				team.add(p);
				team.sort();
			}

			Piece enPassant = getPiece(move.x1, move.y1 - p.color);
			if (enPassant instanceof Pawn && ((Pawn) enPassant).hasDoubled) {
				p.x = move.x1;
				p.y = move.y1;
				getTeam(-p.color).remove(enPassant);
				state.board[move.y1][move.x1] = p;
				state.board[move.y0][move.x0] = null;
				state.board[enPassant.y][enPassant.x] = null;
				return enPassant;
			}
		}
		p.x = move.x1;
		p.y = move.y1;
		Piece captured = getPiece(move.x1, move.y1);
		if (captured != null) {
			getTeam(-p.color).remove(captured);
		}
		state.board[move.y1][move.x1] = p;
		state.board[move.y0][move.x0] = null;
		return captured;
	}

	static public void undoMove(Move move, Piece captured) {
		Piece p = getPiece(move.x1, move.y1);
		if (p == null) {
			return;
		}
		p.x = move.x0;
		p.y = move.y0;
		state.board[move.y0][move.x0] = p;
		state.board[move.y1][move.x1] = null;
		if(p instanceof King) {
			int distance = move.x1 - move.x0;
			if (distance == 2) {
				// Short Castle
				undoMove(new Move(p.x + 3, p.y, p.x + 1, p.y), null);
				((Rook)getPiece(7,p.y)).hasMoved = false;
			} else if (distance == -2) {
				// Long Castle
				undoMove(new Move(p.x - 4, p.y, p.x - 1, p.y), null);
				((Rook)getPiece(0,p.y)).hasMoved = false;
			}
		}
		if(captured != null) {
			if(captured instanceof King) {
				getTeam(-p.color).king = (King) captured;
			}
			getTeam(-p.color).add(captured);
			getTeam(-p.color).sort();
			state.board[captured.y][captured.x] = captured;
		}
	}

	/**
	 * Verifica daca coordonatele x,y sunt in afara tablei de sah
	 *
	 * @param x coordonata x patratului
	 * @param y coordonata y patratului
	 */
	static boolean isInsideBoard(int x, int y) {
		return (0 <= x) && (x < 8) && (0 <= y) && (y < 8);
	}

	/**
	 * Verifica daca pe patratul de coordonate x, y sta o piesa
	 *
	 * @param x coordonata x patratului
	 * @param y coordonata y patratului
	 */
	static boolean isEmpty(int x, int y) {
		return (ChessBoard.state.board[y][x] == null);
	}

	/**
	 * Intoarce piesa de pe pozitia x, y
	 *
	 * @param x coordonata x patratului
	 * @param y coordonata y patratului
	 */
	public static Piece getPiece(int x, int y) {
		if (!isInsideBoard(x, y))
			return null;
		return ChessBoard.state.board[y][x];
	}

	/**
	 * Verifica daca in patratul de coordonate x, y se afla un pion care poate fi luat de  EnPassant
	 *
	 * @param x coordonata x patratului
	 * @param y coordonata y patratului
	 */
	static boolean isEnPassant(int x, int y) {
		if (!isInsideBoard(x, y) || isEmpty(x, y) || !(getPiece(x, y) instanceof Pawn))
			return false;
		Pawn p = (Pawn) getPiece(x, y);
		return p.hasDoubled;
	}

	/**
	 * Intoarce echipa in functie de culoarea data
	 *
	 * @param color culoarea piesei
	 * @return state.whiteTeam daca culoarea == 1, state.blackTeam altfel
	 */
	public static Team getTeam(int color) {
		if (color == 1)
			return state.whiteTeam;
		if (color == -1)
			return state.blackTeam;
		return null;
	}

	/**
	 * Verifica daca patratul de coordonate x, y poate fi atacat de o piesa de o anumita culoare
	 */
	public static boolean canBeAttacked(int x, int y, int color) {
		for (Piece p : getTeam(color)) {
			if (p.canAttack(x, y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica daca macar un rege a murit
	 */
	public static boolean deadKing() {
		return getTeam(1).isKingDead() || getTeam(-1).isKingDead();
	}

	/**
	 * Verifica daca un jucator si-a luat sah mat
	 */
	public static boolean checkmated(int player) {
		Team team = getTeam(player);
		if (!team.king.isInCheck()) {
			return false;
		}

		MoveList moves = new MoveList(ChessBoard.getTeam(player));
		BoardState current = state;
		for (Move m : moves) {
			state = current.clone();
			moveOnBoard(m);
			if (!getTeam(player).king.isInCheck()) {
				state = current;
				return false;
			}
		}
		state = current;
		return true;
	}

	/**
	 * Verifica daca un jucator este in stalemate
	 */
	public static boolean stalemated(int player) {
		Team team = getTeam(player);
		if (team.king.isInCheck()) {
			return false;
		}

		MoveList moves = new MoveList(ChessBoard.getTeam(player));
		BoardState current = state;
		for (Move m : moves) {
			state = current.clone();
			moveOnBoard(m);
			if (!getTeam(player).king.isInCheck()) {
				state = current;
				return false;
			}
		}
		state = current;
		return true;
	}
}