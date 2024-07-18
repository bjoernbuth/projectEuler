package com.euler;

import com.euler.common.Timing;

public class Euler19 {
	private final static int FIRST_YEAR=1901;
	private final static int LAST_YEAR=2000;
	private final static int FIRST_DAY=366%7;	// Because we're counting from 1901, but the date we are given is from 1900!!!
	
	private static boolean isYearLeap(int year)	{
		if ((year%400)==0) return true;
		else if ((year%100)==0) return false;
		else return year%4==0;
	}
	
	private static class YearType	{
		private final int january1st;	// 0 if monday, 1 if tuesday... 6 if sunday.
		private final boolean isLeap;
		private YearType(int january1st,boolean isLeap)	{
			this.january1st=january1st;
			this.isLeap=isLeap;
		}
		private static int getId(int january1st,boolean isLeap)	{
			return january1st+(isLeap?7:0);
		}
		public static YearType getFromId(int id)	{
			int january1st=id%7;
			boolean isLeap=id>=7;
			return new YearType(january1st,isLeap);
		}
		public int getId()	{
			return getId(january1st,isLeap);
		}
		private int getNextYearJan1st()	{
			return (january1st+(isLeap?2:1))%7;
		}
		public int getNextYearId(boolean isLeap)	{
			return getId(getNextYearJan1st(),isLeap);
		}
		private int daysInMonth(int month)	{
			if (month==2) return isLeap?29:28;
			if (month<=7) return ((month%2)==1)?31:30;
			else return ((month%2)==1)?30:31;
		}
		public int countFirstSundays()	{
			int count=0;
			int current=january1st;
			for (int i=1;i<=12;++i)	{
				if (current==6) ++count;
				current+=daysInMonth(i);
				current%=7;
			}
			return count;
		}
	}
	
	private static long count(int initialYear,int finalYear,int firstDay)	{
		YearType[] years=new YearType[14];
		for (int i=0;i<14;++i) years[i]=YearType.getFromId(i);
		int[] counter=new int[14];
		YearType currentYear=years[YearType.getId(firstDay,isYearLeap(initialYear))];
		++counter[currentYear.getId()];
		for (int i=initialYear+1;i<=finalYear;++i)	{
			boolean isLeap=isYearLeap(i);
			int newId=currentYear.getNextYearId(isLeap);
			++counter[newId];
			currentYear=years[newId];
		}
		long result=0;
		for (int i=0;i<14;++i) result+=years[i].countFirstSundays()*counter[i];
		return result;
	}

	private static long solve()	{
		return count(FIRST_YEAR,LAST_YEAR,FIRST_DAY);
	}

	public static void main(String[] args)	{
		Timing.time(Euler19::solve);
	}
}
