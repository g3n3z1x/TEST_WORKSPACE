package com.codewars.test;

import java.util.Arrays;

//Your order, please
//6 kyu
public class Order {

	public static void main(String[] args) {

		String word = "is2 Thi1s T4est 3a";
		System.out.println(order(word));

	}

	public static String order(String words) {
		String result = "";
		if(words.isEmpty()) {
			return result;
		}
		String[] array = words.split(" ");
		int number = Integer.parseInt(new StringBuilder(extractDigits(words)).reverse().toString());
		int k = 0;
		Word[] wordArray = new Word[array.length];
		while (number > 0) {
			wordArray[k] = new Word(number % 10, array[k]);
			number = number / 10;
			System.out.println(wordArray[k].toString());
			k++;
		}
		Arrays.sort(wordArray);

		for (int i = 0; i < wordArray.length; i++) {
			System.out.println(wordArray[i]);
			result += wordArray[i].getWord() + " ";
		}

		return result.trim();
	}

	public static String extractDigits(String src) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (Character.isDigit(c)) {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	public static class Word implements Comparable<Word> {
		private int number;
		private String word;

		public Word(int number, String word) {
			super();
			this.number = number;
			this.word = word;
		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public String getWord() {
			return word;
		}

		public void setWord(String word) {
			this.word = word;
		}

		@Override
		public String toString() {
			return "Word [number=" + number + ", word=" + word + "]";
		}

		@Override
		public int compareTo(Word arg0) {
			// TODO Auto-generated method stub
			return Integer.compare(number, arg0.getNumber());
		}

	}

}
