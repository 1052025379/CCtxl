package com.oracle.messenger.control;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.oracle.messenger.model.Message;
import com.oracle.messenger.model.User;
import com.oracle.messenger.view.MainFrame;
/**
 * 
 * @author TengSir
 *
 */
public class TrayImage implements MouseListener,ActionListener{
	private TrayIcon icon;
	private SystemTray tray;
	private PopupMenu  menus;
	private MenuItem  hide,show,exit;
	private   User user;
	private JFrame frame;
	private Stack<Message>  messages;
	private Stack<MySpecialTimer>  noticers;
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public void setUser(User user) {
		this.user = user;
		icon.setToolTip("账号:"+user.getUsername()+"\r\n昵称:"+user.getNickname()+"\r\n等级:"+user.getLevel()+"级");
	}
	public void removeTrayIcon(){
		tray.remove(icon);
	}


	public Stack<Message> getMessages() {
		return messages;
	}
	public TrayImage(JFrame frame,User user){
		this.frame=frame;
		this.user=user;
		messages=new Stack<Message>();
		noticers=new Stack<MySpecialTimer>();
		menus=new PopupMenu();
		show=new MenuItem("打开");
		hide=new MenuItem("隐藏");
		exit=new MenuItem("退出");
		show.addActionListener(this);
		hide.addActionListener(this);
		exit.addActionListener(this);
		menus.add(show);
		menus.addSeparator();
		menus.add(hide);
		menus.addSeparator();
		menus.add(exit);
		tray=SystemTray.getSystemTray();
		if(user==null)
			icon=new TrayIcon(Toolkit.getDefaultToolkit().createImage("resource/images/system/logo_.png"), "未登录",menus);
		else
			icon=new TrayIcon(Toolkit.getDefaultToolkit().createImage("resource/images/system/logo_.png"), "账号:"+user.getUsername()+"\r\n昵称:"+user.getNickname()+"\r\n等级:"+user.getLevel()+"级", menus);
		icon.setImageAutoSize(true);
		icon.addMouseListener(this);
		try {
			tray.add(icon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	public boolean  processNoticeMessageAboutRepeat(Message m)
	{
		boolean has=false;
		A:for(Message m_:messages)
		{
				switch (m.getType()) {
				case text:
				{
					if(m_.getFrom().getUsername().equals(m_.getFrom().getUsername()))
					{
						has=true;
						messages.remove(m_);break A;
					}
					break;
				}
				case crowdText:
				{
					if(m_.getTo().getUsername().equals(m.getTo().getUsername()))
					{
						has=true;
						messages.remove(m_);break A;
					}
					break;
				}
			}
		}
		messages.push(m);
		return has;
	}
	/**
	 * 制造任务栏通知图标闪动通知的方法
	 * @param message
	 */
	public void noticeNewMessage(final Message message)
	{
		final  MySpecialTimer timer=new MySpecialTimer();
		boolean has=processNoticeMessageAboutRepeat(message);
		switch (message.getType()) {
			case text:
			{
				if(has)
				{
					for (MySpecialTimer t:noticers) {
						if(t.getTimerName().equals(message.getFrom().getUsername()))
						{
							noticers.remove(t);
							t.cancel();
							break;
						}
					}
				}
				timer.setTimerName(message.getFrom().getUsername());
				break;
			}
			case crowdText:
			{
				if(has)
				{
					for (MySpecialTimer t:noticers) {
						if(t.getTimerName().equals(message.getTo().getUsername()))
						{
							noticers.remove(t);
							t.cancel();
							break;
						}
					}
				}
				timer.setTimerName(message.getTo().getUsername());
				break;
			}
		}
		timer.schedule(makeTimerTask(message),0,600);
		noticers.add(timer);
	}
	public TimerTask  makeTimerTask(final Message message){
		 return new TimerTask() {
			int n=0;
			@Override
			public void run() {
				n++;
				String path=null;
				if(n%2==0)
					path=message.getFrom().getImagePath();
				else
					path="resource/images/system/none.png";
				icon.setImage(Toolkit.getDefaultToolkit().createImage(path));
				icon.setImageAutoSize(true);
				switch (message.getType()) {
					case text:
					{
						icon.setToolTip("好友~"+message.getFrom().getNickname()+"~发来消息啦!");
						break;
					}
					case crowdText:
					{
						icon.setToolTip("群^"+message.getTo().getUsername()+"^里的好友~"+message.getFrom().getNickname()+"~发来消息拉!");
						break;
					}
				}
			}
		};
	}
	public void clearMessageTrayNotice(Message  message){
		if(messages.size()>0)
		{
			MainFrame m=(MainFrame)frame;
			switch (message.getType()) {
				case shake:
				{
					try {
						m.getChatFramesOfFriend().get(message.getFrom().getUsername()).setVisible(true);
						messages.remove(messages.indexOf(message.getFrom().getUsername()));
					} catch (Exception e) {//标记这里曾经发生数组越界异常
						// TODO: handle exception
					}
					break;
				}
				case crowdShake:
				{
					m.getChatFramesOfCrowd().get(message.getTo().getUsername()).setVisible(true);
					if(messages.indexOf(message.getTo().getUsername())!=-1)
						messages.remove(messages.indexOf(message.getTo().getUsername()));
					break;
				}
			}
			noticeNewMessage(messages.pop());
		}
	}
	public void showDefaultTrayIcon(){
			icon.setImage(Toolkit.getDefaultToolkit().createImage("resource/images/system/logo_.png"));
			icon.setImageAutoSize(true);
			setUser(user);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==1&&e.getClickCount()==2&&e.getSource()==icon)
		{
			MainFrame  main=(MainFrame)frame;
			if(messages.size()!=0){
				showDefaultTrayIcon();
				Message  nowMessage=messages.pop();
				switch (nowMessage.getType()) {
					case text:
					{
						main.getChatFramesOfFriend().get(nowMessage.getFrom().getUsername()).setVisible(true);
						main.getChatFramesOfFriend().get(nowMessage.getFrom().getUsername()).setFocusable(true);
						break;
					}
					case crowdText:
					{
						main.getChatFramesOfCrowd().get(nowMessage.getTo().getUsername()).setVisible(true);
						main.getChatFramesOfCrowd().get(nowMessage.getTo().getUsername()).setFocusable(true);
						break;
					}
				}
				noticers.get(messages.size()).cancel();
				showDefaultTrayIcon();
				noticers.remove(messages.size());
			}
			else
			{
				showDefaultTrayIcon();
				frame.setVisible(true);
				frame.setFocusable(true);
			}
		}else if(e.getButton()==3&&e.getClickCount()==1)
		{
			try {
				menus.show(frame, e.getX()+150, e.getY()-60);
			} catch (Exception e2) {
			}
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
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==show)
		{
			frame.setVisible(true);
			frame.setFocusable(true);
		}else if(e.getSource()==hide){
			frame.setVisible(false);
		}else if(e.getSource()==exit){
			tray.remove(icon);
			frame.dispose();
		}
	}
}