package com.euler;

import com.euler.common.Primes;
import com.euler.common.Timing;

public class Euler27 {
	private final static int LIMIT=1000;
	
	private static int countPrimes(int a,int b,boolean[] composites)	{
		int result=1;
		int value=b;
		for (int n=1;;++n)	{
			value+=2*n+a-1;
			if ((value<2)||composites[value]) break;
			else ++result;
		}
		return result;
	}
	
	private static long solve()	{
		boolean[] composites=Primes.sieve(LIMIT*(LIMIT+2));
		int[] primes=Primes.listIntPrimesAsArray(LIMIT);
		int maxA=-1;
		int maxB=-1;
		int maxValue=0;
		for (int a=-LIMIT;a<=LIMIT;++a) for (int b:primes)	{
			int count=countPrimes(a,b,composites);
			if (count>maxValue)	{
				maxA=a;
				maxB=b;
				maxValue=count;
			}
		}
		return maxA*maxB;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler27::solve);
	}
}
