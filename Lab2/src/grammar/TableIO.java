package grammar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TableIO {
	public static void saveObjToFile(LRTable p){
		try {
			//写对象流的对象
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("LRtable.bin"));
			oos.writeObject(p);                 //将Person对象p写入到oos中
			oos.close();                        //关闭文件流
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/*
	 * 从文件中读出对象，并且返回lrtable对象
	 */
	public static LRTable getObjFromFile(){
		try {
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream("LRtable.bin"));
			
			LRTable person=(LRTable)ois.readObject();              //读出对象
			
			return person;                                       //返回对象
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
