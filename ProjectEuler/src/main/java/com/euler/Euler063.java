package com.euler;

import com.euler.common.Timing;

public class Euler63 {
	private static long solve()	{
		int result=0;
		for (int i=1;;++i)	{
			double q=((double)(i-1))/(double)(i);
			int minValid=(int)Math.ceil(Math.pow(10,q));
			if (minValid>=10) return result;
			result+=10-minValid;
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler63::solve);
	}
}
