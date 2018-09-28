package com.codewars.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PokerHand {

	String hand;
	String[] thisHand;

	PokerHand(String hand) {
		this.hand = hand;
		this.thisHand = hand.split(" ");
	}

	public static void main(String[] args) {
		// String playerHand = "2H 3H 4H 5H 6H";
		String playerHand = "AH 8S AS KC JH";
		// String opponentHand = "KS AS TS QS JS";
		// String opponentHand = "JS QH KS AH 2S";
		String opponentHand = "KD 4S KC 3H 8S";
		PokerHand player = new PokerHand(playerHand);
		PokerHand opponent = new PokerHand(opponentHand);
		player.compareWith(opponent);
	}

	public Result compareWith(PokerHand hand) {
		Result result = null;
		System.out.println("playerHand  : " + this.hand);
		System.out.println("opponentHand: " + hand.hand);

		String[] otherHand = hand.thisHand;

		// check which case is each hand
		// start with top complexity cases of texas holdem
		Hand thisHandObject = checkHandValue(this.thisHand);
		Hand otherHandObject = checkHandValue(otherHand);

		System.out.println(thisHandObject.toString());
		System.out.println(otherHandObject.toString());

		int playerHandValue = thisHandObject.getHandValue().getValue();
		int opponentHandValue = otherHandObject.getHandValue().getValue();

		// compare thisHand vs otherHand
		if (playerHandValue > opponentHandValue) {
			result = Result.WIN;
		} else if (playerHandValue < opponentHandValue) {
			result = Result.LOSS;
		} else {
			if (thisHandObject.getHandValue().getKey().equals(HandValue.PAIR.getKey())
					|| thisHandObject.getHandValue().getKey().equals(HandValue.TWO_PAIRS.getKey())) {
				// compare highest pair
				int playerHighestPair = thisHandObject.getHighestPair();
				int opponentHighestPair = otherHandObject.getHighestPair();
				if (playerHighestPair > opponentHighestPair) {
					result = Result.WIN;
				} else if (playerHighestPair < opponentHighestPair) {
					result = Result.LOSS;
				} else {
					// pairs restCards comparison in order high to low
					int[] playerRestCards = thisHandObject.getRestCards();
					int[] opponentRestCards = otherHandObject.getRestCards();
					for (int i = 0; i < playerRestCards.length; i++) {
						// compare
						if (playerRestCards[i] > opponentRestCards[i]) {
							result = Result.WIN;
							break;
						} else if (playerRestCards[i] < opponentRestCards[i]) {
							result = Result.LOSS;
							break;
						} else {
							result = Result.TIE;
							continue;
						}
					}
				}
			} else {
				// highest cards comparison
				int playerHandHighestCard = thisHandObject.getHighestCard();
				int oppponentHandHighestCard = otherHandObject.getHighestCard();
				if (playerHandHighestCard > oppponentHandHighestCard) {
					result = Result.WIN;
				} else if (playerHandHighestCard < oppponentHandHighestCard) {
					result = Result.LOSS;
				} else {
					// HIGHCARD case, check every card in order
					int[] playerCards = thisHandObject.getCards();
					int[] opponentCards = otherHandObject.getCards();
					for (int i = playerCards.length - 1; i >= 0; i--) {
						if (playerCards[i] > opponentCards[i]) {
							result = Result.WIN;
							break;
						} else if (playerCards[i] < opponentCards[i]) {
							result = Result.LOSS;
							break;
						} else {
							result = Result.TIE;
							continue;
						}
					}
				}
			}

		}
		System.out.println("RESULT: " + result);
		return result;
	}

	public static Hand checkHandValue(String[] hand) {
		HandValue handValue = null;
		String sameSuit = "";
		String highestInStraight = "";
		int highestCard = 0;
		Map<String, Integer> repeatedCards = new HashMap<String, Integer>();
		int[] restCards = new int[3];
		int highestPair = 0;

		// check if hand is all same suit
		int countSuit = 1;
		for (int i = 0; i < hand.length - 1; i++) {
			if (hand[i].substring(1, 2).equals(hand[i + 1].substring(1, 2))) {
				countSuit++;
			} else {
				break;
			}
		}
		if (countSuit == hand.length) {
			sameSuit = hand[0].substring(1, 2);
		}
		System.out.println("sameSuit: " + sameSuit);

		// check if hand can have an ordered sequence of 5 cards (straight)
		int[] values = new int[hand.length];
		for (int i = 0; i < hand.length; i++) {
			String cardValue = hand[i].substring(0, 1);
			if (cardValue.equals("T")) {
				cardValue = "10";
			} else if (cardValue.equals("J")) {
				cardValue = "11";
			} else if (cardValue.equals("Q")) {
				cardValue = "12";
			} else if (cardValue.equals("K")) {
				cardValue = "13";
			} else if (cardValue.equals("A")) {
				cardValue = "1";
			}
			values[i] = Integer.parseInt(cardValue);
		}
		Arrays.sort(values);
		for (int i = 0; i < values.length - 1; i++) {
			// validate sequence
			if ((values[i] + 1 != values[i + 1]) && (values[i] != 1)) {
				highestInStraight = "";
				break;
			} else {
				highestInStraight = values[i + 1] + "";
			}
		}
		if (values[0] == 1 && !highestInStraight.isEmpty()) {
			// has ace
			if (values[values.length - 1] != 13) {
				highestInStraight = "";
			} else {
				highestInStraight = "14";
			}
		}

		// check if hand has repeated cards
		for (int i = 0; i < values.length; i++) {
			String key = values[i] + "";
			if (repeatedCards.containsKey(key)) {
				int value = repeatedCards.get(key);
				repeatedCards.put(key, value + 1);
			} else {
				repeatedCards.put(key, 1);
			}
		}
		if (repeatedCards.size() == 5) {
			// no repeated cards
			repeatedCards.clear();
		} else {
			// repeated cards, can't be a straight
			highestInStraight = "";
		}
		System.out.println("highestInStraight: " + highestInStraight);

		// get highest card for TIE cases
		for (int i = 0; i < values.length; i++) {
			if (values[i] == 1) {
				highestCard = 14;
				break;
			} else if (values[i] > highestCard) {
				highestCard = values[i];
			}
		}
		System.out.println("highestCard: " + highestCard);

		// get highest pair for TIE in repeated cards cases
		for (Entry<String, Integer> entry : repeatedCards.entrySet()) {
			if (entry.getValue() > 1 && Integer.parseInt(entry.getKey()) == 1) {
				highestPair = 14;
			} else if (entry.getValue() > 1 && Integer.parseInt(entry.getKey()) > highestPair) {
				highestPair = Integer.parseInt(entry.getKey());
			}
		}
		System.out.println("highestPair: " + highestPair);

		// in case of pair/two_pairs get rest of cards for TIE cases
		int restCounter = 0;
		for (int i = values.length - 1; i >= 0; i--) {
			if (repeatedCards.get(values[i] + "") != null && repeatedCards.get(values[i] + "") == 1) {
				restCards[restCounter] = values[i];
				System.out.println("restCards[restCounter]: " + restCards[restCounter]);
				restCounter++;
			}
		}

		// determine the value of the hand in Texas Holdem Poker rules
		// Royal FLush: Straight flush from Ten to Ace
		if (!sameSuit.isEmpty() && !highestInStraight.isEmpty() && highestInStraight.equals("14")
				&& repeatedCards.isEmpty()) {
			handValue = HandValue.ROYAL_FLUSH;
		}
		// Straight flush: Straight of the same suit
		else if (!sameSuit.isEmpty() && !highestInStraight.isEmpty() && repeatedCards.isEmpty()) {
			handValue = HandValue.STRAIGHT_FLUSH;
		}
		// Four of a kind: Four cards of the same value
		else if (sameSuit.isEmpty() && highestInStraight.isEmpty() && repeatedCards.containsValue(4)) {
			handValue = HandValue.FOUR_OF_A_KIND;
		}
		// Full house: Combination of three of a kind and a pair
		else if (sameSuit.isEmpty() && highestInStraight.isEmpty() && repeatedCards.containsValue(3)
				&& repeatedCards.containsValue(2)) {
			handValue = HandValue.FULL_HOUSE;
		}
		// Flush: 5 cards of the same suit
		else if (!sameSuit.isEmpty() && highestInStraight.isEmpty() && repeatedCards.isEmpty()) {
			handValue = HandValue.FLUSH;
		}
		// Straight: Sequence of 5 cards in increasing value (Ace can precede 2 and
		// follow up King)
		else if (sameSuit.isEmpty() && !highestInStraight.isEmpty() && repeatedCards.isEmpty()) {
			handValue = HandValue.STRAIGHT;
		}
		// Three of a kind: Three cards with the same value
		else if (sameSuit.isEmpty() && highestInStraight.isEmpty() && repeatedCards.containsValue(3)) {
			handValue = HandValue.THREE_OF_A_KIND;
		}
		// Two pairs: Two times two cards with the same value
		else if (sameSuit.isEmpty() && highestInStraight.isEmpty() && repeatedCards.containsValue(2)
				&& repeatedCards.size() == 3) {
			handValue = HandValue.TWO_PAIRS;
		}
		// Pair: Two cards with the same value
		else if (sameSuit.isEmpty() && highestInStraight.isEmpty() && repeatedCards.containsValue(2)
				&& repeatedCards.size() == 4) {
			handValue = HandValue.PAIR;
		}
		// Highcard: Simple value of the card. Lowest: 2 - Highest: Ace
		else if (sameSuit.isEmpty() && highestInStraight.isEmpty() && repeatedCards.isEmpty()) {
			handValue = HandValue.HIGHCARD;
		}

		// create the handObject
		Hand handObject = new Hand(handValue, sameSuit, highestInStraight, highestCard, repeatedCards, values,
				restCards, highestPair);
		return handObject;
	}

	public enum Result {
		TIE, WIN, LOSS
	}

	public enum HandValue {
		ROYAL_FLUSH("ROYAL_FLUSH", 10), STRAIGHT_FLUSH("STRAIGHT_FLUSH", 9), FOUR_OF_A_KIND("FOUR_OF_A_KIND",
				8), FULL_HOUSE("FULL_HOUSE", 7), FLUSH("FLUSH", 6), STRAIGHT("STRAIGHT", 5), THREE_OF_A_KIND(
						"THREE_OF_A_KIND", 4), TWO_PAIRS("TWO_PAIRS", 3), PAIR("PAIR", 2), HIGHCARD("HIGHCARD", 1);
		private final String key;
		private final Integer value;

		HandValue(String key, Integer value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static class Hand {
		private HandValue handValue;
		private String sameSuit;
		private String highestInStraight;
		private int highestCard;
		private Map<String, Integer> repeatedCard;
		private int[] cards;
		private int[] restCards;
		private int highestPair;

		public Hand(HandValue handValue, String sameSuit, String highestInStraight, int highestCard,
				Map<String, Integer> repeatedCard, int[] cards, int[] restCards, int highestPair) {
			super();
			this.handValue = handValue;
			this.sameSuit = sameSuit;
			this.highestInStraight = highestInStraight;
			this.highestCard = highestCard;
			this.repeatedCard = repeatedCard;
			this.cards = cards;
			this.restCards = restCards;
			this.highestPair = highestPair;
		}

		public HandValue getHandValue() {
			return handValue;
		}

		public void setHandValue(HandValue handValue) {
			this.handValue = handValue;
		}

		public String getSameSuit() {
			return sameSuit;
		}

		public void setSameSuit(String sameSuit) {
			this.sameSuit = sameSuit;
		}

		public String getHighestInStraight() {
			return highestInStraight;
		}

		public void setHighestInStraight(String highestInStraight) {
			this.highestInStraight = highestInStraight;
		}

		public Map<String, Integer> getRepeatedCard() {
			return repeatedCard;
		}

		public void setRepeatedCard(Map<String, Integer> repeatedCard) {
			this.repeatedCard = repeatedCard;
		}

		@Override
		public String toString() {
			return "Hand [handValue=" + handValue + ", sameSuit=" + sameSuit + ", highestInStraight="
					+ highestInStraight + ", repeatedCard=" + repeatedCard + "]";
		}

		public int getHighestCard() {
			return highestCard;
		}

		public void setHighestCard(int highestCard) {
			this.highestCard = highestCard;
		}

		public int[] getRestCards() {
			return restCards;
		}

		public void setRestCards(int[] restCards) {
			this.restCards = restCards;
		}

		public int[] getCards() {
			return cards;
		}

		public void setCards(int[] cards) {
			this.cards = cards;
		}

		public int getHighestPair() {
			return highestPair;
		}

		public void setHighestPair(int highestPair) {
			this.highestPair = highestPair;
		}

	}

}
