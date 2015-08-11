package com.rambo.wechat.service.wechat;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rambo.wechat.service.wechat.req.BaseMessage;
import com.rambo.wechat.service.wechat.req.ImageMessage;
import com.rambo.wechat.service.wechat.req.TextMessage;
import com.rambo.wechat.util.MessageUtil;
import com.rambo.wechat.web.WeixinController;

/**
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-05-20
 */
public class WechatService {
	static Logger log = LoggerFactory.getLogger(WechatService.class);

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			//消息内容
			String msg = requestMap.get("Content");
			if( msg == null || msg.trim() =="" || !msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				msg = "NULL";
				respContent = "non Text message has been sent. ";
			}
			else 
				respContent = "Text Message send from : " ;
//			respContent = "Text Message send from : " + fromUserName + " --->" + toUserName +" massege type : " + msgType +"/r/n"; 
			
			// 回复文本消息
			BaseMessage textMessage = new BaseMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				
				
				respContent = "Text message send: " + msg + "\n\n";
				
				respContent += MessageUtil.getMainMenu();
				textMessage = new TextMessage(textMessage, respContent);
				
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				
				String picUrl = requestMap.get("PicUrl");
				respContent = "picture info sent -->" + picUrl;
				
				String mediaId = requestMap.get("MediaId");
				
//				textMessage = new ImageMessage(textMessage, picUrl,mediaId);
				
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "location info";
				textMessage = new TextMessage(textMessage, respContent);
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "Link info";
				textMessage = new TextMessage(textMessage, respContent);
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "voice info";
				textMessage = new TextMessage(textMessage, respContent);
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
				}
				textMessage = new TextMessage(textMessage, respContent);
			}

			respMessage = MessageUtil.textMessageToXml(textMessage); 
			
			
			/*"<xml><ToUserName><![CDATA["
			+ textMessage.getToUserName()
			+ "]]></ToUserName><FromUserName><![CDATA["
			+ textMessage.getFromUserName()
			+ "]]></FromUserName><CreateTime>"
			+ textMessage.getCreateTime()
			+ "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[The msg u send is : "
			+ textMessage.getContent() + "]]></Content></xml>";*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("Message to be send back : "+respMessage);
		return respMessage;
	}
}
