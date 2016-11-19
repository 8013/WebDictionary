package com.njucs.dictionary.client.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

// 有道查询
public class Youdao {
	 private static String url = "http://fanyi.youdao.com/openapi.do";
	 private static String keyfrom = "DictionaryZZ";
	 private static String key = "282526326";
	 private static String doctype = "xml";
	 
	 private static String sendGet(String str) {
		 // 编码成UTF-8
		 try {
			 str = URLEncoder.encode(str, "utf-8");
		 } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		 }

		 StringBuffer buf = new StringBuffer();
		 buf.append("keyfrom=").append(keyfrom).append("&key=").append(key);
		 buf.append("&type=data&doctype=").append(doctype).append("&version=1.1&q=").append(str);

		 String param = buf.toString();

		 String result = "";
		 BufferedReader in = null;
		 try {
		 	String urlName = url + "?" + param;
		 	URL realUrl = new URL(urlName);
    
		 	// 打开和URL之间的连接
		 	URLConnection conn = realUrl.openConnection();
		 	in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		 	String str1=in.readLine();
		 	while(str1!=null){
		 		result+=str1;
		 		str1=in.readLine();
		 	}
		 }catch(Exception e){
			System.out.println(e.getMessage());
		 }	
		 return result;
	}
	
	public static String Translate(String str) {
		String result = null;

		// 发送GET请求翻译
		result = sendGet(str);
		// 处理XML中的值
		int re1 = result.indexOf("<errorCode>");
		int re2 = result.indexOf("</errorCode>");
		String temp="",explanation="";
		if(re1>0&&re2>0){
			temp = result.substring(re1 + 11, re2);
		}
		else{
			return "NULL";
		}
			
		if (temp.equals("0")) {			
			while((re1 = result.indexOf("<ex><![CDATA["))>0 && (re2 = result.indexOf("]]></ex>"))>0){
				temp =result.substring(re1 + 13, re2)+"\n";
				explanation+=temp;
				result=result.substring(re2+7);
			}
/*			re1 = result.indexOf("<ex><![CDATA[");
			re2 = result.indexOf("]]></ex>");
			if(re1>0&&re2>0){
				temp =result.substring(re1 + 13, re2);
				explanation+=temp;
			}
*/		
		} else{
			explanation="NULL";
		}
		return explanation;
	}
	
 }