package LexicalAnalysis;

public class hash_key {
    public int number;
    public String str;
    public hash_key(int num,String s)
    {
    	number=num;
    	str=s;
    }
	public hash_key(String s){
		number=0;
		str=s;
	}
	public void reset_hash_key(hash_key key){
		this.number=key.number;
		this.str=key.str;
	}
	public void Prin_key(){
			System.out.println("< Absolute Line:"+number+"\tName:"+str+">");
	}

}
