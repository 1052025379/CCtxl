package com.oracle.messenger.control;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
public class CustomComboBox extends JPanel {  
    JPanel jp;  
    JComboBox setImg;  
    ComboBoxRenderer renderer;  
      
    public JComboBox getSetImg() {
		return setImg;
	}

	public static void main(String[] args) {  
    }  
  
    public CustomComboBox(String[] images) {  
    	this.setLayout(new BorderLayout());
        renderer = new ComboBoxRenderer();  
        renderer.setPreferredSize(new Dimension(150, 40));  
          
        Object elements[][] =new Object[images.length][2];
        for (int i = 0; i < images.length; i++) {
        	elements[i]=new Object[]{images[i],this};
		}
        setImg = new JComboBox(elements);  
        setImg.setRenderer(renderer);             
        add(setImg);  
    }  
  
    private class ComboBoxRenderer extends JLabel implements ListCellRenderer {  
  
        // 这样要是实现接口的方法：  
  
        /* 
         *  
         * This method finds the image and text corresponding to the selected 
         *  
         * value and returns the label, set up to display the text and image. 
         */  
  
        @Override  
        public Component getListCellRendererComponent(JList list, Object value,  
                int index, boolean isSelected, boolean cellHasFocus) {  
  
        	this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
            Image image=null;  
             if (value instanceof Object[]) {  
                 Object[] values = (Object[]) value;                   
                  image = Toolkit.getDefaultToolkit().createImage(values[0].toString());  
                }  
                if (image != null) {  
                  this.setIcon(new ImageIcon(image));  
                }  
                return this;  
              }            
    }  
          
    private class MyIcon extends ImageIcon{  
        private Image m;  
        private String str;  
        private CustomComboBox jp;  
              
        public MyIcon(String str,CustomComboBox jp) {  
            Toolkit tool;  
            tool = jp.renderer.getToolkit();  
            m = tool.getImage(str);  
          }  
         public void paintIcon(Component lab, Graphics g){  
            lab=jp.renderer;  
            g.drawImage(m, 20, 20, lab);  
              
        }     
 }  
}  