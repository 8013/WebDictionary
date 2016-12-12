package com.njucs.dictionary.client.home.tools;

import java.util.ArrayList;

import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.modle.Request;
import com.njucs.dictionary.modle.Word;

public class GetNews {
	public static ArrayList<Word> words;
	
	public static void FromServer(){
		Request request=new Request(9);
		SendRequest.Send(request);
	}
	
	public static int unread(){
		int n=0;
		for(int i=0;i<GetNews.words.size();i++){
			if(!GetNews.words.get(i).isRead()){
				n++;
			}
		}
		return n;
	}
	
}
