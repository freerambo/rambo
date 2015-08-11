package com.rambo.wechat.service.wechat;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rambo.wechat.service.wechat.req.BaseMessage;
import com.rambo.wechat.service.wechat.req.TextMessage;
import com.rambo.wechat.service.wechat.resp.Article;
import com.rambo.wechat.service.wechat.resp.NewsMessage;
import com.rambo.wechat.util.MessageUtil;


/**
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-07-25
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			String respContent = "Welcome to Yuanbo's world";

			// 默认回复此文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
//			textMessage.setContent("欢迎访问<a href=\"http://freerambo.com\">Yuanbo的个人网站</a>!");
			textMessage.setContent(MessageUtil.getMainMenu());
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");

				// 创建图文消息
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				List<Article> articleList = new ArrayList<Article>();
		
				// 单图文消息
				if ("1".equals(content)) {
					Article article = new Article();
					article.setTitle("缅甸大叔到IT攻城狮");
					article.setDescription("潇洒，80后，无产阶级革命家,教育家，诗人，IT工程师！");
					article.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dUjPqtuoS6TVGwVQlq8lpmKjRJXBqVF1IcV334Cmv94K0vO5PRBygjZodicDKIQbaAavbzsyWg7FdoaxJVRSoMA/640?tp=webp&wxfrom=5");
					article.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNDA0NTU4Mg==&mid=204173273&idx=1&sn=5d748b969b9c43e16e7293d350b05f74#rd");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 单图文消息---不含图片
				else if ("2".equals(content)) {
					Article article = new Article();
					article.setTitle("我的自白");
					// 图文消息中可以使用QQ表情、符号表情
					article.setDescription("潇洒，80后，" + emoji(0x1F6B9)
							+ "，求学至北方，混迹于南洋。渊为深，博为广，一直努力做一个有深度有广度的人。\n\n酷爱编程，但绝不宅。爱生活，爱思考，性情温和但充满激情，做事有主见，信奉读万卷书行万里路。\n\nplaceholder我是无耻的展位符placeholder placeholder我是无耻的展位符placeholder placeholder我是无耻的展位符placeholder placeholder我是无耻的展位符placeholder");
					// 将图片置为空
					article.setPicUrl("");
					article.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNDA0NTU4Mg==&mid=205410895&idx=1&sn=a9cb5a81c4b9cd8615b9a5175a87b93a&scene=18#rd");
					articleList.add(article);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息
				else if ("3".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("南洋游记\n引言");
					article1.setDescription("");
					article1.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dUjPqtuoS6R6SZgCWS0PerxQtJT2yia5MBdbpLqofnic3NSS5knIzDSEENVYF0D0dTF6gGGyhCXWjU2xwdfUWwLQ/0?tp=webp&wxfrom=5");
					article1.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNDA0NTU4Mg==&mid=200686468&idx=1&sn=afaffa15097e0a3b77642c957a98bd21#rd");

					Article article2 = new Article();
					article2.setTitle("南洋随笔（一）\n新加坡植物园-国家胡姬园");
					article2.setDescription("");
					article2.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dUjPqtuoS6R6SZgCWS0PerxQtJT2yia5MBdbpLqofnic3NSS5knIzDSEENVYF0D0dTF6gGGyhCXWjU2xwdfUWwLQ/0?tp=webp&wxfrom=5");
					article2.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNDA0NTU4Mg==&mid=201078941&idx=1&sn=9ab3f503870e62e303399cb15af9f7b3#rd");
					
					Article article3 = new Article();
					article3.setTitle("南洋随笔（二）\n新加坡的种族和谐");
					article3.setDescription("http://mmbiz.qpic.cn/mmbiz/dUjPqtuoS6SKmz9ECsKsH1CQn4frMVuc8zVO6Rew6BHWpPoThfguVkYrun9H2Ucl3sTpAYymXBq75lS5oMkxEg/0?tp=webp&wxfrom=5");
					article3.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dUjPqtuoS6R6SZgCWS0PerxQtJT2yia5MBdbpLqofnic3NSS5knIzDSEENVYF0D0dTF6gGGyhCXWjU2xwdfUWwLQ/0?tp=webp&wxfrom=5");
					article3.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNDA0NTU4Mg==&mid=201127753&idx=1&sn=313111ba31fdb22d17508e080a900f7d#rd");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---首条消息不含图片
				else if ("4".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("微信公众帐号开发教程Java版");
					article1.setDescription("");
					// 将图片置为空
					article1.setPicUrl("");
					article1.setUrl("http://blog.csdn.net/lyq8479");

					Article article2 = new Article();
					article2.setTitle("第1篇\n消息及消息处理工具的封装");
					article2.setDescription("");
					article2.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dUjPqtuoS6R6SZgCWS0PerxQtJT2yia5MBdbpLqofnic3NSS5knIzDSEENVYF0D0dTF6gGGyhCXWjU2xwdfUWwLQ/0?tp=webp&wxfrom=5");
					article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8949088");

					Article article3 = new Article();
					article3.setTitle("第2篇\n各种消息的接收与响应");
					article3.setDescription("");
					article3.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dUjPqtuoS6R6SZgCWS0PerxQtJT2yia5MBdbpLqofnic3NSS5knIzDSEENVYF0D0dTF6gGGyhCXWjU2xwdfUWwLQ/0?tp=webp&wxfrom=5");
					article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8952173");

					Article article4 = new Article();
					article4.setTitle("第3篇\n文本消息的内容长度限制揭秘");
					article4.setDescription("");
					article4.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dUjPqtuoS6R6SZgCWS0PerxQtJT2yia5MBdbpLqofnic3NSS5knIzDSEENVYF0D0dTF6gGGyhCXWjU2xwdfUWwLQ/0?tp=webp&wxfrom=5");
					article4.setUrl("http://blog.csdn.net/lyq8479/article/details/8967824");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					articleList.add(article4);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---最后一条消息不含图片
				else if ("5".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("编程语言和女人");
					article1.setDescription("繁华如女人的编程语言，尽阅铅华，life is short. I love python.");
					article1.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dUjPqtuoS6TXPKdlbV4sttqN2Sia6yRBLMbvlpD2tciasFmBabtwxDV8fEPiaJfprsZopE2RhEnRNMibicmpYch2upg/640?wx_fmt=jpeg&tp=webp&wxfrom=5");
					article1.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNDA0NTU4Mg==&mid=206961108&idx=1&sn=48ce793f620c462d427c3ee8c844ad6c#rd");

					Article article2 = new Article();
					article2.setTitle("南洋随笔\n新加坡植物园");
					article2.setDescription("");
					article2.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dUjPqtuoS6R6SZgCWS0PerxQtJT2yia5MBdbpLqofnic3NSS5knIzDSEENVYF0D0dTF6gGGyhCXWjU2xwdfUWwLQ/0?tp=webp&wxfrom=5");
					article2.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNDA0NTU4Mg==&mid=201078905&idx=2&sn=f755495d5db78d66f5f327649d8c64e5#rd");

					Article article3 = new Article();
					article3.setTitle("如果觉得文章对你有所帮助，欢迎留言或关注微信公众帐号freeyuanbo获取更多信息，然而也没啥ruan用！");
					article3.setDescription("");
					// 将图片置为空
					article3.setPicUrl("");
					article3.setUrl("http://freerambo.com");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				else if ("?".equals(content)||"？".equals(content)) {
					// 创建图文消息
					TextMessage msg = new TextMessage();
					msg.setToUserName(fromUserName);
					msg.setFromUserName(toUserName);
					msg.setCreateTime(new Date().getTime());
					msg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					msg.setFuncFlag(0);
					msg.setContent("就你这智商还想用帮助，死去!!!");
					respMessage = MessageUtil.textMessageToXml(msg);
				}
			}// 图片消息
			else{
				//ecletrical
					if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
					respContent = "您发送的是图片消息！";
				}
				// 地理位置消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
					respContent = "您发送的是地理位置消息！";
				}
				// 链接消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
					respContent = "您发送的是链接消息！";
				}
				// 音频消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
					respContent = "您发送的是音频消息！";
				}
				// 事件推送
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
					// 事件类型
					String eventType = requestMap.get("Event");
					// 订阅
					if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
						respContent = "谢谢您的关注！<a href=\"http://freerambo.com/\">Powered by Yuanbo</a>\n\n"+MessageUtil.getMainMenu();
					}
					// 取消订阅
					else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
						// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					}
					// 自定义菜单点击事件
					else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
						// 事件KEY值，与创建自定义菜单时指定的KEY值对应
						String eventKey = requestMap.get("EventKey");
	
						if (eventKey.equals("11")) {
							respContent = "天气预报菜单项被点击！";
						} else if (eventKey.equals("12")) {
							respContent = "公交查询菜单项被点击！";
						} else if (eventKey.equals("13")) {
							respContent = "周边搜索菜单项被点击！";
						} else if (eventKey.equals("14")) {
							respContent = "历史上的今天菜单项被点击！";
						} else if (eventKey.equals("21")) {
							respContent = "歌曲点播菜单项被点击！";
						} else if (eventKey.equals("22")) {
							respContent = "经典游戏菜单项被点击！";
						} else if (eventKey.equals("23")) {
							respContent = "美女电台菜单项被点击！";
						} else if (eventKey.equals("24")) {
							respContent = "人脸识别菜单项被点击！";
						} else if (eventKey.equals("25")) {
							respContent = "聊天唠嗑菜单项被点击！";
						} else if (eventKey.equals("31")) {
							respContent = "幽默笑话菜单项被点击！";
						} else if (eventKey.equals("32")) {
							respContent = "电影排行榜菜单项被点击！";
						} 
					}
					
				}
					
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	
	
	//update the gross area 
	//follow up Ramash for back up system 
	// weather data update
	//doc on NMS import 
	
	
	
	
	
	
	
	
}