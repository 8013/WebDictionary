package com.njucs.dictionary.client.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Biying {
	private StringBuilder content;
	
	public Biying(){
		content=new StringBuilder();
	}
	
	public Biying(String Word){
		try {
			content=new StringBuilder();
			FetchContent(Word);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void FetchContent(String word) throws IOException{
		String strURL="http://xtk.azurewebsites.net/BingDictService.aspx?Word="+word;
		URL url=new URL(strURL);
		HttpURLConnection httpconn=(HttpURLConnection)url.openConnection();
		InputStreamReader input=new InputStreamReader(httpconn.getInputStream(),"utf-8");
		BufferedReader reader=new BufferedReader(input);
		String line="";
		while((line=reader.readLine())!=null){
			content.append(line).append("\n");
		}
	}
	
	public String GetPronunciation(){
		StringBuilder buffer=new StringBuilder();
		buffer.append("发音:\n");
		String reg="\"pronunciation\":\\{.*?\\}";
		Matcher matcher=Pattern.compile(reg).matcher(content.toString());
		while(matcher.find()){
			String reginside="\"AmE\":\".*?\"";
			Matcher matcherinside=Pattern.compile(reginside).matcher(matcher.group());
			while(matcherinside.find()){
				String s=matcherinside.group();
				s=s.substring(s.indexOf("\"AmE\":\"")+"\"AmE\":\"".length(),s.length()-1);
				buffer.append("美音:"+s).append("\n");
			}
			reginside="\"BrE\":\".*?\"";
			matcherinside=Pattern.compile(reginside).matcher(matcher.group());
			while(matcherinside.find()){
				String s=matcherinside.group();
				s=s.substring(s.indexOf("\"BrE\":\"")+"\"BrE\":\"".length(), s.length()-1);
				buffer.append("英音:"+s).append("\n");
			}
		}
		buffer.append("\n");
		return buffer.toString();
	}
	
	public String GetMeaning(){
		StringBuilder buffer=new StringBuilder();
		buffer.append("释义:\n");
		String reg="\"defs\":\\[\\{.*?\\}\\]";
		Matcher matcher=Pattern.compile(reg).matcher(content.toString());
		while(matcher.find()){
			String reginside="\\{.*?\\}";
			Matcher matcherinside=Pattern.compile(reginside).matcher(matcher.group());
			while(matcherinside.find()){
				String s=matcherinside.group();
				s=s.substring(s.indexOf("\"pos\":\"")+"\"pos\":\"".length(), s.indexOf("\","))+"\t"+s.substring(s.indexOf("\"def\":\"")+"\"def\":\"".length(), s.indexOf("\"}"));
				buffer.append(s).append("\n");
			}
		}
		buffer.append("\n");
		return buffer.toString();
	}
	
	public String GetSample(){
		StringBuilder buffer=new StringBuilder();
		buffer.append("例句:\n");
		String reg="\"sams\":\\[\\{.*?\\}\\]";
		Matcher matcher=Pattern.compile(reg).matcher(content.toString());
		while(matcher.find()){
			String reginside="\\{.*?\\}";
			Matcher matcherinside=Pattern.compile(reginside).matcher(matcher.group());
			while(matcherinside.find()){
				String s=matcherinside.group();
				String eng=s.substring(s.indexOf("\"eng\":\"")+"\"eng\":\"".length(), s.indexOf("\","));
				s=s.substring(s.indexOf("\",")+"\",".length());
				String chn=s.substring(s.indexOf("\"chn\":\"")+"\"chn\":\"".length(), s.indexOf("\","));
				eng=eng.replaceAll("\\\\\"", "\"");
				chn=chn.replaceAll("\\\\\"", "\"");
				buffer.append(eng+"\n"+chn).append("\n").append("\n");
			}
		}
		buffer.append("\n");
		return buffer.toString();
	}
	
	public static String Translate(String word){
		Biying b=new Biying(word);
		return b.GetMeaning()+b.GetSample();
	}
	
}
