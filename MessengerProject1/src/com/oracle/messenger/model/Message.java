package com.oracle.messenger.model;

import java.io.Serializable;
/**
 * 
 * @author TengSir
 *
 */
public class Message  implements Serializable{
	private String sendTime;
	private User from;
	private User to;
	private StyledContent content;
	private MessageType type;
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	public StyledContent getContent() {
		return content;
	}
	public void setContent(StyledContent content) {
		this.content = content;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public Message(String sendTime, User from, User to, StyledContent content,
			MessageType type) {
		super();
		this.sendTime = sendTime;
		this.from = from;
		this.to = to;
		this.content = content;
		this.type = type;
	}
	public Message() {
		super();
	}
	@Override
	public String toString() {
		return "Message [sendTime=" + sendTime + ", from=" + from + ", to="
				+ to + ", content=" + content + ", type=" + type + "]";
	}


}