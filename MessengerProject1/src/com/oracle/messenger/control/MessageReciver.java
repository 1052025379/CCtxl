package com.oracle.messenger.control;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.UUID;

import com.oracle.messenger.control.util.DatabaseUtil;
import com.oracle.messenger.model.Message;
import com.oracle.messenger.model.MessageType;
import com.oracle.messenger.model.StyledContent;
import com.oracle.messenger.model.User;
/**
 * 封装一个线程内部类，该线程类是通过传进来的一个socket， 然后读取该socket获取的消息的一个线程
 * 
 * @author TengSir
 * 
 */
public class MessageReciver extends Thread {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket c;

	public MessageReciver(Socket c) {
		try {
			this.c=c;
			in = new ObjectInputStream(c.getInputStream());
			out = new ObjectOutputStream(c.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Message m = null;
		try {
			while ((m = (Message) in.readObject()) != null) {
				switch (m.getType()) {
					case login: {
						User user = DatabaseUtil
								.getUserInfoByUsernameAndPassword(m.getFrom()
										.getUsername(), m.getFrom().getPassword());
						m = new Message();
							if (user != null) {
								if(Server.clients.get(user.getUsername())!=null)
								{
									m.setFrom(null);
									m.setSendTime("reLogin");
								}else
								{
									m.setFrom(user);
									Server.clients.put(m.getFrom().getUsername(), out);
									System.out.println(">>>用户\'"+m.getFrom().getUsername()+"\'登陆了!>>>"+"\t目前有"+Server.clients.size()+"个用户在线!");
								}
							}
							out.writeObject(m);
							out.flush();
						break;
					}
					case text: 
					case shake: {
						for (String username : Server.clients.keySet()) {
							if (username.equals(m.getTo().getUsername())) {
								m.setSendTime(Calendar.getInstance().getTime().toLocaleString());
								Server.clients.get(username).writeObject(m);
								Server.clients.get(username).flush();
								break;
							}
						}
						break;
					}
					case register:{
					}case update:{
							File oldImage=null;
							User user=m.getFrom();
							if(user.getImage()==null)
							{
								user.setImage("resource/images/self/defaultImage.jpg");
							}else
							{
								oldImage=new File(m.getFrom().getImage());
							}
						//先判断是否带上传文件
							
						if(m.getContent()!=null&&m.getSendTime()!=null)
						{
							long size=Long.parseLong(m.getSendTime());
							String fileName=m.getSendTime();
							File uploadImage=new File("resource/images/self/"+UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."), fileName.length()));
							FileOutputStream  o=new FileOutputStream(uploadImage);
							InputStream  imageInput=c.getInputStream();
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
							o.close();
							if(oldImage!=null){
								oldImage.delete();
								oldImage.delete();
							}
							user.setImage(uploadImage.getAbsolutePath());
						}
						Message backMessage=new Message();
						if(m.getType()==MessageType.register)
						{
							if(DatabaseUtil.hasRegistered(user.getUsername()))
							{
								backMessage.setType(MessageType.registerFail);
							}else
							{
								backMessage.setType(DatabaseUtil.addUser(user)?MessageType.registerSucess:MessageType.registerFail);
							}
						}else
						{
							User updateUserInfo=DatabaseUtil.updateUser(user);
							backMessage.setFrom(updateUserInfo);
							backMessage.setType(updateUserInfo==null?MessageType.updateFail:MessageType.updateSuccess);
						}
						out.writeObject(backMessage);
						out.flush();
						break;
					}case downImage:{
						File  image=new File(m.getFrom().getImage());
						Message  fileInfo=new Message();
						fileInfo.setContent(new StyledContent());
						fileInfo.getContent().setContent(image.length()+"");
						fileInfo.setSendTime(image.getName());
						fileInfo.setType(MessageType.downImage);
						fileInfo.setFrom(m.getFrom());
						out.writeObject(fileInfo);
						out.flush();
						OutputStream  o=c.getOutputStream();
						FileInputStream input=new FileInputStream(image);
						int len=-1;
						byte[] bytes=new byte[1024];
						while((len=input.read(bytes))!=-1)
						{
							o.write(bytes,0,len);
							o.flush();
						}
						break;
					}case crowdShake:
					case crowdText:{
						m.setSendTime(Calendar.getInstance().getTime().toLocaleString());
						for (String name:m.getTo().getFriends().keySet()) {
							for(User u:m.getTo().getFriends().get(name))
							{
								if(Server.clients.get(u.getUsername())!=null&&!u.getUsername().equals(m.getFrom().getUsername()))
								{
									Server.clients.get(u.getUsername()).writeObject(m);
									Server.clients.get(u.getUsername()).flush();
								}
							}
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (String key:Server.clients.keySet()) {
				if(Server.clients.get(key)==out)
				{
					Server.clients.remove(key);
					System.out.println("<<<用户\'"+key+"\'退出了!<<<"+"\t目前有"+Server.clients.size()+"个用户在线!");
					break;
				}
			}
		}
	}
}
