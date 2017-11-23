package com.oracle.messenger.model;

import javax.swing.ImageIcon;

public class MyIcon extends ImageIcon {
	private String iconPath;

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public MyIcon(String iconPath) {
		super(iconPath);
		this.iconPath = iconPath;
	}
	

}
