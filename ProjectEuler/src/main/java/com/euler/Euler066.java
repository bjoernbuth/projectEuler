package com.euler;

import java.math.BigInteger;
import java.util.Iterator;

import com.euler.common.Convergents.Convergent;
import com.euler.common.Convergents.PeriodicContinuedFraction;
import com.euler.common.Timing;

public class Euler66 {
	private final static int LIMIT=1000;
	
	private static BigInteger getMinD(int i)	{
		Iterator<Convergent> iterator=PeriodicContinuedFraction.getForSquareRootOf(i).iterator();
		BigInteger bigI=BigInteger.valueOf(i);
		for (;;)	{
			Convergent c=iterator.next();
			BigInteger diff=c.p.multiply(c.p).subtract(c.q.multiply(c.q).multiply(bigI));
			if (diff.equals(BigInteger.ONE)) return c.p;
		}
	}
	
	private static long solve()	{
		int nextSquareRoot=2;
		int nextPerfectSquare=4;
		BigInteger maxFound=BigInteger.ZERO;
		int maxIndex=-1;
		for (int i=2;i<=LIMIT;++i) if (i==nextPerfectSquare)	{
			++nextSquareRoot;
			nextPerfectSquare=nextSquareRoot*nextSquareRoot;
		}	else	{
			BigInteger d=getMinD(i);
			if (d.compareTo(maxFound)>0)	{
				maxFound=d;
				maxIndex=i;
			}
		}
		return maxIndex;
	}

	public static void main(String[] args)	{
		Timing.time(Euler66::solve);
	}
}
