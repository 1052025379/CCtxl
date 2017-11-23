package com.oracle.messenger.control.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import com.oracle.messenger.model.User;
/**
 * �����������������ݿ�Ĳ�����ҵ��bean��
 * @author TengSir
 */
public class DatabaseUtil {
	public static boolean hasRegistered(String username)
	{
		boolean hasRegistered=false;
		File  dir=new File("resource/databases",username+".userinfo");
		if(dir.exists())hasRegistered=true;
		return hasRegistered;
	}
	/**
	 * дһ����ѯ���ݿ�ķ���
	 * @param args
	 */
	public static User  getUserInfoByUsernameAndPassword(String username,String password)
	{
		User user=null;
		//1.�����л��������ݿ��ļ�����
		File  dir=new File("resource/databases");
		String[]  files=dir.list();//��ȡ���ļ��ж�������������ļ��� �ļ��ж���
		for(String file:files)
		{
			File  dataFile=new File(dir,file);
			if(dataFile.isFile())
			{
				try {
					ObjectInputStream  in=new ObjectInputStream(new FileInputStream(dataFile));
					User u=(User)in.readObject();
					if(username.equals(u.getUsername())&&password.equals(u.getPassword()))
					{
						user=u;
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}
	public static void main(String[] args) {
		initialDB();
//		System.out.println(getUserInfoByUsernameAndPassword("666888", "888666"));
		
	}
	public static boolean addUser(User user)
	{
		boolean  saved=false;
		File  data=new File("resource/databases",user.getUsername()+".userinfo");
		try {
			ObjectOutputStream  o=new ObjectOutputStream(new FileOutputStream(data));
			o.writeObject(user);
			o.flush();
			o.close();
			saved=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saved;
	}
	public static User updateUser(User newUserInfo)
	{
		User user=null;
		try {
			//1.�����л��������ݿ��ļ�����
			File  data=new File("resource/databases",newUserInfo.getUsername()+".userinfo");
			data.deleteOnExit();
			ObjectOutputStream  out=new ObjectOutputStream(new FileOutputStream(data));
			out.writeObject(newUserInfo);
			out.flush();
			out.close();
			ObjectInputStream  in=new ObjectInputStream(new FileInputStream(data));
			user=(User)in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
		
	}
	public static void initialDB(){
		/**
		 * ģ�ⴴ������User����Ȼ���⼸���������л����ļ��У�
		 * �൱��ģ�������˼����û��˻����󣬴浽���ݿ�
		 */
		User  user8=new User("888888", "888888", "������", '��', "resource/images/headImage/100.gif", 1,"resource/images/self/1.PNG","�Ҿ����ң��ǲ�һ�����̻�!");
		User  user6=new User("666666", "666666", "��ǧ��", 'Ů', "resource/images/headImage/101.gif", 2,"resource/images/self/2.PNG","����ȽϷ����ȽϷ�!");
		User  user7=new User("777777", "777777", "��С��", 'Ů', "resource/images/headImage/102.gif", 1,"resource/images/self/3.PNG","�������ã���������!");
		User  user9=new User("999999", "999999", "������", 'Ů', "resource/images/headImage/103.gif", 5,"resource/images/self/4.PNG","���̻�ɢ���������㿴��������!");
		User  user10=new User("000000", "000000", "�������Ƹ�", '��', "resource/images/headImage/104.gif", 10,"resource/images/self/5.PNG","ϲ����!");
		User  user1=new User("111111", "111111", "������", '��', "resource/images/headImage/105.gif", 15,"resource/images/self/6.PNG","ôô��!");
		User  user2=new User("222222", "222222", "������", '��', "resource/images/headImage/106.gif", 21,"resource/images/self/7.PNG","������ôһ����!");
		User  user3=new User("333333", "333333", "������", 'Ů', "resource/images/headImage/107.gif", 12,"resource/images/self/8.PNG","what are you Ūɶ��?");
		User  user4=new User("444444", "444444", "����������", '��', "resource/images/headImage/108.gif", 19,"resource/images/self/9.PNG","�����߾���!!!");
		User  user5=new User("555555", "555555", "��ȫ��", '��', "resource/images/headImage/109.gif", 13,"resource/images/self/10.PNG","���²�����,����ͽ�˱�~");
		HashSet<User>  f1=new HashSet<User>();
		f1.add(user1);
		f1.add(user3);
		f1.add(user4);
		f1.add(user5);
		f1.add(user6);
		f1.add(user7);
		
		HashSet<User>  f2=new HashSet<User>();
		f2.add(user1);
		f2.add(user2);
		f2.add(user9);
		f2.add(user10);
		
		HashSet<User>  f3=new HashSet<User>();
		f3.add(user1);
		f3.add(user2);
		f3.add(user6);
		f3.add(user7);
		f3.add(user4);
		f3.add(user3);
		
		HashSet<User>  f4=new HashSet<User>();
		f4.add(user5);
		f4.add(user7);
		f4.add(user10);
		f4.add(user2);
		f4.add(user3);
		
		HashSet<User>  f5=new HashSet<User>();
		f5.add(user8);
		f5.add(user1);
		f5.add(user6);
		f5.add(user4);
		f5.add(user2);
		f5.add(user3);
		f5.add(user4);
		f5.add(user5);
		f5.add(user7);
		f5.add(user9);
		f5.add(user10);
		
		
		
		HashSet<User>  f6=new HashSet<User>();
		f6.add(user8);
		f6.add(user1);
		f6.add(user6);
		f6.add(user4);
		f6.add(user10);
		HashSet<User>  f7=new HashSet<User>();
		f7.add(user1);
		f7.add(user2);
		f7.add(user3);
		f7.add(user4);
		f7.add(user10);
		HashSet<User>  f8=new HashSet<User>();
		f8.add(user10);
		f8.add(user3);
		HashSet<User>  f9=new HashSet<User>();
		f9.add(user5);
		f9.add(user7);
		f9.add(user1);
		f9.add(user8);
		f9.add(user2);
		HashSet<User>  f10=new HashSet<User>();
		f10.add(user2);
		f10.add(user3);
		f10.add(user9);
		f10.add(user10);
		f10.add(user6);
		
		HashMap<String, HashSet<User>>  fr1=new HashMap<String, HashSet<User>>();
		fr1.put("�ҵ�ͬѧ", f1);
		fr1.put("��С����", f10);
		fr1.put("İ����", f3);
		fr1.put("��ѧ����", f8);
		
		HashMap<String, HashSet<User>>  fr2=new HashMap<String, HashSet<User>>();
		fr2.put("����", f2);
		fr2.put("���", f6);
		fr2.put("��С", f4);
		fr2.put("Ů��", f3);
		fr2.put("ͬ��", f9);
		fr2.put("�쵼", f1);
		
		HashMap<String, HashSet<User>>  fr3=new HashMap<String, HashSet<User>>();
		fr3.put("��ү��", f9);
		fr3.put("������", f4);
		fr3.put("α��", f1);
		fr3.put("��������", f8);
		fr3.put("̫������", f7);
		fr3.put("�ó�ҩ��", f3);
		
		HashMap<String, HashSet<User>>  fr4=new HashMap<String, HashSet<User>>();
		fr4.put("�����", f1);
		fr4.put("������", f10);
		fr4.put("���Ĳ�ˬ��", f2);
		fr4.put("����Ů�ѵ�", f5);
		fr4.put("����", f3);
		fr4.put("ߣ��", f8);
		
		HashMap<String, HashSet<User>>  fr5=new HashMap<String, HashSet<User>>();
		fr5.put("�ҵĺ���", f5);
		fr5.put("����", f6);
		fr5.put("Сѧ̸����", f4);
		fr5.put("����̸����", f7);
		fr5.put("����̸����", f8);
		fr5.put("��ѧ̸����", f2);
		
		HashMap<String, HashSet<User>>  fr6=new HashMap<String, HashSet<User>>();
		fr6.put("����", f2);
		fr6.put("����", f5);
		fr6.put("ϲ����", f8);
		fr6.put("̸����", f3);
		fr6.put("����׷", f9);
		fr6.put("�Ѹ㶨��", f7);
		fr6.put("���ȵ�", f1);
		HashMap<String, HashSet<User>>  fr7=new HashMap<String, HashSet<User>>();
		fr7.put("ϲ����", f4);
		fr7.put("�����", f7);
		fr7.put("�в���", f9);
		fr7.put("�ҩ��", f10);
		fr7.put("������", f3);
		fr7.put("Ů������", f1);
		fr7.put("�����ѵ�Ů����", f8);
		HashMap<String, HashSet<User>>  fr8=new HashMap<String, HashSet<User>>();
		fr8.put("�߾���", f5);
		fr8.put("�񾭲�", f10);
		fr8.put("����", f1);
		HashMap<String, HashSet<User>>  fr9=new HashMap<String, HashSet<User>>();
		fr9.put("��Ů", f9);
		fr9.put("˧��", f5);
		fr9.put("��˿", f5);
		fr9.put("����", f6);
		fr9.put("����", f10);
		HashMap<String, HashSet<User>>  fr10=new HashMap<String, HashSet<User>>();
		fr10.put("�׸���", f6);
		fr10.put("�߸�˧", f9);
		fr10.put("�ڸ�˧", f10);
		fr10.put("�ڸ���", f2);
		fr10.put("����˧", f4);
		fr10.put("������", f3);
		fr10.put("������", f7);
		
		
		HashMap<String, HashSet<User>>  cr1=new HashMap<String, HashSet<User>>();
		cr1.put("�������", f1);
		cr1.put("LOLս�Ӻ���", f5);
		
		HashMap<String, HashSet<User>>  cr2=new HashMap<String, HashSet<User>>();
		cr2.put("��˾Ⱥ", f3);
		cr2.put("�ƿư༶����Ⱥ", f2);
		
		HashMap<String, HashSet<User>>  cr3=new HashMap<String, HashSet<User>>();
		cr3.put("һȺ����", f6);
		cr3.put("��Щ�����", f1);
		cr3.put("����Ⱥ", f7);
		
		HashMap<String, HashSet<User>>  cr4=new HashMap<String, HashSet<User>>();
		cr4.put("�����༶Ⱥ", f4);
		cr4.put("������", f8);
		cr4.put("����һ����", f3);
		
		HashMap<String, HashSet<User>>  cr5=new HashMap<String, HashSet<User>>();
		cr5.put("��С��", f10);
		cr5.put("302����", f9);
		cr5.put("��ȭ������Ⱥ", f6);
		
		HashMap<String, HashSet<User>>  cr6=new HashMap<String, HashSet<User>>();
		cr6.put("�߾���Ⱥ", f5);
		cr6.put("��ױ����Ⱥ", f7);
		cr6.put("���۾�ӢȺ", f1);
		
		HashMap<String, HashSet<User>>  cr7=new HashMap<String, HashSet<User>>();
		cr7.put("��Ʒ��", f8);
		cr7.put("������", f2);
		cr7.put("ҵ��", f1);
		cr7.put("����", f3);
		cr7.put("������", f7);
		cr7.put("���²�", f9);
		
		HashMap<String, HashSet<User>>  cr8=new HashMap<String, HashSet<User>>();
		cr8.put("��������Ⱥ", f8);
		
		HashMap<String, HashSet<User>>  cr9=new HashMap<String, HashSet<User>>();
		cr9.put("��ְ����", f2);
		cr9.put("���Խ���", f6);
		
		HashMap<String, HashSet<User>>  cr10=new HashMap<String, HashSet<User>>();
		cr10.put("����Ⱥ", f5);
		cr10.put("��Цһ����", f3);
		cr10.put("��ƽ����", f9);
		cr10.put("��������", f2);
		
		user1.setFriends(fr1);
		user2.setFriends(fr2);
		user3.setFriends(fr3);
		user4.setFriends(fr4);
		user5.setFriends(fr5);
		user6.setFriends(fr6);
		user7.setFriends(fr7);
		user8.setFriends(fr8);
		user9.setFriends(fr9);
		user10.setFriends(fr10);
		
		user1.setCrowd(cr1);
		user2.setCrowd(cr2);
		user3.setCrowd(cr3);
		user4.setCrowd(cr4);
		user5.setCrowd(cr5);
		user6.setCrowd(cr6);
		user7.setCrowd(cr7);
		user8.setCrowd(cr8);
		user9.setCrowd(cr9);
		user10.setCrowd(cr10);
		User[]  users={user1,user2,user3,user4,user5,user6,user7,user8,user9,user10};
		File  dir=new File("resource/databases");
		if(!dir.exists())
		dir.mkdir();
		try {
			for (int i = 0; i < users.length; i++) {
				ObjectOutputStream  out=new ObjectOutputStream(new FileOutputStream(new File(dir,users[i].getUsername()+".userinfo")));
			out.writeObject(users[i]);
			out.flush();
			out.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
