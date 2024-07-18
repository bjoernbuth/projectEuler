package com.euler;

import java.util.BitSet;

import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler36 {
	private final static int MAX_DIGITS=3;
	
	private static int[] replicate(int[] in,boolean replicateCenter)	{
		int N=in.length;
		int[] result=new int[2*N-(replicateCenter?0:1)];
		for (int i=0;i<in.length;++i)	{
			result[i]=in[i];
			result[result.length-i-1]=in[i];
		}
		return result;
	}
	
	private static int generateSym(int bottom,int digitsInBottom,boolean replicateCenter)	{
		int[] original=new int[digitsInBottom];
		for (int i=0;i<digitsInBottom;++i)	{
			original[i]=bottom%10;
			bottom/=10;
		}
		int[] duplicated=replicate(original,replicateCenter);
		int result=0;
		for (int i=0;i<duplicated.length;++i) result=10*result+duplicated[i];
		return result;
	}
	
	private static boolean isPalindrome2(long in)	{
		BitSet bitSet=BitSet.valueOf(new long[] {in});
		int N=bitSet.length();
		for (int i=0;i<N/2;++i) if (bitSet.get(i)!=bitSet.get(N-1-i)) return false;
		return true;
	}
	
	private static long solve()	{
		long sum=0;
		for (int i=1;i<=MAX_DIGITS;++i)	{
			int start=1;
			int end=IntMath.pow(10,i);
			for (int j=start;j<end;j+=2)	{
				int case1=generateSym(j,i,false);
				if (isPalindrome2(case1)) sum+=case1;
				int case2=generateSym(j,i,true);
				if (isPalindrome2(case2)) sum+=case2;
			}
		}
		return sum;
	}

	public static void main(String[] args)	{
		Timing.time(Euler36::solve);
	}
}
