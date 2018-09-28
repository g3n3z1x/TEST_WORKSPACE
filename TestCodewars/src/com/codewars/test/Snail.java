package com.codewars.test;

public class Snail {

	public static void main(String[] args) {
		// int[][] array = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		int[][] array = { {} };
		int[] snail = snail(array);
		for (int i = 0; i < snail.length; i++) {
			System.out.print(snail[i] + " ");
		}
	}

	public static int[] snail(int[][] array) {
		int n = array.length, m = array.length;
		// check if array is empty in both dimensions
		System.out.println("n: " + n);
		System.out.println("array[0]: " + array[0].length);
		if (array == null || n == 0 || (n == 1 && array[0].length == 0)) {
			return new int[] {};
		}
		int[] snail = new int[n * n];
		int snailPos = 0;

		int i, x = 0, y = 0;
		// x: row position
		// y: column position

		while (x < m && y < n) {
			int curVal = 0;
			// Print the first row from the remaining rows
			for (i = y; i < n; ++i) {
				curVal = array[x][i];
				snail[snailPos] = curVal;
				snailPos++;
			}
			x++;

			// Print the last column from the remaining columns
			for (i = x; i < m; ++i) {
				curVal = array[i][n - 1];
				snail[snailPos] = curVal;
				snailPos++;
			}
			n--;

			// Print the last row from the remaining rows */
			if (x < m) {
				for (i = n - 1; i >= y; --i) {
					curVal = array[m - 1][i];
					snail[snailPos] = curVal;
					snailPos++;
				}
				m--;
			}

			// Print the first column from the remaining columns */
			if (y < n) {
				for (i = m - 1; i >= x; --i) {
					curVal = array[i][y];
					snail[snailPos] = curVal;
					snailPos++;
				}
				y++;
			}
		}

		return snail;
	}

}
