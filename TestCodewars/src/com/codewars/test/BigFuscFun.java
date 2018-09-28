package com.codewars.test;

import java.math.BigInteger;

//5 kyu
//-> por completar
public class BigFuscFun {

	public static void main(String[] args) {
		// System.out.println(fusc(new BigInteger("1")));
		// System.out.println(fusc(new BigInteger("2")));
		// System.out.println(fusc(new BigInteger("3")));
		// System.out.println(fusc(new BigInteger("4")));
		// System.out.println(fusc(new BigInteger("5")));
		// System.out.println(fusc(new BigInteger("6")));
		System.out.println(closestFusc(new BigInteger("123456789012345678901234567890"), true));
	}

	public static BigInteger closestFusc(BigInteger n, boolean isEven) {
		BigInteger m = n;

		if (isEven) {
			System.out.println("even");
			while (!fusc(m).mod(new BigInteger("2")).equals(new BigInteger("0"))) {
				System.out.println("m " + m + " n " + n);
				m = m.add(BigInteger.ONE);
			}
		} else {
			while (!fusc(m).mod(new BigInteger("2")).equals(new BigInteger("1"))) {
				System.out.println("m " + m + " n " + n);
				m = m.add(BigInteger.ONE);
			}
		}
		return m;
	}

	public static BigInteger fusc(BigInteger n) {
		if (n.equals(new BigInteger("1"))) {
			// case "1"
			return BigInteger.ONE;
		}
		if (n.mod(new BigInteger("2")).equals(BigInteger.ZERO)) {
			// is even
			return fusc(n.divide(BigInteger.valueOf(2)));
		} else {
			// is odd
			BigInteger newN = (n.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
			return fusc(newN).add(fusc(newN.add(BigInteger.ONE)));
		}
	}

}
