package com.codewars.test;

import java.math.BigInteger;

//The Millionth Fibonacci
//3 kyu
public class Fibonacci {

	public static void main(String[] args) {
		System.out.println(fib(BigInteger.valueOf(10000)));
	}

	public static BigInteger fib(BigInteger n) {
		BigInteger result[][] = { { BigInteger.ONE, BigInteger.ONE }, { BigInteger.ONE, BigInteger.ZERO } };

		boolean isNegative = false;

		switch (n.compareTo(BigInteger.ZERO)) {
		case -1:
			n = n.negate();
			isNegative = true;
			break;
		case 0:
			return BigInteger.ZERO;
		}

		power(result, n.subtract(BigInteger.ONE));

		if (isNegative)
			return n.mod(BigInteger.valueOf(2)).equals(BigInteger.ONE) ? result[0][0] : result[0][0].negate();
		return result[0][0];
	}

	private static void power(BigInteger array[][], BigInteger n) {
		if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE))
			return;

		BigInteger idArray[][] = { { BigInteger.ONE, BigInteger.ONE }, { BigInteger.ONE, BigInteger.ZERO } };

		power(array, n.divide(BigInteger.valueOf(2)));
		multiply(array, array);
		if (!n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
			multiply(array, idArray);
	}

	private static void multiply(BigInteger a1[][], BigInteger a2[][]) {
		BigInteger bi1 = (a1[0][0].multiply(a2[0][0])).add(a1[0][1].multiply(a2[1][0]));
		BigInteger bi2 = (a1[0][0].multiply(a2[0][1])).add(a1[0][1].multiply(a2[1][1]));
		BigInteger bi3 = (a1[1][0].multiply(a2[0][0])).add(a1[1][1].multiply(a2[1][0]));
		BigInteger bi4 = (a1[1][0].multiply(a2[0][1])).add(a1[1][1].multiply(a2[1][1]));
		a1[0][0] = bi1;
		a1[0][1] = bi2;
		a1[1][0] = bi3;
		a1[1][1] = bi4;
	}

}
