package com.codewars.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Binomial Expansion
//3 kyu
public class BinomialExpansion {

	public static void main(String[] args) {
		String expr = "(x+1)^5";
		expand(expr);
	}

	public static String expand(String expr) {
		System.out.println("Expression: " + expr);
		String result = "";
		int A = 0, X = 0, n = 0;
		String var = "";
		n = Integer.parseInt(expr.substring(expr.indexOf("^") + 1, expr.length()));
		// matching to find appearance of letter (variable)
		Pattern p = Pattern.compile("\\p{L}");
		Matcher m = p.matcher(expr);
		if (m.find()) {
			String xCoef = expr.substring(expr.indexOf("(") + 1, m.start());
			var = expr.substring(m.start(), m.start() + 1);
			if (xCoef.equals("")) {
				X = 1;
			} else if (xCoef.equals("-")) {
				X = -1;
			} else {
				X = Integer.parseInt(xCoef);
			}
		}
		A = Integer.parseInt(expr.substring(expr.indexOf(var) + 1, expr.indexOf(")")));

		System.out.println("A=" + A + " X=" + X + " n=" + n);

		// case n = 0
		if (n == 0) {
			return "1";
		}
		// case n = 1
		if (n == 1) {
			result = expr.substring(expr.indexOf("(") + 1, expr.indexOf(")"));
			System.out.println(result);
			return result;
		}
		// case A = 0
		if (A == 0) {
			String Xsquare = "";
			if (X != 1 || X != -1) {
				Xsquare = (int) Math.pow(X, n) + "";
				Xsquare += var;
			}
			System.out.println(Xsquare + "^" + n);
			return Xsquare + "^" + n;
		}

		// get the coefficient series
		String[] coefs = series(A, X, n).split(" ");
		for (int i = coefs.length - 1; i >= 0; i--) {
			//System.out.println("coefs[" + i + "] " + coefs[i]);
			String term = "";
			if (i == (coefs.length - 1)) {
				// first term of equation
				if (coefs[i].equals("1")) {
					term = var + "^" + i;
				} else if (coefs[i].equals("-1")) {
					term = "-" + var + "^" + i;
				} else {
					term = coefs[i] + var + "^" + i;
				}
				//System.out.println("first term: " + term);
			} else if (i == 1) {
				// second to last term
				if (Long.parseLong(coefs[i]) >= 0) {
					term += "+";
				}
				term += coefs[i] + var;
			} else if (i == 0) {
				// last term
				if (Long.parseLong(coefs[i]) >= 0) {
					term += "+";
				}
				term += coefs[i];
			} else {
				// rest of terms
				if (coefs[i].equals("1")) {
					term = "+" + coefs[i] + var + "^" + i;
				} else if (coefs[i].equals("-1")) {
					term = "-" + coefs[i] + var + "^" + i;
				} else {
					if (Long.parseLong(coefs[i]) >= 0) {
						term += "+";
					}
					term += coefs[i] + var + "^" + i;
				}
				//System.out.println("term: " + term);
			}
			result += term;
		}
		System.out.println("Final result: " + result);
		return result;

	}

	// print the coefficient series
	static String series(int A, int X, int n) {
		String series = "";

		// first term
		long term = (long) Math.pow(A, n);
		series += term + " ";

		// remaining terms
		for (int i = 1; i <= n; i++) {
			// we use binomials property: nCi+1 = nCi*(n-i)/(i+1)
			term = term * X * (n - i + 1) / (i * A);
			series += term + " ";
		}
		System.out.println(series.trim());
		return series.trim();
	}

}
