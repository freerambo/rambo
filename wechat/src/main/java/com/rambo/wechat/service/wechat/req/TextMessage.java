package com.rambo.wechat.service.wechat.req;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @description Text Message
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime 20 Jul, 2015 9:10:54 pm
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	
	public TextMessage() {
		super();
	}
	public TextMessage(String content) {
		super();
		Content = content;
	}
	
	
	//String ToUserName, String FromUserName, long CreateTime,
//	String MsgType, int FuncFlag
	public TextMessage(BaseMessage bm, String content) {
		super(bm.getToUserName(),bm.getFromUserName(),bm.getCreateTime(),bm.getMsgType(),bm.getFuncFlag());
		Content = content;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
	
}

