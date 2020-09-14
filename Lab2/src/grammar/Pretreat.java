package grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class Pretreat 
{
	public static String emp = "ε";  // 空串
	public static String end = "#";  // 结束符
	public static TreeSet<String> VN = new TreeSet<String>();  // 非终结符集
	public static TreeSet<String> VT = new TreeSet<String>();  // 终结符集
	public static ArrayList<Production> F = new ArrayList<Production>();  // 产生式集
	//  每个符号的first集
	public static HashMap<String,TreeSet<String> > firstMap = new HashMap<String,TreeSet<String>>();
	static//在类被加载的时候运行此段代码
	{
		// 从文件中读取文法，添加相应的产生式
		try 
		{
			read("Productions.txt");
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

		// 添加非终结符   18 个
		VN.add("P'");  VN.add("P");   VN.add("D");   VN.add("S");   VN.add("T");  
		VN.add("X");   VN.add("C");   VN.add("E");   VN.add("E1");  VN.add("E2");  
		VN.add("S1");  VN.add("S2");  VN.add("L");   VN.add("B");   VN.add("B1");  
		VN.add("B2");  VN.add("R");   VN.add("EL");  VN.add("S3");  VN.add("S0");
		// 添加终结符  35 个
		VT.add("proc");  VT.add("id");    VT.add(";");     VT.add("record"); VT.add("integer");
		VT.add("real");  VT.add("[");     VT.add("]");     VT.add("num");    VT.add("=");  
		VT.add("+");     VT.add("*");     VT.add("-");     VT.add("(");      VT.add(")");  
		VT.add("if");    VT.add("then");  VT.add("else");  VT.add("while");  VT.add("do");    
		VT.add("or");     VT.add("and");  VT.add("not");   VT.add("true");   VT.add("false"); 
		VT.add("<");      VT.add("<=");   VT.add("==");    VT.add("!=");     VT.add(">");     
		VT.add(">=");     VT.add("call"); VT.add("begin");     VT.add("end");    VT.add(",");
		
		addFirst();
	}
	//解析表达式
	private static void read(String filename) throws FileNotFoundException
	{
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		while(scanner.hasNext())
		{
			String line = scanner.nextLine().trim();
			if(!line.equals(""))
			{
				String[] div = line.split("->");
				String[] right = div[1].split("\\|");//将合并书写的多个表达式解析成多个
				for(String r:right)
				{
					Production derivation = new Production(div[0].trim()+"->"+r.trim());
					F.add(derivation);//存储到静态的容器中
				}
			}			
		}
		scanner.close();
	}
	
   //计算first集合
	private static void addFirst()
	{
		//将所有的终结符的first都设为本身
		Iterator<String> iterVT = VT.iterator();
		while(iterVT.hasNext())
		{
			String vt = iterVT.next();
			firstMap.put(vt,new TreeSet<String>());
			firstMap.get(vt).add(vt);
		}
		//计算所有非终结符的first集合
		Iterator<String> iterVN = VN.iterator();
		while(iterVN.hasNext())
		{
			String vn = iterVN.next();
			firstMap.put(vn, new TreeSet<String>());//因为后续操作没有交叉涉及firstMap，所以不必分成两个while循环，合成一趟即可
			firstMap.get(vn).addAll(findFirst(vn));
		}
	}
	
	/**
	 * 一个用于查找first的递归函数
	 * @param vn
	 * @return 返回first集
	 */
	private static TreeSet<String> findFirst(String vn)
	{
		TreeSet<String> set = new TreeSet<String>();
		int size1 = 0;
		int size2 = 0;
		while(true)
		{
			size1 = set.size(); 
			for(Production d:F)
			{
				if(d.left.equals(vn))
				{
					if(VT.contains(d.list.get(0)))  // 终结符，直接加入
					{
						set.add(d.list.get(0));
					} 
					else if(d.list.get(0).equals(emp))  // 空符号，直接加入
					{
						set.add(emp);
					}
					else if(VN.contains(d.list.get(0)))  // 非终结符，递归
					{
						if(!vn.equals(d.list.get(0)))  // 去除类似于E->E*E这样的左递归
						{
							TreeSet<String> set2 = findFirst(d.list.get(0));
							set.addAll(set2);  // 再次递归
							if(set2.contains(emp))
							{
								for(int j=1; j<d.list.size(); j++)
								{
									TreeSet<String> set3 = findFirst(d.list.get(j));									
									set.addAll(set3);  // 再次递归
									if(!set3.contains(emp))
									{
										break;
									}
								}
							}							
						}
					}
				}
			}
			size2 = set.size(); 
			if(size1 == size2)
				break;
		}
		
		return set;
	}
}
