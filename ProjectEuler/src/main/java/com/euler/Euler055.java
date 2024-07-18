package com.euler;

import java.math.BigInteger;

import com.euler.common.Timing;

public class Euler55 {
	private final static int LIMIT=10000;
	private final static int MAX_ITERATIONS=50;
	
	private static String reverse(String in)	{
		return new StringBuilder(in).reverse().toString();
	}
	
	private static BigInteger addReverse(BigInteger in)	{
		String inStr=in.toString();
		String newStr=reverse(inStr);
		BigInteger n2=new BigInteger(newStr);
		return in.add(n2);
	}
	
	private static boolean isPalindrome(BigInteger in)	{
		String inStr=in.toString();
		String newStr=reverse(inStr);
		return inStr.equals(newStr);
	}
	
	private static boolean isLychrel(int in)	{
		BigInteger value=BigInteger.valueOf(in);
		for (int k=1;k<=MAX_ITERATIONS;++k)	{
			value=addReverse(value);
			if (isPalindrome(value)) return false;
		}
		return true;
	}
	
	private static long solve()	{
		int counter=0;
		for (int i=1;i<=LIMIT;++i) if (isLychrel(i)) ++counter;
		return counter;
	}

	public static void main(String[] args)	{
		Timing.time(Euler55::solve);
	}
}
