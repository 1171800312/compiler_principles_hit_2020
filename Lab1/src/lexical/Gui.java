package lexical;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class Gui extends JFrame
{
	private static final long serialVersionUID=1L;
	
	public Gui()
	{
		String filename = "test.txt";
		getContentPane().setForeground(Color.WHITE);
		setTitle("词法分析器 by A25");    //设置显示窗口标题
		setSize(800,660);    //设置窗口显示尺寸
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //置窗口是否可以关闭
		getContentPane().setLayout(null);//设置为绝对定位

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(15, 25, 350, 580);
		scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollPane1);
		
		JTextArea textArea = new JTextArea();
		scrollPane1.setViewportView(textArea);
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setToolTipText("");
		scrollPane2.setBackground(SystemColor.menu);
		scrollPane2.setBounds(400, 25, 350, 460);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollPane2);
		String[] name1 = new String[] {"Line Number","Token", "Category", "Code"};
        JTable table1 = new JTable(new DefaultTableModel(new Object[][] {}, name1));
        table1.setForeground(Color.BLACK);
        table1.setFillsViewportHeight(true);
		table1.setBackground(new Color(255, 255, 255));
		scrollPane2.setViewportView(table1);		
		JButton button1 = new JButton("open");
		button1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
					File file = new File(filename);
					try
					{
						textArea.setText("");
						InputStream in = new FileInputStream(file);
						int tempbyte;
						while ((tempbyte=in.read()) != -1) 
						{
							textArea.append(""+(char)tempbyte);
						}
						in.close();
					}
					catch(Exception event)
					{
						event.printStackTrace();
					}
			}
		});
		button1.setBounds(400,490, 350, 50);
		getContentPane().add(button1);
		JButton button3 = new JButton("analysis");
		button3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				DefaultTableModel model1 = new DefaultTableModel(new Object[][]{},name1);
				table1.setModel(model1);
				Main text_lex = new Main(textArea.getText(), table1, table1);
				text_lex.lex();
				if (table1.getRowCount() == 0)
				{
					JOptionPane.showMessageDialog(null, "Open file first", "Warning", JOptionPane.DEFAULT_OPTION);
				}
			
			}
		});
		button3.setBounds(400, 550, 350, 50);
		getContentPane().add(button3);
		setVisible(true);    //设置窗口是否可见
	}
    public static void main(String[] agrs)
    {    	
        new Gui();    //创建一个实例化对象
    }
}
