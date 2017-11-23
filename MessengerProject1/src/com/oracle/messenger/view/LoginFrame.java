package com.oracle.messenger.view;
import static com.oracle.messenger.control.CommonUtil.initialJButton;
import static com.oracle.messenger.control.CommonUtil.initialJCombobox;
import static com.oracle.messenger.control.CommonUtil.initialJFrame;
import static com.oracle.messenger.control.CommonUtil.initialJLabel;
import static com.oracle.messenger.control.CommonUtil.initialJPasswordField;
import static com.oracle.messenger.control.MessageUtil.wrapMessage;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import sun.misc.Regexp;

import com.oracle.messenger.config.SocketConfig;
import com.oracle.messenger.control.TrayImage;
import com.oracle.messenger.model.Message;
import com.oracle.messenger.model.MessageType;
import com.oracle.messenger.model.User;
/**
 * 窗口类一般都要做窗口的初始化设置(大小，位置，是否显示)
 * 
 * @author TengSir
 */
public class LoginFrame extends JFrame implements ActionListener,ItemListener,KeyListener,FocusListener{
	
	/**
	 * 所有的组件应该属于这个窗口，应该用java中has-a
	 */
	private JLabel userNameLabel, passwordLabel;// 定义显示‘用户账号’，‘用户密码’的标签组件
	private JComboBox<String> usernameCombobox;// 定义用户输入用户名的文本框组件
	private JPasswordField passwordField;// 定义用户输入密码框的组件
	private JLabel logoLabel;
	private JButton login, register;
	public static final int width = 360, height = 280;
	public static final String title = "畅聊";
	private Socket client;
	private 	ObjectOutputStream  out;
	private ObjectInputStream in;
	private TrayImage  tray;
	private  Timer noticeTimer;
	/**
	 * 构造器里面负责初始化窗口的初始属性
	 */
	public LoginFrame(Socket client,ObjectOutputStream out,ObjectInputStream in) {
		this.client=client;
		this.out=out;
		this.in=in;
		tray=new TrayImage(this, null);
		initialJFrame(this, title, Toolkit.getDefaultToolkit().createImage(
				"resource/images/system/logo_.png"), 0, 0, width, height, false, true, false, EXIT_ON_CLOSE, true, null, null);
		initComponent();
		this.addKeyListener(this);
		/**
		 * 执行组件重绘或者pack一下
		 */
		/*
		 * this.paintComponents(this.getGraphics());
		 * this.paintAll(this.getGraphics());
		 */
		this.pack();
		this.setSize(width, height);
		this.setLocationRelativeTo(null);// 设置该窗口相对于其他某个窗口的位置(null)
	}

	/**
	 * 窗口中的组件往往用户自定义一个方法，来执行初始化
	 * 
	 * @param args
	 */
	public void initComponent() {
		initialJLabel(logoLabel=new JLabel(),null,new ImageIcon(Toolkit.getDefaultToolkit()
				.createImage("resource/images/system/three.gif")
				.getScaledInstance(360, 120, Image.SCALE_DEFAULT)),0, 0, 360, 110,this);//初始化显示图片的JLabel
		initialJLabel(userNameLabel=new JLabel(),"用户账号:",null,30, 120, 60, 20,this);//初始化显示‘用户账户’的JLabel
		String[] accounts=new String[]{"111111","222222","333333","444444","555555","666666","777777","888888","999999","000000"};
		initialJCombobox(usernameCombobox=new JComboBox<String>(accounts),"111111",100, 120, 200, 20,this,this);//初始化‘用户名’的输入框JCombobox
		usernameCombobox.setEditable(true);
		initialJLabel(passwordLabel=new JLabel(),"用户密码:",null,30, 150, 60, 20,this);////初始化显示‘用户密码’的JLabel
		initialJPasswordField(passwordField=new JPasswordField(), "111111",100, 150, 200, 20, this);//初始化‘用户密码’的输入框JPasswordField
		initialJButton(login=new JButton(), "登陆", null, 80, 180, 100, 20, this,this);//初始化‘登陆’按钮
		initialJButton(register=new JButton(), "注册", null, 190, 180, 100, 20, this,this);//初始化‘注册’按钮
		usernameCombobox.addKeyListener(this);
		passwordField.addFocusListener(this);
		passwordField.addKeyListener(this);
		login.addKeyListener(this);
		usernameCombobox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		passwordField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
					/*JFrame.setDefaultLookAndFeelDecorated(true);*/
					LoginFrame f = new LoginFrame(null,null,null);
					/*AWTUtilities.setWindowShape(f,new RoundRectangle2D.Double(0,0,width,height,20,20));
					AWTUtilities.setWindowOpacity(f, 0.9f);*/
		}});
	}
	public boolean getConnection()
	{
		boolean connect=false;
		try {
			client=new Socket(SocketConfig.serverIP,SocketConfig.port);
			  out=new ObjectOutputStream(client.getOutputStream());
			  in=new ObjectInputStream(client.getInputStream());
			  connect=true;
		} catch (UnknownHostException e1) {
			JOptionPane.showMessageDialog(this, "连接服务器失败，检查网络!", "错误消息", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "连接服务器失败，检查网络!", "错误消息", JOptionPane.ERROR_MESSAGE);
		}
		return connect;
	}

	/**
	 * 下面是处理监听到的事件的方法
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			validataLoginForm();
		} else if (e.getSource() == register) {
			RegisterFrame  r=null;
			if(client==null){
				r=new RegisterFrame(MessageType.register,null,null,null,null);
			}else{
				r=new RegisterFrame(MessageType.register,client,out,in,null);
			}
			this.dispose();
		}
	}
	/**
	 * 提示内容没填的方法
	 */
	public void noticeRequired(final JComponent c){
		noticeTimer=new Timer();
		noticeTimer.schedule(new TimerTask() {
			int n=0;
			@Override
			public void run() {
				
				if(n%2==0)c.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
				else c.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
				if(n>7)
					{noticeTimer.cancel();c.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));}
				n++;
			}
		}, 0, 500);
		c.requestFocus();
	}
	/**
	 * 验证信息是否填写完整，同时提交登陆的方法
	 */
	public void  validataLoginForm()
	{
		// 1.当用户点击这个按钮，则应该去用户名框和密码框里面的数据
		String username = usernameCombobox.getSelectedItem().toString();// 获取该组件上用户输入的文本
		String password = passwordField.getText();// 获取密码
		// 2.做基本的表单验证，验证用户是否填了
		if (username.length() == 0) {
			noticeRequired(usernameCombobox);
		} else {
			if (password.length() == 0) {
				noticeRequired(passwordField);
			} else {
				usernameCombobox.setBackground(null);
				passwordField.setBackground(null);
				//如果进到这个分支，说明表单验证成功(用户名和密码都填写了)
				if(client==null)//判断是不是第一次连接到服务器,则创建socket
				{
					if(!getConnection())return ;
				}
				sendAndProcessLoginMessage(username,password);//建立到服务器的socket通信,调用方法发送登陆消息，并处理服务器返回的结果
			}
		}
	}
	/**
	 * 发送登陆消息，并处理服务器返回登陆结果的方法
	 * @param username
	 * @param password
	 */
	public void sendAndProcessLoginMessage(String username,String password){
		try {
			Message m=wrapMessage(new User(username,password),null,null,null,MessageType.login);
			out.writeObject(m);
			out.flush();
			m=(Message)in.readObject();
			if(m.getFrom()==null)
			{
				if(m.getSendTime()!=null&&m.getSendTime().equals("reLogin"))
					JOptionPane.showMessageDialog(this, "该账号已经在其他地方登陆，不能重复登陆!", "错误消息", JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "用户名和密码不正确!", "错误消息", JOptionPane.ERROR_MESSAGE);
			}else
			{
				try {
						File  image=new File("resource/images/self/",m.getFrom().getUsername()+m.getFrom().getImage().substring(m.getFrom().getImage().lastIndexOf("."), m.getFrom().getImage().length()));
						if(!image.exists())
						{
							   Message  downImage=new Message();
							   downImage.setType(MessageType.downImage);
							   downImage.setFrom(m.getFrom());
							   out.writeObject(downImage);
							   out.flush();
							Message  sizeMessage=(Message)in.readObject();
							long size=Long.parseLong(sizeMessage.getContent().getContent());
							String fileName=sizeMessage.getSendTime();
							InputStream  imageInput=client.getInputStream();
							FileOutputStream o=new FileOutputStream(image);
							byte[] bytes=new byte[1024];
							int len=-1;
							int all=0;
							while(all!=size)
							{
								len=imageInput.read(bytes);
								o.write(bytes,0,len);
								o.flush();
								all+=len;
							}
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					MainFrame f=new MainFrame(m.getFrom(),this.out,this.in,this.client);
					tray.setUser(m.getFrom());
					tray.setFrame(f);
					f.setTray(tray);
					f.startReciveMessage();
					this.dispose();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()==usernameCombobox)
		{
			passwordField.setText(usernameCombobox.getSelectedItem().toString());
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		 if(e.getSource()==passwordField&&e.getKeyChar()==e.VK_ENTER)
		{
			validataLoginForm();
		}else if(e.getSource()==login&&e.getKeyChar()==e.VK_ENTER)
		{
			validataLoginForm();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource()==passwordField)
		{
			Pattern  reg=Pattern.compile("^[0-9]*$");
			String inputUsername=usernameCombobox.getSelectedItem().toString();
			Matcher  m=reg.matcher(inputUsername);
			if(!m.matches())
			{
				noticeRequired(usernameCombobox);
				usernameCombobox.requestFocus();
			}else
			{
				if(noticeTimer!=null){
					noticeTimer.cancel();
					usernameCombobox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
					passwordField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
				}
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
	
		
	}
}