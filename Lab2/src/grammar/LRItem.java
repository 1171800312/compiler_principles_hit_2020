package grammar;

import java.io.Serializable;
//这个类表示一个产生式可能所处的状态
public class LRItem implements java.lang.Cloneable,Serializable
{
	public Production d;  // 产生式
	public String lr;  // 展望符
	public int index;  // 当前点所处位置
	 // 构造函数，要求输入产生式本身，展望符和点所在位置
	public LRItem(Production d,String lr,int index)
	{
		this.d = d;
		this.lr = lr;
		this.index = index;
	}
	
	public String toString()
	{
		String result = d.left+"->";
		int length = d.list.size();
		for(int i = 0;i < length;i++)
		{
			if(length == 1 && d.list.get(0).equals("ε"))
			{
				result += " .";
				break;
			}
			else
			{
				result += " ";
				if(i == index)//在index处额外输出一个点
				{
					result += ".";
				}
				result += d.list.get(i);
			}
		}
		if(index == length && !d.list.get(0).equals("ε"))
		{
			result += ".";
		}
		result += " ,";
		result += lr;
		return result;
	}
	
	public boolean equalTo(LRItem lrd)
	{
		if(d.equalTo(lrd.d)&&lr.hashCode()==lrd.lr.hashCode()&&index==lrd.index)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public void print()
	{
		System.out.println(this.toString());
	}
	
	public Object clone()
	{
		return new LRItem(d,lr,index);
	}

}
