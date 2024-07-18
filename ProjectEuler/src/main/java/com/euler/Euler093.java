package com.euler;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.OptionalInt;

import com.euler.common.CustomCombinationIterator;
import com.euler.common.EulerUtils;
import com.euler.common.Rational;
import com.euler.common.Timing;
import com.euler.common.VariationIterator;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler93 {
	private static enum Operation	{
		ADDITION	{
			@Override
			public Rational apply(Rational a,Rational b)	{
				return a.sum(b);
			}
		},	SUBTRACTION	{
			@Override
			public Rational apply(Rational a,Rational b)	{
				return a.subtract(b);
			}
		},	PRODUCT	{
			@Override
			public Rational apply(Rational a,Rational b)	{
				return a.multiply(b);
			}
		},	DIVISION	{
			@Override
			public Rational apply(Rational a,Rational b)	{
				return b.isNotZero()?a.divide(b):null;
			}
		},	REV_SUBTRACTION	{
			@Override
			public Rational apply(Rational a,Rational b)	{
				return b.subtract(a);
			}
		},	REV_DIVISION	{
			@Override
			public Rational apply(Rational a,Rational b)	{
				return a.isNotZero()?b.divide(a):null;
			}
		};
		public abstract Rational apply(Rational a,Rational b);
	}
	
	private static Operation[][] getAllOperatorCombinations()	{
		Operation[] operations=Operation.values();
		int N=operations.length;
		Operation[][] result=new Operation[N*N*N][3];
		int index=0;
		for (int[] array:new VariationIterator(3,N,false))	{
			Operation[] copying=result[index];
			for (int i=0;i<3;++i) copying[i]=operations[array[i]];
			++index;
		}
		return result;
	}
	
	private static class CalculationGenerator	{
		private final static Operation[][] COMBINATIONS=getAllOperatorCombinations();
		private static int getMaxGenerable(int[] values)	{
			IntSet generated=HashIntSets.newMutableSet();
			do for (Operation[] comb:COMBINATIONS)	{
				OptionalInt tmp=reduce(values,comb);
				if (tmp.isPresent()) generated.add(tmp.getAsInt());
			}	while (EulerUtils.nextPermutation(values));
			for (int i=1;;++i) if (!generated.contains(i)) return i-1;
		}
		private static OptionalInt reduce(int[] values,Operation[] operations)	{
			Deque<Rational> digits=new ArrayDeque<>();
			for (int v:values) digits.add(new Rational(v));
			for (int i=0;i<3;++i) {
				Rational d1=digits.poll();
				Rational d2=digits.poll();
				Rational result=operations[i].apply(d1,d2);
				if (result==null) return OptionalInt.empty();
				digits.addFirst(result);
			}
			Rational ratResult=digits.poll();
			return ratResult.isInteger()?OptionalInt.of((int)ratResult.getIntegerValue()):OptionalInt.empty();
		}
	}
	
	private static String solve()	{
		int[] bestCombination=null;
		int bestValue=0;
		for (int[] digits:new CustomCombinationIterator(4,new int[] {1,2,3,4,5,6,7,8,9},false,true))	{
			int maxGenerable=CalculationGenerator.getMaxGenerable(digits);
			if (maxGenerable>bestValue)	{
				bestValue=maxGenerable;
				bestCombination=digits;
			}
		}
		Arrays.sort(bestCombination);
		StringBuilder sb=new StringBuilder();
		for (int i:bestCombination) sb.append(i);
		return sb.toString();
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler93::solve);
	}
}
