package grammar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
//这个类是一个辅助类，用于整合各个分析器状态
public class LRStateSet implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<LRState> states = new ArrayList<LRState>();
	public LRState get(int i)
	{
		return states.get(i);
	}
	
	public int size()
	{
		return states.size();
	}	
	public void writefile()
	{		
        String path = "Sets.txt";
        try 
        {
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
    		int size = states.size();
    		for(int i = 0;i < size;i++)
    		{
    			bw.write("\n"+"I"+i+":"+"\n"); 
    			bw.write(states.get(i).toString());
    			bw.write("\n");
    		} 
            bw.close(); 
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
}
