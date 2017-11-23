package com.oracle.messenger.view;
import static com.oracle.messenger.control.CommonUtil.initialJButton;
import static com.oracle.messenger.control.CommonUtil.initialJFrame;
import static com.oracle.messenger.control.CommonUtil.initialJLabel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.tree.DefaultMutableTreeNode;

import com.oracle.messenger.control.FontUtil;
import com.oracle.messenger.control.FrameUtil;
import com.oracle.messenger.control.TrayImage;
import com.oracle.messenger.model.CrowdTreeNode;
import com.oracle.messenger.model.FriendTreeNode;
import com.oracle.messenger.model.Message;
import com.oracle.messenger.model.MessageType;
import com.oracle.messenger.model.TreeCell;
import com.oracle.messenger.model.User;
/**
 * 
 * @author TengSir
 *
 */
public class MainFrame extends JFrame implements MouseListener { 
	public static final int width=300,height=800;
	private JLabel  show;
	private JTree friendList,crowdList,groupList;
	private  JScrollPane  friendScroll,crowdScroll,groupScroll;
	private JTabbedPane  optionPane;
	private JLayeredPane lay;
	private Container content;
	private JPanel top;
	private JLabel  head,nickName,level,signature;
	private JButton  add;
	private  User user;
	private ObjectOutputStream  out;
	private ObjectInputStream in;
	private HashMap<String, ChatFrame>  chatFramesOfFriend,chatFramesOfCrowd;
	private RegisterFrame updateFrame;
	private TrayImage tray;
	private Socket client;
	
	public void setTray(TrayImage tray) {
		this.tray = tray;
	}
	public MainFrame(final User  user,final ObjectOutputStream  out,final ObjectInputStream in,Socket client)
	{
		this.client=client;
		this.user=user;
		this.out=out;
		this.in=in;
		chatFramesOfFriend=new HashMap<String, ChatFrame>();
		chatFramesOfCrowd=new HashMap<String, ChatFrame>();
		initialJFrame(this, LoginFrame.title, Toolkit.getDefaultToolkit().createImage(
		"resource/images/system/logo_.png"), 50, 50, width, height, false, true, false, EXIT_ON_CLOSE, false, null, null);
		lay=new JLayeredPane();
		initComponent();
		this.pack();
		this.setVisible(true);
		this.setBounds(50, 50, width, height);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				tray.removeTrayIcon();
			}
		});
	}
	public void  initComponent(){
		content=this.getContentPane();
		content.setLayout(null);
		show=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resource/images/gif/201227112454800.gif").getScaledInstance(width, 80, Image.SCALE_DEFAULT)));
		show.setBounds(0, 0, width, 80);
		content.add(show);
		
		DefaultMutableTreeNode  friendRoot=new DefaultMutableTreeNode("我的好友");//用于构造出jtree的一个元素节点
		HashMap<String, HashSet<User>>  friends=user.getFriends();
		/*
		 * 遍历上面这个’数据库‘里面查询出来的好友列表，
		 * 将这里面的人名构造成treenode添加到根节点上面
		 */
		for(String teamName:friends.keySet())
		{
			DefaultMutableTreeNode  team=new DefaultMutableTreeNode(teamName);
			for(User u:friends.get(teamName))
			{
				FriendTreeNode  child=new FriendTreeNode(u,"<html><b style='color:black'>"+u.getNickname()+"</b>\""+u.getSignature()+"\"</html>");
				team.add(child);
			}
			friendRoot.add(team);
		}
		
		DefaultMutableTreeNode  crowdRoot=new DefaultMutableTreeNode("我的群");//用于构造出jtree的一个元素节点
		HashMap<String, HashSet<User>>  crowds=user.getCrowd();
		/*
		 * 遍历上面这个’数据库‘里面查询出来的好友列表，
		 * 将这里面的人名构造成treenode添加到根节点上面
		 */
		for(String crowdName:crowds.keySet())
		{
			CrowdTreeNode   crowd=new CrowdTreeNode(crowds.get(crowdName),crowdName,"<html><b style='color:red'>"+crowdName+"</b></html>");
			crowdRoot.add(crowd);
		}
		
		friendList=new JTree(friendRoot);
		friendList.setRootVisible(false);
		friendList.addMouseListener(this);
		friendList.setCellRenderer(new TreeCell(friends));
		friendScroll=new JScrollPane(friendList);
		
		optionPane=new JTabbedPane();
		optionPane.setBounds(20, 100, width-45, 630);
		optionPane.add("好友", friendScroll);
		
		crowdList=new JTree(crowdRoot);
		crowdList.addMouseListener(this);
		crowdList.setRootVisible(false);
		crowdScroll=new JScrollPane(crowdList);
		optionPane.add("群聊",crowdScroll);
		
		
		groupScroll=new JScrollPane();
		optionPane.add("讨论",groupScroll);
		
		content.add(optionPane);
		top=new JPanel();
		top.setBounds(0, 0, width, height);
		top.setBorder(BorderFactory.createLineBorder(Color.white));
		top.setLayout(null);
		top.setOpaque(false);
		
		initialJLabel(head=new JLabel(), "", new ImageIcon(Toolkit.getDefaultToolkit().createImage(user.getImagePath()).getScaledInstance(50	, 50, Image.SCALE_DEFAULT)), 175, 20, 50, 50, top);
		head.setCursor(new Cursor(Cursor.HAND_CURSOR));
		head.addMouseListener(this);
		initialJLabel(nickName=new JLabel("",SwingConstants.RIGHT),user.getNickname(), null, 5, 20, 160, 16, top);
		nickName.setForeground(Color.white);
		initialJLabel(level=new JLabel("",SwingConstants.RIGHT),user.getLevel()+"级", null, 5, 36, 160, 16, top);
		level.setForeground(Color.white);
		initialJLabel(signature=new JLabel("",SwingConstants.RIGHT),user.getSignature(), null, 5, 52, 160, 16, top);
		signature.setForeground(Color.white);
		/*nickName.setBorder(BorderFactory.createLineBorder(Color.white));
		level.setBorder(BorderFactory.createLineBorder(Color.white));
		signature.setBorder(BorderFactory.createLineBorder(Color.white));*/
		head.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
		
		initialJButton(add=new JButton(), null, new ImageIcon(Toolkit.getDefaultToolkit().createImage("resource/images/system/addFriend.png").getScaledInstance(20, 20, Image.SCALE_DEFAULT)), 50, 540, 20, 20, content, null);
		add.setBorder(BorderFactory.createEmptyBorder());
		lay.add(content,JLayeredPane.DEFAULT_LAYER);
		lay.add(top,JLayeredPane.POPUP_LAYER);
		this.setLayeredPane(lay);
	}
	public Socket getClient() {
		return client;
	}
	public void setClient(Socket client) {
		this.client = client;
	}
	public HashMap<String, ChatFrame> getChatFramesOfFriend() {
		return chatFramesOfFriend;
	}
	public HashMap<String, ChatFrame> getChatFramesOfCrowd() {
		return chatFramesOfCrowd;
	}
	/**
	 * 主窗口开启一个线程，监听并接受服务端推送过来的消息
	 */
	public  void  startReciveMessage(){
		class Reciver extends Thread{
			@Override
			public void run() {
				Message  m=null;
				try {
					while((m=(Message)in.readObject())!=null)
					{
						if(m.getType()==MessageType.updateFail|m.getType()==MessageType.updateSuccess)
						{
							processUpdateResultMessage(m);
						}else if(m.getType()==MessageType.text|m.getType()==MessageType.shake)
						{
							processTextAndShakeMessage(m);
						}else if(m.getType()==MessageType.downImage)
						{
							processDownloadImageMessage(m);
						}else if(m.getType()==MessageType.crowdText|m.getType()==MessageType.crowdShake)
						{
							processTextOrShakeOfCrowdMessage(m);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			/**
			 * 处理登陆结果消息的方法
			 * @param m
			 */
			public void  processUpdateResultMessage(Message m)
			{
				switch (m.getType()) {
					case updateFail:
					{
						JOptionPane.showMessageDialog(updateFrame, "修改失败!", "修改结果", JOptionPane.ERROR_MESSAGE);
						break;
					}
					case updateSuccess:
					{
						updateFrame.setVisible(false);
						head.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(m.getFrom().getImagePath()).getScaledInstance(50	, 50, Image.SCALE_DEFAULT)));
						nickName.setText(m.getFrom().getNickname());
						level.setText(m.getFrom().getLevel()+"级");
						signature.setText(m.getFrom().getSignature());
						JOptionPane.showMessageDialog(updateFrame, "修改成功!", "修改结果", JOptionPane.INFORMATION_MESSAGE);
						user=m.getFrom();
						break;
					}
				}
			}
			public void processStyledMessage(JTextPane  t,Message m){
				try {
					t.getStyledDocument().insertString(t.getStyledDocument().getLength(),(m.getFrom().getNickname()+"\t"+m.getSendTime()+"\r\n"),null);
				} catch (BadLocationException e2) {
					e2.printStackTrace();
				}
					switch (m.getType()) {
						case text:
						case crowdText:
						{	
							
							HashMap<Integer, String>  contents=m.getContent().getPics();
							Set<Integer>  indexes=contents.keySet();
							Queue<Integer>  index=new LinkedList<Integer>();
							for(Integer i:indexes)
							{
								index.offer(i);
							}
							MutableAttributeSet  attr=FontUtil.wrapAttributeSet(m.getContent().getStyle());
					           StringBuffer  c=new StringBuffer(m.getContent().getContent());
					           int index_=-1;
					           if(index.size()>0)
					        	   index_ =index.poll();
					           for(int i = 0; i < c.length(); i++){
					        	  	if(i==index_&&index_!=-1)
					        	  	{
					        	  		t.setCaretPosition(t.getStyledDocument().getLength());
					        	  		 t.insertIcon(new ImageIcon(contents.get(index_).toString()));
					        	  		if(index.size()==0)continue;
					        	  			index_=index.poll();
					        	  	}else
					        	  	{
					        	  		try {
					        	  			t.getStyledDocument().insertString(t.getStyledDocument().getLength(),c.substring(i, i+1) , attr);
					        	  		} catch (BadLocationException e1) {
					        	  			e1.printStackTrace();
					        	  		}
					        	  	}
					           }
							break;
						}
						case shake:
						case crowdShake:{
							try {
								t.getStyledDocument().insertString(t.getStyledDocument().getLength(),("对方发送了一个窗口抖动"),null);
							} catch (BadLocationException e2) {
								e2.printStackTrace();
							}
							break;
						}
					}
		           try {
						t.getStyledDocument().insertString(t.getStyledDocument().getLength(),"\r\n\r\n",null);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
					t.setSelectionStart(t.getStyledDocument().getLength());
			}
			/**
			 * 处理个人发送的文本和抖动消息
			 * @param m
			 */
			public void  processTextAndShakeMessage(Message m){
				ChatFrame  f=null;
				boolean hashOpened=false;
				for (String username:chatFramesOfFriend.keySet()) {
					if(m.getFrom().getUsername().equals(username))
					{
						f=chatFramesOfFriend.get(username);
						processStyledMessage(f.getMessages(),m);
						if(m.getType()==MessageType.text)
						{
							if(!f.isVisible())
							{
								tray.noticeNewMessage(m);
								FrameUtil.messageNotice();
							}
							FrameUtil.messageNotice();
						}else if(m.getType()==MessageType.shake)
						{
							f.setVisible(true);
							FrameUtil.shakeWindow(f);
							tray.clearMessageTrayNotice(m);
						}
						hashOpened=true;
						break;
					}
				}
				if(!hashOpened)
				{
						  f=new ChatFrame(m.getFrom(), user, out);
							f.setTitle(f.getTitle()+"   和\'"+m.getFrom().getNickname()+"\'聊天");
							processStyledMessage(f.getMessages(),m);
						if(m.getType()==MessageType.text)
						{
							tray.noticeNewMessage(m);
							FrameUtil.messageNotice();
						}else if(m.getType()==MessageType.shake)
						{
							f.setVisible(true);
							FrameUtil.shakeWindow(f);
							tray.clearMessageTrayNotice(m);
						}
						chatFramesOfFriend.put(m.getFrom().getUsername(), f);
				}
				f.getMessages().setSelectionStart(f.getMessages().getText().length());
			}
			/**
			 * 处理下载用户头像消息的方法
			 * @param m
			 * @throws Exception
			 */
			public void processDownloadImageMessage(Message m) throws Exception{
				String fileName=m.getSendTime();
				long size=Long.parseLong(m.getContent().getContent());
				InputStream  imageInput=client.getInputStream();
				File downloadImage=new File("resource/images/self/"+m.getFrom().getUsername()+fileName.substring(fileName.lastIndexOf("."), fileName.length()));
				FileOutputStream  out=new FileOutputStream(downloadImage);
				byte[] bytes=new byte[1024];
				int len=-1;
				int all=0;
				while(all!=size)
				{
					len=imageInput.read(bytes);
					out.write(bytes,0,len);
					out.flush();
					all+=len;
				}
				chatFramesOfFriend.get(m.getFrom().getUsername()).getFriendImage().setIcon(new ImageIcon(Toolkit.getDefaultToolkit()
				.createImage(downloadImage.getAbsolutePath())
				.getScaledInstance(120, 200, Image.SCALE_DEFAULT)));
				chatFramesOfFriend.get(m.getFrom().getUsername()).getFriendImage().paintComponents(chatFramesOfFriend.get(m.getFrom().getUsername()).getFriendImage().getGraphics());
				
			}
			/**
			 * 处理群发的文本或者抖动消息的方法
			 * @param m
			 */
			public void  processTextOrShakeOfCrowdMessage(Message m)
			{
				ChatFrame  f=null;
				String crowdName=null;
				for(String name:m.getTo().getFriends().keySet())
				{
					crowdName=name;break;
				}
				boolean hashOpened=false;
				for (String username:chatFramesOfCrowd.keySet()) {
					if(crowdName.equals(username))
					{
						f=	chatFramesOfCrowd.get(crowdName);
						processStyledMessage(f.getMessages(),m);
						if(m.getType()==MessageType.crowdText)
						{
							if(! f.isVisible())
							{
								tray.noticeNewMessage(m);
							}
							FrameUtil.messageNotice();
						}else if(m.getType()==MessageType.crowdShake)
						{
							f.setVisible(true);
							FrameUtil.shakeWindow(f);
							tray.clearMessageTrayNotice(m);
						}
						hashOpened=true;
						break;
					}
				}
				if(!hashOpened)
				{
					  f=new ChatFrame(crowdName,m.getTo().getFriends().get(crowdName),null,user,out);
					  f.setTitle("\""+crowdName+"\" 群聊");
					  processStyledMessage(f.getMessages(),m);
						if(m.getType()==MessageType.crowdText)
						{
							chatFramesOfCrowd.put(crowdName, f);
							tray.noticeNewMessage(m);
							FrameUtil.messageNotice();
						}else if(m.getType()==MessageType.crowdShake)
						{
							f.setVisible(true);
							chatFramesOfCrowd.put(crowdName, f);
							FrameUtil.shakeWindow(f);
						}
				}
				f.getMessages().setSelectionStart(f.getMessages().getText().length());
			}
		}
		new Reciver().start();
	}
	
	/**
	 * 双击好友树状菜单的时候，打开对应的好友聊天界面
	 */
	public void openChatFrameWithFriend(){
		try {
			FriendTreeNode d=(FriendTreeNode)friendList.getSelectionPath().getLastPathComponent();
			if(d.isLeaf()){
					for (String key:chatFramesOfFriend.keySet()) {
						if(key==d.getFriend().getUsername())
						{
							chatFramesOfFriend.get(key).setVisible(true);
							return;
						}
					}
					ChatFrame  c=new ChatFrame(d.getFriend(),user,out);
					c.setTitle(c.getTitle()+"   和\'"+d.getFriend().getNickname()+"\'聊天");
					chatFramesOfFriend.put(d.getFriend().getUsername(), c);
					c.setVisible(true);
					/**
					 * 下载对方好友照片
					 */
						Message  downLoadFriendImage=new Message();
						downLoadFriendImage.setFrom(d.getFriend());
						downLoadFriendImage.setType(MessageType.downImage);
						out.writeObject(downLoadFriendImage);
						out.flush();
			}
		} catch (Exception e2) {
		}
	}
	/**
	 * 双击群名称的时候，打开对应群聊天界面的方法
	 */
	public  void openChatFrameWithCrowd(){
		CrowdTreeNode c=(CrowdTreeNode)crowdList.getSelectionPath().getLastPathComponent();
		if(c.isLeaf()){
			for (String key:chatFramesOfCrowd.keySet()) {
				if(key==c.getCrowdName())
				{
					chatFramesOfCrowd.get(key).setVisible(true);
					return;
				}
			}
				ChatFrame  cf=new ChatFrame(c.getCrowdName(),c.getUsers(),null,user,out);
				cf.setTitle("\""+c.getCrowdName()+"\" 群聊");
				chatFramesOfCrowd.put(c.getCrowdName(), cf);
				cf.setVisible(true);
		}
	}
	/**
	 * 点击主窗口自己的头像时候打开修改信息的窗口
	 */
	public void editMessageOfMyself(){
		if(updateFrame==null)
			updateFrame=new RegisterFrame(MessageType.update, client, out, in,user);
		updateFrame.setVisible(true);
		updateFrame.setTitle("修改信息");
		updateFrame.getUsername().setText(user.getUsername());
		updateFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
		updateFrame.getUsername().setEditable(false);
		updateFrame.getNickname().setText(user.getNickname());
		updateFrame.getPassword().setText(user.getPassword());
		updateFrame.getConfirmPassword().setText(user.getPassword());
		if(user.getSex()=='男')
			updateFrame.getMale().setSelected(true);
		else
			updateFrame.getFemale().setSelected(true);
		String imageName=user.getImagePath();
		updateFrame.getImagePath().getSetImg().setSelectedIndex(Integer.parseInt(imageName.substring(imageName.lastIndexOf("/")+1, imageName.lastIndexOf(".")))-97);
		updateFrame.getSignaturel().setText(user.getSignature());
		updateFrame.getLevel().setText(user.getLevel()+"级");
		updateFrame.getLevel().paintAll(updateFrame.getLevel().getGraphics());
		updateFrame.setFriends(user.getFriends());
		updateFrame.setCrowds(user.getCrowd());
		updateFrame.getRegister().setText("修改");
		updateFrame.getLogin().setText("取消");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==1&&e.getClickCount()==2&&e.getSource()==friendList)
		{
			openChatFrameWithFriend();
		}else	if(e.getButton()==1&&e.getClickCount()==2&&e.getSource()==crowdList)
		{
			openChatFrameWithCrowd();
		}else if(e.getButton()==1&&e.getClickCount()==1&&e.getSource()==head){
			editMessageOfMyself();
		}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}
}