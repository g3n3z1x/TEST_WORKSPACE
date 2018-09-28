package com.codewars.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// The Observed Pin
// 4 kyu
public class ObservedPin {

	public static void main(String[] args) {
		String observed = "12";
		getPINs(observed);
	}

	private static final String[] case1 = new String[] { "1", "2", "4" };
	private static final String[] case2 = new String[] { "2", "1", "5", "3" };
	private static final String[] case3 = new String[] { "3", "2", "6" };
	private static final String[] case4 = new String[] { "4", "1", "5", "7" };
	private static final String[] case5 = new String[] { "5", "2", "4", "6", "8" };
	private static final String[] case6 = new String[] { "6", "3", "5", "9" };
	private static final String[] case7 = new String[] { "7", "4", "8" };
	private static final String[] case8 = new String[] { "8", "5", "7", "9", "0" };
	private static final String[] case9 = new String[] { "9", "6", "8" };
	private static final String[] case0 = new String[] { "0", "8" };

	public static List<String> getPINs(String observed) {
		if (observed.length() < 1 || observed.length() > 8) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		List<String> preResult = new ArrayList<String>();
		List<List<String>> list = new ArrayList<List<String>>();
		int depth = list.size();
		generateLists(observed, list);
		generatePermutations(list, preResult, depth, observed);
		cleanLeading(observed, preResult, result);
		System.out.println(result.toString());
		return result;
	}

	private static void cleanLeading(String observed, List<String> preResult, List<String> result) {
		for (String s : preResult) {
			result.add(s.replaceFirst(observed, ""));
		}
	}

	public static void generatePermutations(List<List<String>> Lists, List<String> result, int depth, String current) {
		if (depth == Lists.size()) {
			result.add(current);
			return;
		}
		for (int i = 0; i < Lists.get(depth).size(); ++i) {
			generatePermutations(Lists, result, depth + 1, current + Lists.get(depth).get(i));
		}
	}

	private static void generateLists(String observed, List<List<String>> list) {
		// get all arrays needed according to each digit observed
		for (int i = 0; i < observed.length(); i++) {
			switch (observed.charAt(i)) {
			case '1':
				list.add(Arrays.asList(case1));
				break;
			case '2':
				list.add(Arrays.asList(case2));
				break;
			case '3':
				list.add(Arrays.asList(case3));
				break;
			case '4':
				list.add(Arrays.asList(case4));
				break;
			case '5':
				list.add(Arrays.asList(case5));
				break;
			case '6':
				list.add(Arrays.asList(case6));
				break;
			case '7':
				list.add(Arrays.asList(case7));
				break;
			case '8':
				list.add(Arrays.asList(case8));
				break;
			case '9':
				list.add(Arrays.asList(case9));
				break;
			case '0':
				list.add(Arrays.asList(case0));
				break;
			default:
				break;
			}
		}
	}

}
