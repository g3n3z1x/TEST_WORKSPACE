package com.codewars.test;

import java.util.ArrayList;
import java.util.List;

//Calculator
//3 kyu
public class Calculator {

	public static void main(String[] args) {
		// String expression = "2 / 2 + 3 * 4 - 6";
		// String expression = "127";
		// String expression = "2 + 3";
		// String expression = "2 - 3 - 4";
		// String expression = "10 * 5 / 5";
		// String expression = "2 / 2 + 3 * 4";
		String expression = "7.7 - 3.3 - 4.4";

		System.out.println("\nFINAL RESULT: " + evaluate(expression));
	}

	private static final Character add = '+';
	private static final Character substract = '-';
	private static final Character multiply = '*';
	private static final Character divide = '/';

	public static Double evaluate(String expression) {
		// get rid of spaces
		expression = expression.replaceAll(" ", "");
		System.out.println(expression);
		if (expression.equals("")) {
			// empty string
			return -1.0D;
		}
		// save in a list the positions of each operator
		List<OperatorIndex> operatorList = new ArrayList<OperatorIndex>();

		return Double.valueOf(processOperation(operatorList, expression));
	}

	// recursive method
	private static String processOperation(List<OperatorIndex> operatorList, String expression) {
		// update the list of operators
		updateOperatorList(operatorList, expression);

		// iterate in mathematical order and get an operator
		OperatorIndex operator = null;
		int listIndex = 0;
		for (OperatorIndex oi : operatorList) {
			if (oi.getOperator().equals(multiply) || oi.getOperator().equals(divide)) {
				operator = oi;
				break;
			}
			listIndex++;
		}
		if (operator == null) {
			listIndex = 0;
			for (OperatorIndex oi : operatorList) {
				if (oi.getOperator().equals(add) || oi.getOperator().equals(substract)) {
					operator = oi;
					break;
				}
				listIndex++;
			}
		}
		if (operator == null) {
			// contains no operators, just a number (final result)
			return expression;
		}
		System.out.println(operator.toString());
		System.out.println("listIndex " + listIndex);

		// obtain the involved numbers (in this point operatorList.size() >= 3)
		OperatorIndex previousOperator = operatorList.get(listIndex - 1);
		OperatorIndex nextOperator = operatorList.get(listIndex + 1);
		int operationStartIndex = previousOperator.getIndex() + 1;
		int operationEndIndex = nextOperator.getIndex();
		Double n1 = Double.valueOf(expression.substring(operationStartIndex, operator.getIndex()));
		Double n2 = Double.valueOf(expression.substring(operator.getIndex() + 1, operationEndIndex));
		System.out.println("n1: " + n1 + " / n2: " + n2);

		// operate
		Double result = 0D;
		switch (operator.getOperator()) {
		case '/':
			// divide
			result = n1 / n2;
			break;
		case '*':
			// multiply
			result = n1 * n2;
			break;
		case '+':
			// add
			result = n1 + n2;
			break;
		case '-':
			// substract
			result = n1 - n2;
			break;
		default:
			break;
		}
		System.out.println("result: " + result);

		// set the result at the position, generate new expression
		StringBuffer sb = new StringBuffer(expression);
		sb.replace(operationStartIndex, operationEndIndex, result.toString());
		System.out.println(sb);

		return processOperation(operatorList, sb.toString());
	}

	private static void updateOperatorList(List<OperatorIndex> operatorList, String expression) {
		operatorList.clear();
		// signals start
		operatorList.add(new OperatorIndex(-1, '_'));
		// get all operators with index
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			if (c == add || (c == substract && i != 0) || c == multiply || c == divide) {
				OperatorIndex oi = new OperatorIndex(i, expression.charAt(i));
				operatorList.add(oi);
			}
		}
		// signals end
		operatorList.add(new OperatorIndex(expression.length(), '_'));
	}

	public static class OperatorIndex {
		private int index;
		private Character operator;

		public OperatorIndex(int index, Character operator) {
			super();
			this.index = index;
			this.operator = operator;
		}

		public OperatorIndex() {

		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public Character getOperator() {
			return operator;
		}

		public void setOperator(Character operator) {
			this.operator = operator;
		}

		@Override
		public String toString() {
			return "OperatorIndex [index=" + index + ", operator=" + operator + "]";
		}

	}

}
