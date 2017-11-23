package com.oracle.messenger.control;
import java.util.HashMap;

import javax.swing.text.MutableAttributeSet;

import com.oracle.messenger.model.Message;
import com.oracle.messenger.model.MessageType;
import com.oracle.messenger.model.StyledContent;
import com.oracle.messenger.model.User;
/**
 * 
 * @author TengSir
 *
 */
public class MessageUtil {
	public static Message  wrapMessage(User from,User to,String sendTime,StyledContent content,MessageType type){
		Message m=new Message(sendTime, from, to, content, type);
		return m;
	}
	public static StyledContent wrapStyledContent(String content,HashMap<Integer, String> pics,Style style)
	{
		StyledContent  s=new StyledContent();
		s.setPics(pics);
		s.setContent(content);
		s.setStyle(style);
		return s;
	}

}
