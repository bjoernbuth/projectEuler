package com.euler;

import java.util.Stack;

import com.euler.common.EulerUtils.Pair;
import com.euler.common.Timing;

public class Euler73 {
	private final static int LIMIT=12000;
	
	private static class Fraction	{
		public final int num;
		public final int den;
		public Fraction(int num,int den)	{
			this.num=num;
			this.den=den;
		}
		public static Fraction getChild(Fraction a,Fraction b)	{
			return new Fraction(a.num+b.num,a.den+b.den);
		}
	}
	
	public static long solve()	{
		Stack<Pair<Fraction,Fraction>> pending=new Stack<>();
		pending.add(new Pair<>(new Fraction(1,2),new Fraction(1,3)));
		long result=0;
		while (!pending.isEmpty())	{
			Pair<Fraction,Fraction> range=pending.pop();
			Fraction start=range.first;
			Fraction end=range.second;
			Fraction child=Fraction.getChild(start,end);
			++result;
			if (start.den+child.den<=LIMIT) pending.add(new Pair<>(start,child));
			if (child.den+end.den<=LIMIT) pending.add(new Pair<>(child,end));
		}
		return result;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler73::solve);
	}
}
