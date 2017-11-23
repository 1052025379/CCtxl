package com.oracle.messenger.model;

import javax.swing.tree.DefaultMutableTreeNode;
/**
 * 
 * @author TengSir
 *
 */
public class FriendTreeNode extends DefaultMutableTreeNode {
	private User  friend;
	public FriendTreeNode(User  friend,String nodeValue){
		super(nodeValue);
		this.friend=friend;
	}
	public User getFriend() {
		return friend;
	}
	public void setFriend(User friend) {
		this.friend = friend;
	}
}
