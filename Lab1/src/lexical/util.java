package lexical;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class util
{
	public static String[] readfromfile(String filename) {
	File file = new File(filename);
	try
	{
		String temp = "";
		InputStream in = new FileInputStream(file);
		int tempbyte;
		while ((tempbyte=in.read()) != -1) 
		{
			temp+=(""+(char)tempbyte);
		}
		in.close();
		return temp.split("\n");
	}
	catch(Exception event)
	{
		event.printStackTrace();
	}
	return null;
	}
    //关键字
	public static String keywords[] = { "auto", "double", "int", "struct",  
        "break", "else", "long", "switch", "case", "enum", "register",  
        "typedef", "char", "extern", "return", "union", "const", "float",  
        "short", "unsigned", "continue", "for", "signed", "void",  
        "default", "goto", "sizeof", "volatile", "do", "if", "while",  
        "static", "String"};
	public static Map<String, Integer> keywords_code = new HashMap<String, Integer>()
	{
		private static final long serialVersionUID=1L;
		{
			for (int i = 0; i < keywords.length; i++)
			{
				put(keywords[i], i + 101);
			}
		}	
	};	
	public static Boolean isKeyword(String s) 
	{  
        return keywords_code.containsKey(s);  
    }
    public static String operator[] = { "+", "-", "*", "/", "%", "++", "--",
    		"<", "<=", ">", ">=", "==", "!=",
    		"&&", "||", "!","~", "&", "|", "^", ">>", "<<", 
    		"+=", "-=", "*=", "/=", "%=", "&=", " ^=", "|=", "<<=", ">>="};
	public static Map<String, Integer> operator_code = new HashMap<String, Integer>()
	{
		private static final long serialVersionUID=1L;
		{
			for (int i = 0; i < operator.length; i++)
			{
				put(operator[i], i + 201);
			}
		}	
	};
	public static Boolean isOperator(String s) 
    {  
		return operator_code.containsKey(s);
    }
	 
	public static String getName(String s)
	{
		if(s.equals(","))
			return "COL";
		if(s.equals(";"))
			return "SEMI";
		if(s.equals("["))
			return "MLP";
		if(s.equals("]"))
			return "MRP";
		if(s.equals("{"))
			return "LP";
		if(s.equals("}"))
			return "RP";
		if(s.equals("("))
			return "SLP";
		if(s.equals(")"))
			return "SRP";
		if(s.equals("="))
			return "EQU";
		return "ERROR";
		
	}
	public static String delimiter[] = { ",", ";", "[", "]", "{", "}", "(", ")","="};
	public static Map<String, Integer> delimiter_code = new HashMap<String, Integer>()
	{
		private static final long serialVersionUID=1L;
		{
			for (int i = 0; i < delimiter.length; i++)
			{
				put(delimiter[i], i + 301);
			}
		}	
	};
	public static Boolean isDelimiter(String s) 
    {  
		return delimiter_code.containsKey(s);
    }
	
	
	public String toUpper(String s)
	{
		return s.toUpperCase();
	}
	
	public static Boolean isPlusEqu(char ch)  // 这些符号后面可跟运算符"="
    {  
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '=' || ch == '>' 
        		|| ch == '<' || ch == '&' || ch == '|'  || ch == '^' || ch == '%' || ch == '!' ;  
    }
	
	public static Boolean isPlusSame(char ch)  // 这些符号后面可再跟相同运算符  
    {  
        return ch == '+' || ch == '-' || ch == '&' || ch == '|' || ch == '>' || ch == '<';  
    }
	
	public static Boolean isAlpha(char ch)
	{
	    return ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_');
	}

	public static Boolean isDigit(char ch)
	{  
        return (ch >= '0' && ch <= '9');  
    }


	// String DFA : a代表任意字符，b代表除\和"之外的字符
	public static String stringDFA[] = 
	{ 
		"#\\b#", 
		"##a#", 
		"#\\b\"", 
		"####" 
	};
	
	/**
	 * 字符串DFA状态匹配函数
	 * @param ch 当前字符
	 * @param key 状态表中的字符
	 * @return 匹配成功返回true，否则返回false 
	 */
	public static Boolean is_string_state(char ch, char key) 
	{  
        if (key == 'a')  
            return true;  
        if (key == '\\')  
            return ch == key;  
        if (key == '"')  
            return ch == key;  
        if (key == 'b')  
            return ch != '\\' && ch != '"';  
        return false;  
    }
	
	// char DFA : a代表任意字符，b代表除\和'之外的字符
	public static String charDFA[] = 
	{
		"#\\b#", 
		"##a#", 
		"###\'", 
		"####"
	}; 
	
	/**
	 * 字符DFA状态匹配函数
	 * @param ch 当前字符
	 * @param key 状态表中的字符
	 * @return 匹配成功返回true，否则返回false 
	 */
	public static Boolean is_char_state(char ch, char key) 
	{  
        if (key == 'a')  
            return true;  
        if (key == '\\')  
            return ch == key;  
        if (key == '\'')  
            return ch == key;  
        if (key == 'b')  
            return ch != '\\' && ch != '\'';  
        return false;  
    }
	public static Boolean isEsSt(char ch) 
	{  
        return ch == 'a' || ch == 'b' || ch == 'f' || ch == 'n' || ch == 'r'  
                || ch == 't' || ch == 'v' || ch == '?' || ch == '0';  
    }
	public static String digitDFA[] = readfromfile("digitdfa.txt");
	/**
	 * 数字DFA状态匹配函数
	 * @param ch 当前字符
	 * @param test 状态表中的字符
	 * @return 匹配成功返回true，否则返回false 
	 */
	public static int is_digit_state(char ch, char test) 
	{  
        if (test == 'd') 
        {  
            if (isDigit(ch))  
                return 1;  
            else  
                return 0;  
        }  
        else if (test == '-')
        {
        	if (ch == '-' || ch == '+')
        		return 1;
        	else
        		return 0;
        }
        else if (test == 'e')
        {
        	if (ch == 'e' || ch == 'E')
        		return 1;
        	else
        		return 0;
        }
        else
        {
        	if (ch == test)
        		return 1;
        	else
        		return 0;
        }
    }
	public static String noteDFA[] = readfromfile("notedfa.txt");
	
	/**
	 * 注释DFA状态匹配函数
	 * @param ch 当前字符
	 * @param test 状态表中的字符
	 * @return 匹配成功返回true，否则返回false 
	 */
	public static Boolean is_note_state(char ch, char nD, int s) 
	{  
        if (s == 2) 
        {  
            if (nD == 'c') 
            {  
                if (ch != '*') 
                	return true;  
                else 
                	return false;  
            }  
        }  
        if (s == 3) 
        {  
            if (nD == 'c') 
            {  
                if (ch != '*' && ch != '/') 
                	return true;  
                else 
                	return false;  
            }  
        }  
        return (ch == nD) ? true : false;  
    }
	public static void main(String args[])
	{
		String[] a = util.readfromfile("test.txt");
		for(String temp:a) {
			System.out.println(temp);
		}
	}
}
