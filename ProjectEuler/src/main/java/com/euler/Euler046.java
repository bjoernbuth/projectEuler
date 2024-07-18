package com.euler;

import java.util.ArrayList;
import java.util.List;

import com.euler.common.Primes;
import com.euler.common.Timing;

public class Euler46 {
	private final static int LIMIT=100000;
	
	private static int[] distillOddPrimes(boolean[] composites)	{
		List<Integer> result=new ArrayList<>();
		result.add(3);
		boolean add4=false;
		for (int i=5;i<composites.length;i+=(add4?4:2),add4=!add4) if (!composites[i]) result.add(i);
		return result.stream().mapToInt(Integer::intValue).toArray();
	}
	
	private static long solve()	{
		boolean[] composites=Primes.sieve(LIMIT);
		int[] primes=distillOddPrimes(composites);
		int lastChecked=1;
		boolean[] summation=new boolean[LIMIT];
		for (int i=1;;++i)	{
			int sq=i*i;
			while (lastChecked<sq)	{
				lastChecked+=2;
				if (composites[lastChecked]&&!summation[lastChecked]) return lastChecked;
			}
			sq=2*sq;
			for (int p:primes)	{
				int sum=p+sq;
				if (sum>=LIMIT) break;
				summation[sum]=true;
			}
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler46::solve);
	}
}
