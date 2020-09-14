package lexical;

import java.util.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Main
{
	private String text;  // 读入的测试样例文本
	private JTable jtable1;  // 行数-Token-种别码-单词类别
	private JTable jtable2;  // 行数-错误内容-错误信息
	public Main(String text, JTable jtable1, JTable jtable2)
	{
		this.text = text;
		this.jtable1 = jtable1;
		this.jtable2 = jtable2;
	}
	public static int symbol_pos = 0;  // 记录符号表位置
	public static Map<String, Integer> symbol = new HashMap<String, Integer>();  // 符号表HashMap
	
	public static int constant_pos = 0;  // 记录常量位置
	public static Map<String, Integer> constant = new HashMap<String, Integer>();  // 常量表HashMap
	public void lex()
	{
		String[] texts = text.split("\n");
		//按行分割
		symbol.clear();
		//清除符号表
		symbol_pos = 0;
		constant.clear();
		//清除常量表
		constant_pos = 0;
		for(int m = 0; m < texts.length; m++)
		{
			String str = texts[m];
			if (str.equals(""))
				continue;
			//忽略空行
			else 
			{
				char[] strline = str.toCharArray();
				for(int i = 0; i < strline.length; i++) 
				{
					//逐个字符的分析过程
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
                            DefaultTableModel tableModel = (DefaultTableModel)jtable1.getModel();
                            tableModel.addRow(new Object[] {m+1, token, token.toUpperCase(), "-"});
                            jtable1.invalidate();
                        }                      
                        else  // 识别标识符
                        {
                        	if (symbol.isEmpty() || (!symbol.isEmpty() && !symbol.containsKey(token))) 
                        		//当前识别到的token没有重复出现
                        	{  
                                symbol.put(token, symbol_pos);   
                                symbol_pos++;
                            }
                        	DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                            tableModel1.addRow(new Object[] {m+1, token, "IDN", token});
                            jtable1.invalidate();
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
                                //依据当前状态索引DFA转换表
                                if (ch != '#' && util.is_digit_state(ch, tmpstr[k]) == 1) 
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
                        if (haveMistake)   // 错误处理策略是直接读取到下一个界符
                        {  
                        	while (ch != '\0' && ch != ',' && ch != ';' && ch != ' ')
                            {  
                                token += ch;  
                                i++;
                                if (i >= strline.length) 
                                	break;  
                                ch = strline[i];  
                            }  
                        	DefaultTableModel tableModel2 = (DefaultTableModel) jtable2.getModel();
                            tableModel2.addRow(new Object[] {m+1, token, "unsigned int error","ERROR"});
                            jtable2.invalidate();
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
                            	DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                                tableModel1.addRow(new Object[] {m+1, token, "SCONST", token});
                                jtable1.invalidate();    
                            } 
                        	else if (isfloat) 
                            {  
                            	DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                                tableModel1.addRow(new Object[] {m+1, token, "FCONST", token});
                                jtable1.invalidate();    
                            } 
                            else
                            {   
                            	DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                                tableModel1.addRow(new Object[] {m+1, token, "CONST", token});
                                jtable1.invalidate();   
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
                                if (util.is_char_state(ch, tmpstr[k])) 
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
                        	DefaultTableModel tableModel2 = (DefaultTableModel) jtable2.getModel();
                            tableModel2.addRow(new Object[] {m+1, token, "String error","ERROR"});
                            jtable2.invalidate();
                            i--;  
                        } 
                        else 
                        {  
                        	if (constant.isEmpty() || (!constant.isEmpty() && !constant.containsKey(token))) 
                        	{  
                        		constant.put(token, constant_pos);   
                                constant_pos++;
                            }
                        	DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                            tableModel1.addRow(new Object[] {m+1, token, "CCONST", token});
                            jtable1.invalidate(); 
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
                                if (util.is_string_state(ch, tmpstr[k])) 
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
                        	DefaultTableModel tableModel2 = (DefaultTableModel) jtable2.getModel();
                            tableModel2.addRow(new Object[] {m+1, str1, "String error"});
                            jtable2.invalidate();  
                            i--;  
                        } 
                        else 
                        {  
                        	if (constant.isEmpty() || (!constant.isEmpty() && !constant.containsKey(token))) 
                        	{  
                        		constant.put(token, constant_pos);   
                                constant_pos++;
                            }
                        	DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                            tableModel1.addRow(new Object[] {m+1, str1, "STRCONST", str1});
                            jtable1.invalidate();  
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
                                i--; // 指针回退 
                            }  
                            DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                            tableModel1.addRow(new Object[] {m+1, token, "OP", token});
                            jtable1.invalidate();    
                            token = "";  
                        }
                        // 注释可能是‘//’也可能是‘/*’
                        else 
                        {
                        	Boolean haveMistake = false;
                        	int State = 0;
                        	if (ch == '*') 
                        	{  
                        		// ch == '*'
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
                                        if (util.is_note_state(ch, tmpstr[k], state)) 
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
                        		DefaultTableModel tableModel2 = (DefaultTableModel) jtable2.getModel();
                                tableModel2.addRow(new Object[] {m+1, token, "Note error","ERROR"});
                                jtable2.invalidate();  
                                --i;
                        	}
                        	else
                        	{
                        		DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                                tableModel1.addRow(new Object[] {m+1, token, "NOTE", "-"});
                                jtable1.invalidate();
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
                            	if (util.isPlusSame(strline[i-1]) && ch == strline[i-1])  // 后面可以用一个和自己一样的
                                    token += ch;  
                                else  
                                    i--;   
                            }  
                        }                  
                        if(token.length() == 1)  //判断是否为界符
                        {
                        	String signal = token;
                        	if(util.isDelimiter(signal))
                        	{
                        		DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                                tableModel1.addRow(new Object[] {m+1, token,util.getName(token), "-"});
                                jtable1.invalidate();
                        	}                        
                        	else
                        	{
                        		DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                                tableModel1.addRow(new Object[] {m+1, token, "OP", token});
                                jtable1.invalidate();
                        	}
                        }
                        else
                        {
                        	DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
                            tableModel1.addRow(new Object[] {m+1, token, "OP", token});
                            jtable1.invalidate();
                        }                        
                        token = "";		
                    }
					else  //不合法字符
                    {  
                        if(ch != ' ' && ch != '\t' && ch != '\0' && ch != '\n' && ch != '\r')  
                        {                         	
                        	DefaultTableModel tableModel2 = (DefaultTableModel) jtable2.getModel();
                            tableModel2.addRow(new Object[] {m+1, token, "Unknown char"});
                            jtable2.invalidate();
                            System.out.println(ch);
                        }  
                    }				
				}
			} 
		}
    }
}
