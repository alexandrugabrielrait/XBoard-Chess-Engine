package intelligence;

import datasystem.*;

public class Minimax {
	public static final float CHECK_MATE = 3000;
	static int maxDepth;
	static int turn;

	/**
	 * Gaseste cea mai buna mutare
	 */
	public static Move getMove(int player) {
		++turn;
		float alpha = -CHECK_MATE;
		float beta = CHECK_MATE;
		MoveList moves = new MoveList(ChessBoard.getTeam(player));
		if (moves.size() < 7 && ChessBoard.getTeam(player).size() <= 5) {
			maxDepth = 8;
		}
		else if(moves.size() < 30 && ChessBoard.getTeam(player).size() <= 12) {
			if(turn % 2 == 0)
				maxDepth = 7;
			else maxDepth = 6;
		}
		else if(turn % 3 == 0)
			maxDepth = 6;
		else maxDepth = 5;

		Move bestMove = new Move(-CHECK_MATE - 1);

		for (Move m : moves) {
			Piece p = ChessBoard.getPiece(m.x0, m.y0);
			boolean isPawn = false;
			boolean hasMoved = false;
			if (p instanceof Pawn) {
				isPawn = true;
				hasMoved = ((Pawn)p).hasDoubled;
			} else if(p instanceof Rook){
				hasMoved = ((Rook)p).hasMoved;
			} else if(p instanceof King){
				hasMoved = ((King)p).hasMoved;
			}

			float value = 0;
			boolean valid = false;
			Piece captured = ChessBoard.moveOnBoard(m);
			if (!ChessBoard.getTeam(player).king.isInCheck()) {
				valid = true;
				value = -negamax(-player, maxDepth - 1, -beta, -alpha);
			}
			else {
				value = ChessBoard.state.evaluate(player);
			}
			ChessBoard.undoMove(m, captured);
			p = ChessBoard.getPiece(m.x0, m.y0);

			if (isPawn && !(p instanceof Pawn)) {
				ChessBoard.getTeam(p.color).remove(p);
				p = new Pawn(p.color, m.x0, m.y0);
				ChessBoard.getTeam(p.color).add(p);
				ChessBoard.getTeam(p.color).sort();
				ChessBoard.state.board[m.y0][m.x0] = p;
			}
			if (p instanceof Pawn) {
				((Pawn)p).hasDoubled = hasMoved;
			} else if(p instanceof Rook){
				((Rook)p).hasMoved = hasMoved;
			} else if(p instanceof King){
				((King)p).hasMoved = hasMoved;
			}
			if(!valid) {
				continue;
			}

			if (value > bestMove.value) {
				bestMove = m;
				bestMove.value = value;
			}
			alpha = Math.max(alpha, bestMove.value);
			if (alpha >= beta) {
				break;
			}
		}
		return bestMove;
	}
	/**
	 * Aplica urmatorul nivel de negamax cu alpha-beta pruning
	 */
	public static float negamax(int player, int depth, float alpha, float beta) {
		if (depth == 0 || ChessBoard.deadKing())
			return ChessBoard.state.evaluate(player);
		MoveList moves = new MoveList(ChessBoard.getTeam(player));
		float best = -CHECK_MATE - 1;
		for (Move m : moves) {
			Piece p = ChessBoard.getPiece(m.x0, m.y0);

			boolean isPawn = false;
			boolean hasMoved = false;
			if (p instanceof Pawn) {
				isPawn = true;
				hasMoved = ((Pawn)p).hasDoubled;
			} else if(p instanceof Rook){
				hasMoved = ((Rook)p).hasMoved;
			} else if(p instanceof King){
				hasMoved = ((King)p).hasMoved;
			}

			float value = 0;
			Piece captured = ChessBoard.moveOnBoard(m);
			if (!ChessBoard.getTeam(player).king.isInCheck()) {
				value = -negamax(-player, depth - 1, -beta, -alpha);
			}
			else {
				value = ChessBoard.state.evaluate(player);
			}

			ChessBoard.undoMove(m, captured);
			p = ChessBoard.getPiece(m.x0, m.y0);
			// daca pionul a promovat, il retrogradam in stare inintiala
			if (isPawn && !(p instanceof Pawn)) {
				ChessBoard.getTeam(p.color).remove(p);
				p = new Pawn(p.color, m.x0, m.y0);
				ChessBoard.getTeam(p.color).add(p);
				ChessBoard.getTeam(p.color).sort();
				ChessBoard.state.board[m.y0][m.x0] = p;
			}
			if (p instanceof Pawn) {
				((Pawn)p).hasDoubled = hasMoved;
			} else if(p instanceof Rook){
				((Rook)p).hasMoved = hasMoved;
			} else if(p instanceof King){
				((King)p).hasMoved = hasMoved;
			}

			if (value > best) {
				best = value;
			}
			alpha = Math.max(alpha, best);
			if (alpha >= beta) {
				break;
			}
		}
		return best;
	}
}
