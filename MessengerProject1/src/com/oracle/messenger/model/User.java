package com.oracle.messenger.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 这个是用户类，是用来创建用户对象的模型类 什么叫‘模型类’，一般只有属性和get，set方法，没有业务方法的类
 * 
 * 该类主要是为了创建对象，然后来存储某一个对象的属性的， 我们统称为数据模型，简称为模型类,需要放在model包里面
 * 
 * @author TengSir
 * 
 */
public class User implements Serializable {

	public User(HashMap<String, HashSet<User>> friends) {
		super();
		this.friends = friends;
	}

	public User(String username, HashMap<String, HashSet<User>> friends) {
		super();
		this.username = username;
		this.friends = friends;
	}

	public User(String username, String password, String nickname, char sex,
			String imagePath, int level, String image, String signature) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.imagePath = imagePath;
		this.level = level;
		this.image = image;
		this.signature = signature;
	}

	public User(String username, String password, String nickname, char sex,
			String imagePath, int level, String image) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.imagePath = imagePath;
		this.level = level;
		this.image = image;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/**
	 * 账号，密码，昵称，性别，头像，等级
	 */
	private String username;
	private String password;
	private String nickname;
	private char sex;
	private String imagePath;
	private int level;
	private String image;
	private String signature;
	private HashMap<String, HashSet<User>> crowd;
	

	public User(String username, String password, String nickname, char sex,
			String imagePath, int level, String image, String signature,
			HashMap<String, HashSet<User>> crowd,
			HashMap<String, HashSet<User>> friends) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.imagePath = imagePath;
		this.level = level;
		this.image = image;
		this.signature = signature;
		this.crowd = crowd;
		this.friends = friends;
	}

	public HashMap<String, HashSet<User>> getCrowd() {
		return crowd;
	}

	public void setCrowd(HashMap<String, HashSet<User>> crowd) {
		this.crowd = crowd;
	}

	public User(String username, String password, String nickname, char sex,
			String imagePath, int level, String image, String signature,
			HashMap<String, HashSet<User>> friends) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.imagePath = imagePath;
		this.level = level;
		this.image = image;
		this.signature = signature;
		this.friends = friends;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	private HashMap<String, HashSet<User>> friends;

	public User(String username, String password, String nickname, char sex,
			String imagePath, int level, String image,
			HashMap<String, HashSet<User>> friends) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.imagePath = imagePath;
		this.level = level;
		this.image = image;
		this.friends = friends;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public HashMap<String, HashSet<User>> getFriends() {
		return friends;
	}

	public void setFriends(HashMap<String, HashSet<User>> friends) {
		this.friends = friends;
	}

	public User(String username, String password, String nickname, char sex,
			String imagePath, int level, HashMap<String, HashSet<User>> friends) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.imagePath = imagePath;
		this.level = level;
		this.friends = friends;
	}

	public User(String username, String password, String nickname, char sex,
			String imagePath, int level) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.imagePath = imagePath;
		this.level = level;
	}

	public User() {
		super();
	}

	public User(String username) {
		super();
		this.username = username;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", nickname=" + nickname + ", sex=" + sex + ", imagePath="
				+ imagePath + ", level=" + level + ", image=" + image
				+ ", signature=" + signature + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result
				+ ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + level;
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + sex;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (level != other.level)
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (sex != other.sex)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
