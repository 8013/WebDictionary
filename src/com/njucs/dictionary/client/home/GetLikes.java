package com.njucs.dictionary.client.home;

import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.modle.Request;

public class GetLikes {
	
	public static void FromServer(String word){
		Request request=new Request(3, word);
		SendRequest.Send(request);
	}
}
