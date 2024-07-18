package com.euler;

import java.math.RoundingMode;

import com.euler.common.Timing;
import com.google.common.math.LongMath;

public class Euler45 {
	private final static int STARTING_NUMBER=1+143;
	
	private static boolean isPentagonal(long in)	{
		long det=1+24*in;
		long sq=LongMath.sqrt(det,RoundingMode.DOWN);
		if (sq*sq!=det) return false;
		return (sq+1)%6==0;
	}
	
	private static long solve()	{
		for (long i=STARTING_NUMBER;;++i)	{
			long hexagonal=(2*i-1)*i;
			if (isPentagonal(hexagonal)) return hexagonal;
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler45::solve);
	}
}
