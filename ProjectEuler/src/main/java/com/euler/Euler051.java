package com.euler;

import com.euler.common.CombinationIterator;
import com.euler.common.Primes;
import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler51 {
	private final static int HOW_MANY=8;
	
	private static int[] generateNumbers(int[] variable,int[] fixed,int lastDigit)	{
		int expandedBase=0;
		int arithmeticBase=0;
		int baseIndex=0;
		int variableIndex=0;
		int size=variable.length+fixed.length+1;
		for (int i=0;i<size;++i) if ((variableIndex<variable.length)&&(variable[variableIndex]==i))	{
			++variableIndex;
			expandedBase*=10;
			arithmeticBase*=10;
			++arithmeticBase;
		}	else	{
			expandedBase*=10;
			expandedBase+=(baseIndex<fixed.length)?fixed[baseIndex]:lastDigit;
			++baseIndex;
			arithmeticBase*=10;
		}
		int[] result;
		if (variable[0]==0)	{
			result=new int[9];
			expandedBase+=arithmeticBase;
		}	else result=new int[10];
		for (int i=0;i<result.length;++i)	{
			result[i]=expandedBase;
			expandedBase+=arithmeticBase;
		}
		return result;
	}
	
	private static long solve()	{
		boolean[] composites=Primes.sieve(IntMath.pow(10,6));	// Increase if it doesn't work.
		int[] lastDigits=new int[] {1,3,7,9};
		for (int i=5;;++i) for (int j=3;j<i;j+=3) for (int[] variableDigits:new CombinationIterator(j,i-1,false,false)) for (int[] fixedDigits:new CombinationIterator(i-j-1,9,true,false)) for (int last:lastDigits)	{
			int[] candidates=generateNumbers(variableDigits,fixedDigits,last);
			int counter=0;
			for (int p:candidates) if (!composites[p]) ++counter;
			if (counter>=HOW_MANY) return candidates[0];
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler51::solve);
	}
}
