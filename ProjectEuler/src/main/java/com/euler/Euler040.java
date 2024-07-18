package com.euler;

import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler40 {
	private final static int[] POSITIONS=new int[] {1,10,100,1000,10000,100000,1000000};
	
	private static class ChampernowneChunk	{
		private final int firstNumber;
		private final int totalNumbers;
		private final int digits;
		private final int startCount;
		public ChampernowneChunk(int firstNumber,int totalNumbers,int digits,int startCount)	{
			this.firstNumber=firstNumber;
			this.totalNumbers=totalNumbers;
			this.digits=digits;
			this.startCount=startCount;
		}
		public int maxDigitAvailable()	{
			return totalNumbers*digits+startCount;
		}
		public int getDigitAt(int position)	{
			int normalized=position-startCount;
			int index=normalized/digits;
			int digitOrder=normalized%digits;
			int n=index+firstNumber;
			return getNthDigit(digits-1-digitOrder,n);
		}
		public static int getNthDigit(int d,int number)	{
			number/=IntMath.pow(10,d);
			return number%10;
		}
	}
	
	private static class ChampernowneCounter	{
		private int lastCount;
		private int currentDigits;
		public ChampernowneCounter()	{
			lastCount=0;
			currentDigits=0;
		}
		public ChampernowneChunk nextChunk()	{
			++currentDigits;
			if (currentDigits==1)	{
				lastCount=10;
				return new ChampernowneChunk(1,9,1,1);
			}	else	{
				int firstNumber=IntMath.pow(10,currentDigits-1);
				int totalNumbers=9*firstNumber;
				int startCount=lastCount;
				lastCount+=currentDigits*totalNumbers;
				return new ChampernowneChunk(firstNumber,totalNumbers,currentDigits,startCount);
			}
		}
	}
	
	public static long solve()	{
		long product=1;
		ChampernowneCounter counter=new ChampernowneCounter();
		ChampernowneChunk currentChunk=counter.nextChunk();
		for (int n:POSITIONS)	{
			while (n>=currentChunk.maxDigitAvailable()) currentChunk=counter.nextChunk();
			product*=currentChunk.getDigitAt(n);
		}
		return product;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler40::solve);
	}
}
