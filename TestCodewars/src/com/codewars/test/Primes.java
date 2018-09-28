package com.codewars.test;

import java.util.BitSet;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

// Prime Streaming --> INCOMPLETO! (hace timeOut en la version NC-17)
// 2 kyu
public class Primes {

	public static void main(String[] args) {
		stream2();
	}
	
	public static IntStream stream2() {
	    return IntStream.range(2, Integer.MAX_VALUE).filter(Primes::isPrime);
	  }
	  
	  private static boolean isPrime(int n) {
	    if(n <= 1) {
	      return false;
	    } else if(n <= 3) {
	      return true;
	    } else if(n % 2 == 0 || n % 3 == 0) {
	      return false;
	    }
	    for(int i = 5; i * i <= n; i += 6) {
	      if(n % i == 0 || n % (i + 2) == 0) {
	        return false;
	      }
	    }
	    return true;  
	  }

	public static IntStream stream() {
		// int max = 50000000;
		return IntStream.iterate(2, i -> i + 1).filter(x -> intIsPrime(x));
	}

	public static boolean intIsPrime(int x) {
		return IntStream.rangeClosed(2, (int) (Math.sqrt(x))).noneMatch(n -> x % n == 0);
	}

	/*
	 * shorter version public static IntStream stream() { IntPredicate isPrime = (n)
	 * -> n > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(n)).noneMatch(i -> n % i
	 * == 0); return IntStream.iterate(2, i -> i + 1).filter(isPrime); }
	 */

	/*
	 * Erathosthene bitset 1 public static final BitSet eratosteme = new
	 * BitSet(100000000);
	 * 
	 * static { eratosteme.flip(0, eratosteme.size() - 1); eratosteme.clear(0);
	 * eratosteme.clear(1); for (int prime = 2; prime > 0 && prime <
	 * eratosteme.size(); prime = eratosteme.nextSetBit(prime + 1)) { for (int i =
	 * prime + prime; i < eratosteme.size(); i += prime) { eratosteme.clear(i); } }
	 * }
	 * 
	 * public static IntStream stream3() { return eratosteme.stream(); }
	 */

	/*
	 * old slower version public static boolean isPrime(int x) { for (int n = 2; n
	 * <= Math.sqrt(x); n++) { if (x % n == 0) { return false; } } return true; }
	 */

	/*
	 * rangeClosed version public static IntStream stream2() { return
	 * IntStream.rangeClosed(2, 50000000) .filter(i -> IntStream.rangeClosed(2,
	 * (int) Math.sqrt(i)).allMatch(j -> i % j != 0)); }
	 */

	/*
	 * long version public static LongStream primeSequence(long max) { return
	 * LongStream.iterate(2, i -> i + 1).filter(x -> longIsPrime(x)).limit(max); }
	 * 
	 * public static boolean longIsPrime(long x) { return LongStream.rangeClosed(2,
	 * (long) (Math.sqrt(x))).allMatch(n -> x % n != 0); }
	 */

}
