package com.euler;

import java.util.ArrayList;
import java.util.List;

import com.euler.common.CombinationIterator;
import com.euler.common.Timing;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler90 {
	private static List<IntSet> getAllDice()	{
		List<IntSet> result=new ArrayList<>();
		CombinationIterator generator=new CombinationIterator(6,10,false,true);
		while (generator.hasNext()) result.add(HashIntSets.newImmutableSet(generator.next()));
		return result;
	}
	
	private static class Square	{
		private final int tens;
		private final int ones;
		public Square(int i)	{
			int sq=i*i;
			tens=sq/10;
			ones=sq%10;
		}
		public boolean canBeRepresented(IntSet die1,IntSet die2)	{
			return (contains(die1,tens)&&contains(die2,ones))||(contains(die2,tens)&&contains(die1,ones));
		}
		private boolean contains(IntSet die,int digit)	{
			if ((digit==6)||(digit==9)) return die.contains(6)||die.contains(9);
			else return die.contains(digit);
		}
	}
	
	private static boolean canAllSquaresBeRepresented(IntSet die1,IntSet die2,List<Square> squares)	{
		for (Square s:squares) if (!s.canBeRepresented(die1,die2)) return false;
		return true;
	}
	
	private static long solve()	{
		List<IntSet> dice=getAllDice();
		List<Square> squares=new ArrayList<>(9);
		for (int i=1;i<10;++i) squares.add(new Square(i));
		long count=0;
		for (int i=0;i<dice.size();++i) for (int j=i+1;j<dice.size();++j) if (canAllSquaresBeRepresented(dice.get(i),dice.get(j),squares)) ++count;
		return count;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler90::solve);
	}
}
