package com.euler;

import com.euler.common.EulerUtils.IntPermutation;
import com.euler.common.EulerUtils.IntPermutationGenerator;
import com.euler.common.Timing;

public class Euler68 {
	private final static int[] FORBIDDEN_TEN=new int[] {2,3,4,6,7};
	private final static int[] INDICES=new int[] {0,2,4, 1,4,7, 8,7,6, 9,6,3, 5,3,2};
	
	private static boolean check(int[] numbers)	{
		for (int position:FORBIDDEN_TEN) if (numbers[position]==9) return false;
		int sum=numbers[INDICES[0]]+numbers[INDICES[1]]+numbers[INDICES[2]];
		for (int i=1;i<5;++i) if (numbers[INDICES[3*i]]<numbers[INDICES[0]]) return false;
		else	{
			int otherSum=numbers[INDICES[3*i]]+numbers[INDICES[3*i+1]]+numbers[INDICES[3*i+2]];
			if (otherSum!=sum) return false;
		}
		return true;
	}
	
	private static long build(int[] numbers)	{
		long result=0;
		for (int position:INDICES)	{
			int number=numbers[position];
			result*=(number==9)?100:10;
			result+=number+1;
		}
		return result;
	}
	
	private static long solve()	{
		long currentMax=0;
		for (IntPermutation perm:new IntPermutationGenerator(10)) if (check(perm.getNumbers())) currentMax=Math.max(currentMax,build(perm.getNumbers()));
		return currentMax;
	}

	public static void main(String[] args)	{
		Timing.time(Euler68::solve);
	}
}
