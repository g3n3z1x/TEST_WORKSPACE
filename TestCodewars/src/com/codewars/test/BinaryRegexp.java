package com.codewars.test;

import java.util.regex.Pattern;

public class BinaryRegexp {

	public static void main(String[] args) {
		String test = "000";
		boolean matches = BinaryRegexp.multipleOf3().matcher(test).matches();
		if (matches) {
			System.out.println("IS MULTIPLE OF 3");
		} else {
			System.out.println("IS NOT MULTIPLE OF 3");
		}
	}

	public static Pattern multipleOf3() {
		String regex = "(0*(1(01*0)*1)+)*0*";
		Pattern p = Pattern.compile(regex);
		return p;
	}

}
