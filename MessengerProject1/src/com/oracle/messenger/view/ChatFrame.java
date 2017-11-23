package com.oracle.messenger.view;

import static com.oracle.messenger.control.CommonUtil.initialJButton;
import static com.oracle.messenger.control.CommonUtil.initialJCombobox;
import static com.oracle.messenger.control.CommonUtil.initialJFrame;
import static com.oracle.messenger.control.CommonUtil.initialJLabel;
import static com.oracle.messenger.control.CommonUtil.initialJPanel;
import static com.oracle.messenger.control.CommonUtil.initialJScrollPane;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import com.oracle.messenger.control.FontUtil;
import com.oracle.messenger.control.FrameUtil;
import com.oracle.messenger.control.MessageUtil;
import com.oracle.messenger.control.Style;
import com.oracle.messenger.control.util.DatabaseUtil;
import com.oracle.messenger.model.FriendTreeNode;
import com.oracle.messenger.model.Message;
import com.oracle.messenger.model.MessageType;
import com.oracle.messenger.model.MyIcon;
import com.oracle.messenger.model.StyledContent;
import com.oracle.messenger.model.TreeCell;
import com.oracle.messenger.model.User;
import com.sun.awt.AWTUtilities;
/**
 * 
 * @author TengSir
 *
 */
public class ChatFrame extends JFrame implements ActionListener ,ItemListener,KeyListener{
	private JPanel expressPanel, fontSet,rightUserInfoPanel;
	public static final int width = 480, height = 550;
	private JTextPane messages,editMessage;
	private JScrollPane messages_Scroll, editMessage_scroll,usersScroll;
	private JButton send, close, text, express, shake, capture,picture,color;
	private JLabel friendImage, myImage,count,nameOfCrowd,member;
	private JComboBox<String>  fontNames,fontSize;
	private Container content;
	private JLayeredPane lay;
	private JToggleButton bold,italic,underline;
	private JWindow  w;
	private User friend,myself;
	private ObjectOutputStream  out;
	private HashSet<User>  users;
	private String crowdName;
	private JTree  usersOfCrowds;
	private  Vector<String> picVector ;
	private MutableAttributeSet  attributeSet;
	private Style  style;
	private Color c=Color.black;
	public JTextPane getMessages() {
		return messages;
	}

	public JLabel getFriendImage() {
		return friendImage;
	}

	public ChatFrame(User friend,User myself,ObjectOutputStream  out) {
		this(null,null,friend,myself,out);
	}
	public ChatFrame(String crowdName,HashSet<User> users,User friend,User myself,ObjectOutputStream  out) {
		this.crowdName=crowdName;
		this.users=users;
		this.myself=myself;
		this.out=out;
		this.friend=friend;
		initialJFrame(this, LoginFrame.title, Toolkit.getDefaultToolkit().createImage(
				"resource/images/system/logo_.png"), 0, 0, width, height, false, false, false, HIDE_ON_CLOSE, true, null, null);
		lay = new JLayeredPane();
		content = this.getContentPane();
		initComponent();
	}

	public void initComponent() {
		messages = new JTextPane();
		messages.setEditable(false);
		initialJScrollPane(messages_Scroll = new JScrollPane(messages),5, 5, width - 145, 240,content);
		
		initialJPanel(rightUserInfoPanel=new JPanel(), 345, 5, 120, 428, content, null,true);
		rightUserInfoPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		if(users==null)
		{
			if(!new File("resource/images/self/"+friend.getUsername()+".png").exists())
				initialJLabel(friendImage=new JLabel(), "", null, -1, 0, 120, 200, rightUserInfoPanel);
			else
			initialJLabel(friendImage=new JLabel(), "", new ImageIcon(Toolkit.getDefaultToolkit()
					.createImage("resource/images/self/"+friend.getUsername()+".png")
					.getScaledInstance(120, 200, Image.SCALE_DEFAULT)), -1, 0, 120, 200, rightUserInfoPanel);
			
			initialJLabel(myImage = new JLabel(), "", new ImageIcon(Toolkit.getDefaultToolkit()
					.createImage("resource/images/self/"+myself.getUsername()+".png")
					.getScaledInstance(120, 200, Image.SCALE_DEFAULT)),-1, 232, 120, 200, rightUserInfoPanel);
			/*friendImage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 0, 0,Color.orange), "对方形象", TitledBorder.RIGHT, TitledBorder.BELOW_BOTTOM,null,Color.black));
			myImage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 0, 0,Color.orange), "我的形象", TitledBorder.RIGHT, TitledBorder.ABOVE_TOP,null,Color.black));*/
		}else
		{
			initialJLabel(myImage = new JLabel(), "", new ImageIcon(Toolkit.getDefaultToolkit()
					.createImage("resource/images/self/"+myself.getUsername()+".png")
					.getScaledInstance(120, 185, Image.SCALE_DEFAULT)),0, 0, 120, 200, rightUserInfoPanel);
			/*myImage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "我的形象", TitledBorder.RIGHT, TitledBorder.ABOVE_TOP));*/
			initialJLabel(nameOfCrowd = new JLabel("名称:"+crowdName,SwingConstants.LEFT),null , null,0, 205, 120, 15, rightUserInfoPanel);
			initialJLabel(count = new JLabel("人数:"+users.size()+"人",SwingConstants.LEFT),null , null,0, 225, 120, 15, rightUserInfoPanel);
			initialJLabel(member = new JLabel("成员如下:",SwingConstants.LEFT),null , null,0, 245, 120, 15, rightUserInfoPanel);
			
			DefaultMutableTreeNode  crowd=new DefaultMutableTreeNode(crowdName);//用于构造出jtree的一个元素节点
			/*
			 * 遍历上面这个’数据库‘里面查询出来的好友列表，
			 * 将这里面的人名构造成treenode添加到根节点上面
			 */
			for(User u:users)
			{
					FriendTreeNode  child=new FriendTreeNode(u,"<html><b style='color:black'>"+u.getNickname()+"</b></html>");
					crowd.add(child);
			}
			usersOfCrowds=new JTree(crowd);
			usersOfCrowds.setRootVisible(false);
			/*usersOfCrowds.addMouseListener(this);*/
			usersOfCrowds.setCellRenderer(new TreeCell(users));
			initialJScrollPane(usersScroll=new JScrollPane(usersOfCrowds),0, 260	, 120, 168,rightUserInfoPanel);
			usersScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		
		initialJButton(text=new JButton(), "", new ImageIcon("resource/images/system/font.png"), 40, 250, 20, 20, content	, this);
		
		initialJPanel(fontSet=new JPanel(), 7, 202, 330, 40, null, null,false);
		fontSet.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		initialJCombobox(fontNames=new JComboBox<String>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()),"宋体",10, 10, 130, 20,fontSet,null);
		
		initialJCombobox(fontSize=new JComboBox<String>(),"12",145, 10, 45, 20,fontSet,null);
		for (int i = 12; i <=72; i+=2) {
			fontSize.addItem(i+"");
		}
		
		initialJButton(bold=new JToggleButton(), "<html><b>B</b></html>", null,195, 10, 30, 20, fontSet, this);
		
		initialJButton(italic=new JToggleButton(), "<html><b style='font-style:italic'>I</b></html>", null,230, 10, 30, 20, fontSet, this);

		initialJButton(underline=new JToggleButton(), "<html><b style='text-decoration:underline'>U</b></html>", null, 265, 10, 30, 20, fontSet, this);
		
		initialJButton(color = new JButton(), "", new ImageIcon("resource/images/system/color.png"), 305, 10, 20, 20, fontSet, this);
		
		initialJButton(express = new JButton(), "", new ImageIcon("resource/images/system/express.png"), 80, 250, 20, 20, content, this);

		initialJButton(shake = new JButton(), "", new ImageIcon("resource/images/system/shake.png"), 120, 250, 20, 20, content, this);

		initialJButton(picture = new JButton(), "", new ImageIcon("resource/images/system/picture.png"), 160, 250, 20, 20, content, this);
		
		initialJButton(capture = new JButton(), "", new ImageIcon("resource/images/system/capture.png"), 200, 250, 20, 20, content, this);

		fontNames.addItemListener(this);
		fontSize.addItemListener(this);
		editMessage = new JTextPane();
		attributeSet=editMessage.getInputAttributes();
		style=new Style();
		editMessage.addKeyListener(this);
		initialJScrollPane(editMessage_scroll = new JScrollPane(editMessage),5, 281, width - 145, 152, content);

		initialJButton(send= new JButton(), "send", null, 80, 450, 80, 20, content, this);

		initialJButton(close= new JButton(), "close", null, 180, 450, 80, 20, content, this);

		expressPanel = new JPanel();
		expressPanel.setLayout(new GridLayout(8, 15, 1, 1));
		for (int n = 0; n < 96; n++) {
			JButton b = new JButton(
					new ImageIcon(Toolkit.getDefaultToolkit().createImage(
							"resource/images/express/" + (n + 1) + ".gif")));
			expressPanel.add(b);
			b.setActionCommand("resource/images/express/" + (n + 1) + ".gif");
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String path=((JButton)e.getSource()).getActionCommand();
					editMessage.insertIcon(new MyIcon(path));
					expressPanel.setVisible(false);
					editMessage.requestFocus();
				}
			});
		}
		expressPanel.setVisible(false);
		expressPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		expressPanel.setBounds(10, 5, 455, 240);
		
		lay.add(fontSet, JLayeredPane.POPUP_LAYER);
		lay.add(expressPanel, JLayeredPane.POPUP_LAYER);
		lay.add(content, JLayeredPane.DEFAULT_LAYER);
		this.setLayeredPane(lay);
		
	}

	public Container getContent() {
		return content;
	}

	public JTextPane getEditMessage() {
		return editMessage;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == express) {
			if (expressPanel.isVisible()) {
				expressPanel.setVisible(false);
			} else {
				expressPanel.setVisible(true);
				fontSet.setVisible(false);
			}

		}else if(e.getSource()==text)
		{
			if (fontSet.isVisible()) {
				fontSet.setVisible(false);
			} else {
				fontSet.setVisible(true);
				expressPanel.setVisible(false);
			}
		}
		else if(e.getSource()==shake)
		{
			sendShakeMessage();
		}else if(e.getSource()==picture)
		{
			String path=FrameUtil.selectImage(this);
			if(path!=null)
			editMessage.insertIcon(new ImageIcon(path));
		}else if(e.getSource()==capture)
		{
			getCaptureOfScreen();
		}else if(e.getSource()==color)
		{
			Color c=JColorChooser.showDialog(this, "设置字体颜色", this.c);
			this.c=c;
			attributeSet=FontUtil.setFont(editMessage, bold, italic, underline, fontNames, fontSize,0,editMessage.getStyledDocument().getLength(),c);
			style=FontUtil.getStyle(bold,italic,underline,fontNames,fontSize,c);
		}else if(e.getSource()==bold|e.getSource()==italic|e.getSource()==underline)
		{
			attributeSet=FontUtil.setFont(editMessage, bold, italic, underline, fontNames, fontSize,0,editMessage.getStyledDocument().getLength(),c);
			style=FontUtil.getStyle(bold,italic,underline,fontNames,fontSize,c);
		}else if(e.getSource()==send)
		{
			sendTextMessage();
		}else if(e.getSource()==close)
		{
			this.setVisible(false);
		}
	}
	/**
	 * 发送抖动消息的方法
	 */
	public void  sendShakeMessage(){
		FrameUtil.shakeWindow(this);
		try {
			String time=Calendar.getInstance().getTime().toLocaleString();
			messages.getStyledDocument().insertString(messages.getStyledDocument().getLength(),(myself.getNickname()+"\t"+time+"\r\n您发送了一个窗口抖动"),null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		try {
			messages.getStyledDocument().insertString(messages.getStyledDocument().getLength(),"\r\n\r\n",null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
			this.messages.setSelectionStart(this.messages.getText().length());
		Message m=null;
		if(users==null)
		{
			m=MessageUtil.wrapMessage(myself, friend, null, null, MessageType.shake);
		}else
		{	
			HashMap<String, HashSet<User>> users_=new HashMap<String, HashSet<User>>();
			users_.put(crowdName, users);
			m=MessageUtil.wrapMessage(myself, new User(crowdName,users_), null, null, MessageType.crowdShake);
		}
		try {
			out.writeObject(m);
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 发送文本消息 的方法
	 */
	public void  sendTextMessage(){
		/**
		 * 将消息提取到本地聊天窗口
		 */
		/**
		 * 1.先提交上去发送时间和人
		 */
		try {
			String time=Calendar.getInstance().getTime().toLocaleString();
			messages.getStyledDocument().insertString(messages.getStyledDocument().getLength(),(myself.getNickname()+"\t"+time+"\r\n"),null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		/**
		 * 解析其中的图片和文字
		 */
		HashMap<Integer, String> pics=processWillSendMessage();
		String contentText=editMessage.getText().toString();
		try {
			editMessage.getStyledDocument().remove(0, editMessage.getStyledDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		messages.setSelectionStart(messages.getStyledDocument().getLength());
		messages.setSelectionEnd(messages.getStyledDocument().getLength());
		
		/**
		 * 封装格式化的消息内容对象
		 */
		StyledContent contents=MessageUtil.wrapStyledContent(contentText, pics,style);
		/**
		 * 将消息封装为message对象，然后发出去，让服务器转发
		 */
		Message m=null;
		if(users==null)
		{
			m=MessageUtil.wrapMessage(myself, friend, null, contents, MessageType.text);
		}else
		{	
			HashMap<String, HashSet<User>> users_=new HashMap<String, HashSet<User>>();
			users_.put(crowdName, users);
			m=MessageUtil.wrapMessage(myself, new User(crowdName,users_), null, contents, MessageType.crowdText);
		}
		try {
			out.writeObject(m);
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 获取屏幕截屏的方法
	 */
	public void getCaptureOfScreen(){
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if(w==null)
					{
					  w=new JWindow();
					}
					int width_=Toolkit.getDefaultToolkit().getScreenSize().width-400;
					int height_=Toolkit.getDefaultToolkit().getScreenSize().height-300;
					w.setBounds(0, 0, width_, height_);
					w.setBackground(Color.black);
					w.setVisible(true);
					w.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					AWTUtilities.setWindowOpacity(w, 0.5f);
				}});
	
	}
	

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()==fontNames||e.getSource()==fontSize)
		{
			/*FontUtil.setFont(editMessage, bold, italic, underline, fontNames, fontSize,editMessage.getSelectionStart(),editMessage.getSelectionEnd());*/
			attributeSet=FontUtil.setFont(editMessage, bold, italic, underline, fontNames, fontSize,0,editMessage.getStyledDocument().getLength(),c);
			style=FontUtil.getStyle(bold,italic,underline,fontNames,fontSize,c);
		}			
	}

	@Override
	public void keyTyped(KeyEvent e) {
		 if(e.getSource()==editMessage)
		{
			 attributeSet=FontUtil.setFont(editMessage, bold, italic, underline, fontNames, fontSize,0,editMessage.getStyledDocument().getLength(),c);
			 style=FontUtil.getStyle(bold,italic,underline,fontNames,fontSize,c);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		attributeSet=FontUtil.setFont(editMessage, bold, italic, underline, fontNames, fontSize,0,editMessage.getStyledDocument().getLength(),c);
		style=FontUtil.getStyle(bold,italic,underline,fontNames,fontSize,c);
		if(e.getSource()==editMessage&&e.getKeyChar()==e.VK_ENTER)
		{
			sendTextMessage();
			try {
				e.setKeyCode(KeyEvent.VK_UP);
				e.consume();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		attributeSet=FontUtil.setFont(editMessage, bold, italic, underline, fontNames, fontSize,0,editMessage.getStyledDocument().getLength(),c);
		style=FontUtil.getStyle(bold,italic,underline,fontNames,fontSize,c);
	}
	public HashMap<Integer, String>  processWillSendMessage(){
		HashMap<Integer, String>  contents=new LinkedHashMap<Integer, String>();
		if(picVector==null)picVector=new Vector<String>();
		 picVector  = new Vector<String>();
           for(int i = 0; i < editMessage.getStyledDocument().getRootElements()[0].getElement(0).getElementCount(); i++){
               MyIcon icon =(MyIcon)StyleConstants.getIcon(editMessage.getStyledDocument().getRootElements()[0].getElement(0).getElement(i).getAttributes());
               if(icon != null){
            	   picVector.add(icon.getIconPath());
               }
           }
           int k = 0;
           for(int i = 0; i < editMessage.getText().length(); i++){
               if(editMessage.getStyledDocument().getCharacterElement(i).getName().equals("icon")){
            	   contents.put(i, picVector.get(k));
                   messages.insertIcon(new ImageIcon(picVector.get(k++).toString()));
               }else{
                   try {
                	   messages.getStyledDocument().insertString(messages.getStyledDocument().getLength(), editMessage.getStyledDocument().getText(i,1), attributeSet);
                   } catch (BadLocationException e1) {
                       e1.printStackTrace();
                   }
               }
           }
           try {
			messages.getStyledDocument().insertString(messages.getStyledDocument().getLength(),"\r\n\r\n",null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
           picVector.clear();
           return  contents;
       }
	
	public static void main(String[] args) {
		ChatFrame  c=new ChatFrame(DatabaseUtil.getUserInfoByUsernameAndPassword("111111", "111111"),DatabaseUtil.getUserInfoByUsernameAndPassword("222222", "222222"),null);
		c.setVisible(true);
	}
}