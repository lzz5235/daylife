package org.lizhaozhong.weixin.service;

import java.util.ArrayList;
import java.util.Date;  
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;  

import org.lizhaozhong.weixin.message.resp.*;
import org.lizhaozhong.weixin.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
/** 
 * 核心服务类 
 *  
 * @author lizhaozhong 
 * @date 2014-05-20 
 */  
public class CoreService {
	
	private static Logger log = LoggerFactory.getLogger(CoreService.class);
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
		            Map<String,String> requestMap = MessageUtil.parseXml(request);  
		  
		            	// 发送方帐号（open_id）  
		            String fromUserName = (String)requestMap.get("FromUserName");  
		            	// 公众帐号  
		            String toUserName = (String)requestMap.get("ToUserName");  
		            	// 消息类型  
		            String msgType = (String)requestMap.get("MsgType"); 						
		  
						// 文本消息  
		            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
		            	// 接收用户发送的文本消息内容
								String content = requestMap.get("Content");
						
								// 单图文消息
								if ("1".equals(content)) {
										// 创建图文消息
									TextMessage textMessage = new TextMessage();  
									textMessage.setToUserName(fromUserName);  
									textMessage.setFromUserName(toUserName);  
									textMessage.setCreateTime(new Date().getTime());  
									textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
									textMessage.setFuncFlag(0);
									
									textMessage.setContent(getWeatherUsage()); 
									respMessage = MessageUtil.textMessageToXml(textMessage);
									
								}
								else if ("2".equals(content)) {
									TextMessage textMessage = new TextMessage();  
									textMessage.setToUserName(fromUserName);  
									textMessage.setFromUserName(toUserName);  
									textMessage.setCreateTime(new Date().getTime());  
									textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
									textMessage.setFuncFlag(0);
									
									textMessage.setContent(TodayInHistoryService.getTodayInHistoryInfo()); 
									respMessage = MessageUtil.textMessageToXml(textMessage);
								}
								else if("3".equals(content)){
									TextMessage textMessage = new TextMessage();  
									textMessage.setToUserName(fromUserName);  
									textMessage.setFromUserName(toUserName);  
									textMessage.setCreateTime(new Date().getTime());  
									textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
									textMessage.setFuncFlag(0);
									
									textMessage.setContent(getTranslateUsage()); 
									respMessage = MessageUtil.textMessageToXml(textMessage);
								}
								else if("4".equals(content)){
									TextMessage textMessage = new TextMessage();  
									textMessage.setToUserName(fromUserName);  
									textMessage.setFromUserName(toUserName);  
									textMessage.setCreateTime(new Date().getTime());  
									textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
									textMessage.setFuncFlag(0);
									
									textMessage.setContent(getMusicUsage()); 
									respMessage = MessageUtil.textMessageToXml(textMessage);
								}
								else if("5".equals(content)){
									NewsMessage newsMessage = new NewsMessage();
									newsMessage.setToUserName(fromUserName);
									newsMessage.setFromUserName(toUserName);
									newsMessage.setCreateTime(new Date().getTime());
									newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
									newsMessage.setFuncFlag(0);
									
									List<Article> articleList = getGameList();
									
									// 设置图文消息个数
									newsMessage.setArticleCount(articleList.size());
									// 设置图文消息包含的图文集合
									newsMessage.setArticles(articleList);
									// 将图文消息对象转换成xml字符串
									respMessage = MessageUtil.newsMessageToXml(newsMessage);
								}
								else if("7".equals(content)){
									TextMessage textMessage = new TextMessage();  
									textMessage.setToUserName(fromUserName);  
									textMessage.setFromUserName(toUserName);  
									textMessage.setCreateTime(new Date().getTime());  
									textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
									textMessage.setFuncFlag(0);
									
									textMessage.setContent(getFaceUsage()); 
									respMessage = MessageUtil.textMessageToXml(textMessage);
								}
								else if("9".equals(content)){
									TextMessage textMessage = new TextMessage();  
									textMessage.setToUserName(fromUserName);  
									textMessage.setFromUserName(toUserName);  
									textMessage.setCreateTime(new Date().getTime());  
									textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
									textMessage.setFuncFlag(0);
									
									textMessage.setContent(BaiduMoviesService.searchMovie("北京")); 
									respMessage = MessageUtil.textMessageToXml(textMessage);
								}
								// 单图文消息
								else if ("10".equals(content)) {
									
									NewsMessage newsMessage = new NewsMessage();
									newsMessage.setToUserName(fromUserName);
									newsMessage.setFromUserName(toUserName);
									newsMessage.setCreateTime(new Date().getTime());
									newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
									newsMessage.setFuncFlag(0);
					
									List<Article> articleList = new ArrayList<Article>();
									Article article = new Article();
									article.setTitle("About Me");
									article.setDescription("Enjoy code~Enjoy life");
									article.setPicUrl("http://daylife.sinaapp.com/image/2.png");
									article.setUrl("http://www.lizhaozhong.info/my-resume");
									articleList.add(article);
									// 设置图文消息个数
									newsMessage.setArticleCount(articleList.size());
									// 设置图文消息包含的图文集合
									newsMessage.setArticles(articleList);
									// 将图文消息对象转换成xml字符串
									respMessage = MessageUtil.newsMessageToXml(newsMessage);
								}
								else if("？".equals(content)){
									respMessage = welcome(fromUserName,toUserName);
								}
								else if (content.startsWith("翻译")) {
									TextMessage textMessage = new TextMessage();  
									textMessage.setToUserName(fromUserName);  
									textMessage.setFromUserName(toUserName);  
									textMessage.setCreateTime(new Date().getTime());  
									textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
									textMessage.setFuncFlag(0);
									String keyWord = content.replaceAll("^翻译", "").trim();  
									
									if ("".equals(keyWord)) {
										textMessage.setContent(getTranslateUsage());
										} 
									else {
										textMessage.setContent(BaiduTranslateService.translate(keyWord));
										}
									respMessage = MessageUtil.textMessageToXml(textMessage);
								}
								else if (content.startsWith("歌曲")) {  
				                    // 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉  
				                    String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");  
				                    // 如果歌曲名称为空  
				                    if ("".equals(keyWord)) {  
				                        respContent = getMusicUsage();  
				                    } else {  
				                        String[] kwArr = keyWord.split("@");  
				                        // 歌曲名称  
				                        String musicTitle = kwArr[0];  
				                        // 演唱者默认为空  
				                        String musicAuthor = "";  
				                        if (2 == kwArr.length)  
				                            musicAuthor = kwArr[1];  
				  
				                        // 搜索音乐  
				                        Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);  
				                        // 未搜索到音乐  
				                        if (null == music) {  
				                            respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";  
				                        } else {  
				                            // 音乐消息  
				                            MusicMessage musicMessage = new MusicMessage();  
				                            musicMessage.setToUserName(fromUserName);  
				                            musicMessage.setFromUserName(toUserName);  
				                            musicMessage.setCreateTime(new Date().getTime());  
				                            musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);  
				                            musicMessage.setMusic(music);  
				                            respMessage = MessageUtil.musicMessageToXml(musicMessage);  
				                        }  
				                    } 
				                    if (null == respMessage) {  
				                        if (null == respContent)  
				                            respContent = getMusicUsage();
				                        
				                        TextMessage textMessage = new TextMessage();  
				                        textMessage.setToUserName(fromUserName);  
				                        textMessage.setFromUserName(toUserName);  
				                        textMessage.setCreateTime(new Date().getTime());  
				                        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT); 
				                        textMessage.setContent(respContent);  
				                        respMessage = MessageUtil.textMessageToXml(textMessage);  
				                    } 
				                }
								else if(content.startsWith("天气")){ 
				                    String keyWord = content.replaceAll("^天气[\\+ ~!@#%^-_=]?", "");  
				                    // 如果名称为空  
				                    if ("".equals(keyWord)) {  
				                        respContent = getWeatherUsage();  
				                    } else {  
				                        String[] kwArr = keyWord.split("@");  
				                        // City 
				                        String City = kwArr[0];  
//				                        // 演唱者默认为空  
//				                        String musicAuthor = "";  
//				                        if (2 == kwArr.length)  
//				                            musicAuthor = kwArr[1];  
				  
				                        // 搜索weather 
				                        List<Article> articlelist = BaiduWeatherService.searchWeather(City);  
				                        // 未搜索到weather  
				                        if (null == articlelist) {  
				                            respContent = "对不起，没有找到你想要的天气<" + City + ">。";  
				                        } else {  
				                        	NewsMessage newsMessage = new NewsMessage();
													newsMessage.setToUserName(fromUserName);
													newsMessage.setFromUserName(toUserName);
													newsMessage.setCreateTime(new Date().getTime());
													newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
													newsMessage.setFuncFlag(0);
										
													// 设置图文消息个数
													newsMessage.setArticleCount(articlelist.size());
													// 设置图文消息包含的图文集合
													newsMessage.setArticles(articlelist);
													// 将图文消息对象转换成xml字符串
													respMessage = MessageUtil.newsMessageToXml(newsMessage); 
				                        }  
				                    } 
				                    if (null == respMessage) {  
				                        if (null == respContent)  
				                            respContent = getMusicUsage();
				                        
				                        TextMessage textMessage = new TextMessage();  
				                        textMessage.setToUserName(fromUserName);  
				                        textMessage.setFromUserName(toUserName);  
				                        textMessage.setCreateTime(new Date().getTime());  
				                        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT); 
				                        textMessage.setContent(respContent);  
				                        respMessage = MessageUtil.textMessageToXml(textMessage);
				                    } 									
								}
								
								else{
									respMessage = welcome(fromUserName,toUserName);
								}
		            }  
		            // 图片消息  
		            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
	            			//respContent = "您发送的是图片消息！";
								TextMessage textMessage = new TextMessage();  
								textMessage.setToUserName(fromUserName);  
								textMessage.setFromUserName(toUserName);  
								textMessage.setCreateTime(new Date().getTime());  
								textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
								textMessage.setFuncFlag(0);
								// 取得图片地址  
								String picUrl = requestMap.get("PicUrl");  
								// 人脸检测  
								String detectResult = FaceService.detect(picUrl);  
								textMessage.setContent(detectResult);
								respMessage = MessageUtil.textMessageToXml(textMessage);
		            }  
		            // 地理位置消息  
		            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
		                	 
//		                	<Latitude>23.137466</Latitude>
//		                	<Longitude>113.352425</Longitude>
//		                	<Precision>119.385040</Precision>
		                	String Longitude = (String)requestMap.get("Location_X");//经度
		                	String Latitude = (String)requestMap.get("Location_Y");//纬度
		                
		                	TextMessage textMessage = new TextMessage();  
	                     textMessage.setToUserName(fromUserName);  
	                     textMessage.setFromUserName(toUserName);  
	                     textMessage.setCreateTime(new Date().getTime());  
	                     textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT); 
	                     
	                     respContent = BaiduLocationService.Location(Latitude, Longitude);
	                     textMessage.setContent(respContent);  
	                     respMessage = MessageUtil.textMessageToXml(textMessage);  
		                
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
		                respMessage = welcome(fromUserName,toUserName);
		                }  
		                // 取消订阅  
		                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
		                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
		                }  
		                // 自定义菜单点击事件  
		                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
		                    		// TODO 自定义菜单权没有开放，暂不处理该类消息 
										String eventKey = requestMap.get("EventKey");
										TextMessage textMessage = new TextMessage();  
										textMessage.setToUserName(fromUserName);  
										textMessage.setFromUserName(toUserName);  
										textMessage.setCreateTime(new Date().getTime());  
										textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
										textMessage.setFuncFlag(0);  
		                	  
		                    if (eventKey.equals("11")) {  
		                        //respContent = "天气预报菜单项被点击！";
		                        textMessage.setContent(getWeatherUsage()); 
										respMessage = MessageUtil.textMessageToXml(textMessage);
		                    } else if (eventKey.equals("12")) {  
		                        respContent = "公交查询菜单项被点击！";
		                        textMessage.setContent(respContent);  
		                        respMessage = MessageUtil.textMessageToXml(textMessage);  
		                    } else if (eventKey.equals("13")) {  
		                        respContent = "周边搜索菜单项被点击！";
		                        textMessage.setContent(respContent);  
		                        respMessage = MessageUtil.textMessageToXml(textMessage);  
		                    } else if (eventKey.equals("14")) {  
		                        //respContent = "历史上的今天菜单项被点击！";
			                    	textMessage.setContent(TodayInHistoryService.getTodayInHistoryInfo()); 
										respMessage = MessageUtil.textMessageToXml(textMessage);
		                    } else if (eventKey.equals("15")){
		                    		textMessage.setContent(BaiduMoviesService.searchMovie("北京")); 
										respMessage = MessageUtil.textMessageToXml(textMessage); 
		                    }else if (eventKey.equals("21")) {  
		                        //respContent = "歌曲点播菜单项被点击！"; 
		                    		textMessage.setContent(getMusicUsage()); 
										respMessage = MessageUtil.textMessageToXml(textMessage);
		                    } else if (eventKey.equals("22")) {  
		                        //respContent = "经典游戏菜单项被点击！";
				                  NewsMessage newsMessage = new NewsMessage();
										newsMessage.setToUserName(fromUserName);
										newsMessage.setFromUserName(toUserName);
										newsMessage.setCreateTime(new Date().getTime());
										newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
										newsMessage.setFuncFlag(0);
										
										List<Article> articleList = getGameList();
										
										// 设置图文消息个数
										newsMessage.setArticleCount(articleList.size());
										// 设置图文消息包含的图文集合
										newsMessage.setArticles(articleList);
										// 将图文消息对象转换成xml字符串
										respMessage = MessageUtil.newsMessageToXml(newsMessage);  
		                    } else if (eventKey.equals("23")) {  
//		                        respContent = "美女电台菜单项被点击！"; 
//		                        textMessage.setContent(respContent);  
//		                        respMessage = MessageUtil.textMessageToXml(textMessage);  
		                    } else if (eventKey.equals("24")) {  
		                        //respContent = "人脸识别菜单项被点击！";  
		                        textMessage.setContent(getFaceUsage());  
		                        respMessage = MessageUtil.textMessageToXml(textMessage);  
		                    } else if (eventKey.equals("25")) {  
		                        respContent = "聊天唠嗑菜单项被点击！";
		                        textMessage.setContent(respContent);  
		                        respMessage = MessageUtil.textMessageToXml(textMessage);  
		                    } else if (eventKey.equals("31")) {  
		                        //respContent = "Q友圈菜单项被点击！";
		                    	NewsMessage newsMessage = new NewsMessage();
									newsMessage.setToUserName(fromUserName);
									newsMessage.setFromUserName(toUserName);
									newsMessage.setCreateTime(new Date().getTime());
									newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
									newsMessage.setFuncFlag(0);
					
									List<Article> articleList = new ArrayList<Article>();
									Article article = new Article();
									article.setTitle("About Me");
									article.setDescription("Enjoy code~Enjoy life");
									article.setPicUrl("http://daylife.sinaapp.com/image/2.png");
									article.setUrl("http://www.lizhaozhong.info/my-resume");
									articleList.add(article);
									// 设置图文消息个数
									newsMessage.setArticleCount(articleList.size());
									// 设置图文消息包含的图文集合
									newsMessage.setArticles(articleList);
									// 将图文消息对象转换成xml字符串
									respMessage = MessageUtil.newsMessageToXml(newsMessage);
		                    } else if (eventKey.equals("32")) {  
		                    		respContent = "微社区项被点击！"; 
		                        textMessage.setContent(respContent);  
		                        respMessage = MessageUtil.textMessageToXml(textMessage);  
		                    } else if (eventKey.equals("33")) {  
		                        respContent = "幽默笑话菜单项被点击！"; 
		                        textMessage.setContent(respContent);  
		                        respMessage = MessageUtil.textMessageToXml(textMessage);  
		                    }   
		                }  
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
    	public static String welcome(String fromUserName,String toUserName){
    		   TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);
            
            StringBuffer contentMsg = new StringBuffer();  
//				contentMsg.append("欢迎访问<a href=\"http://www.lizhaozhong.info\">个人主页</a>").append("\n");  
				contentMsg.append("您好，我是生活小贴士，请回复数字选择服务：\ue414").append("\n\n");  
				contentMsg.append("1  天气预报").append("\n");  
				contentMsg.append("2  历史上的今天").append("\n");  
				contentMsg.append("3  智能翻译").append("\n");  
				contentMsg.append("4  歌曲点播").append("\n");  
				contentMsg.append("5  经典游戏").append("\n");  
//				contentMsg.append("6  美女电台").append("\n");  
				contentMsg.append("7  人脸识别").append("\n"); 
//				contentMsg.append("8  聊天唠嗑").append("\n");
				contentMsg.append("9  电影热映排行榜").append("\n\n");
//				contentMsg.append("10 About Me").append("\n\n");
				contentMsg.append("回复“？”显示此帮助菜单");
				
				textMessage.setContent(contentMsg.toString());
				// 将文本消息对象转换成xml字符串
				String respMessage = MessageUtil.textMessageToXml(textMessage);
				return respMessage;
    	}
    	public static String getTranslateUsage() {  
    	    StringBuffer buffer = new StringBuffer();  
    	    //buffer.append(XiaoqUtil.emoji(0xe148)).append("Q译通使用指南").append("\n\n");  
    	    buffer.append("\ue14d 小贴士翻译为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");  
    	    buffer.append("    中 -> 英").append("\n");  
    	    buffer.append("    英 -> 中").append("\n");  
    	    buffer.append("    日 -> 中").append("\n\n");  
    	    buffer.append("使用示例：").append("\n");  
    	    buffer.append("    翻译我是中国人").append("\n");  
    	    buffer.append("    翻译dream").append("\n");  
    	    buffer.append("    翻译さようなら").append("\n\n");  
    	    buffer.append("回复“?”显示主菜单");  
    	    return buffer.toString();  
    	}  
    	public static String getMusicUsage() {  
            StringBuffer buffer = new StringBuffer();  
            buffer.append("\ue03e 歌曲点播操作指南 \ue03e").append("\n\n");  
            buffer.append("回复：歌曲+歌名").append("\n");  
            buffer.append("例如：歌曲存在").append("\n");  
            buffer.append("或者：歌曲存在@汪峰").append("\n\n");  
            buffer.append("回复“?”显示主菜单");  
            return buffer.toString();  
        } 
    	public static String getWeatherUsage() {  
            StringBuffer buffer = new StringBuffer();  
            buffer.append("\ue049 天气预报操作指南 \ue049").append("\n\n");  
            buffer.append("回复：天气+地名").append("\n");  
            buffer.append("例如：天气北京").append("\n\n");  
            buffer.append("回复“?”显示主菜单");  
            return buffer.toString();  
        }
    	/** 
         * 人脸检测帮助菜单 
         */  
        public static String getFaceUsage() {  
            StringBuffer buffer = new StringBuffer();  
            buffer.append("\ue516 人脸检测使用指南 \ue516 ").append("\n\n");  
            buffer.append("发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息").append("\n");  
            buffer.append("快来试试你是不是长得太着急");  
            return buffer.toString();  
        }
        public static List<Article> getGameList() {
	        	List<Article> articleList = new ArrayList<Article>();
				Article article1 = new Article();
				
				article1.setTitle("微信上也能斗地主");
				article1.setPicUrl("http://daylife.sinaapp.com/image/screenhot_0.jpg");
				article1.setUrl("http://www.duopao.com");
				articleList.add(article1);
				
				Article article2 = new Article();
				
				article2.setTitle("傲气雄鹰\n90后不得不玩的经典游戏");
				article2.setDescription("90后不得不玩的经典游戏");
				article2.setPicUrl("http://daylife.sinaapp.com/image/355.jpg");
				article2.setUrl("http://www.duopao.com/games/info?game_code=g20140120024137769765");
				articleList.add(article2);
				
				Article article3 = new Article();
				
				article3.setTitle("飞车高手\n你的驾驶技巧过关么");
				article3.setDescription("你的驾驶技巧过关么");
				article3.setPicUrl("http://daylife.sinaapp.com/image/39.jpg");
				article3.setUrl("http://www.duopao.com/games/info?game_code=g20140102135129409497");
				articleList.add(article3);
				
				return articleList;
        }
}  
