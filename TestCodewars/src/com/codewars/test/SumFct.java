package com.codewars.test;

import java.math.BigInteger;

//Perimeter of squares in a rectangle
//5 Kyu
public class SumFct {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Result: " + perimeter(new BigInteger("5")).intValue());
	}

	public static BigInteger perimeter(BigInteger n) {
		Integer newN = n.intValue() + 1; // add 0 and 1 from n+1
		BigInteger res = getFibonacciSum(newN);
		System.out.println("Valor sumFib: " + res.intValue());
		return res.multiply(new BigInteger("4"));
	}

	private static BigInteger getFibonacciSum(long n) {
		BigInteger a = new BigInteger("0"), b = new BigInteger("1"), c = new BigInteger("0"), sum = new BigInteger("0");

		for (int i = 0; i < n - 1; i++) {
			c = a.add(b);
			a = b;
			sum = sum.add(b);
			b = c;
		}
		sum = sum.add(c);
		return sum;
	}

}
