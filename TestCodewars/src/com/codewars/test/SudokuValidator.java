package com.codewars.test;

//Sudoku Solution Validator
//4 kyu
public class SudokuValidator {

	public static void main(String[] args) {
		// valid
		int[][] sudoku = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 }, { 6, 7, 2, 1, 9, 5, 3, 4, 8 }, { 1, 9, 8, 3, 4, 2, 5, 6, 7 }, { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
				{ 4, 2, 6, 8, 5, 3, 7, 9, 1 }, { 7, 1, 3, 9, 2, 4, 8, 5, 6 }, { 9, 6, 1, 5, 3, 7, 2, 8, 4 }, { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
				{ 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
		check(sudoku);
		// invalid
		sudoku[4][4] = 0;
		check(sudoku);
	}

	public static boolean check(int[][] sudoku) {
		printSudoku(sudoku);
		// check if sudoku contains 1+ zeroes = invalid
		System.out.println(checkZeroes(sudoku));

		// check that every row, column and square 3x3 contains every digit 1-9 only once

		return checkZeroes(sudoku);
	}

	private static boolean checkZeroes(int[][] sudoku) {
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[0].length; j++) {
				if (sudoku[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public static void printSudoku(int[][] sudoku) {
		System.out.println("-----------------");
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[0].length; j++) {
				System.out.print(sudoku[i][j] + " ");
			}
			System.out.println();
		}
	}

}
