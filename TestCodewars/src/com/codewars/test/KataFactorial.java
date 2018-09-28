package com.codewars.test;

import java.math.BigInteger;

//Large Factorials
//4 Kyu
public class KataFactorial {

	public static void main(String[] args) {

		Factorial(20);

	}

	public static String Factorial(int n) {
		if (n < 0)
			return null;
		BigInteger factorial = new BigInteger("1");
		for (int i = n; i > 1; i--) {
			factorial = factorial.multiply(BigInteger.valueOf(i));
			System.out.println("Value of Factorial: " + factorial);
		}
		System.out.println("RESULT: " + factorial);
		return factorial + "";
	}

}
