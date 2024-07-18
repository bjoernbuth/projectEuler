package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

import com.euler.common.Timing;

public class Euler96 {
	// ZUTUN! Depurar.
	private final static int N=3;
	private final static int N2=N*N;
	private final static long[] FULL_CELL_BITSET=new long[] {(1l<<9)-1};
	
	private static class Guess	{
		public final int i,j,value;
		public Guess(int i,int j,int value)	{
			this.i=i;
			this.j=j;
			this.value=value;
		}
		@Override
		public String toString()	{
			return "("+i+","+j+","+value+")";
		}
	}
	
	private static class SudokuBoard	{
		private BitSet[][] data;
		public SudokuBoard()	{
			data=new BitSet[N2][N2];
			for (int i=0;i<N2;++i) for (int j=0;j<N2;++j) data[i][j]=BitSet.valueOf(FULL_CELL_BITSET);
		}
		public boolean isSolvable()	{
			for (int i=0;i<N2;++i) for (int j=0;j<N2;++j) if (data[i][j].cardinality()==0) return false;
			return true;
		}
		public boolean isSolved()	{
			for (int i=0;i<N2;++i) for (int j=0;j<N2;++j) if (data[i][j].cardinality()!=1) return false;
			return true;
		}
		public int turnedOnBits()	{
			int result=0;
			for (int i=0;i<N2;++i) for (int j=0;j<N2;++j) result+=data[i][j].cardinality();
			return result;
		}
		public void readFromStringData(List<String> lines)	{
			for (int i=0;i<N2;++i)	{
				char[] array=lines.get(i).toCharArray();
				for (int j=0;j<N2;++j) if (array[j]!='0')	{
					data[i][j].clear(0,N2);
					data[i][j].set(array[j]-'1');
				}
			}
		}
		private void localConsistency()	{
			int turnedOn=turnedOnBits();
			BitSet[] tmpArray=new BitSet[N2];
			for (int i=0;i<N2;++i) tmpArray[i]=new BitSet();
			for (;;)	{
				// Squares.
				for (int i=0;i<N2;++i) for (int j=0;j<N2;++j) if (data[i][j].cardinality()==1)	{
					for (int k=0;k<N2;++k)	{
						if (i!=k) data[k][j].andNot(data[i][j]);
						if (j!=k) data[i][k].andNot(data[i][j]);
					}
					int sqI=i-(i%N);
					int sqJ=j-(j%N);
					int sqI2=sqI+N;
					int sqJ2=sqJ+N;
					for (int k=sqI;k<sqI2;++k) for (int l=sqJ;l<sqJ2;++l) if ((i!=k)||(j!=l)) data[k][l].andNot(data[i][j]);
				}
				for (int i=0;i<N2;++i)	{
					// Rows.
					for (int k=0;k<N2;++k) tmpArray[i].clear();
					for (int j=0;j<N2;++j) for (int k=0;k<N2;++k) if (data[i][j].get(k)) tmpArray[k].set(j);
					for (int k=0;k<N2;++k) if (tmpArray[k].cardinality()==1)	{
						int l=tmpArray[k].nextSetBit(0);
						data[i][l].clear();
						data[i][l].set(k);
					}	else if (tmpArray[k].cardinality()==0)	{
						data[0][0].clear();
						return;
					}
					// Columns.
					for (int k=0;k<N2;++k) tmpArray[k].clear();
					for (int j=0;j<N2;++j) for (int k=0;k<N2;++k) if (data[j][i].get(k)) tmpArray[k].set(j);
					for (int k=0;k<N2;++k) if (tmpArray[k].cardinality()==1)	{
						int l=tmpArray[k].nextSetBit(0);
						data[l][i].clear();
						data[l][i].set(k);
					}	else if (tmpArray[k].cardinality()==0)	{
						data[0][0].clear();
						return;
					}
				}
				//NxN Squares
				for (int i1=0;i1<N2;i1+=N) for (int j1=0;j1<N2;j1+=N)	{
					for (int k=0;k<N2;++k) tmpArray[k].clear();
					for (int i=0;i<N;++i) for (int j=0;j<N;++j)	{
						int i2=i1+i;
						int j2=j1+j;
						for (int k=0;k<N2;++k) if (data[i2][j2].get(k)) tmpArray[k].set(N*i+j);
					}
					for (int k=0;k<N2;++k) if (tmpArray[k].cardinality()==1)	{
						int l=tmpArray[k].nextSetBit(0);
						int j=l%N;
						int i=(l-j)/N;
						data[i1+i][j1+j].clear();
						data[i1+i][j1+j].set(k);
					}	else if (tmpArray[k].cardinality()==0)	{
						data[0][0].clear();
						return;
					}
				}
				int turnedOn2=turnedOnBits();
				if (turnedOn2<turnedOn) turnedOn=turnedOn2;
				else break;
			}
		}
		private List<Guess> generateGuesses()	{
			List<Guess> result=new ArrayList<>();
			for (int i=0;i<N2;++i) for (int j=0;j<N2;++j) if (data[i][j].cardinality()>=2)	{
				int pointer=0;
				for (;;)	{
					int setBit=data[i][j].nextSetBit(pointer);
					if (setBit==-1) break;
					result.add(new Guess(i,j,setBit));
					pointer=1+setBit;
				}
			}
			return result;
		}
		private SudokuBoard applyGuess(Guess g)	{
			SudokuBoard result=new SudokuBoard();
			for (int i=0;i<N2;++i) for (int j=0;j<N2;++j) if ((i==g.i)&&(j==g.j))	{
				result.data[i][j].clear();
				result.data[i][j].set(g.value);
			}	else result.data[i][j].and(data[i][j]);
			return result;
		}
		private static SudokuBoard merge(SudokuBoard b1,SudokuBoard b2)	{
			SudokuBoard result=new SudokuBoard();
			for (int i=0;i<N2;++i) for (int j=0;j<N2;++j)	{
				result.data[i][j].and(b1.data[i][j]);
				result.data[i][j].and(b2.data[i][j]);
			}
			result.localConsistency();
			return result;
		}
		private static SudokuBoard solve(SudokuBoard in)	{
			in.localConsistency();
			if (in.isSolved()) return in;
			List<SudokuBoard> branches=new ArrayList<>();
			for (Guess g:in.generateGuesses())	{
				SudokuBoard branch=in.applyGuess(g);
				if (branch.isSolved()) return branch;
				else if (branch.isSolvable()) branches.add(branch);
			}
			while (!branches.isEmpty())	{
				List<SudokuBoard> newBranches=new ArrayList<>();
				for (int i=0;i<branches.size();++i) for (int j=i+1;j<branches.size();++j) {
					SudokuBoard merged=merge(branches.get(i),branches.get(j));
					if (merged.isSolved()) return merged;
					else if (merged.isSolvable()) newBranches.add(merged);
				}
				branches=newBranches;
			}
			throw new RuntimeException("No :(.");
		}
		private int getCorner()	{
			int h=data[0][0].nextSetBit(0)+1;
			int t=data[0][1].nextSetBit(0)+1;
			int o=data[0][2].nextSetBit(0)+1;
			return 100*h+10*t+o;
		}
	}
	
	private static long solve()	{
		try	{
			URL resource=Euler96.class.getResource("in96.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			int size=lines.size()/10;
			int result=0;
			for (int i=0;i<size;++i)	{
				List<String> input=lines.subList(10*i+1,10*i+10);
				SudokuBoard board=new SudokuBoard();
				board.readFromStringData(input);
				board=SudokuBoard.solve(board);
				result+=board.getCorner();
			}
			return result;
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler96::solve);
	}
}
