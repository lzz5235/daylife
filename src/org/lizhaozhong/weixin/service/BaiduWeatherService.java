package org.lizhaozhong.weixin.service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.lizhaozhong.weixin.message.resp.Article;

public class BaiduWeatherService {

	public static List<Article> searchWeather(String City) {

		String requestUrl = "http://api.map.baidu.com/telematics/v3/weather?location={CITY}&output=xml&ak=ric9eLENBDb88Y5CXO5ZMcGV";

		requestUrl = requestUrl.replace("{CITY}", urlEncodeUTF8(City));
		
		// 查询并获取返回结果
		InputStream inputStream = httpRequest(requestUrl);
		
		List<Article> article = parseWeather(inputStream);

		return article;
	}

	/**
	 * UTF-8编码
	 * 
	 * @param source
	 * @return
	 */
	private static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 发送http请求取得返回的输入流
	 * 
	 * @param requestUrl 请求地址
	 * @return InputStream
	 */
	private static InputStream httpRequest(String requestUrl) {
		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
         httpUrlConn.setDoOutput(false);  
         httpUrlConn.setDoInput(true);  
         httpUrlConn.setUseCaches(false);  
  
         httpUrlConn.setRequestMethod("GET");  
         httpUrlConn.connect(); 
			// 获得返回的输入流
			inputStream = httpUrlConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	/**
	 * 解析天气参数
	 * 
	 * @param inputStream 百度天气搜索API返回的输入流
	 * @return Music
	 */
	@SuppressWarnings("unchecked")
	private static List<Article> parseWeather(InputStream inputStream) {
		List<Article> articlelist = new ArrayList<Article>();
		
		List<Element> listdate = null;
		List<Element> listday = null;
		List<Element> listnight = null;
		List<Element> listweather = null;
		List<Element> listwind = null;
		List<Element> listtemporture = null;
		
		try {
			// 使用dom4j解析xml字符串
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			
			String status = root.element("status").getText();			
			
			if ("success".equals(status)) {
				
				Iterator iter = root.elementIterator("results");
				Element currentCity = root.element("results").element("currentCity");
				
				while(iter.hasNext())
				{
					Element child = (Element)iter.next();
					Iterator iters = child.elementIterator("weather_data");
					
					while(iters.hasNext())
					{
						Element childs = (Element)iters.next();
						
						listdate = childs.elements("date");
						listday = childs.elements("dayPictureUrl");
						listnight = childs.elements("nightPictureUrl");
						listweather = childs.elements("weather");
						listwind = childs.elements("wind");
						listtemporture = childs.elements("temperature");
					}
					
					for(int i=0;i<listday.size();i++)
					{
						Article article = new Article();
						
						Element eledate = (Element)listdate.get(i);
						Element eleday = (Element)listday.get(i);
						Element elenight = (Element)listnight.get(i);
						Element eleweather = (Element)listweather.get(i);
						Element elewind = (Element)listwind.get(i);
						Element eletemporture = (Element)listtemporture.get(i);
						
						article.setTitle(eledate.getText()+"\n"+eleweather.getText()+" "+elewind.getText()+" "+eletemporture.getText());
						article.setDescription("");
						article.setPicUrl(eleday.getText());
						//article.setUrl(eleday.getText());
						
						articlelist.add(article);
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articlelist;
	}
}

