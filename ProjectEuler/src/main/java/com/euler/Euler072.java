package com.euler;

import com.euler.common.SumOfTotientCalculator;
import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler72 {
	private final static int LIMIT=IntMath.pow(10,6);
	
	private static long solve()	{
		SumOfTotientCalculator calc=SumOfTotientCalculator.getWithoutMod();
		return calc.getTotientSum(LIMIT)-1;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler72::solve);
	}
}
