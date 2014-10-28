package org.lizhaozhong.weixin.thread;


import org.lizhaozhong.weixin.pojo.AccessToken;
import org.lizhaozhong.weixin.util.WeixinUtil;
import org.lizhaozhong.weixin.menu.MenuManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时获取微信access_token的线程
 * 
 * @author liuyq
 * @date 2013-05-02
 */
public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	// 第三方用户唯一凭证
	public static String appid = "";
	// 第三方用户唯一凭证密钥
	public static String appsecret = "";
	public static AccessToken accessToken = null;

	public void run() {
		while (true) {
			try {
				accessToken = WeixinUtil.getAccessToken(appid, appsecret);
				if (null != accessToken) {
					log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());
					// 调用接口创建菜单
					int result = WeixinUtil.createMenu(MenuManager.getMenu(), accessToken.getToken());
					
		            // 判断菜单创建结果  
		            if (0 == result)  
		                log.info("菜单创建成功！");  
		            else  
		                log.info("菜单创建失败，错误码：" + result);					
					
					// 休眠7000秒
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(60 * 1000);
				}				
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
				}
				log.error("{}", e);
			}
		}
	}
}
