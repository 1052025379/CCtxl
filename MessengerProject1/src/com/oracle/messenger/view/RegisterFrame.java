package com.oracle.messenger.view;
import static com.oracle.messenger.control.CommonUtil.initialJButton;
import static com.oracle.messenger.control.CommonUtil.initialJFrame;
import static com.oracle.messenger.control.CommonUtil.initialJLabel;
import static com.oracle.messenger.control.CommonUtil.initialJPasswordField;
import static com.oracle.messenger.control.CommonUtil.initialJRadioButton;
import static com.oracle.messenger.control.CommonUtil.initialJTextfield;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.oracle.messenger.config.SocketConfig;
import com.oracle.messenger.control.CustomComboBox;
import com.oracle.messenger.control.FrameUtil;
import com.oracle.messenger.model.Message;
import com.oracle.messenger.model.MessageType;
import com.oracle.messenger.model.User;
/**
 * 
 * @author TengSir
 *
 */
public class RegisterFrame extends JFrame implements ActionListener,MouseListener {
	private JLabel usernameLabel,passwordLabel,confirmPasswordLabel,nicknameLabel,sexLabel,imagePathLabel,levelLabel,signatureLabel,userImage;
	private JTextField  username,nickname,level;
	private JPasswordField  password,confirmPassword;
	private JRadioButton male,female;
	private ButtonGroup sex;
	private JTextArea  signaturel;
	private JButton   register,login;
	private JPanel all;
	private CustomComboBox imagePath;
	private Socket  client;
	private ObjectOutputStream  out;
	private ObjectInputStream in;
	public static final int width=440,height=400;
	public MessageType  type;
	private String newImage;
	private Timer noticeTimer;
	private HashMap<String, HashSet<User>>  friends;
	private HashMap<String, HashSet<User>>  crowds;
	public void setCrowds(HashMap<String, HashSet<User>> crowds) {
		this.crowds = crowds;
	}
	private User user;
	public void setFriends(HashMap<String, HashSet<User>> friends) {
		this.friends = friends;
	}

	public RegisterFrame(MessageType type,Socket client,ObjectOutputStream out,ObjectInputStream in,User user){
		this.user=user;
		this.client=client;
		this.out=out;
		this.in=in;
		this.type=type;
		initialJFrame(this, LoginFrame.title,  Toolkit.getDefaultToolkit().createImage(
				"resource/images/system/logo_.png"), 0, 0, width, height, false, false, false, EXIT_ON_CLOSE, true, null, null);
		initComponent();
		this.pack();
		this.setSize(width, height);
		this.setVisible(true);
	}
	


	public JRadioButton getMale() {
		return male;
	}


	public JButton getRegister() {
		return register;
	}



	public JButton getLogin() {
		return login;
	}



	public JTextField getUsername() {
		return username;
	}

	public JTextField getNickname() {
		return nickname;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public JPasswordField getConfirmPassword() {
		return confirmPassword;
	}

	public JRadioButton getFemale() {
		return female;
	}


	public JTextField getLevel() {
		return level;
	}



	public JTextArea getSignaturel() {
		return signaturel;
	}

	public CustomComboBox getImagePath() {
		return imagePath;
	}

	public  void initComponent(){
		all=new JPanel();
		all.setLayout(null);
		all.setBounds(0, 0, width-10, height-38);
		all.setBorder(BorderFactory.createTitledBorder("用户注册"));
		initialJLabel(usernameLabel=new JLabel("<html>用户账户<b style='color:red'>*</b></html>"), null, null, 30, 20, 80, 20, all);
		initialJLabel(nicknameLabel=new JLabel( "<html>用户昵称<b style='color:red'>*</b></html>"),null, null, 30, 50, 80, 20, all);
		initialJLabel(passwordLabel=new JLabel( "<html>账户密码<b style='color:red'>*</b></html>"),null, null, 30, 80, 80, 20, all);
		initialJLabel(passwordLabel=new JLabel( "<html>确认密码<b style='color:red'>*</b></html>"),null, null, 30, 110, 80, 20, all);
		initialJLabel(sexLabel=new JLabel("<html>用户性别<b style='color:red'>*</b></html>"), null, null, 30, 140, 60, 20, all);
		initialJLabel(levelLabel=new JLabel(), "用户等级", null, 30, 170, 60, 20, all);
		initialJLabel(imagePathLabel=new JLabel(), "用户头像", null, 30, 200, 60, 20, all);
		initialJLabel(signatureLabel=new JLabel(), "用户签名", null, 30, 250, 60, 20, all);
		
		initialJTextfield(username=new JTextField(), "", 100, 20, 150, 20, all);
		username.setToolTipText("必须是数值类型");
		initialJTextfield(nickname=new JTextField(), "", 100, 50, 150, 20, all);
		initialJPasswordField(password=new JPasswordField(), "", 100, 80, 150, 20, all);
		password.setToolTipText("长度不能少于六位");
		initialJPasswordField(confirmPassword=new JPasswordField(), "", 100, 110, 150, 20, all);
		initialJRadioButton(male=new JRadioButton(),"男",100,140,60,20,all);
		initialJRadioButton(female=new JRadioButton(),"女",170,140,60,20,all);
		male.setSelected(true);
		sex=new ButtonGroup();
		sex.add(male);
		sex.add(female);
		initialJTextfield(level=new JTextField(), "1", 100, 170, 150, 20, all);
		level.setEditable(false);
		level.setToolTipText("等级无法更改，由系统维护，具体升级规则，请参照‘畅聊’升级法则!");
		String[] headImages=new String[20];
		for (int i = 97; i <97+20; i++) {
			headImages[i-97]="resource/images/headImage/"+i+".gif";
		}
		imagePath=new CustomComboBox(headImages);
		imagePath.setBounds(100, 200, 150, 40);
		all.add(imagePath);
//		initialJCombobox(imagePath=new CustomComboBox("resource/images/headImage"), "", 100, 170, 150, 20, all, null);
		signaturel=new JTextArea();
		signaturel.setBounds(100, 250, 150, 60);
		signaturel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		all.add(signaturel);
		if(user==null)
			initialJLabel(userImage=new JLabel(),null,new ImageIcon( Toolkit.getDefaultToolkit().createImage("resource/images/self/defaultImage.jpg").getScaledInstance(150, 270, Image.SCALE_DEFAULT)), 260, 20, 160, 292, all);
		else
			initialJLabel(userImage=new JLabel(),null,new ImageIcon( Toolkit.getDefaultToolkit().createImage("resource/images/self/"+user.getUsername()+user.getImage().substring(user.getImage().lastIndexOf("."), user.getImage().length())).getScaledInstance(150, 270, Image.SCALE_DEFAULT)), 260, 20, 160, 292, all);
			
		userImage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray),"用户照片",TitledBorder.CENTER,TitledBorder.CENTER,new Font("方正姚体", Font.BOLD, 12),Color.red));
		userImage.setToolTipText("提示:点击该图片区域可以选择新的照片!");
		userImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		userImage.addMouseListener(this);
		initialJButton(register=new JButton(), "注册", null, 100, 315, 80, 20, all, this);
		initialJButton(login=new JButton(), "登陆", null, 250, 315, 80, 20, all, this);
		this.add(all);
	}
	public static void main(String[] args) {
			RegisterFrame f=new RegisterFrame(MessageType.register,null,null,null,null);
	}
	public boolean  connectServer(){
		boolean connected=false;
		try {
			client=new Socket(SocketConfig.serverIP, SocketConfig.port);
			out=new ObjectOutputStream(client.getOutputStream());
			in=new ObjectInputStream(client.getInputStream());
			connected=true;
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, "连接服务器失败，检查网络!", "错误消息", JOptionPane.ERROR_MESSAGE);
		}
		return connected;
	}
	/**
	 * 发送注册消息的方法
	 */
	public void registerUserinfo(){
		User  register=new User();
		register.setUsername(this.username.getText().trim());
		register.setPassword(this.password.getText());
		register.setNickname(this.nickname.getText().trim());
		register.setSex(male.isSelected()?'男':'女');
		register.setLevel(Integer.parseInt(level.getText().replace("级", "")));
		register.setSignature(this.signaturel.getText().trim());
		register.setImagePath("resource/images/headImage/"+(97+this.imagePath.getSetImg().getSelectedIndex())+".gif");
		register.setFriends(friends==null?new HashMap<String, HashSet<User>>():friends);
		register.setCrowd(crowds==null?new HashMap<String, HashSet<User>>():crowds);
		register.setImage(user==null?null:user.getImage());
		Message  	m=new Message(null, register, null, null, type);
		try {
			if(newImage!=null)
			{
					File  f=new File(newImage);
					m.setSendTime(f.length()+"");
					m.setSendTime(f.getName());
					out.writeObject(m);
					out.flush();
					OutputStream  o=client.getOutputStream();
					FileInputStream input=new FileInputStream(f);
					int len=-1;
					byte[] bytes=new byte[1024];
					while((len=input.read(bytes))!=-1)
					{
						o.write(bytes,0,len);
						o.flush();
					}
			
			}else
			{
					out.writeObject(m);
					out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(user==null)
			processRegisterResult();
	}
	/**
	 * 处理注册的结果
	 */
	public void processRegisterResult()
	{
		try {
			Message recive=(Message)in.readObject();
			if(recive.getType()==MessageType.registerSucess)
			{
				int n=JOptionPane.showConfirmDialog(this, "注册成功,您可以用您注册的账号登陆了!\r\n立即登陆，请点击'确认'，否则，请点击'取消'!",  "注册结果", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(n==0)
				{
						LoginFrame f = new LoginFrame(client,out,in);
						this.dispose();
				}
			}else if(recive.getType()==MessageType.registerFail)
			{
				JOptionPane.showMessageDialog(this, "注册失败，用户账户已经被注册，请更换账户!", "注册结果",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==register)
		{
			if(validataRegisterForm())
			{
				if(client==null&out==null&in==null)
				{
					if(!connectServer())return;
				}
				registerUserinfo();
			}
		}else if(e.getSource()==login)
		{
			if(login.getText().equals("取消"))
			{
				this.setVisible(false);
			}
			else
			{
				try {
					LoginFrame f =null;
					if(client==null)
					{	 f=new LoginFrame(null,null,null);}
					else{
					 f = new LoginFrame(client,out,in);}
					this.dispose();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
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
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==userImage)
		{
			String path=FrameUtil.selectImage(RegisterFrame.this);
			if(path!=null)
			{
				File f=new File(path);
				String[]  exts={".jpg",".png",".gif",".gif",".jpeg"};
				boolean isImage=false;
				for (String ext:exts) {
					if(f.getName().toLowerCase().endsWith(ext))isImage=true;
				}
				if(!isImage)
				{
					JOptionPane.showMessageDialog(RegisterFrame.this, "请选择图片!", "错误消息", JOptionPane.ERROR_MESSAGE);
				}else
				{
					newImage=path;
					userImage.setIcon(new ImageIcon( Toolkit.getDefaultToolkit().createImage(path).getScaledInstance(150, 270, Image.SCALE_DEFAULT)));
					JOptionPane.showMessageDialog(RegisterFrame.this, "现在看到的只是预览图，必须提交后才能上传到服务器!", "提醒", JOptionPane.INFORMATION_MESSAGE);
					
				}
			}}
		
	}
	public boolean validataRegisterForm(){
		String inputUsername=username.getText().trim().toString();
		String inputNickname=nickname.getText().trim().toString();
		String inputPassword=password.getText().trim().toString();
		String inputConfirmPassword=confirmPassword.getText().trim().toString();
		if(username.isEditable())
		{
			if(inputUsername.length()==0)
			{
				noticeRequired(username);
				return false;
			}else
			{
				Pattern  reg=Pattern.compile("^[0-9]*$");
				Matcher  m=reg.matcher(inputUsername);
				if(!m.matches())
				{
					noticeRequired(username);
						return false;
				}
			}
		} 
		if(inputNickname.length()==0)
		{
			noticeRequired(nickname);
				return false;
		}
		if(inputPassword.length()<6)
		{
			noticeRequired(password);
				return false;
		}
		if(!inputPassword.equals(inputConfirmPassword))
		{
			noticeRequired(password);
			noticeRequired(confirmPassword);
			return false;
		}
		if(noticeTimer!=null)noticeTimer.cancel();
		username.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		nickname.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		password.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		confirmPassword.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		return true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==userImage)
		{
			final Timer  t=new Timer();
			t.schedule(new TimerTask() {
				int a;
				@Override
				public void run() {
					a++;
					Color  c=new Color((((int)(Math.random()*200000))%256),(((int)(Math.random()*200000))%256),(((int)(Math.random()*200000))%256));
					userImage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(a%2+1, 1, a%2+1, 1, c),"用户照片",TitledBorder.CENTER,TitledBorder.CENTER,new Font("方正姚体", Font.BOLD, 12),Color.red));
					if(a==10)
					{
						t.cancel();
						userImage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"用户照片",TitledBorder.CENTER,TitledBorder.CENTER,new Font("方正姚体", Font.BOLD, 12),Color.red));
					}
				}
			},0,300);
		}
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}