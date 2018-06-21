package symbol;

public class Word extends Token{
	
	public String index = "";

	public Word(String s,String tag) {
		super(tag);
		index = s;	
	}
	
	public static final Word
	 
	and = new Word("&&",Tag.AND),  or = new Word("||",Tag.OR ),
    equ = new Word("==",Tag.EQU),  ne = new Word( "!=", Tag.NE ),
    le  = new Word("<", Tag.LE ),  ge = new Word( ">", Tag.GE ),
    lee = new Word("<=",Tag.LEE),  gee = new Word(">=", Tag.GE ),

    add   = new Word("+",Tag.ADD),        sub = new Word("-",Tag.SUB),
    minus = new Word("minus",Tag.MINUS ), div = new Word("/",Tag.DIV),
    
    True   = new Word( "true",  Tag.TRUE  ),
    False  = new Word( "false", Tag.FALSE );
   

}
