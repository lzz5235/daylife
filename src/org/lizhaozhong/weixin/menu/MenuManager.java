package org.lizhaozhong.weixin.menu;
 
import org.lizhaozhong.weixin.pojo.Button;  
import org.lizhaozhong.weixin.pojo.CommonButton;  
import org.lizhaozhong.weixin.pojo.ComplexButton;  
import org.lizhaozhong.weixin.pojo.ViewButton;
import org.lizhaozhong.weixin.pojo.Menu;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
/** 
 * 菜单管理器类 
 *  
 * @author lizhaozhong 
 * @date 2014-05-08 
 */  
public class MenuManager {  
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
  
//    public static void main(String[] args) {  
//        // 第三方用户唯一凭证  
//        String appId = "wx3f8268324cc0d89e";  
//        // 第三方用户唯一凭证密钥  
//        String appSecret = "e5d1d5d57f3a070b55ac2e3738a96000";  
//  
//        // 调用接口获取access_token  
//        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);  
//  
//        if (null != at) {  
//            // 调用接口创建菜单  
//            int result = WeixinUtil.createMenu(getMenu(), at.getToken());  
//  
//            // 判断菜单创建结果  
//            if (0 == result)  
//                log.info("菜单创建成功！");  
//            else  
//                log.info("菜单创建失败，错误码：" + result);  
//        }  
//    }  放到initServlet里面。
  
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    public static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("天气预报");  
        btn11.setType("click");  
        btn11.setKey("11");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("公交查询");  
        btn12.setType("click");  
        btn12.setKey("12");  
  
        CommonButton btn13 = new CommonButton();  
        btn13.setName("周边搜索");  
        btn13.setType("click");  
        btn13.setKey("13");  
  
        CommonButton btn14 = new CommonButton();  
        btn14.setName("历史上的今天");  
        btn14.setType("click");  
        btn14.setKey("14");
        
        CommonButton btn15 = new CommonButton();  
        btn15.setName("大陆热播电影");  
        btn15.setType("click");  
        btn15.setKey("15");
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("歌曲点播");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("经典游戏");  
        btn22.setType("click");  
        btn22.setKey("22");  
  
        ViewButton btn23 = new ViewButton();  
        btn23.setName("挑战2048");  
        btn23.setType("view");  
        btn23.setUrl("http://www.duopao.com/games/info?game_code=g20140324115109221580");  
  
        CommonButton btn24 = new CommonButton();  
        btn24.setName("人脸识别");  
        btn24.setType("click");  
        btn24.setKey("24");  
  
        CommonButton btn25 = new CommonButton();  
        btn25.setName("聊天唠嗑");  
        btn25.setType("click");  
        btn25.setKey("25");  
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("About Me");  
        btn31.setType("click");  
        btn31.setKey("31");  
  
        CommonButton btn32 = new CommonButton();  
        btn32.setName("微社区");  
        btn32.setType("click");  
        btn32.setKey("32");  
  
        CommonButton btn33 = new CommonButton();  
        btn33.setName("幽默笑话");  
        btn33.setType("click");  
        btn33.setKey("33");  
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("生活助手");  
        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14 , btn15});  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("休闲驿站");  
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("更多体验");  
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }  
}  
