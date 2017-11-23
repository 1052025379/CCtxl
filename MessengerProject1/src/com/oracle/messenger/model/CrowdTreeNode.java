package com.oracle.messenger.model;

import java.util.HashSet;

import javax.swing.tree.DefaultMutableTreeNode;
/**
 * 
 * @author TengSir
 *
 */
public class CrowdTreeNode  extends DefaultMutableTreeNode{
	public String getCrowdName() {
		return crowdName;
	}

	public void setCrowdName(String crowdName) {
		this.crowdName = crowdName;
	}

	private  HashSet<User>  users;
	private String crowdName;
	public CrowdTreeNode(HashSet<User> users, String crowdName, String nodeName) {
		super(nodeName);
		this.users = users;
		this.crowdName = crowdName;
	}

	public CrowdTreeNode() {
		super();
	}

	public HashSet<User> getUsers() {
		return users;
	}

	public void setUsers(HashSet<User> users) {
		this.users = users;
	}
	

}
