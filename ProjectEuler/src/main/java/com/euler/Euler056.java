package com.euler;

import java.math.BigInteger;

import com.euler.common.Timing;

public class Euler56 {
	private static int sumDigits(BigInteger in)	{
		int result=0;
		for (char c:in.toString().toCharArray()) result+=c-'0';
		return result;
	}
	
	private static long solve()	{
		int maxSum=0;
		for (int a=2;a<100;++a)	{
			BigInteger bigA=BigInteger.valueOf(a);
			BigInteger power=bigA;
			for (int b=2;b<100;++b)	{
				power=power.multiply(bigA);
				maxSum=Math.max(maxSum,sumDigits(power));
			}
		}
		return maxSum;
	}

	public static void main(String[] args)	{
		Timing.time(Euler56::solve);
	}
}
