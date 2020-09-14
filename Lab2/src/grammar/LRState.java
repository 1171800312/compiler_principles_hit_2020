package grammar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class LRState implements Serializable
{
	// 项目集编号,即DFA状态号
	public int id;  
	//LR项目的集合,每个元素表示一个产生式状态
	public ArrayList<LRItem> set = new ArrayList<LRItem>();  
	
    //构造函数，输入是状态号
	public LRState(int id)
	{
		this.id = id;
	}
	
	public boolean contains(LRItem lrd)
	{
		for(LRItem l:set)
		{
			if(l.equalTo(lrd))
			{
				return true;
			}
		}
		return false;
	}
	//向状态中添加新的项目，成功返回true,否则返回false
	public boolean addNewDerivation(LRItem d)
	{
		if(contains(d))
		{
			return false;
		} 
		else 
		{
			set.add(d);
			return true;
		}
	}
	
    //返回点后所有的符号
	public ArrayList<String> getGotoPath()
	{
		ArrayList<String> result = new ArrayList<String>();
		for(LRItem lrd:set)
		{
			if(lrd.d.list.size()==lrd.index)  // 规约状态
			{
				continue;
			}
			String s = lrd.d.list.get(lrd.index);  // "."后面的符号
			if(!result.contains(s))
			{
				result.add(s);
			}
		}
		return result;
	}
	
    //返回此项目的后继项目集
	public ArrayList<LRItem> getLRDs(String s)
	{
		ArrayList<LRItem> result = new ArrayList<LRItem>();
		for(LRItem lrd:set)
		{
			if(lrd.d.list.size() != lrd.index)  // 非规约状态
			{
				String s1 = lrd.d.list.get(lrd.index);
				if(s1.equals(s))
				{
					result.add((LRItem)lrd.clone());
				}
			}
		}
		return result;
	}
	
	public boolean equalTo(LRState state)
	{
		if(this.toString().hashCode()==state.toString().hashCode())
		{
            // if(contains(set,state.set)&&contains(state.set,set)){
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public String toString()
	{
		String result = "";
		for(int i = 0;i < set.size();i++)
		{
			result += set.get(i);
			if(i < set.size()-1)
			{
				result += "\n";
			}
		}
		return result;
	}
	
	public void print()
	{
		Iterator<LRItem> iter = set.iterator();
		while(iter.hasNext())
		{
			iter.next().print();
		}
	}
}
