package com.rambo.wechat.service.wechat.req;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 图片消息
 * 
 * @author liufeng
 * @date 2013-05-19
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;

	// MediaId 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String MediaId;
	// MsgId 消息id，64位整型
//	private int MsgId;

	
	
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

//	public int getMsgId() {
//		return MsgId;
//	}
//
//	public void setMsgId(int msgId) {
//		MsgId = msgId;
//	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public ImageMessage() {
	}

	public ImageMessage(BaseMessage bm, String picUrl, String mediaId) {
		super(bm.getToUserName(), bm.getFromUserName(), bm.getCreateTime(), bm
				.getMsgType(), bm.getFuncFlag());
		this.PicUrl = picUrl;
		this.MediaId = mediaId;
//		this.MsgId = msgId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
