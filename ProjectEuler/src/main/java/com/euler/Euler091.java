package com.euler;

import com.euler.common.EulerUtils;
import com.euler.common.Timing;

public class Euler91 {
	private final static long MAX_X=50;
	private final static long MAX_Y=50;
	
	private static long solve()	{
		long result=3*MAX_X*MAX_Y;	// Right angle at the center.
		for (long i=1;i<=MAX_X;++i) for (long j=1;j<=MAX_Y;++j)	{
			long gcd=EulerUtils.gcd(i,j);
			long incrX=i/gcd;
			long incrY=j/gcd;
			long coordX=i;
			long coordY=j;
			for (;;)	{
				coordX-=incrY;
				coordY+=incrX;
				if ((coordX<0)||(coordY>MAX_Y)) break;
				++result;
			}
			coordX=i;
			coordY=j;
			for (;;)	{
				coordX+=incrY;
				coordY-=incrX;
				if ((coordY<0)||(coordX>MAX_X)) break;
				++result;
			}
		}
		return result;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler91::solve);
	}
}
