package com.euler;

import com.euler.common.Timing;

public class Euler6 {
	private final static long N=100;
	
	private static long solve()	{
		/*
		 * Sum of squares: (2n^3+3n^2+n)/6
		 * Sum: (n^2+n)/2
		 * Square of sums: (n^4+2n^3+n)/4
		 * 
		 * Square of sums minus sum of squares: (n^4+2n^3+n^2)/4 - (2n^3+3n^2+n)/6 =
		 *  = (3n^4+6n^3+3n^2)/12 - (4n^3+6n^2+2n)/12 = (3n^4+2n^3-3n^2-2n)/12
		 */
		return (N*(-2+N*(-3+N*(2+N*3))))/12l;
	}

	public static void main(String[] args)	{
		Timing.time(Euler6::solve);
	}
}
