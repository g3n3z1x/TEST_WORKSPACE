package com.codewars.test;

//Persistent buggers
//6 kyu
public class Persist {

	public static void main(String[] args) {
		persistence(39);
	}

	private static int numberOfTimes = 0;

	public static int persistence(long n) {
		System.out.println("n=" + n);
		numberOfTimes = 0;
		multiplyUntilOneDigit(n);
		return numberOfTimes;
	}

	public static long multiplyUntilOneDigit(long n) {
		long number = 1;
		String numString = String.valueOf(n);
		if (numString.length() == 1) {
			return n;
		} else {
			for (int i = 0; i < numString.toCharArray().length; i++) {
				number *= Integer.parseInt(numString.toCharArray()[i] + "");
			}
			numberOfTimes++;
			System.out.println("number=" + number + " / numberOfTimes=" + numberOfTimes);
			number = multiplyUntilOneDigit(number);
			return number;
		}
	}

}
