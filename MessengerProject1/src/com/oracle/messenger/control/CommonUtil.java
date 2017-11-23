package com.oracle.messenger.control;

import java.awt.Container;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 * 这是一个公共的帮助类，
 * @author TengSir
 *
 */
public class CommonUtil {

	public static void  initialJLabel(JLabel l,String text,Icon  image,int x,int y,int width,int height,Container  parent){
		l.setBounds(x, y, width, height);
		if(text!=null)
		l.setText(text);
		l.setIcon(image);
		parent.add(l);
	}
	public static void  initialJButton(JButton l,String text,Icon  image,int x,int y,int width,int height,Container  parent,ActionListener listener){
		l.setBounds(x, y, width, height);
		l.setText(text);
		l.setIcon(image);
		l.addActionListener(listener);
		parent.add(l);
	}
	public static void  initialJButton(JToggleButton l,String text,Icon  image,int x,int y,int width,int height,Container  parent,ActionListener listener){
		l.setBounds(x, y, width, height);
		l.setText(text);
		l.setIcon(image);
		l.addActionListener(listener);
		parent.add(l);
	}
	public static void  initialJTextfield(JTextField l,String text,int x,int y,int width,int height,Container  parent){
		l.setBounds(x, y, width, height);
		l.setText(text);
		parent.add(l);
	}
	public static void  initialJPasswordField(JPasswordField l,String text,int x,int y,int width,int height,Container  parent){
		l.setBounds(x, y, width, height);
		l.setText(text);
		parent.add(l);
	}
	public static void  initialJFrame(JFrame f,String title,Image image,int x,int y,int width,int height,boolean isResizable,boolean isVisiable,boolean isTopShow,int defaultCloseOperation,boolean isRelativeTo,JComponent relativeTo,LayoutManager  lay){
		f.setTitle(title);
		f.setIconImage(image);
		f.setSize(width, height);
		if(isRelativeTo)
			f.setLocationRelativeTo(relativeTo);
		else
			f.setLocation(x, y);
		f.setResizable(isResizable);
		f.setAlwaysOnTop(isTopShow);
		f.setVisible(isVisiable);
		f.setDefaultCloseOperation(defaultCloseOperation);
		f.setLayout(lay);
	}
	public static void initialJScrollPane(JScrollPane p,int x,int y,int width,int height,Container parent)
	{
		p.setBounds(x, y, width, height);
		parent.add(p);
	}
	public static void initialJPanel(JPanel p,int x,int y,int width,int height,Container parent,LayoutManager l,boolean visiable)
	{
		p.setBounds(x, y, width, height);
		p.setLayout(l);
		p.setVisible(visiable);
		if(parent!=null)
		parent.add(p);
	}
	public static void initialJCombobox(JComboBox<String> c,String seletected,int x,int y,int width,int height,Container parent,ItemListener listener){
		c.setSelectedItem(seletected);
		c.setBounds(x, y, width, height);
		c.addItemListener(listener);
		parent.add(c);
		
	}public static void initialJRadioButton(JRadioButton c ,String text,int x,int y,int width,int height,Container parent){
		c.setText(text);
		c.setBounds(x, y, width, height);
		parent.add(c);
	}
}
