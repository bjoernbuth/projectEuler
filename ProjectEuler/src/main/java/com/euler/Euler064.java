package com.euler;

import com.euler.common.Convergents.PeriodicContinuedFraction;
import com.euler.common.Timing;

public class Euler64 {
	private final static int LIMIT=10000;
	
	private static long solve()	{
		int nextSquareRoot=2;
		int nextPerfectSquare=4;
		int counter=0;
		for (int i=2;i<=LIMIT;++i) if (i==nextPerfectSquare)	{
			++nextSquareRoot;
			nextPerfectSquare=nextSquareRoot*nextSquareRoot;
		}	else	{
			PeriodicContinuedFraction fraction=PeriodicContinuedFraction.getForSquareRootOf(i);
			if (fraction.getPeriodLength()%2==1) ++counter;
		}
		return counter;
	}

	public static void main(String[] args)	{
		Timing.time(Euler64::solve);
	}
}
