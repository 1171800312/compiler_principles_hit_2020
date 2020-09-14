package grammar;

public class test {
	
	public static void main(String args[]) 
	{
       Production prod = new Production("A -> B C D");
       System.out.println("产生式左部："+prod.left);
       System.out.println("产生式右部："+prod.list);
       LRItem item = new LRItem(prod,"a",2);
       System.out.println("一个LR（1）项目："+item);
       LRState state = new LRState(0);
       state.addNewDerivation(item);
       System.out.println("一个LR（1）自动机的状态："+state);
       System.out.println("点后所有符号："+state.getGotoPath());
	}
}
