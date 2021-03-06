package com.codewars.test;

import java.util.ArrayList;
import java.util.List;

public class Decompose {

	public static void main(String[] args) {
		long n = 2;
		System.out.println(decompose(n));

	}

	public static String decompose(long n) {
		List<Long> decomposeArray = decomposer(n, n * n);

		// no valid solution exists
		if (decomposeArray == null)
			return null;

		// remove the last element
		decomposeArray.remove(decomposeArray.size() - 1);
		List<String> result = new ArrayList<>();

		for (Long ele : decomposeArray) {
			result.add(String.valueOf(ele.longValue()));
		}

		return String.join(" ", result);
	}

	// Recursion to decompose n
	public static List<Long> decomposer(long n, long remain) {
		// basic case
		if (remain == 0) {
			List<Long> r = new ArrayList<Long>();
			r.add(n);
			return r;
		}

		// iterate all element less than n
		for (long i = n - 1; i > 0; i--) {
			if ((remain - i * i) >= 0) {
				List<Long> r = decomposer(i, (remain - i * i));

				// only get the answer
				if (r != null) {
					r.add(n);
					return r;
				}
			}
		}

		// no answer
		return null;
	}

}
