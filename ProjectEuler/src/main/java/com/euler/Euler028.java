package com.euler;

import com.euler.common.Timing;

public class Euler28 {
	private final static int SIZE=1001;
	
	private static long solve()	{
		long n=(SIZE-1)/2;
		long poly=n*(52+n*(60+32*n));
		return poly/6+1;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler28::solve);
	}
}
