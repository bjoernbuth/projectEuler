package com.euler;

import java.math.BigInteger;

import com.euler.common.Timing;

public class Euler80 {
	private final static int DIGITS=100;
	private final static int LIMIT=100;
	
	public static boolean isSquare(int i)	{
		int sq=(int)Math.sqrt((double)i);
		return sq*sq==i;
	}

	private static long solve()	{
		int sum=0;
		BigInteger googol=BigInteger.valueOf(10l).pow(DIGITS);
		double log2=Math.log(2.0);
		int numIterations=(int)Math.ceil(Math.log(DIGITS*Math.log(10)/log2)/log2);
		for (int i=2;i<=LIMIT;++i)	{
			if (isSquare(i)) continue;
			int sq=(int)Math.sqrt((double)i);
			BigInteger n=BigInteger.valueOf(sq);
			BigInteger d=BigInteger.ONE;
			BigInteger A=BigInteger.valueOf(i);
			if (sq*sq+sq<i) ++sq;
			for (int j=0;j<numIterations;++j)	{
				BigInteger tmp=n.multiply(n).add(d.multiply(d).multiply(A));
				d=d.multiply(n.add(n));
				n=tmp;
			}
			n=n.multiply(googol);
			String number=n.divide(d).toString();
			for (int j=0;j<number.length()-1;++j) sum+=(int)(number.charAt(j)-'0');
		}
		return sum;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler80::solve);
	}
}
