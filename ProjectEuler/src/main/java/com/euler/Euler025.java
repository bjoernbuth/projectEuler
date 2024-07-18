package com.euler;

import com.euler.common.Timing;

public class Euler25 {
	private final static long DIGITS=1000;
	
	private static long solve()	{
		double sq5=Math.sqrt(5);
		double phi=(1+sq5)/2.0;
		double logBound=(DIGITS-1)*Math.log(10)+Math.log(sq5);
		return (int)Math.ceil(logBound/Math.log(phi));
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler25::solve);
	}
}
