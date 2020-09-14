package grammar;

import java.util.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class Lexical
{
	private String text;  // 读入的测试样例文本	
	private ArrayList<TokenNode> tokenList;  // 
	public Lexical(String text,ArrayList<TokenNode> tokenList)//JTable jtable1, JTable jtable2, JTable jtable3, JTable jtable4)
	{
		this.text = text;
		this.tokenList = tokenList;
	}

	public static int symbol_pos = 0;  // 记录符号表位置
	public static Map<String, Integer> symbol = new HashMap<String, Integer>();  // 符号表HashMap
	
	public static int constant_pos = 0;  // 记录常量位置
	public static Map<String, Integer> constant = new HashMap<String, Integer>();  // 常量表HashMap
	
	public void analyze()
	{
		String[] texts = text.split("\n");
		symbol.clear();
		symbol_pos = 0;
		for(int m = 0; m < texts.length; m++)
		{
			String str = texts[m];
			if (str.equals(""))
				continue;
			else 
			{
				char[] strline = str.toCharArray();
				for(int i = 0; i < strline.length; i++) 
				{
					char ch = strline[i];
					if (ch == ' ')
						continue;	
					
					String token = "";  
					
					if (util.isAlpha(ch)) // 识别关键字和标识符  
                    {  
                        do 
                        {  
                            token += ch;  
                            i++;  
                            if(i >= strline.length) 
                            	break;  
                            ch = strline[i];  
                        } while (ch != '\0' && (util.isAlpha(ch) || util.isDigit(ch)));  
                        i--; 
                        if (util.isKeyword(token))  // 识别关键字
                        {  
                            tokenList.add(new TokenNode(m+1,token,util.keywords_code.get(token)));
                        }                      
                        else  // 识别标识符
                        {
                        	if (symbol.isEmpty() || (!symbol.isEmpty() && !symbol.containsKey(token))) 
                        	{  
                                symbol.put(token, symbol_pos);   
                                symbol_pos++;
                            }
                            tokenList.add(new TokenNode(m+1,token,1));
                        }
                        token = "";
                    }
					
					else if(util.isDigit(ch))  // 识别无符号数
					{
						int state = 1;
						int k;
                        Boolean isfloat = false;  
                        Boolean isSci_not = false;  
                        while ( (ch != '\0') && (util.isDigit(ch) || ch == '.' || ch == 'e' 
                        		|| ch == '-' || ch == 'E' || ch == '+'))
                        {
                        	if (ch == '.') 
                        		isfloat = true;
                        	if (ch == 'e' || ch == 'E')  
                        	{
                        		isfloat = false;
                        		isSci_not = true;
                        	}
                        	
                            for (k = 0; k <= 6; k++) 
                            {                             	
                                char tmpstr[] = util.digitDFA[state].toCharArray();  
                                if (ch != '#' && util.in_digitDFA(ch, tmpstr[k]) == 1) 
                                {  
                                    token += ch;  
                                    state = k;  
                                    break;  
                                }  
                            }
                            if (k > 6) 
                            	break;
                            i++;
                            if (i >= strline.length) 
                            	break;  
                            ch = strline[i]; 
                        }
                        
                        Boolean haveMistake = false;  
                        
                        if (state == 2 || state == 4 || state == 5)  // 非终态
                        {  
                            haveMistake = true;  
                        }                     
                        else  // 无符号数后面紧跟的符号错误
                        {  
                            if ((ch == '.') || (!util.isOperator(String.valueOf(ch)) 
                            		&& !util.isDigit(ch) && !util.isDelimiter(String.valueOf(ch))
                            		&& ch != ' ')) 
                                haveMistake = true;  
                        }  
                                            
                        if (haveMistake)   // 错误处理 
                        {  
                        	while (ch != '\0' && ch != ',' && ch != ';' && ch != ' ')
                            {  
                                token += ch;  
                                i++;
                                if (i >= strline.length) 
                                	break;  
                                ch = strline[i];  
                            }
                        }
                        else 
                        {  
                        	if (constant.isEmpty() || (!constant.isEmpty() && !constant.containsKey(token))) 
                        	{  
                        		constant.put(token, constant_pos);   
                                constant_pos++;
                            }
                        	if (isSci_not)
                        	{  
                                tokenList.add(new TokenNode(m+1,token,4));
                            } 
                        	else if (isfloat) 
                            {  
                                tokenList.add(new TokenNode(m+1,token,3));   
                            } 
                            else
                            {   
                                tokenList.add(new TokenNode(m+1,token,2));   
                            }  
                        }
                        i--;
                        token = "";
                    }
					
					else if(ch == '\'')  // 识别字符常量
					{
						int state = 0;				        
                        token += ch;                    
                        while (state != 3) 
                        {  
                            i++;
                            if (i >= strline.length) 
                            	break;
                            ch = strline[i]; 
                            Boolean flag = false;
                            for (int k = 0; k < 4; k++) 
                            {  
                                char tmpstr[] = util.charDFA[state].toCharArray();  
                                if (util.in_charDFA(ch, tmpstr[k])) 
                                {            
                                    token += ch;
                                    state = k; 
                                    flag = true;
                                    break;  
                                }  
                            }  
                            if (flag == false)
                            	break;
                        }
                        if (state != 3) 
                        {  
                            i--;  
                        } 
                        else 
                        {  
                        	if (constant.isEmpty() || (!constant.isEmpty() && !constant.containsKey(token))) 
                        	{  
                        		constant.put(token, constant_pos);   
                                constant_pos++;
                            }
                            tokenList.add(new TokenNode(m+1,token,5));
                        }
                        token = "";
					}

					else if (ch == '"')  // 识别字符串常量
					{
						Boolean haveMistake = false;
						String str1 = "";  
						str1 += ch;  

                        int state = 0;  
                        
						
                        while (state != 3) 
                        {  
                            i++;                             
                            if (i>=strline.length-1) 
                            {  
                                haveMistake = true;  
                                break;  
                            }                              
                            ch = strline[i]; 
                            if (ch == '\0') 
                            {  
                                haveMistake = true;  
                                break;  
                            }
                            for (int k = 0; k < 4; k++) 
                            {  
                                char tmpstr[] = util.stringDFA[state].toCharArray();  
                                if (util.in_stringDFA(ch, tmpstr[k])) 
                                {  
                                	str1 += ch;  
                                    if (k == 2 && state == 1)  // 转义字符  
                                    {  
                                        if (util.isEsSt(ch))
                                            token = token + '\\' + ch;  
                                        else  
                                            token += ch;  
                                    } 
                                    else if (k != 3 && k != 1)  
                                        token += ch;  
                                    state = k;  
                                    break;  
                                }  
                            }  
                        }
                        if (haveMistake) 
                        {   
                            i--;  
                        } 
                        else 
                        {  
                        	if (constant.isEmpty() || (!constant.isEmpty() && !constant.containsKey(token))) 
                        	{  
                        		constant.put(token, constant_pos);   
                                constant_pos++;
                            }
                            tokenList.add(new TokenNode(m+1,str1,6));
                        }  
                        token = "";		
					}
					
					
					else if (ch == '/')  //  识别注释//
					{
						token += ch;  
                        i++;
                        if (i>=strline.length) 
                        	break;  
                        ch = strline[i];
                        
						//不是多行注释及单行注释
                        if (ch != '*' && ch != '/')   
                        {  
                            if (ch == '=')  
                                token += ch; // /=  
                            else 
                            {  
                                i--; // 指针回退 // /  
                            }  
                           
                            tokenList.add(new TokenNode(m+1,token,util.operator_code.get(token)));  
                            token = "";  
                        }
                        else 
                        {
                        	Boolean haveMistake = false;
                        	int State = 0;
                        	if (ch == '*') 
                        	{  
                        		token += ch;  
                                int state = 2;  

                                while (state != 4) 
                                {                                      
                                    if (i == strline.length-1) 
                                    {  
                                    	token += '\n';  
                                    	m++;
                                    	if (m >= texts.length)
                                    	{
                                    		haveMistake = true;  
                                            break;  
                                    	}
                                		str = texts[m];
                                		if (str.equals(""))
                                			continue;
                                		else 
                                		{
                                			strline = str.toCharArray();
                                			i=0;
                                			ch = strline[i];
                                		}
                                    }  
                                    else
                                    {
                                    	i++;
	                                    ch = strline[i];
                                    }
                               
                                    for (int k = 2; k <= 4; k++) 
                                    {  
                                        char tmpstr[] = util.noteDFA[state].toCharArray();  
                                        if (util.in_noteDFA(ch, tmpstr[k], state)) 
                                        {  
                                            token += ch;  
                                            state = k;  
                                            break;  
                                        }  
                                    }  
                                }
                                State = state;
                            }
                        	else if(ch == '/')
                        	{
                        		//单行注释读取所有字符
                        		int index = str.lastIndexOf("//");  
                                
                                String tmpstr = str.substring(index);  
                                int tmpint = tmpstr.length();  
                                for(int k=0;k<tmpint;k++)                                     
                                  i++;    
                                token = tmpstr;
                                State = 4;
                        	}
                        	if(haveMistake || State != 4)
                        	{
                                --i;
                        	}
                        	else
                        	{
                                tokenList.add(new TokenNode(m+1,token,7));
                        	}
                        	token = "";
                        }	
					}
					
					else if (util.isOperator(String.valueOf(ch)) || util.isDelimiter(String.valueOf(ch)))  // 运算符和界符
                    {  
						token += ch; 						
                        if (util.isPlusEqu(ch))  // 后面可以用一个"="
                        {  
                            i++;
                            if (i>=strline.length) 
                            	break;  
                            ch = strline[i];  
                            if (ch == '=')  
                                token += ch;  
                            else 
                            {                              	
                            	i--;   
                            }  
                        }                  
                        if(token.length() == 1)  //判断是否为界符
                        {
                        	String signal = token;
                        	if(util.isDelimiter(signal))
                        	{
                                tokenList.add(new TokenNode(m+1,token,util.delimiter_code.get(token)));
                        	}                        
                        	else
                        	{
                                tokenList.add(new TokenNode(m+1,token,util.operator_code.get(token)));
                        	}
                        }
                        else
                        {
                            tokenList.add(new TokenNode(m+1,token,util.operator_code.get(token)));
                        }                        
                        token = "";		
                    }
					
					else  //不合法字符
                    {  
                        if(ch != ' ' && ch != '\t' && ch != '\0' && ch != '\n' && ch != '\r')  
                        {                         	
                            System.out.println(ch);
                        }  
                    }				
				}
			} 
		}
    }
}
