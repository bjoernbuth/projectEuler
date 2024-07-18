package com.euler;

import com.euler.common.Primes;
import com.euler.common.Timing;

public class Euler77 {
	private final static int LIMIT=5000;
	
	private final static int PRIME_LIMIT=100;
	
	public static long solve()	{
		int[] primes=Primes.listIntPrimesAsArray(PRIME_LIMIT);
		long[][] result=new long[primes.length][1+PRIME_LIMIT];
		for (int j=0;j<=PRIME_LIMIT;j+=primes[0]) result[0][j]=1;
		for (int i=1;i<primes.length;++i) for (int j=0;j<PRIME_LIMIT;++j) for (int k=j;k>=0;k-=primes[i]) result[i][j]+=result[i-1][k];
		for (int j=0;j<=PRIME_LIMIT;++j) if (result[result.length-1][j]>LIMIT) return j;
		return -1;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler77::solve);
	}
}
