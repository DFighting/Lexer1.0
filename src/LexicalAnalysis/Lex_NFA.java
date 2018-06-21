package LexicalAnalysis;

import symbol.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Lex_NFA {
	public static int line = 1;
	public static int number = 1;
	public static int state = 0;
	private int current = 0, predict = 0, len = 0;
	char[] peek = new char[1024];
	Hashtable<hash_key, Token> symbols = new Hashtable<hash_key, Token>();
	private ArrayList<hash_key> key_list=new ArrayList<hash_key>();
	private int key_count=0;

	void reserve(Word w) {
		hash_key key = new hash_key(w.index);
		symbols.put(key, new Token(w.getTag()));
	}

	public Lex_NFA() {
		reserve(new Word("if", Tag.IF));
		reserve(new Word("else", Tag.ELSE));

		reserve(new Word("true", Tag.TRUE));
		reserve(new Word("false", Tag.FALSE));

		reserve(new Word("int", Tag.INT));
		reserve(new Word("float", Tag.FLOAT));
		reserve(new Word("char", Tag.CHAR));
		reserve(new Word("bool", Tag.BOOL));

	}

	public void Input() throws IOException {
		FileReader reader = null;
		File file = new File("D:\\Code\\Java\\workspace\\Lexer1.0\\code.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		reader = new FileReader(file);
		while ((reader.read(peek)) != -1)
			continue;
		//System.out.print("Test Code:" + "\n" + "1" + "\t");
		for (int i = 0, j = 2; i < peek.length; i++) {
			if (peek[i] == '@')
				break;
			if (peek[i] == '\n') {
			//	System.out.print(peek[i]);
			//	System.out.print(j++ + "\t");
				j++;
			} else
			//	System.out.print(peek[i]);
			len++;
		}
		//System.out.println();
		reader.close();
		System.out.println("NAME  \tTAG   \tLINE  \t");
		
	}

	public FullToken scan() throws IOException {
		Input();
		// System.out.println("Success Implement Input()");
		Token tok = new Token(Tag.NULL);
		hash_key key = new hash_key(0, " ");
		// First phase:put code into different handle function
		while (current < len) {
			// terminal symbols,State(1)
			if ((peek[current] == '{') || (peek[current] == '}') || (peek[current] == '(') || (peek[current] == ')')
					|| (peek[current] == ' ') || (peek[current] == '\t')) {
				current++;
				continue;
			} // State(4),read next
			else if (peek[current] == ';') {
				number++;
				current++;
				continue; // State(5),read next
			} else if (peek[current] == '\n') {
				line = line + 1;
				number++;
				current++;
				continue; // State(6),read next
			}
			// Keyboard:Letter or Digit,State(3)
			else if (Character.isLetterOrDigit(peek[current])) {
				state = 3;
				Handle_Keyboard(tok, key);
				key_list.add(key);
				key_count++;
				//System.out.print("\nToken--Name:" + symbols.get(key).getName() + "\tkey:");
				//key.Prin_key();
				//System.out.print("Symbol--");
				tok.Print();
				current++;
				continue;
			} // Instruction Character,State(2)
			else {
				state = 2;
				Handle_Instruction(tok, key);
				key_list.add(key);
				key_count++;
				//System.out.print("\nToken--Name:" + symbols.get(key).getName() + "\tkey:");
				//key.Prin_key();
				//System.out.print("Symbol--");
				tok.Print();
				current++;
				continue;
			}
		}
		FullToken Full_Token = new FullToken(tok.getName(), key);
		return Full_Token;
	}

	private void Handle_Instruction(Token tok, hash_key key) throws IOException {
		predict = current;
		switch (peek[current]) {
		// arithmetic instruction,State(7)
		case '+':
			predict++;
			if ((peek[predict] == ' ') || (peek[predict] == '\t') || (peek[predict] == '\n') || (peek[predict] == '(')
					|| (peek[predict] == ')') || (Character.isLetterOrDigit(peek[predict])))
				tok.resetName("+");
			tok.resetTag(Tag.ADD);
			tok.resetline(line);
			key.reset_hash_key(new hash_key(number, Tag.ADD));
			symbols.put(key, tok);
			current = predict;
			break;
		case '-':
			predict++;
			if ((peek[predict] == ' ') || (peek[predict] == '\t') || (peek[predict] == '\n') || (peek[predict] == '(')
					|| (peek[predict] == ')') || (Character.isLetterOrDigit(peek[predict])))
				tok.resetName("-");
			tok.resetTag(Tag.SUB);
			tok.resetline(line);
			key.reset_hash_key(new hash_key(number, Tag.SUB));
			symbols.put(key, tok);
			current = predict;
			break;
		case '*':
			predict++;
			if ((peek[predict] == ' ') || (peek[predict] == '\t') || (peek[predict] == '\n') || (peek[predict] == '(')
					|| (peek[predict] == ')') || (Character.isLetterOrDigit(peek[predict])))
				tok.resetName("*");
			tok.resetTag(Tag.MULT);
			tok.resetline(line);
			key.reset_hash_key(new hash_key(number, Tag.MULT));
			symbols.put(key, tok);
			current = predict;
			break;
		case '/':
			predict++;
			if ((peek[predict] == ' ') || (peek[predict] == '\t') || (peek[predict] == '\n') || (peek[predict] == '(')
					|| (peek[predict] == ')') || (Character.isLetterOrDigit(peek[predict])))
				tok.resetName("/");
			tok.resetTag(Tag.DIV);
			tok.resetline(line);
			key.reset_hash_key(new hash_key(number, Tag.DIV));
			symbols.put(key, tok);
			current = predict;
			break;
		// logic instruction,State(8)
		case '&':
			predict++;
			if (peek[predict] == '&') {
				tok.resetName("&&");
				tok.resetTag(Tag.BASIC);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.BASIC));
				symbols.put(key, tok);
				current = predict;
				break;
			} else {
				tok.resetName("&");
				tok.resetTag(Tag.AND);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.AND));
				symbols.put(key, tok);
				break;
			}
		case '|':
			predict++;
			if (peek[predict] == '|') {
				tok.resetName("||");
				tok.resetTag(Tag.BASIC);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.BASIC));
				symbols.put(key, tok);
				current = predict;
				break;
			} else {
				tok.resetName("|");
				tok.resetTag(Tag.OR);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.OR));
				symbols.put(key, tok);
				break;
			}
		case '!':
			predict++;
			if (peek[predict] == '=') {
				tok.resetName("!=");
				tok.resetTag(Tag.BASIC);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.BASIC));
				symbols.put(key, tok);
				current = predict;
				break;
			} else {
				tok.resetName("!");
				tok.resetTag(Tag.NOT);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.NOT));
				symbols.put(key, tok);
				break;
			}
			// other instruction,State(9)
		case '>':
			predict++;
			if (peek[predict] == '=') {
				tok.resetName(">=");
				tok.resetTag(Tag.GEE);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.GEE));
				symbols.put(key, tok);
				current = predict;
				break;
			} else {
				tok.resetName(">");
				tok.resetTag(Tag.GE);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.GE));
				symbols.put(key, tok);
				break;
			}
		case '<':
			predict++;
			if (peek[predict] == '=') {
				tok.resetName("<=");
				tok.resetTag(Tag.LEE);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.LEE));
				symbols.put(key, tok);
				current = predict;
				break;
			} else {
				tok.resetName("<");
				tok.resetTag(Tag.LE);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.LE));
				symbols.put(key, tok);
				break;
			}
		case '=':
			predict++;
			if (peek[predict] == '=') {
				tok.resetName("==");
				tok.resetTag(Tag.EQU);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.EQU));
				symbols.put(key, tok);
				current = predict;
				break;
			} else {
				tok.resetName("=");
				tok.resetTag(Tag.ASS);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, Tag.ASS));
				symbols.put(key, tok);
				break;
			}
		}
	}

	private void Handle_Keyboard(Token tok, hash_key key) throws IOException {
		// int and float,State(3)
		predict = current;
		if (Character.isDigit(peek[predict])) {
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek[predict], 10);
				predict++;
			} while (Character.isDigit(peek[predict]));
			if (peek[predict] != '.') {
				tok.resetline(line);
				tok.resetName(Integer.toString(v));
				tok.resetTag(Tag.INT);
				key.reset_hash_key(new hash_key(number, Integer.toString(v)));
				symbols.put(key, tok);
				current = predict;
				return;
			}
			float x = v;
			float d = 10;
			for (;;) {
				predict++;
				if (!Character.isDigit(peek[predict]))
					break;
				x = x + Character.digit(peek[predict], 10) / d;
				d = d * 10;
			}
			tok.resetline(line);
			tok.resetName(Float.toString(x));
			tok.resetTag(Tag.FLOAT);
			key.reset_hash_key(new hash_key(number, Float.toString(x)));
			symbols.put(key, tok);
			current = predict;
			return;
		}

		if (Character.isLetter(peek[predict])) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek[predict]);
				predict++;
			} while (Character.isLetterOrDigit(peek[predict]));
			String s = b.toString();
			switch (s) {
			case "if":
				tok.resetName(s);
				tok.resetTag(Tag.IF);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, s));
				symbols.put(key, tok);
				break;
			case "else":
				tok.resetName(s);
				tok.resetTag(Tag.ELSE);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, s));
				symbols.put(key, tok);
				break;
			case "int":
				tok.resetName(s);
				tok.resetTag(Tag.BASIC);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, s));
				symbols.put(key, tok);
				break;
			case "float":
				tok.resetName(s);
				tok.resetTag(Tag.FLOAT);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, s));
				symbols.put(key, tok);
				break;
			case "char":
				tok.resetName(s);
				tok.resetTag(Tag.BASIC);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, s));
				symbols.put(key, tok);
				break;
			case "bool":
				tok.resetName(s);
				tok.resetTag(Tag.BASIC);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, s));
				symbols.put(key, tok);
				break;
			case "return":
				tok.resetName(s);
				tok.resetTag(Tag.BASIC);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, s));
				symbols.put(key, tok);
				break;
			default:
				tok.resetName(s);
				tok.resetTag(Tag.STRING);
				tok.resetline(line);
				key.reset_hash_key(new hash_key(number, s));
				symbols.put(key, tok);
			}
			current = predict - 1;
			return;
		}
	}
	
	public void print_kry()
	{
		System.out.println("Symbol Table is:");
		for (int k=0;k<key_count;k++)
		{
			System.out.println("number:"+k+1);
			symbols.get(key_list.get(k)).Print();
		}
	}
}
