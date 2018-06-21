package symbol;

public class Token {
	private String name = "";
    private String tag;
    private int line;
    
    private  static final String def = "basic";
    
    public Token(String Name,String Tag,int Line){
    	name = Name;
    	tag = Tag;	
    	line = Line;
    }
    
    public Token(String Tag){
    	name = def;
    	tag = Tag;
    	line =0;
    }
    
    public void Print() { 
    	//System.out.println("name:"+this.name+"\ttag:"+this.tag+"\tline:"+this.line);}
    	System.out.println(Formate(this.name)+"\t"+Formate(this.tag)+"\t"+this.line);}
    public String getName(){ return this.name;}
    public void resetName(String Name){ this.name=Name;}
    public String getTag() { return this.tag; }
    public void resetTag(String Tag) {this.tag=Tag;}
    public int getline(){ return this.line;}
    public void resetline(int Line)  {this.line=Line;}
    
    private String Formate(String s)
    {
    	while(s.length()<6)
    		s=s+" ";	
    	return s;
    }

}
