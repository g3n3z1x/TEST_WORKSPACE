package com.codewars.test.go.game;

public class Stone {

	private static final char empty = '.';
	private static final char white = 'o';
	private static final char black = 'x';
	private static final char liberty = 'l';

	public Stone() {
	};

	// G&S
	public static char getEmpty() {
		return empty;
	}

	public static char getWhite() {
		return white;
	}

	public static char getBlack() {
		return black;
	}

	public static char getLiberty() {
		return liberty;
	}

}
