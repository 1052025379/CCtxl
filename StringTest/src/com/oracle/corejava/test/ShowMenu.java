package com.oracle.corejava.test;



import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ShowMenu extends JFrame{
	private JMenu File,Edit,Search,Help,Insert;//主菜单
	private JFrame Frame;
	private JButton Button,Login;
	private JMenuBar Bar;
	private JMenuItem open,close;
	
	
	public ShowMenu() throws HeadlessException {//主界面
		super();
		File=new JMenu("文件");
		Edit=new JMenu("编辑");
		Search=new JMenu("查询");
		Insert=new JMenu("插入");
		Help=new JMenu("帮助");
		
		Bar=new JMenuBar();
		Bar.add(File);	
		Bar.add(Edit);	
		Bar.add(Search);	
		Bar.add(Insert);	
		Bar.add(Help);	
		this.setJMenuBar(Bar);
		this.setSize(400, 300);
		this.setVisible(true);
		this.setTitle("MainMenu");
		
		
	/*	JFrame frame=new JFrame();//主框架
		frame.setSize(400, 400);
		setLocationRelativeTo(null);
		frame.setVisible(false);
		frame.setResizable(true);//带有最大化界面
		frame.setLayout(null);
		frame.setTitle("软件主界面");
		frame.setLocation(400,300 );//距离窗口（400，300）的坐标显示当前的界面frame
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);//关掉**的主界面程序结束了；
		*/
	
	
		JButton Button=new JButton();
		JButton b1=new JButton("按钮1");//按纽
		JButton b2=new JButton("按钮2");//按纽
		JButton b3=new JButton("按钮3");//按纽	
	
		Button.add(b1);
		Button.add(b1);
		Button.add(b1);
		b1.setVisible(true);
		b1.setLocation(420, 320);
		b1.setText("This is a button!");
		b1.setSize(100, 80);
		b2.setSize(100, 80);
		b3.setSize(100, 80);
		
		
		Button.setVisible(true);
		Button.setSize(100, 80);
		Button.setText("这是一个按钮");
		Button.setBackground(getBackground().ORANGE);
	
	
		
		
		open=new JMenuItem("打开");
		close=new JMenuItem("关闭");
		File.add(open);
		File.add(close);
		
		
		
		
		
	}

	public static void main(String[] args) {
		new ShowMenu();
	
	
	
	}
}