package com.euler;

import java.util.ArrayList;
import java.util.List;

import com.euler.common.Primes;
import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler50 {
	private final static int LIMIT=IntMath.pow(10,6);
	
	private static int[] distillPrimes(boolean[] composites)	{
		List<Integer> result=new ArrayList<>();
		result.add(2);
		result.add(3);
		boolean add4=false;
		for (int i=5;i<composites.length;i+=(add4?4:2),add4=!add4) if (!composites[i]) result.add(i);
		return result.stream().mapToInt(Integer::intValue).toArray();
	}
	
	private static int sum(int[] array,int from,int to)	{
		int result=0;
		for (int i=from;i<to;++i) result+=array[i];
		return result;
	}
	
	private static long solve()	{
		boolean[] composites=Primes.sieve(LIMIT);
		int[] primes=distillPrimes(composites);
		int maxLength=21;	// Known because of the problem description.
		int N=primes.length;
		int maxPrime=-1;
		for (int i=0;i<N-maxLength;++i) for (int j=i+maxLength+1;j<=N;++j)	{
			int candidate=sum(primes,i,j);
			if (candidate>LIMIT) break;
			else if (!composites[candidate])	{
				maxLength=j-i;
				maxPrime=candidate;
			}
		}
		return maxPrime;
	}

	public static void main(String[] args)	{
		Timing.time(Euler50::solve);
	}
}
