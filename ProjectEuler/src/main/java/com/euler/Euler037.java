package com.euler;

import java.util.Arrays;
import java.util.Iterator;

import com.euler.common.Primes;
import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler37 {
	private final static int MAX_PRIMES=11;
	
	private final static int MAX_DIGITS=6;
	
	private static class CandidateIterator implements Iterable<int[]>,Iterator<int[]>	{
		private int[] digits;
		public CandidateIterator(int size)	{
			digits=new int[size];
			Arrays.fill(digits,1);
		}
		@Override
		public boolean hasNext() {
			for (int i=1;i<digits.length;++i) if (digits[i]!=9) return true;
			return false;
		}
		@Override
		public int[] next() {
			int i=0;
			while (digits[i]==9)	{
				digits[i]=1;
				++i;
			}
			if ((i==0)&&(digits[i]<=2)) digits[i]++;
			else digits[i]+=((i>0)&&(digits[i]==3))?4:2;
			return digits;
		}
		@Override
		public Iterator<int[]> iterator() {
			return this;
		}
	}
	
	private static class TruncatableChecker	{
		private final int[] digits;
		private final boolean[] composites;
		private int n;
		public TruncatableChecker(int[] digits,boolean[] composites)	{
			this.digits=digits;
			this.composites=composites;
			n=-1;
		}
		private int subArrayToNumber(int i,int j)	{
			int result=0;
			for (int k=i;k<j;++k)	{
				result*=10;
				result+=digits[k];
			}
			return result;
		}
		public boolean isTruncatable()	{
			int N=digits.length;
			for (int i=1;i<N;++i)	{
				if (composites[subArrayToNumber(0,i)]) return false;
				if (composites[subArrayToNumber(i,N)]) return false;
			}
			n=subArrayToNumber(0,N);
			return !composites[n];
		}
		public int getNumber()	{
			return n;
		}
	}
	
	private static long solve()	{
		boolean[] composites=Primes.sieve(IntMath.pow(10,MAX_DIGITS));
		long result=0;
		int count=0;
		for (int i=2;i<=MAX_DIGITS;++i) if (count<MAX_PRIMES) for (int[] candidate:new CandidateIterator(i))	{
			TruncatableChecker check=new TruncatableChecker(candidate,composites);
			if (check.isTruncatable())	{
				result+=check.getNumber();
				++count;
				if (count>=MAX_PRIMES) break;
			}
		}
		if (count<MAX_PRIMES) throw new RuntimeException("Not enough primes found.");
		return result;
	}

	public static void main(String[] args)	{
		Timing.time(Euler37::solve);
	}
}
