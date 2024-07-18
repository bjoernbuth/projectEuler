package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.euler.common.EulerUtils;
import com.euler.common.EulerUtils.Pair;
import com.euler.common.Timing;

public class Euler54 {
	private static enum HandDef	{
		HIGH_CARD,PAIR,TWO_PAIRS,THREE_OF_A_KIND,STRAIGHT,FLUSH,FULL_HOUSE,POKER,STRAIGHT_FLUSH;
	}
	
	private static Comparator<Pair<Integer,Character>> COMPARATOR=new Comparator<>()	{
		@Override
		public int compare(Pair<Integer, Character> o1, Pair<Integer, Character> o2) {
			int comp1=o1.first-o2.first;
			if (comp1!=0) return comp1;
			else return o1.second-o2.second;
		}
	};
	
	private static class Hand implements Comparable<Hand>	{
		private final List<Pair<Integer,Character>> cards;
		private final NavigableMap<Integer,Integer> distribution;
		private HandDef hand;
		private int value1;
		private int value2;
		private static NavigableMap<Integer,Integer> calculateDistribution(List<Pair<Integer,Character>> cards)	{
			NavigableMap<Integer,Integer> distribution=new TreeMap<>();
			for (Pair<Integer,Character> card:cards) EulerUtils.increaseCounter(distribution,card.first);
			return distribution;
		}
		private boolean checkStraightFlush()	{
			if (checkFlush()&&checkStraight())	{
				hand=HandDef.STRAIGHT_FLUSH;
				return true;
			}	else return false;
		}
		private boolean checkPoker()	{
			for (Map.Entry<Integer,Integer> entry:distribution.entrySet()) if (entry.getValue()==4)	{
				value1=entry.getKey();
				hand=HandDef.POKER;
				return true;
			}
			return false;
		}
		private boolean checkFullHouse()	{
			value1=0;
			value2=0;
			for (Map.Entry<Integer,Integer> entry:distribution.entrySet()) if (entry.getValue()==3) value1=entry.getKey();
			else if (entry.getValue()==2) value2=entry.getKey();
			if (value1!=0&&value2!=0)	{
				hand=HandDef.FULL_HOUSE;
				return true;
			}	else return false;
		}
		private boolean checkFlush()	{
			for (int i=1;i<cards.size();++i) if (!cards.get(i).second.equals(cards.get(0).second)) return false;
			hand=HandDef.FLUSH;
			return true;
		}
		private boolean checkStraight()	{
			for (int i=1;i<cards.size();++i) if (cards.get(i).first!=1+cards.get(i-1).first) return false;
			value1=cards.get(4).first;
			hand=HandDef.STRAIGHT;
			return true;
		}
		private boolean checkThree()	{
			for (Map.Entry<Integer,Integer> entry:distribution.entrySet()) if (entry.getValue()==3)	{
				value1=entry.getKey();
				hand=HandDef.THREE_OF_A_KIND;
				return true;
			}
			return false;
		}
		private boolean checkTwoPairs()	{
			value1=value2=0;
			for (Map.Entry<Integer,Integer> entry:distribution.entrySet()) if (entry.getValue()==2)	{
				int newVal=entry.getKey();
				if (value1==0) value1=newVal;
				else if (value1>newVal) value2=newVal;
				else	{
					value2=value1;
					value1=newVal;
				}
			}
			if (value1!=0&&value2!=0)	{
				hand=HandDef.TWO_PAIRS;
				return true;
			}	else return false;
		}
		private boolean checkPair()	{
			for (Map.Entry<Integer,Integer> entry:distribution.entrySet()) if (entry.getValue()==2)	{
				value1=entry.getKey();
				hand=HandDef.PAIR;
				return true;
			}
			return false;
		}
		private boolean checkHighCard()	{
			hand=HandDef.HIGH_CARD;
			return true;
		}
		public Hand(List<Pair<Integer,Character>> cards)	{
			this.cards=cards;
			cards.sort(COMPARATOR);
			value1=0;
			value2=0;
			distribution=calculateDistribution(cards);
			boolean unused=checkStraightFlush()||checkPoker()||checkFullHouse()||checkFlush()||checkStraight()||checkThree()||checkTwoPairs()||checkPair()||checkHighCard();
			if (!unused) throw new RuntimeException();
		}
		@Override
		public int compareTo(Hand o) {
			int comparing=hand.compareTo(o.hand);
			if (comparing!=0) return comparing;
			comparing=value1-o.value1;
			if (comparing!=0) return comparing;
			comparing=value2-o.value2;
			if (comparing!=0) return comparing;
			// Everything equal! Resorting to "high"...
			for (int i=4;i>=0;--i)	{
				comparing=cards.get(i).first-o.cards.get(i).first;
				if (comparing!=0) return comparing;
			}
			return 0;
		}
	}
	
	private static int char2num(char c)	{
		if (c>='2'&&c<='9') return (int)(c-'0');
		if (c=='t'||c=='T') return 10;
		if (c=='j'||c=='J') return 11;
		if (c=='q'||c=='Q') return 12;
		if (c=='k'||c=='K') return 13;
		if (c=='a'||c=='A') return 14;
		return 0;
	}
	
	private static Pair<Hand,Hand> readHands(String line)	{
		List<Pair<Integer,Character>> cards1=new ArrayList<>(5);
		List<Pair<Integer,Character>> cards2=new ArrayList<>(5);
		for (int i=0;i<5;++i)	{
			char num1=line.charAt(3*i);
			char suit1=line.charAt(3*i+1);
			char num2=line.charAt(3*(i+5));
			char suit2=line.charAt(3*(i+5)+1);
			cards1.add(new Pair<>(char2num(num1),suit1));
			cards2.add(new Pair<>(char2num(num2),suit2));
		}
		Hand h1=new Hand(cards1);
		Hand h2=new Hand(cards2);
		return new Pair<>(h1,h2);
	}
	
	private static long solve()	{
		try	{
			URL resource=Euler54.class.getResource("in54.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			int counter=0;
			for (String line:lines)	{
				Pair<Hand,Hand> hands=readHands(line);
				if (hands.first.compareTo(hands.second)>0) ++counter;
			}
			return counter;
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler54::solve);
	}
}
