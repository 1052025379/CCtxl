package com.oracle.messenger.view.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class LayeredFrame extends JFrame {
	private JPanel  top,bottom;
	private JLayeredPane l;
	private JLabel  backImage,usernameLabel;
	private JTextField  username;
	private JButton  testButton;
	public LayeredFrame()
	{
		this.setBounds(100, 100, 427, 160);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		l=new JLayeredPane();
		this.setLayeredPane(l);
		top=new JPanel();
		top.setBounds(0, 0, 427, 160);
		top.setOpaque(false);
		top.setLayout(null);
		top.setBorder(BorderFactory.createLineBorder(Color.red));
		
		testButton=new JButton("≤‚ ‘∞¥≈•");
		testButton.setBounds(160,107,100,20);
		top.add(testButton);
		l.add(top, JLayeredPane.POPUP_LAYER);
		backImage=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resource/images/logo.png").getScaledInstance(427, 160, Image.SCALE_DEFAULT)));
		bottom=new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setBounds(0, 0, 427,160);
		bottom.setBackground(Color.green);
		bottom.add(backImage,BorderLayout.CENTER);
		l.add(bottom, JLayeredPane.DEFAULT_LAYER);
		
		usernameLabel=new JLabel("”√ªß’À∫≈:");
		usernameLabel.setForeground(Color.white);
		usernameLabel.setBounds(10, 10, 100, 20);
		top.add(usernameLabel);
		username=new JTextField();
		username.setBounds(120, 10, 120,20);
		top.add(username);
		
	}
	public static void main(String[] args) {
		new LayeredFrame();
	}

}
