package com.codewars.test.go.game;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Board {

	private char[][] board;
	private static Integer height;
	private static Integer width;
	private ArrayDeque<char[][]> history;

	public Board(int sizeY, int sizeX) {
		System.out.println("sizeY: " + sizeY + " / sizeX:" + sizeX);
		if (sizeY <= 0 || sizeX <= 0 || sizeY > 31 || sizeX > 31) {
			throw new IllegalArgumentException("Incorrect size for board creation.");
		}
		board = new char[sizeY][sizeX];
		setHeight(sizeY);
		setWidth(sizeX);
		initialize();
	}

	public void printBoard() {
		System.out.println("=====================================");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("=====================================");
	}

	public void printState(char[][] state) {
		System.out.println("=====================================");
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				System.out.print(state[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("=====================================");
	}

	public void move(String coordinate, boolean isBlackTurn) throws IllegalArgumentException {
		System.out.println("new move: " + coordinate + " / isBlackTurn: " + isBlackTurn);
		// find position and stone
		char stone;
		if (isBlackTurn) {
			stone = Stone.getBlack();
		} else {
			stone = Stone.getWhite();
		}
		int y = getY(coordinate);
		int x = getX(coordinate);
		System.out.println("y: " + y + " / x: " + x);
		if (y >= this.getHeight() || x >= this.getWidth()) {
			throw new IllegalArgumentException("Cannot move to position out of board.");
		}
		// validations
		// check if position is not occupied
		if (board[y][x] == Stone.getEmpty()) {
			board[y][x] = stone;
		} else {
			throw new IllegalArgumentException("Cannot place stone in an occupied position.");
		}
		// find group for positioned stone
		char[][] mask = new char[this.getHeight()][this.getWidth()];
		initializeState(mask);
		findGroup(x, y, stone, mask);
		System.out.println("mask");
		printState(mask);

		// check for liberties
		// group is removed if enemy liberties = 0

		// 1. for surrounding enemy stones/groups, then remove captured stones from board
		char[][] neighborEnemyStones = getNeighboringEnemyStones(stone, mask);
		// System.out.println("neighborEnemyStones");
		// printState(neighborEnemyStones);
		findAndCaptureStones(stone, neighborEnemyStones);

		System.out.println("Liberties for placed stone: " + getLiberties(stone, mask));
		// 2. for self group (1 or more connected stones in mask) to avoid suicide
		if (getLiberties(stone, mask) == 0) {
			// this is suicide ;_;
			// board = history.getLast().clone();
			deepCopyState(history.getLast(), board);
			throw new IllegalArgumentException("Suicide is not allowed. Please try placing your stone in a new position.");
		}

		// check for simple KO rule
		System.out.println("board:");
		printState(board);
		System.out.println("ko check (2 history ago)");
		if (history.size() >= 2) {
			printState(getKOState());
		}
		if (history.size() >= 2 && Arrays.deepEquals(board, getKOState())) {
			// return board to last state
			// board = history.getLast().clone();
			deepCopyState(history.getLast(), board);
			throw new IllegalArgumentException("KO state exception. Please try placing your stone in a new position.");
		}

		// if all validations ok then make history entry (commit the move)
		history.add(copyBoard());
		System.out.println("history.getLast() at turn END");
		printState(history.getLast());
	}

	// this is the most reliable way to get a copy of 2D arrays
	private void deepCopyState(char[][] source, char[][] destination) {
		for (int i = 0; i < source.length; i++) {
			for (int j = 0; j < source[0].length; j++) {
				destination[i][j] = source[i][j];
			}
		}
	}

	private void findAndCaptureStones(char stone, char[][] neighborEnemyStones) {
		char enemyStone;
		if (stone == Stone.getBlack()) {
			enemyStone = Stone.getWhite();
		} else {
			enemyStone = Stone.getBlack();
		}
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				if (neighborEnemyStones[i][j] == enemyStone) {
					// find the group for this stone
					char[][] mask = new char[this.getHeight()][this.getWidth()];
					initializeState(mask);
					findGroup(j, i, enemyStone, mask);
					if (getLiberties(enemyStone, mask) == 0) {
						// no liberties, remove stones from board
						removeCapturedStones(enemyStone, mask);
					}
				}
			}
		}
	}

	private void removeCapturedStones(char enemyStone, char[][] mask) {
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				if (board[i][j] == mask[i][j]) {
					board[i][j] = Stone.getEmpty();
				}
			}
		}
	}

	public int getLiberties(char stone, char[][] mask) {
		int liberties = 0;
		char[][] libertiesMask = new char[this.getHeight()][this.getWidth()];
		char liberty = Stone.getLiberty();

		// i,j coordinates of stone to check liberties
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				if (mask[i][j] == stone) {
					// count liberties of neighboring positions if not visited
					if (i + 1 < this.getHeight() && board[i + 1][j] == Stone.getEmpty() && !(libertiesMask[i + 1][j] == liberty)) {
						liberties++;
						libertiesMask[i][j] = liberty;
					}
					if (i - 1 >= 0 && board[i - 1][j] == Stone.getEmpty() && !(libertiesMask[i - 1][j] == liberty)) {
						liberties++;
						libertiesMask[i][j] = liberty;
					}
					if (j + 1 < this.getWidth() && board[i][j + 1] == Stone.getEmpty() && !(libertiesMask[i][j + 1] == liberty)) {
						liberties++;
						libertiesMask[i][j] = liberty;
					}
					if (j - 1 >= 0 && board[i][j - 1] == Stone.getEmpty() && !(libertiesMask[i][j - 1] == liberty)) {
						liberties++;
						libertiesMask[i][j] = liberty;
					}
				}
			}
		}
		return liberties;
	}

	public char[][] getNeighboringEnemyStones(char stone, char[][] mask) {
		char[][] neighborEnemyStones = new char[this.getHeight()][this.getWidth()];
		char enemyStone;
		if (stone == Stone.getBlack()) {
			enemyStone = Stone.getWhite();
		} else {
			enemyStone = Stone.getBlack();
		}
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				if (mask[i][j] == stone) {
					// save enemy stones position
					if (i + 1 < this.getHeight() && board[i + 1][j] == enemyStone && !(neighborEnemyStones[i + 1][j] == enemyStone)) {
						neighborEnemyStones[i + 1][j] = enemyStone;
					}
					if (i - 1 >= 0 && board[i - 1][j] == enemyStone && !(neighborEnemyStones[i - 1][j] == enemyStone)) {
						neighborEnemyStones[i - 1][j] = enemyStone;
					}
					if (j + 1 < this.getWidth() && board[i][j + 1] == enemyStone && !(neighborEnemyStones[i][j + 1] == enemyStone)) {
						neighborEnemyStones[i][j + 1] = enemyStone;
					}
					if (j - 1 >= 0 && board[i][j - 1] == enemyStone && !(neighborEnemyStones[i][j - 1] == enemyStone)) {
						neighborEnemyStones[i][j - 1] = enemyStone;
					}
				}
			}
		}
		return neighborEnemyStones;
	}

	public char[][] copyBoard() {
		char[][] tempBoard = new char[this.getHeight()][this.getWidth()];
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				tempBoard[i][j] = this.board[i][j];
			}
		}
		return tempBoard;
	}

	private char[][] getKOState() {
		// Deque provides better implementation
		ArrayDeque<char[][]> tempHistory = new ArrayDeque<char[][]>();

		/* TODO: .clone() esta bugeado, usar otro metodo para obtener el item size-2 de history */
		tempHistory = history.clone();

		// get previous board on player last turn
		tempHistory.removeLast();
		// System.out.println("KO State CHECK (2 turns before)");
		// printState(tempHistory.getLast());
		return tempHistory.getLast();
	}

	private int getX(String coordinate) {
		Character c = coordinate.substring(coordinate.length() - 1, coordinate.length()).toCharArray()[0];
		String regex = "[A-Z]";
		Pattern p = Pattern.compile(regex);
		boolean isCapitalLetter = p.matcher(c.toString()).matches();
		if (isCapitalLetter) {
			int charInt = (int) c >= 73 ? (int) c - 1 : (int) c;
			int x = charInt - 65;
			if (x < getWidth()) {
				return x;
			} else {
				throw new IllegalArgumentException("Position x cannot be larger than board width.");
			}
		} else {
			throw new IllegalArgumentException("Coordinate x incorrect.");
		}
	}

	private int getY(String coordinate) {
		int y = Integer.parseInt(coordinate.substring(0, coordinate.length() - 1));
		if (y <= getHeight()) {
			y = getHeight() - y;
			return y;
		} else {
			throw new IllegalArgumentException("Position y cannot be larger than board height.");
		}
	}

	public void initialize() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = Stone.getEmpty();
			}
		}
		history = new ArrayDeque<char[][]>();
		history.add(copyBoard());
	}

	public char[][] initializeState(char[][] state) {
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				state[i][j] = Stone.getEmpty();
			}
		}
		return state;
	}

	public void rollback(int turns) {
		if (history.size() > turns - 1) {
			for (int i = 0; i < turns; i++) {
				history.removeLast();
			}
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					board[i][j] = history.getLast()[i][j];
				}
			}
		}
	}

	public void recordHistory() {
		history.add(copyBoard());
	}

	// handicaps are always black stones
	public void placeHandicapStones(int size, int handicapStones) {
		String[] coords = null;
		switch (size) {
		case 9:
			if (handicapStones <= Handicaps.handicap9.getArray().length) {
				coords = Handicaps.handicap9.getArray();
			} else {
				throw new IllegalArgumentException("Incorrect number of handicap stones for board size: " + size);
			}
			break;
		case 13:
			if (handicapStones <= Handicaps.handicap13.getArray().length) {
				coords = Handicaps.handicap13.getArray();
			} else {
				throw new IllegalArgumentException("Incorrect number of handicap stones for board size: " + size);
			}
			break;
		case 19:
			if (handicapStones <= Handicaps.handicap19.getArray().length) {
				coords = Handicaps.handicap19.getArray();
			} else {
				throw new IllegalArgumentException("Incorrect number of handicap stones for board size: " + size);
			}
			break;
		default:
			throw new IllegalArgumentException("Incorrect size of board for handicap.");
		}
		// set handicap stones in each position
		for (int i = 0; i < handicapStones; i++) {
			String coord = coords[i];
			int x = getX(coord);
			int y = getY(coord);
			board[y][x] = Stone.getBlack();
		}
		history.add(copyBoard());
	}

	public void findGroup(int x, int y, char color, char[][] mask) {
		if (x > this.getWidth() - 1 || x < 0 || y > this.getHeight() - 1 || y < 0) {
			return;
		}
		// if this square is the colour expected and has not been visited before
		if (board[y][x] == color && mask[y][x] == Stone.getEmpty()) {
			// save this group member
			mask[y][x] = board[y][x];
			// look at the neighbours
			findGroup(x + 1, y, color, mask);
			findGroup(x - 1, y, color, mask);
			findGroup(x, y + 1, color, mask);
			findGroup(x, y - 1, color, mask);
		}
	}

	private enum Handicaps {
		handicap9(new String[] { "7G", "3C", "3G", "7C", "5E" }), handicap13(new String[] { "10K", "4D", "4K", "10D", "7G", "7D", "7K", "10G", "4G" }), // D G K
																																						// - 4 7
																																						// 10
		handicap19(new String[] { "16Q", "4D", "4Q", "16D", "10K", "10D", "10Q", "16K", "4K", }); // D K Q - 4 10 16
		private final String[] array;

		Handicaps(String[] array) {
			this.array = array;
		}

		public String[] getArray() {
			return this.array;
		}
	}

	public char getPosition(String coord) {
		int x = getX(coord);
		int y = getY(coord);
		return board[y][x];
	}

	// G&S
	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public Integer getHeight() {
		return height;
	}

	public static void setHeight(Integer height) {
		Board.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public static void setWidth(Integer width) {
		Board.width = width;
	}

	public ArrayDeque<char[][]> getHistory() {
		return history;
	}

	public void setHistory(ArrayDeque<char[][]> history) {
		this.history = history;
	}

}
