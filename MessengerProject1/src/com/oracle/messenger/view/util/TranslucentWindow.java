package com.oracle.messenger.view.util;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
public class TranslucentWindow  extends JFrame {
	    public TranslucentWindow() {   
	        super("Test translucent window");
	        this.setLayout(new FlowLayout());   
	        this.add(new JButton("test"));   
	        this.add(new JCheckBox("test"));   
	        this.add(new JRadioButton("test"));   
	        this.add(new JProgressBar(0, 100));   
	        this.setSize(new Dimension(400, 300));   
	        this.setLocationRelativeTo(null);   
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
	    }   
	    public static void main(String[] args) {   
	        JFrame.setDefaultLookAndFeelDecorated(true);   
	        SwingUtilities.invokeLater(new Runnable() {   
	            public void run() {   
	                Window w = new TranslucentWindow();   
	                w.setVisible(true);   
	                //要使窗口透明，您可以使用 AWTUtilities.setWindowOpacity(Window, float) 方法   
	                com.sun.awt.AWTUtilities.setWindowOpacity(w, 0.8f);   
	            }   
	        });   
	    }   
	}