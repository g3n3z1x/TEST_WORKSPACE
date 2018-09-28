package com.codewars.test;

//Decode the Morse Code
//6 kyu
public class MorseCodeDecoder {

	public static void main(String[] args) {
		String morseCode = ".... . -.--   .--- ..- -.. .";
		decode(morseCode);
	}

	public static String decode(String morseCode) {
		// your brilliant code here, remember that you can access the preloaded Morse
		// code table through MorseCode.get(code)
		String decodedPhrase = "";
		if(morseCode.trim().length() <= 1) {
			return MorseCode.get(morseCode);
		}
		String[] wordsArray = morseCode.trim().split("   ");

		for (int i = 0; i < wordsArray.length; i++) {
			String[] lettersArray = (wordsArray[i].trim()).split(" ");
			for (int j = 0; j < lettersArray.length; j++) {
				System.out.println(lettersArray[j]);
				String decodedWord = MorseCode.get(lettersArray[j]);
				decodedPhrase += decodedWord;
			}
			decodedPhrase += " ";
		}
		return decodedPhrase.trim();
	}

}
