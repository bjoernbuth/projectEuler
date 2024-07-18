package com.euler;

import java.math.BigInteger;

import com.euler.common.Timing;

public class Euler20 {
	private final static int FACTORIAL_OPERAND=100;
	
	private static long solve()	{
		BigInteger factorial=BigInteger.ONE;
		for (int i=2;i<=FACTORIAL_OPERAND;++i) factorial=factorial.multiply(BigInteger.valueOf(i));
		long result=0;
		for (char c:factorial.toString().toCharArray()) result+=c-'0';
		return result;
	}

	public static void main(String[] args)	{
		Timing.time(Euler20::solve);
	}
}
