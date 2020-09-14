package gui;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import grammar.Lexical;
import grammar.SyntaxParser;
import grammar.Tree;
import semantic.FourAddr;
import semantic.Symbol;
import semantic.Smantic;
import semantic.util;


import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import javax.swing.UIManager;

public class Gui extends JFrame
{

	private static final long serialVersionUID=1L;
	private static String file_name = ""; 
//	public void Gui_3()
//	{
//		JFrame JFrame = new JFrame();
//		JFrame.setForeground(Color.WHITE);
//		JFrame.setTitle("分析结果");  
//		JFrame.setSize(1017,868);    
//		JFrame.setDefaultCloseOperation(2);   
//		JFrame.getContentPane().setLayout(null);
//		JScrollPane scrollPane2 = new JScrollPane();
//		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//		scrollPane2.setToolTipText("");
//		scrollPane2.setBackground(SystemColor.menu);
//		scrollPane2.setBounds(15, 25, 965, 466);
//		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		JFrame.getContentPane().add(scrollPane2);
//		String[] name1 = new String[] {"指令编号","四元式", "三地址"};
//        JTable table1 = new JTable(new DefaultTableModel(new Object[][] {}, name1));
//        table1.setForeground(Color.BLACK);
//        table1.setFillsViewportHeight(true);
//		table1.setBackground(new Color(255, 255, 255));
//		scrollPane2.setViewportView(table1);
//		JScrollPane scrollPane4 = new JScrollPane();
//		scrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		scrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//		scrollPane4.setBackground(SystemColor.menu);
//		scrollPane4.setBounds(15, 512, 965, 289);
//		JFrame.getContentPane().add(scrollPane4);
//		String[] name3 = new String[] {"错误报告"};
//		JTable table3 = new JTable(new DefaultTableModel(new Object[][] {}, name3));
//		table3.setForeground(Color.RED);
//		table3.setFillsViewportHeight(true);
//		table3.setBackground(Color.WHITE);
//		scrollPane4.setViewportView(table3);
//		List<Stack<Symbol>> table = new ArrayList<Stack<Symbol>>();  // 符号表  
//		List<String> three_addr = new ArrayList<String>();  // 三地址指令序列
//		List<FourAddr> four_addr = new ArrayList<FourAddr>();  // 三地址指令序列
//		List<String> errors = new ArrayList<String>();  // 错误序列
//	
//		Smantic se = new Smantic(file_name,table,three_addr,four_addr,errors);
//		
//		Object[][] gui_ins = util.gui_ins(three_addr,four_addr);
//		Object[][] gui_table = util.gui_table(table);
//		Object[][] gui_errors = util.gui_errors(errors);
//		DefaultTableModel model1 = new DefaultTableModel(gui_ins,name1);
//		table1.setModel(model1);
//		DefaultTableModel model3 = new DefaultTableModel(gui_errors,name3);
//		table3.setModel(model3);
//
//		JFrame.setVisible(true);    //设置窗口是否可见
//	}
	public void Gui_3()
	{
		JFrame JFrame = new JFrame();

		JFrame.setForeground(Color.WHITE);
//		JFrame.setFont(new Font("宋体", Font.BOLD, 25));
		
		JFrame.setTitle("分析结果");    //设置显示窗口标题
		JFrame.setSize(1017,868);    //设置窗口显示尺寸
		JFrame.setDefaultCloseOperation(2);    //置窗口是否可以关闭
		JFrame.getContentPane().setLayout(null);
		

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setToolTipText("");
		scrollPane2.setBackground(SystemColor.menu);
		scrollPane2.setBounds(15, 25, 965, 466);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JFrame.getContentPane().add(scrollPane2);
		
		String[] name1 = new String[] {"序号","四元式", "三地址"};
        JTable table1 = new JTable(new DefaultTableModel(new Object[][] {}, name1));
        table1.setForeground(Color.BLACK);
        table1.setFillsViewportHeight(true);
      //  table1.setFont(new Font("宋体", Font.BOLD, 15));
		table1.setBackground(new Color(255, 255, 255));
		scrollPane2.setViewportView(table1);
		
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane3.setBackground(SystemColor.menu);
		scrollPane3.setBounds(479, 513, 501, 288);
		JFrame.getContentPane().add(scrollPane3);
		
		String[] name2 = new String[] {"表号", "符号", "类型", "offset"};
		JTable table2 = new JTable(new DefaultTableModel(new Object[][] {}, name2));
		table2.setForeground(Color.BLACK);
//		table2.setFont(new Font("宋体", Font.BOLD, 15));
		table2.setFillsViewportHeight(true);
		table2.setBackground(Color.WHITE);
		//table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane3.setViewportView(table2);
		
		
		JScrollPane scrollPane4 = new JScrollPane();
		scrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane4.setBackground(SystemColor.menu);
		scrollPane4.setBounds(15, 512, 449, 289);
		JFrame.getContentPane().add(scrollPane4);
		
		String[] name3 = new String[] {"错误报告"};
		JTable table3 = new JTable(new DefaultTableModel(new Object[][] {}, name3));
		table3.setForeground(Color.RED);
	//	table3.setFont(new Font("楷体", Font.BOLD, 15));
		table3.setFillsViewportHeight(true);
		table3.setBackground(Color.WHITE);
		scrollPane4.setViewportView(table3);

		List<Stack<Symbol>> table = new ArrayList<Stack<Symbol>>();  // 符号表  
		List<String> three_addr = new ArrayList<String>();  // 三地址指令序列
		List<FourAddr> four_addr = new ArrayList<FourAddr>();  // 三地址指令序列
		List<String> errors = new ArrayList<String>();  // 错误序列
		
		Smantic se = new Smantic(file_name,table,three_addr,four_addr,errors);
		
		Object[][] gui_ins = util.gui_ins(three_addr,four_addr);
		Object[][] gui_table = util.gui_table(table);
		Object[][] gui_errors = util.gui_errors(errors);
		
		DefaultTableModel model1 = new DefaultTableModel(gui_ins,name1);
		table1.setModel(model1);
		
		DefaultTableModel model2 = new DefaultTableModel(gui_table,name2);
		table2.setModel(model2);
	
		DefaultTableModel model3 = new DefaultTableModel(gui_errors,name3);
		table3.setModel(model3);

		JFrame.setVisible(true);    //设置窗口是否可见
	}

	public Gui()
	{
		getContentPane().setForeground(Color.WHITE);
		setTitle("语法分析器 by A25");    //设置显示窗口标题
		setSize(670,610);    //设置窗口显示尺寸
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //置窗口是否可以关闭
		getContentPane().setLayout(null);

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(10, 18, 630, 476);
		scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollPane1);
		
		JTextArea textArea = new JTextArea();
		scrollPane1.setViewportView(textArea);

		JButton button1 = new JButton("openfile");
		button1.setBackground(UIManager.getColor("Button.highlight"));
		button1.setForeground(Color.BLACK);
		
		button1.setBounds(10, 509, 200, 46);
		getContentPane().add(button1);
		
		JButton button2 = new JButton("clear");
		button2.setBackground(UIManager.getColor("Button.highlight"));
		button2.setForeground(Color.BLACK);
		
		button2.setBounds(430, 509, 200, 46);
		getContentPane().add(button2);
		JButton button3_3 = new JButton("analysis");
		button3_3.setBounds(220, 509, 200,46);
		getContentPane().add(button3_3);
		button1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText("");
				
				JFileChooser file_open_filechooser = new JFileChooser();
				file_open_filechooser.setCurrentDirectory(new File("."));
				file_open_filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = file_open_filechooser.showOpenDialog(scrollPane1);	
				
				if (result == JFileChooser.APPROVE_OPTION) // 证明有选择
				{
					file_name = file_open_filechooser.getSelectedFile().getPath();
					// 读取文件，写到JTextArea里面
					try
					{
						FileReader reader = new FileReader(file_name);
			            BufferedReader br = new BufferedReader(reader);
			            String line;

			            while ((line = br.readLine()) != null) 
			            {
							textArea.append(line);
							textArea.append("\n");
			            }
						reader.close();
					}
					catch(Exception event)
					{
						event.printStackTrace();
					}
					if (file_name.equals("") || textArea.getText().equals(""))
					{	
						JOptionPane.showMessageDialog(null,
								"请先打开文件", "Warning", JOptionPane.DEFAULT_OPTION);
					}
				} 
			}
		});

		button2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				textArea.setText("");
			}
		});
		

		button3_3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if (file_name.equals("") || textArea.getText().equals(""))
				{	
					JOptionPane.showMessageDialog(null,
							"请先打开文件", "Warning", JOptionPane.DEFAULT_OPTION);
				}
				Gui_3();
			}
		});
		setVisible(true);    //设置窗口是否可见
	}
	
	
    public static void main(String[] agrs)
    {    	
        new Gui();    //创建一个实例化对象
    }
}



