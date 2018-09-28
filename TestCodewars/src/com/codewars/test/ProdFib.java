package com.codewars.test;

import java.util.ArrayList;
import java.util.List;

public class ProdFib {

	public static void main(String[] args) {
		long prod = 4895;
		productFib(prod);
	}

	private static long[] fibonaccis = { 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584,
			4181, 6765, 10946, 17711, 28657, 46368 }; // 25 first fibonacci

	public static long[] productFib(long prod) {
		long[] result = { 0, 0, 0 };
		List<Long> fibProd = new ArrayList<Long>();

		// 24 first products of fibonacci
		for (int i = 0; i < fibonaccis.length - 1; i++) {
			long num = fibonaccis[i] * fibonaccis[i + 1];
			fibProd.add(num);
		}

		// the product exists and is contained in first 25 fibs
		if (fibProd.contains(prod)) {
			// by array
			int index = fibProd.indexOf(prod);
			result[0] = fibonaccis[index];
			result[1] = fibonaccis[index + 1];
			result[2] = 1;
		}
		// the product does not exist and answer is contained in first 25 fibs
		else if (prod < fibProd.get(23)) {
			// find in array
			for (int i = 0; i < fibProd.size(); i++) {
				if (fibProd.get(i) > prod) {
					result[0] = fibonaccis[i];
					result[1] = fibonaccis[i + 1];
					result[2] = 0;
					break;
				}
			}
		}
		// the product may/may not exist and is larger than 25 fib
		else if (prod >= fibProd.get(23)) {
			// by calculating fibs
			// start with 26th fib
			long fib1 = fibonaccis[23];
			long fib2 = fibonaccis[24];
			long curFib = 0;
			long product = 0;
			while (prod > product) {
				curFib = fib1 + fib2;
				product = fib2 * curFib;
				result[0] = fib2;
				result[1] = curFib;
				if (prod == product) {
					result[2] = 1;
					break;
				} else if (prod < product) {
					result[2] = 0;
					break;
				}
				fib1 = fib2;
				fib2 = curFib;
			}
		}

		System.out.println("{" + result[0] + ", " + result[1] + ", " + result[2] + "}");
		return result;
	}

}
