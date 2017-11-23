package com.oracle.messenger.model;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
/**
 * 
 * @author TengSir
 *
 */
public class TreeCell extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, HashSet<User>> friends;

	private  HashSet<User>  users;
	public TreeCell(HashMap<String, HashSet<User>> friends) {
		super();
		this.friends = friends;
	}

	public TreeCell(HashSet<User> users) {
		super();
		this.users = users;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				leaf, row, hasFocus);
	if(users!=null)
	{
		int n = 0;
			for (User u : users) {
				if (row == n&&leaf) {
					this.setIcon(new ImageIcon(Toolkit.getDefaultToolkit()
							.createImage(u.getImagePath())
							.getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
				}
				n++;
		}
	}else
	{
		int n = 0;
		for (String teamName : friends.keySet()) {
			n++;
			for (User u : friends.get(teamName)) {
				if (row == n&&leaf) {
					this.setIcon(new ImageIcon(Toolkit.getDefaultToolkit()
							.createImage(u.getImagePath())
							.getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
				}
				n++;
			}
		}
	}
		return this;
	}
}