package utilities;

import datasystem.*;

public class Boards {
	public static void stalemateSituation () {
		ChessBoard.state.board[6][1] = new Pawn(1, 1, 6);
		ChessBoard.state.board[7][1] = new King(-1, 1, 7);

		ChessBoard.state.board[5][0] = new King(1, 0, 5);

		ChessBoard.state.blackTeam.add(ChessBoard.state.board[7][1]);
		ChessBoard.state.whiteTeam.add(ChessBoard.state.board[6][1]);
		ChessBoard.state.whiteTeam.add(ChessBoard.state.board[5][0]);
	}

	public static void checkmateSituation () {
		ChessBoard.state.board[7][0] = new Rook(1, 0, 7);
		ChessBoard.state.board[6][0] = new Rook(1, 0, 6);
		ChessBoard.state.board[7][7] = new King(-1, 7, 7);

		ChessBoard.state.board[0][0] = new King(1, 0, 0);

		ChessBoard.state.whiteTeam.add(ChessBoard.state.board[7][0]);
		ChessBoard.state.whiteTeam.add(ChessBoard.state.board[6][0]);
		ChessBoard.state.whiteTeam.add(ChessBoard.state.board[0][0]);
		ChessBoard.state.blackTeam.add(ChessBoard.state.board[7][7]);
	}

	public static void castleSituation(){
		ChessBoard.state.board[7][0] = new Rook(-1, 0, 7);
		ChessBoard.state.board[0][0] = new Rook(1, 0, 0);
		ChessBoard.state.board[0][1] = new Rook(1, 1, 0);


		ChessBoard.state.board[7][4] = new King(-1, 4, 7);
		ChessBoard.state.board[0][4] = new King(1, 4, 0);

		ChessBoard.state.whiteTeam.add(ChessBoard.state.board[0][0]);
		ChessBoard.state.whiteTeam.add(ChessBoard.state.board[0][1]);
		ChessBoard.state.whiteTeam.add(ChessBoard.state.board[0][4]);

		ChessBoard.state.blackTeam.add(ChessBoard.state.board[7][4]);
		ChessBoard.state.blackTeam.add(ChessBoard.state.board[7][0]);
	}

	public void enPassant() {
//		ChessBoard.
	}
}
