package com.euler;

import com.euler.common.Timing;
import com.google.common.math.LongMath;

public class Euler86 {
	private final static long LIMIT=LongMath.pow(10l,6);
	
	public static boolean isSquare(long i)	{
		int sq=(int)Math.sqrt((double)i);
		return sq*sq==i;
	}

	private static long solve()	{
		long count=0;
		for (long z=3;;++z)	{
			long zSq=z*z;
			long z2=z+z;
			for (long xy=4;xy<=z2;++xy) if (isSquare(zSq+xy*xy))	{
				if (xy<z) count+=xy/2;
				else count+=(z2+2-xy)/2;
			}
			if (count>LIMIT) return z;
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler86::solve);
	}
}
