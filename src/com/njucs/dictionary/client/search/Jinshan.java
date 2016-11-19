package com.njucs.dictionary.client.search;

import java.io.*;
import java.net.*;

public class Jinshan {
	private static String apiUrl="http://dict-co.iciba.com/api/dictionary.php";
	private static String key="E6067027F5488B571B378DAE3884CC73";
	
	// 向金山翻译的查词接口发送请求，得到XML格式的返回文件
	private static String SendGet(String word){
		// 构造查词接口的URL参数
		StringBuffer parameter=new StringBuffer();
		parameter.append(apiUrl).append("?")
					   .append("w=").append(word)
					   .append("&key=").append(key);
		
		String result="";
		
		try {
			// 打开和URL的连接，并创建输入流
			URL url=new URL(parameter.toString());
			URLConnection connection=url.openConnection();
			BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			// 读取
			String str;
			while((str=in.readLine())!=null){
				result+=str;
				result+="\n";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 获取发音	当前编码是UTF-8，显示音标会有乱码
	@SuppressWarnings("unused")
	private static String getPronunciation(String result){
		// 英式和美式<ps>
		int p1=0, p2=0, n=0;
		String pronunciation="";
		while((p1=result.indexOf("<ps>"))>0 && (p2=result.indexOf("</ps>"))>0){
			if(n==0){
				pronunciation+="英式音标： / ";
				pronunciation+=result.substring(p1+4, p2);
				pronunciation+=" /     \n";
				result=result.substring(p2+5);
				n++;
			}
			else{
				pronunciation+="美式音标： / ";
				pronunciation+=result.substring(p1+4, p2);
				pronunciation+=" /     \n";
				result=result.substring(p2+5);
			}
		}
		return pronunciation;
	}

	// 获取词性和释义
	private static String getExplanation(String result){
		// 包括词性<pos>和释义<acceptation>
		int p1=0, p2=0;
		String explanation="";
		while((p1=result.indexOf("<pos>"))>0 && (p2=result.indexOf("</pos>"))>0){
			explanation+=result.substring(p1+5, p2);
			p1=result.indexOf("<acceptation>");
			p2=result.indexOf("</acceptation>");
			explanation+=result.substring(p1+13, p2);
			result=result.substring(p2+14);
		}
		return explanation+"\n";
	}
	
	// 获取例句和翻译
	private static String getExampleSentence(String result){
		// 包括英语原句<orig>和汉语翻译<trans>
		int p1=0, p2=0, n=1;
		String exampleSentence="";
		while((p1=result.indexOf("<sent><orig>"))>0 && (p2=result.indexOf("</orig>"))>0){
			exampleSentence+="例句"+n+":\n";
			exampleSentence+=result.substring(p1+13, p2);
			p1=result.indexOf("<trans>");
			p2=result.indexOf("</trans></sent>");
			exampleSentence+=result.substring(p1+8, p2)+"\n";
			result=result.substring(p2+15);
			n++;
		}
		return exampleSentence;
	}
	
	// 外部接口，供翻译使用
	public static String Translate(String word){
		String result=SendGet(word);
		
		String explanation=word+"\n";
//		explanation+=getPronunciation(result);
		explanation+=getExplanation(result);
		explanation+=getExampleSentence(result);
		
		return explanation;
	}
	
}
