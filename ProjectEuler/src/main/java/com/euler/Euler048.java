package com.euler;

import com.euler.common.Timing;
import com.google.common.math.LongMath;

public class Euler48 {
	private final static int LIMIT=1000;
	private final static long MOD=LongMath.pow(10l,10);
	
	private static long solve()	{
		long result=0;
		for (int i=2;i<LIMIT;++i)	{
			long pow=i;
			for (int k=2;k<=i;++k) pow=(pow*i)%MOD;
			result=(result+pow)%MOD;
		}
		return result;
	}

	public static void main(String[] args)	{
		Timing.time(Euler48::solve);
	}
}
