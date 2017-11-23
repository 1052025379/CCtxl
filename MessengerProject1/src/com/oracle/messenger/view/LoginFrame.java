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
 * ������һ�㶼Ҫ�����ڵĳ�ʼ������(��С��λ�ã��Ƿ���ʾ)
 * 
 * @author TengSir
 */
public class LoginFrame extends JFrame implements ActionListener,ItemListener,KeyListener,FocusListener{
	
	/**
	 * ���е����Ӧ������������ڣ�Ӧ����java��has-a
	 */
	private JLabel userNameLabel, passwordLabel;// ������ʾ���û��˺š������û����롯�ı�ǩ���
	private JComboBox<String> usernameCombobox;// �����û������û������ı������
	private JPasswordField passwordField;// �����û��������������
	private JLabel logoLabel;
	private JButton login, register;
	public static final int width = 360, height = 280;
	public static final String title = "����";
	private Socket client;
	private 	ObjectOutputStream  out;
	private ObjectInputStream in;
	private TrayImage  tray;
	private  Timer noticeTimer;
	/**
	 * ���������渺���ʼ�����ڵĳ�ʼ����
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
		 * ִ������ػ����packһ��
		 */
		/*
		 * this.paintComponents(this.getGraphics());
		 * this.paintAll(this.getGraphics());
		 */
		this.pack();
		this.setSize(width, height);
		this.setLocationRelativeTo(null);// ���øô������������ĳ�����ڵ�λ��(null)
	}

	/**
	 * �����е���������û��Զ���һ����������ִ�г�ʼ��
	 * 
	 * @param args
	 */
	public void initComponent() {
		initialJLabel(logoLabel=new JLabel(),null,new ImageIcon(Toolkit.getDefaultToolkit()
				.createImage("resource/images/system/three.gif")
				.getScaledInstance(360, 120, Image.SCALE_DEFAULT)),0, 0, 360, 110,this);//��ʼ����ʾͼƬ��JLabel
		initialJLabel(userNameLabel=new JLabel(),"�û��˺�:",null,30, 120, 60, 20,this);//��ʼ����ʾ���û��˻�����JLabel
		String[] accounts=new String[]{"111111","222222","333333","444444","555555","666666","777777","888888","999999","000000"};
		initialJCombobox(usernameCombobox=new JComboBox<String>(accounts),"111111",100, 120, 200, 20,this,this);//��ʼ�����û������������JCombobox
		usernameCombobox.setEditable(true);
		initialJLabel(passwordLabel=new JLabel(),"�û�����:",null,30, 150, 60, 20,this);////��ʼ����ʾ���û����롯��JLabel
		initialJPasswordField(passwordField=new JPasswordField(), "111111",100, 150, 200, 20, this);//��ʼ�����û����롯�������JPasswordField
		initialJButton(login=new JButton(), "��½", null, 80, 180, 100, 20, this,this);//��ʼ������½����ť
		initialJButton(register=new JButton(), "ע��", null, 190, 180, 100, 20, this,this);//��ʼ����ע�ᡯ��ť
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
			JOptionPane.showMessageDialog(this, "���ӷ�����ʧ�ܣ��������!", "������Ϣ", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "���ӷ�����ʧ�ܣ��������!", "������Ϣ", JOptionPane.ERROR_MESSAGE);
		}
		return connect;
	}

	/**
	 * �����Ǵ�����������¼��ķ���
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
	 * ��ʾ����û��ķ���
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
	 * ��֤��Ϣ�Ƿ���д������ͬʱ�ύ��½�ķ���
	 */
	public void  validataLoginForm()
	{
		// 1.���û���������ť����Ӧ��ȥ�û��������������������
		String username = usernameCombobox.getSelectedItem().toString();// ��ȡ��������û�������ı�
		String password = passwordField.getText();// ��ȡ����
		// 2.�������ı���֤����֤�û��Ƿ�����
		if (username.length() == 0) {
			noticeRequired(usernameCombobox);
		} else {
			if (password.length() == 0) {
				noticeRequired(passwordField);
			} else {
				usernameCombobox.setBackground(null);
				passwordField.setBackground(null);
				//������������֧��˵������֤�ɹ�(�û��������붼��д��)
				if(client==null)//�ж��ǲ��ǵ�һ�����ӵ�������,�򴴽�socket
				{
					if(!getConnection())return ;
				}
				sendAndProcessLoginMessage(username,password);//��������������socketͨ��,���÷������͵�½��Ϣ����������������صĽ��
			}
		}
	}
	/**
	 * ���͵�½��Ϣ����������������ص�½����ķ���
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
					JOptionPane.showMessageDialog(this, "���˺��Ѿ��������ط���½�������ظ���½!", "������Ϣ", JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "�û��������벻��ȷ!", "������Ϣ", JOptionPane.ERROR_MESSAGE);
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