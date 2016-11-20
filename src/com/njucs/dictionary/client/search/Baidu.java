package com.njucs.dictionary.client.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Baidu {
	private static StringBuilder contentbuffer;
	
	private static void FetchHTML(String word) throws IOException{
		String strURL="http://dict.baidu.com/s?wd="+word+"&device=pc&from=home&q=privac";
		URL url=new URL(strURL);
		HttpURLConnection httpconn=(HttpURLConnection)url.openConnection();
		InputStreamReader input=new InputStreamReader(httpconn.getInputStream(),"utf-8");
		BufferedReader reader=new BufferedReader(input);
		String line="";
		contentbuffer=new StringBuilder();
		while((line=reader.readLine())!=null){
			contentbuffer.append(line);
			contentbuffer.append("\n");
		}
	}
	
	private static Vector<String> getPronounce(){
		Vector<String> v=new Vector<String>();
		String reg="<b lang=.*?</b>";
		Matcher m=Pattern.compile(reg).matcher(contentbuffer);
		while(m.find()){
			String s=m.group().substring(m.group().indexOf("["), m.group().indexOf("]")+1);
			v.add(s);
			//System.out.println(s);
		}
		return v;
	}
	
	private static Vector<String> getTranslation(){
		Vector<String> Translation=new Vector<String>();
		
		//将有释义的一行分离出来
		String reg="<div><p><strong>.*?\n";
		Matcher m=Pattern.compile(reg).matcher(contentbuffer);
		if(m.find()){
			String input=m.group();
			//System.out.println(input);
			
			//搜索包含翻译的字段
			reg="<div>.*?</div>";
			m=Pattern.compile(reg).matcher(input);
			if(m.find()){
				
				//分离多个释义
				String reginside="<p>.*?</p>";
				Matcher minside=Pattern.compile(reginside).matcher(m.group());
				while(minside.find()){
					String s=minside.group().substring(minside.group().indexOf("<strong>")+"<strong>".length(),minside.group().indexOf("</strong>"));
					if(s.indexOf("&amp;")!=-1){
						s=s.replace("&amp;", "&");
					}
					s+="\t";
					s+=minside.group().substring(minside.group().indexOf("<span>")+"<span>".length(), minside.group().indexOf("</span>"));
					Translation.add(s);
					//System.out.println(s);
				}
			}
			
			//得到其他信息
			input=input.substring(m.group().length());
			//System.out.println(input);
			reg="<span>.*?</span>";
			m=Pattern.compile(reg).matcher(input);
			while(m.find()){
				String s=m.group().substring(m.group().indexOf("<span>")+"<span>".length(), m.group().indexOf("<a"));
				s+="\t";
				String reginside="<a href.*?</a>";
				Matcher minside=Pattern.compile(reginside).matcher(m.group());
				while(minside.find()){
					s+=minside.group().substring(minside.group().indexOf("\">")+"\">".length(),minside.group().indexOf("</a>"));
					s+=" ";
				}
				Translation.add(s);
				//System.out.println(s);
			}
		}
		return Translation;
	}
	
	public static String Translate(String word){
		String res=new String();
		try {
			FetchHTML(word);
			Vector<String> v=getPronounce();
//			for(int i=0;i<v.size();i++){
//				res+=v.get(i);
//			}
			v=getTranslation();
			for(int i=0;i<v.size();i++){
				res+=v.get(i)+"\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
