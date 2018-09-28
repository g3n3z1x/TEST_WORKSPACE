package com.codewars.test;

public class MultiplesOf3And5 {

	public static void main(String[] args) {
		solution(1000);
	}

	public static int solution(int number) {
		int result = 0;

		// we use sum of n first numbers property and multiply it by each multiple to
		// find its sum
		// ksum = n * (n+1) / 2

		int n = number - 1;
		int n3 = n / 3;
		int n5 = n / 5;
		int n15 = n / 15;
		System.out.println(n3 + " " + n5 + " " + n15);

		result = (3 * n3 * (n3 + 1) / 2) + (5 * n5 * (n5 + 1) / 2) - (15 * n15 * (n15 + 1) / 2);
		System.out.println(result);
		return result;
	}

}
