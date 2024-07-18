package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import com.euler.common.Timing;

public class Euler83 {
	private static long solve()	{
		try	{
			URL resource=Euler83.class.getResource("in8.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			int[][] originalMatrix=Euler81.parseLines(lines);
			int[][] result=Euler81.arraySameSize(originalMatrix);
			result[0][0]=originalMatrix[0][0];
			int N=result.length;
			int M=result[0].length;
			for (int j=1;j<M;++j) result[0][j]=result[0][j-1]+originalMatrix[0][j];
			for (int i=1;i<N;++i) result[i][0]=result[i-1][0]+originalMatrix[i][0];
			for (int i=1;i<N;++i) for (int j=1;j<M;++j) result[i][j]=originalMatrix[i][j]+Math.min(result[i-1][j],result[i][j-1]);
			boolean anyChange=true;
			while (anyChange)	{
				anyChange=false;
				for (int i=0;i<N;++i) for (int j=0;j<M;++j)	{
					int prev=result[i][j];
					OptionalInt right=(j==M-1)?OptionalInt.empty():OptionalInt.of(result[i][j+1]);
					OptionalInt up=(i==0)?OptionalInt.empty():OptionalInt.of(result[i-1][j]);
					OptionalInt left=(j==0)?OptionalInt.empty():OptionalInt.of(result[i][j-1]);
					OptionalInt down=(i==N-1)?OptionalInt.empty():OptionalInt.of(result[i+1][j]);
					int curr=Euler82.getMinimum(right,up,left,down)+originalMatrix[i][j];
					if (curr<prev)	{
						anyChange=true;
						result[i][j]=curr;
					}
				}
			}
			return result[N-1][M-1];
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler83::solve);
	}
}
