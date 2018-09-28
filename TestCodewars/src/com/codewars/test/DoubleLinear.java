package com.codewars.test;

import java.util.BitSet;
import java.util.SortedSet;
import java.util.TreeSet;

//Twice Linear
//4 kyu
public class DoubleLinear {

	public static void main(String[] args) {

		System.out.println("RESULT: " + dblLinear2(60000));

	}

	public static int dblLinear(int n) {
		System.out.println("Will find element: " + n);
		if (n == 0) {
			return 1;
		}
		BitSet u = new BitSet();
		u.set(1);
		int x1 = 0;
		int x2 = 0;
		for (int i = 1; i < n + 1; i++) {
			//System.out.println(u.nextSetBit(i));
			int y = 2 * u.stream().skip(x1).findFirst().getAsInt() + 1;
			int z = 3 * u.stream().skip(x2).findFirst().getAsInt() + 1;
//			if((y > u.nextSetBit(i)) || (z > u.nextSetBit(i)) ) {
//				break;
//			}
//			u.set(y);
//			u.set(z);
			if(y <= z) {
				u.set(y);
				x1++;
				if(y == z) {
					x2++;
				}
			}else {
				u.set(z);
				x2++;
			}
//			if ((u.size()) / 96 >= n + 1) {
//				break;
//			}
//			if(u.cardinality() > n) {
//				break;
//			}
		}
//		System.out.println("Size: " + u.size());
//		System.out.println("Size/64: " + u.size() / 64);
//		System.out.println("Cardinality: "+u.cardinality());
//		
//		System.out.println(u.toString());

		return u.stream().skip(n).findFirst().getAsInt();
	}
	
	//mucho mas rapido
	public static int dblLinear2(int n) {
        if (n == 0) return 1;
        SortedSet<Integer> u = new TreeSet<>();
        u.add(1);
        for (int i = 0; i < n; i++) {
            int x = u.first();
            u.add(x * 2 + 1);
            u.add(x * 3 + 1);
            u.remove(x);
        }
        System.out.println(u.size());
        return u.first();
    }

}
