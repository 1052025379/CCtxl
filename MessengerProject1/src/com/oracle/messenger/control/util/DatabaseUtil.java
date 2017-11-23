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
 * 这里我们来处理数据库的操作（业务bean）
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
	 * 写一个查询数据库的方法
	 * @param args
	 */
	public static User  getUserInfoByUsernameAndPassword(String username,String password)
	{
		User user=null;
		//1.反序列化所有数据库文件对象
		File  dir=new File("resource/databases");
		String[]  files=dir.list();//获取该文件夹对象下面的所有文件和 文件夹对象
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
			//1.反序列化所有数据库文件对象
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
		 * 模拟创建几个User对象，然后将这几个对象序列化到文件中，
		 * 相当于模拟生成了几个用户账户对象，存到数据库
		 */
		User  user8=new User("888888", "888888", "王百万", '男', "resource/images/headImage/100.gif", 1,"resource/images/self/1.PNG","我就是我，是不一样的烟火!");
		User  user6=new User("666666", "666666", "刘千金", '女', "resource/images/headImage/101.gif", 2,"resource/images/self/2.PNG","最近比较烦，比较烦!");
		User  user7=new User("777777", "777777", "王小花", '女', "resource/images/headImage/102.gif", 1,"resource/images/self/3.PNG","你若安好，便是晴天!");
		User  user9=new User("999999", "999999", "蓝精灵", '女', "resource/images/headImage/103.gif", 5,"resource/images/self/4.PNG","待烟花散尽，我陪你看花开花落!");
		User  user10=new User("000000", "000000", "二龙湖浩哥", '男', "resource/images/headImage/104.gif", 10,"resource/images/self/5.PNG","喜当爹!");
		User  user1=new User("111111", "111111", "蓝百万", '男', "resource/images/headImage/105.gif", 15,"resource/images/self/6.PNG","么么哒!");
		User  user2=new User("222222", "222222", "张铁柱", '男', "resource/images/headImage/106.gif", 21,"resource/images/self/7.PNG","胖了那么一丢丢!");
		User  user3=new User("333333", "333333", "李美丽", '女', "resource/images/headImage/107.gif", 12,"resource/images/self/8.PNG","what are you 弄啥咧?");
		User  user4=new User("444444", "444444", "二龙湖姜浩", '男', "resource/images/headImage/108.gif", 19,"resource/images/self/9.PNG","都是蛇精病!!!");
		User  user5=new User("555555", "555555", "张全蛋", '男', "resource/images/headImage/109.gif", 13,"resource/images/self/10.PNG","五月不减肥,六月徒伤悲~");
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
		fr1.put("我的同学", f1);
		fr1.put("从小长大", f10);
		fr1.put("陌生人", f3);
		fr1.put("大学室友", f8);
		
		HashMap<String, HashSet<User>>  fr2=new HashMap<String, HashSet<User>>();
		fr2.put("死党", f2);
		fr2.put("情敌", f6);
		fr2.put("发小", f4);
		fr2.put("女友", f3);
		fr2.put("同事", f9);
		fr2.put("领导", f1);
		
		HashMap<String, HashSet<User>>  fr3=new HashMap<String, HashSet<User>>();
		fr3.put("纯爷们", f9);
		fr3.put("老娘们", f4);
		fr3.put("伪娘", f1);
		fr3.put("不正经的", f8);
		fr3.put("太正经的", f7);
		fr3.put("该吃药的", f3);
		
		HashMap<String, HashSet<User>>  fr4=new HashMap<String, HashSet<User>>();
		fr4.put("打过架", f1);
		fr4.put("吵过嘴", f10);
		fr4.put("看的不爽的", f2);
		fr4.put("抢我女友的", f5);
		fr4.put("酒友", f3);
		fr4.put("撸友", f8);
		
		HashMap<String, HashSet<User>>  fr5=new HashMap<String, HashSet<User>>();
		fr5.put("我的好友", f5);
		fr5.put("闺蜜", f6);
		fr5.put("小学谈过的", f4);
		fr5.put("初中谈过的", f7);
		fr5.put("高中谈过的", f8);
		fr5.put("大学谈过的", f2);
		
		HashMap<String, HashSet<User>>  fr6=new HashMap<String, HashSet<User>>();
		fr6.put("坏人", f2);
		fr6.put("好人", f5);
		fr6.put("喜欢的", f8);
		fr6.put("谈过的", f3);
		fr6.put("正在追", f9);
		fr6.put("难搞定的", f7);
		fr6.put("劈腿的", f1);
		HashMap<String, HashSet<User>>  fr7=new HashMap<String, HashSet<User>>();
		fr7.put("喜欢的", f4);
		fr7.put("讨厌的", f7);
		fr7.put("有病的", f9);
		fr7.put("嗑药的", f10);
		fr7.put("好室友", f3);
		fr7.put("女朋友们", f1);
		fr7.put("好朋友的女朋友", f8);
		HashMap<String, HashSet<User>>  fr8=new HashMap<String, HashSet<User>>();
		fr8.put("蛇精病", f5);
		fr8.put("神经病", f10);
		fr8.put("精神病", f1);
		HashMap<String, HashSet<User>>  fr9=new HashMap<String, HashSet<User>>();
		fr9.put("美女", f9);
		fr9.put("帅哥", f5);
		fr9.put("屌丝", f5);
		fr9.put("土豪", f6);
		fr9.put("土鳖", f10);
		HashMap<String, HashSet<User>>  fr10=new HashMap<String, HashSet<User>>();
		fr10.put("白富美", f6);
		fr10.put("高富帅", f9);
		fr10.put("黑富帅", f10);
		fr10.put("黑富丑", f2);
		fr10.put("矮富帅", f4);
		fr10.put("矮富丑", f3);
		fr10.put("矮富戳", f7);
		
		
		HashMap<String, HashSet<User>>  cr1=new HashMap<String, HashSet<User>>();
		cr1.put("三年二班", f1);
		cr1.put("LOL战队好友", f5);
		
		HashMap<String, HashSet<User>>  cr2=new HashMap<String, HashSet<User>>();
		cr2.put("公司群", f3);
		cr2.put("计科班级交流群", f2);
		
		HashMap<String, HashSet<User>>  cr3=new HashMap<String, HashSet<User>>();
		cr3.put("一群疯子", f6);
		cr3.put("那些年的人", f1);
		cr3.put("扯淡群", f7);
		
		HashMap<String, HashSet<User>>  cr4=new HashMap<String, HashSet<User>>();
		cr4.put("初二班级群", f4);
		cr4.put("闺蜜们", f8);
		cr4.put("滕氏一家亲", f3);
		
		HashMap<String, HashSet<User>>  cr5=new HashMap<String, HashSet<User>>();
		cr5.put("发小们", f10);
		cr5.put("302寝室", f9);
		cr5.put("跆拳道社团群", f6);
		
		HashMap<String, HashSet<User>>  cr6=new HashMap<String, HashSet<User>>();
		cr6.put("蛇精病群", f5);
		cr6.put("化妆交流群", f7);
		cr6.put("销售精英群", f1);
		
		HashMap<String, HashSet<User>>  cr7=new HashMap<String, HashSet<User>>();
		cr7.put("产品部", f8);
		cr7.put("行政部", f2);
		cr7.put("业务部", f1);
		cr7.put("财务部", f3);
		cr7.put("开发部", f7);
		cr7.put("人事部", f9);
		
		HashMap<String, HashSet<User>>  cr8=new HashMap<String, HashSet<User>>();
		cr8.put("汽车交流群", f8);
		
		HashMap<String, HashSet<User>>  cr9=new HashMap<String, HashSet<User>>();
		cr9.put("求职交流", f2);
		cr9.put("面试交流", f6);
		
		HashMap<String, HashSet<User>>  cr10=new HashMap<String, HashSet<User>>();
		cr10.put("闲聊群", f5);
		cr10.put("搞笑一家人", f3);
		cr10.put("四平青年", f9);
		cr10.put("花花公子", f2);
		
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
