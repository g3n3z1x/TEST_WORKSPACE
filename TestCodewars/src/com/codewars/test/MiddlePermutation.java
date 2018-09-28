package com.codewars.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

//Simple fun #159: Middle Permutation
//4 kyu
public class MiddlePermutation {

	public static void main(String[] args) {
		String s = "abcdxgz";
		// System.out.println(findMidPerm(s));
		middlePermutation(s);
	}

	public static String findMidPerm(String strng) {
		if (strng.length() < 2 || strng.length() > 26) {
			return "";
		}
		String result = "";
		SortedSet<String> sortedSet = new TreeSet<String>(permutationFinder(strng));
		List<String> list = new ArrayList<String>(sortedSet);
		if (list.size() % 2 == 0) {
			result = list.get(list.size() / 2 - 1);
		} else {
			result = list.get(list.size() / 2);
		}
		return result;
	}

	// calcula todas las permutaciones...no es eficiente
	public static Set<String> permutationFinder(String str) {
		Set<String> perm = new HashSet<String>();
		// Handling error scenarios
		if (str == null) {
			return null;
		} else if (str.length() == 0) {
			perm.add("");
			return perm;
		}
		char initial = str.charAt(0); // first character
		String rem = str.substring(1); // Full string without first character
		Set<String> words = permutationFinder(rem);
		for (String strNew : words) {
			for (int i = 0; i <= strNew.length(); i++) {
				perm.add(charInsert(strNew, initial, i));
			}
		}
		return perm;
	}

	public static String charInsert(String str, char c, int j) {
		String begin = str.substring(0, j);
		String end = str.substring(j);
		return begin + c + end;
	}

	// otra forma mas eficiente?
	// Solucion Aceptada!!
	public static String middlePermutation(String strng) {
		if (strng.length() < 2 || strng.length() > 26) {
			return "";
		}
		char[] ar = strng.toCharArray();
		Arrays.sort(ar);
		String sorted = String.valueOf(ar);
		int middle = 0;
		String remainder = "";
		String result = "";

		if (sorted.length() % 2 == 0) {
			middle = sorted.length() / 2 - 1;
			remainder = sorted.substring(0, middle) + sorted.substring(middle + 1, sorted.length());
			result = sorted.charAt(middle) + new StringBuilder(remainder).reverse().toString();
		} else {
			middle = sorted.length() / 2;
			remainder = sorted.substring(0, middle) + sorted.substring(middle + 1, sorted.length());
			result = sorted.charAt(middle) + middlePermutation(remainder);
		}
		System.out.println(result);

		return result;
	}

}
