package com.euler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.euler.common.EulerUtils.Pair;
import com.euler.common.ArithmeticProgressionIterator;
import com.euler.common.Timing;
import com.google.common.math.IntMath;
import com.koloboke.collect.IntCursor;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler32 {
	private static boolean canAdd(boolean[] digits,int number)	{
		while (number>0)	{
			if (digits[number%10]) return false;
			digits[number%10]=true;
			number/=10;
		}
		return true;
	}
	
	private static boolean areAllSet(boolean[] digits)	{
		if (digits[0]) return false;
		for (int i=1;i<10;++i) if (!digits[i]) return false;
		return true;
	}
	
	private static boolean check(int n1,int n2)	{
		boolean[] digits=new boolean[10];
		if (!canAdd(digits,n1)) return false;
		if (!canAdd(digits,n2)) return false;
		if (!canAdd(digits,n1*n2)) return false;
		return areAllSet(digits);
	}
	
	private static boolean hasZeroOrRepeatedDigits(int in)	{
		boolean[] digits=new boolean[10];
		while (in>0)	{
			int d=in%10;
			in/=10;
			if ((d==0)||digits[d]) return true;
			digits[d]=true;
		}
		return false;
	}
	
	private static void addAll(ArithmeticProgressionIterator it1,ArithmeticProgressionIterator it2,IntSet out)	{
		it1.reset();
		while (it1.moveNext())	{
			int n1=it1.elem();
			if (hasZeroOrRepeatedDigits(n1)) continue;
			it2.reset();
			while (it2.moveNext())	{
				int n2=it2.elem();
				if (check(n1,n2))	{
					System.out.println(n1+"*"+n2+"="+(n1*n2));
					out.add(n1*n2);
				}
				else if (n1*n2>=10000) break;
			}
		}
	}
	
	private static class IntPair	{
		public final int a;
		public final int b;
		public IntPair(int a,int b)	{
			this.a=a;
			this.b=b;
		}
	}
	
	private static int getFirstValidPoint(int in)	{
		while (hasZeroOrRepeatedDigits(in)) in+=9;
		return in;
	}
	
	private static List<Pair<ArithmeticProgressionIterator,ArithmeticProgressionIterator>> getAllPairs(List<IntPair> validSizes,List<IntPair> validMods)	{
		List<Pair<ArithmeticProgressionIterator,ArithmeticProgressionIterator>> result=new ArrayList<>(validSizes.size()*validMods.size());
		for (IntPair size:validSizes)	{
			int s1=size.a-1;
			int s2=size.b-1;
			int s1p=IntMath.pow(10,s1);
			int s1p2=10*s1p;
			int s2p=IntMath.pow(10,s2);
			int s2p2=10*s2p;
			for (IntPair modPair:validMods)	{
				ArithmeticProgressionIterator i1=new ArithmeticProgressionIterator(getFirstValidPoint(s1p-1+modPair.a),9,s1p2-1);
				ArithmeticProgressionIterator i2=new ArithmeticProgressionIterator(getFirstValidPoint(s2p-1+modPair.b),9,s2p2-1);
				result.add(new Pair<>(i1,i2));
			}
		}
		return result;
	}
	
	private static long solve()	{
		IntSet result=HashIntSets.newMutableSet();
		List<IntPair> validSizes=Arrays.asList(new IntPair(1,4),new IntPair(2,3));
		List<IntPair> validMods=Arrays.asList(new IntPair(1,4),new IntPair(4,1),new IntPair(3,6),new IntPair(6,3),new IntPair(7,7),new IntPair(0,0));
		for (Pair<ArithmeticProgressionIterator,ArithmeticProgressionIterator> pair:getAllPairs(validSizes,validMods)) addAll(pair.first,pair.second,result);
		long sum=0;
		IntCursor cursor=result.cursor();
		while (cursor.moveNext()) sum+=cursor.elem();
		return sum;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler32::solve);
	}
}
