package com.euler;

import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler71 {
	private final static int NUM=3;
	private final static int DEN=7;
	private final static int MAX_DEN=IntMath.pow(10,6);
	
	private static long solve()	{
		long bestNum=0;
		long bestDenom=1;
		long minDenom=1;
		for (long denom=MAX_DEN;denom>=minDenom;--denom)	{
			long num=(long)Math.ceil((double)(denom*NUM)/(double)DEN)-1;
			if (bestNum*denom<num*bestDenom)	{	// I.e. bestNum/bestDenom < num/denom.
				bestNum=num;
				bestDenom=denom;
				long delta=NUM*denom-DEN*num;
				minDenom=Math.max(minDenom,(int)Math.ceil(((double)denom)/((double)delta)));
			}
		}
		return bestNum;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler71::solve);
	}
}
