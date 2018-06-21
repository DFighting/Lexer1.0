package symbol;

import LexicalAnalysis.hash_key;

public class FullToken {
	public static String Name;
	public static hash_key key=new hash_key(0," ");
	public FullToken(String name,hash_key hashkey){
		Name=name;
		key.reset_hash_key(hashkey);		
	}
public void Prin_FullToken()
{
	System.out.print("Name:"+Name+"\tkey:");
	key.Prin_key();
	
}
}
