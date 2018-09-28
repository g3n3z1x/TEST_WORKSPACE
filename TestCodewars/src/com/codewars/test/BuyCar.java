package com.codewars.test;

import java.math.RoundingMode;
import java.text.DecimalFormat;

//6 kyu
public class BuyCar {

	public static void main(String[] args) {

		int[] out = new int[2];
		out = nbMonths(2000, 8000, 1000, 1.5);
		System.out.println(out[0] + " " + out[1]);

	}

	public static int[] nbMonths(int startPriceOld, int startPriceNew, int savingperMonth, double percentLossByMonth) {
		int[] result = new int[2];

		Double saved = 0D;
		int months = 0;

		if (startPriceOld >= startPriceNew) {
			// buy new car immediately
			saved = (double) (startPriceOld - startPriceNew);
			result[0] = months;
			result[1] = saved.intValue();
			return result;
		} else {
			// start iterating months
			double adjustedPriceOld = startPriceOld;
			double adjustedPriceNew = startPriceNew;
			double adjustedpercentLossByMonth = percentLossByMonth;
			while (saved < adjustedPriceNew) {
				months++;
				saved += (double) savingperMonth;
				if ((months % 2) == 0) {
					System.out.println("month adjust perc +0.5: " + months);
					adjustedpercentLossByMonth += 0.5D;
				}
				adjustedPriceOld *= (1D - adjustedpercentLossByMonth / 100D);
				adjustedPriceNew *= (1D - adjustedpercentLossByMonth / 100D);
				System.out.println("adjustedPriceOld: " + adjustedPriceOld);
				System.out.println("adjustedPriceNew " + adjustedPriceNew);
				if (saved + adjustedPriceOld >= adjustedPriceNew) {
					saved += adjustedPriceOld - adjustedPriceNew;
					result[0] = months;
					DecimalFormat df = new DecimalFormat("#");
					df.setRoundingMode(RoundingMode.HALF_EVEN);
					Double d = saved.doubleValue();
					result[1] = Integer.parseInt(df.format(d));
					System.out.println("result[1] " + result[1]);
					return result;
				}
			}

		}
		return result;
	}

}
