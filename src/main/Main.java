package main;
import java.io.*; import lexer.*; import parser.*;

public class Main {

	public static void main(String[] args) throws IOException {
		//System.out.println(args[0]);
		Lexer lex = new Lexer(args[0]);
		Parser parse = new Parser(lex);
		//System.out.println("1");
		parse.program();
		//System.out.println("2");
		System.out.write('\n');
	}
}
