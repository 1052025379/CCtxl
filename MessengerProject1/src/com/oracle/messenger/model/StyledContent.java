package com.oracle.messenger.model;

import java.io.Serializable;
import java.util.HashMap;

import com.oracle.messenger.control.Style;

public class StyledContent  implements Serializable{
	@Override
	public String toString() {
		return "StyledContent [content=" + content + ", style=" + style + "]";
	}

	private String content;
	private HashMap<Integer, String> pics;
	private Style style;

	public StyledContent(String content, HashMap<Integer, String> pics,
			Style style) {
		super();
		this.content = content;
		this.pics = pics;
		this.style = style;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public HashMap<Integer, String> getPics() {
		return pics;
	}

	public void setPics(HashMap<Integer, String> pics) {
		this.pics = pics;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public StyledContent() {
		super();
	}

}
