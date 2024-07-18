package com.euler;

import com.euler.common.Timing;

public class Euler38 {
	private static boolean isPandigital(int in)	{
		boolean[] digits=new boolean[10];
		for (int i=0;i<9;++i)	{
			int d=in%10;
			in/=10;
			if ((d==0)||(digits[d])) return false;
			digits[d]=true;
		}
		return true;
	}
	
	public static long solve()	{
		/*
		 * For 1 digit: only a special case, 918273645, already known.
		 * 
		 * For 2 digits: there will be 4 multiples. 3x has two digits and 4x has three, therefore x<=33 and x>=25.
		 * Since this case will start with 2 or 3, it can't get higher than the known one and it will be discarded.
		 * 
		 * For 3 digits: there will be 3 multiples, all of them with 3 digits. This means that x<=333.
		 * Again it can't get higher than the known maximum so we won't bother.
		 * 
		 * For 4 digits: 2 multiples, one with 4 digits and the other one with 5.
		 * It must be a multiple of 3, otherwise x+2x can't be a multiple of 9 so the concatenation of both numbers
		 * can't be pandigital.
		 * The range is 9999-9183, and we can just stop if we find a single pandigital. Wee!
		 */
		for (int i=9999;i>=9183;i-=3)	{
			int x=100002*i;
			if (isPandigital(x)) return x;
		}
		return 918273645;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler38::solve);
	}
}
