package main;

import java.io.*;
import LexicalAnalysis.*;


public class main {
	public static void  main(String[] args) throws IOException  {
		PrintStream out=System.out;
		PrintStream temp=new PrintStream(new File("symbol.txt"));
		System.setOut(temp);
		Lex_NFA lex=new Lex_NFA();
		lex.scan();
		System.setOut(out);
		System.out.println("Lex Analysis Complete!");
	}

}
