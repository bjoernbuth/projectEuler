package com.euler;

import com.euler.common.EulerUtils.IntPermutation;
import com.euler.common.EulerUtils.IntPermutationGenerator;
import com.euler.common.Primes.RabinMiller;
import com.euler.common.Timing;

public class Euler41 {
	private static int transform(int[] digits)	{
		int reverse=digits.length;
		int result=0;
		for (int d:digits)	{
			result*=10;
			result+=reverse-d;
		}
		return result;
	}
	
	private static long solve()	{
		int[] witnesses=new int[] {31,73};
		RabinMiller tester=new RabinMiller();
		for (int i=7;;i-=3) for (IntPermutation perm:new IntPermutationGenerator(i))	{
			int[] base=perm.getNumbers();
			if ((base[0]%2)==1) continue;
			int candidate=transform(base);
			if (tester.isPrime(candidate,witnesses)) return candidate;
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler41::solve);
	}
}
