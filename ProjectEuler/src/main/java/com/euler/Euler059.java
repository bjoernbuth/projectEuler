package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.euler.common.Timing;

public class Euler59 {
	private static char[] xor(char[] encrypted,char[] password)	{
		char[] decrypted=new char[encrypted.length];
		for (int i=0;i<encrypted.length;++i) decrypted[i]=(char)(encrypted[i]^password[i%3]);
		return decrypted;
	}
	
	private static int countThe(char[] decrypted)	{
		int counter=0;
		int current=0;
		for (char c:decrypted) if (current==0)	{
			if ((c=='t')||(c=='T')) ++current;
		}	else if (current==1)	{
			if (c=='h') ++current;
			else current=0;
		}	else if (current==2)	{
			if (c=='e') ++counter;
			current=0;
		}
		return counter;
	}
	
	private static int sumAscii(char[] chars)	{
		int result=0;
		for (char c:chars) result+=c;
		return result;
	}
	
	private static long solve()	{
		try	{
			URL resource=Euler59.class.getResource("in59.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			if (lines.size()!=1) throw new RuntimeException("Unexpected format.");
			String[] split=lines.get(0).split(",");
			char[] fullText=new char[split.length];
			for (int i=0;i<split.length;++i) fullText[i]=(char)Integer.parseInt(split[i]);
			char[] password=new char[3];
			int maxThe=0;
			char[] decryptedText=null;
			for (password[0]='a';password[0]<='z';++password[0]) for (password[1]='a';password[1]<='z';++password[1]) for (password[2]='a';password[2]<='z';++password[2])	{
				char[] decrypted=xor(fullText,password);
				int counter=countThe(decrypted);
				if (counter>maxThe)	{
					maxThe=counter;
					decryptedText=decrypted;
				}
			}
			return sumAscii(decryptedText);
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler59::solve);
	}
}
