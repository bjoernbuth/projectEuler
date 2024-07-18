package com.euler;

import java.util.ArrayList;
import java.util.List;

import com.euler.common.EulerUtils.Pair;
import com.euler.common.Timing;
import com.koloboke.collect.IntCursor;
import com.koloboke.collect.map.IntDoubleCursor;
import com.koloboke.collect.map.IntDoubleMap;
import com.koloboke.collect.map.hash.HashIntDoubleMaps;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler84 {
	private final static IntSet CC=HashIntSets.newImmutableSetOf(2,17,33);
	private final static IntSet CH=HashIntSets.newImmutableSetOf(7,22,36);
	
	private final static int SQUARES=40;
	private final static int MAX_DOUBLES=3;
	private final static int STATES=MAX_DOUBLES*SQUARES;
	private final static int DIE_SIDES=4;
	
	private static double[][] getBaseMatrix(IntDoubleMap baseAdvances,IntDoubleMap doubleAdvances)	{
		double[][] result=new double[STATES][STATES];
		for (int n=0;n<MAX_DOUBLES;++n) for (int i=0;i<SQUARES;++i)	{
			int index=SQUARES*n+i;
			IntDoubleCursor cursor=baseAdvances.cursor();
			while (cursor.moveNext())	{
				int nextSquare=(i+cursor.key())%SQUARES;
				result[index][nextSquare]=cursor.value();
			}
			IntDoubleCursor doubleCursor=doubleAdvances.cursor();
			if (n>=2) while (doubleCursor.moveNext()) result[index][10]+=doubleCursor.value();
			else while (doubleCursor.moveNext())	{
				int nextSquare=(i+doubleCursor.key())%SQUARES;
				nextSquare+=SQUARES*(n+1);
				result[index][nextSquare]=doubleCursor.value();
			}
		}
		return result;
	}
	
	private static int nextR(int current)	{
		int mod=current%10;
		int toAdvance=((mod<5)?5:15)-mod;
		return (current+toAdvance)%SQUARES;
	}
	
	private static int nextU(int current)	{
		return ((current>12)&&(current<28))?28:12;
	}
	
	private static void applySpecialRules(double[][] matrix,int offsetM,int offsetN)	{
		// G2J
		for (int i=0;i<SQUARES;++i) if (matrix[offsetM+i][offsetN+30]!=0)	{
			matrix[offsetM+i][offsetN+10]+=matrix[offsetM+i][offsetN+30];
			matrix[offsetM+i][offsetN+30]=0;
		}
		// CH
		IntCursor chCursor=CH.cursor();
		while (chCursor.moveNext()) for (int i=0;i<SQUARES;++i) if (matrix[offsetM+i][offsetN+chCursor.elem()]!=0)	{
			double sixteenth=matrix[offsetM+i][offsetN+chCursor.elem()]/16;
			matrix[offsetM+i][offsetN]+=sixteenth;
			matrix[offsetM+i][offsetN+10]+=sixteenth;
			matrix[offsetM+i][offsetN+11]+=sixteenth;
			matrix[offsetM+i][offsetN+24]+=sixteenth;
			matrix[offsetM+i][offsetN+39]+=sixteenth;
			matrix[offsetM+i][offsetN+5]+=sixteenth;
			matrix[offsetM+i][offsetN+nextR(chCursor.elem())]+=2*sixteenth;
			matrix[offsetM+i][offsetN+nextU(chCursor.elem())]+=sixteenth;
			matrix[offsetM+i][offsetN+((chCursor.elem()+SQUARES-3)%SQUARES)]+=sixteenth;
			matrix[offsetM+i][offsetN+chCursor.elem()]-=10*sixteenth;
		}
		// CC
		IntCursor ccCursor=CC.cursor();
		while (ccCursor.moveNext()) for (int i=0;i<SQUARES;++i) if (matrix[offsetM+i][offsetN+ccCursor.elem()]!=0)	{
			double sixteenth=matrix[offsetM+i][offsetN+ccCursor.elem()]/16;
			matrix[offsetM+i][offsetN]+=sixteenth;
			matrix[offsetM+i][offsetN+10]+=sixteenth;
			matrix[offsetM+i][offsetN+ccCursor.elem()]-=2*sixteenth;
		}
	}
	
	private static void applySpecialRules(double[][] matrix)	{
		for (int m=0;m<STATES;m+=SQUARES) for (int n=0;n<STATES;n+=SQUARES) applySpecialRules(matrix,m,n);
	}
	
	private static Pair<double[][],double[]> getLinearProblem(double[][] baseTransitionMatrix)	{
		double[][] matrix=new double[STATES-1][STATES-1];
		double[] vector=new double[STATES-1];
		for (int i=0;i<STATES-1;++i)	{
			for (int j=0;j<STATES-1;++j) matrix[i][j]=baseTransitionMatrix[j][i];
			matrix[i][i]-=1.0;
			vector[i]=-baseTransitionMatrix[STATES-1][i];
		}
		return new Pair<>(matrix,vector);
	}
	
	private static void gaussianElimination(double[][] matrix,double[] vector)	{
		int N=matrix.length;
		for (int i=0;i<N;++i)	{
			double pivot=matrix[i][i];
			if (pivot==0) throw new RuntimeException("I should exchange rows here. But let's assume this doesn't happen!");
			for (int j=i+1;j<N;++j) matrix[i][j]/=pivot;
			vector[i]/=pivot;
			matrix[i][i]=1;
			for (int k=i+1;k<N;++k)	{
				pivot=matrix[k][i];
				if (pivot==0) continue;
				for (int j=i+1;j<N;++j) matrix[k][j]-=pivot*matrix[i][j];
				matrix[k][i]=0;
				vector[k]-=pivot*vector[i];
			}
		}
		// At this point we won't update the matrix any more. We only need the vector.
		for (int i=N-1;i>=1;--i) for (int j=i-1;j>=0;--j) vector[j]-=matrix[j][i]*vector[i];
	}
	
	private static double[] getTrueSolution(double[] in)	{
		double sum=1;	// The 40th element, assumed to be 1.
		for (int i=0;i<in.length;++i) sum+=in[i];
		double[] expanded=new double[1+in.length];
		for (int i=0;i<in.length;++i) expanded[i]=in[i]/sum;
		expanded[in.length]=1/sum;
		double[] result=new double[SQUARES];
		for (int i=0;i<SQUARES;++i) for (int j=0;j<MAX_DOUBLES;++j) result[i]+=expanded[SQUARES*j+i];
		return result;
	}
	
	private static Pair<IntDoubleMap,IntDoubleMap> get2DiceConfiguration(int sides)	{
		IntDoubleMap baseResult=HashIntDoubleMaps.newMutableMap();
		IntDoubleMap doubleResult=HashIntDoubleMaps.newMutableMap();
		double baseProb=1d/(sides*sides);
		int s2=sides*2;
		for (int i=2;i<=s2;++i)	{
			int cases=(i<=sides)?(i-1):(s2+1-i);
			if ((i%2)==0)	{
				doubleResult.put(i,baseProb);
				--cases;
			}
			if (cases>0) baseResult.put(i,cases*baseProb);
		}
		return new Pair<>(baseResult,doubleResult);
	}
	
	private static class Square implements Comparable<Square>	{
		public final int square;
		public final double probability;
		public Square(int square,double probability)	{
			this.square=square;
			this.probability=probability;
		}
		@Override
		public int compareTo(Square other)	{
			int result=Double.compare(other.probability,probability);
			if (result!=0) return result;
			else return square-other.square;
		}
	}
	
	private static List<Square> getSortedSolutions(double[] vector)	{
		List<Square> result=new ArrayList<>();
		for (int i=0;i<vector.length;++i) result.add(new Square(i,vector[i]));
		result.sort(null);
		return result;
	}
	
	private static String solve()	{
		Pair<IntDoubleMap,IntDoubleMap> diceConfiguration=get2DiceConfiguration(DIE_SIDES);
		double[][] baseMatrix=getBaseMatrix(diceConfiguration.first,diceConfiguration.second);
		applySpecialRules(baseMatrix);
		Pair<double[][],double[]> linearProblem=getLinearProblem(baseMatrix);
		double[][] matrix=linearProblem.first;
		double[] vector=linearProblem.second;
		gaussianElimination(matrix,vector);
		double[] solution=getTrueSolution(vector);
		List<Square> sorted=getSortedSolutions(solution);
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<3;++i) sb.append(String.format("%02d",sorted.get(i).square));
		return sb.toString();
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler84::solve);
	}
}
