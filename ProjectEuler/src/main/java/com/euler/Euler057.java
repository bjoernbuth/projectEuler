package com.euler;

import java.util.Iterator;

import com.euler.common.Convergents.Convergent;
import com.euler.common.Convergents.PeriodicContinuedFraction;
import com.euler.common.Timing;

public class Euler57 {
	private static long solve()	{
		PeriodicContinuedFraction continuedFraction=PeriodicContinuedFraction.getForSquareRootOf(2);
		Iterator<Convergent> convergents=continuedFraction.iterator();
		int counter=0;
		convergents.next();	// Fraction "0" (1/1 in this case) discarded.
		for (int i=0;i<1000;++i)	{
			Convergent c=convergents.next();
			if (c.p.toString().length()>c.q.toString().length()) ++counter;
		}
		return counter;
	}

	public static void main(String[] args)	{
		Timing.time(Euler57::solve);
	}
}
