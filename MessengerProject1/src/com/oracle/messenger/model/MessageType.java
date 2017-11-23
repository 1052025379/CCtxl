package com.oracle.messenger.model;

import java.io.Serializable;
/**
 * 
 * @author TengSir
 *
 */
public enum MessageType  implements Serializable{
	login,logoff,register,text,crowdText,shake,crowdShake,update,registerSucess,registerFail,updateSuccess,updateFail,downImage;
}
