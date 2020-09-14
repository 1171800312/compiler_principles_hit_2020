package grammar;


import java.util.ArrayList;
import java.util.List;

public class TokenNode 
{
	public int line;
	public String value;
	public int code;
	public List<TokenNode> children = new ArrayList<TokenNode>();
	public TokenNode(int line, String value, int code)
	{
		this.line = line;
		this.value = value;
		this.code = code;		
	}
	public String toString()
	{
		return this.line + ":< " + this.value + " ," + this.code + " >";
	}
}
