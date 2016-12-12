package com.njucs.dictionary.client.home.tools;

import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.modle.Request;

public class GetOnlineUsers {
		
		public static String[][] userTable;
		
		public static void FromServer(){
			Request request=new Request(6);
			SendRequest.Send(request);
		}
		
		public static void setUserTable(String[][] usertable){
			userTable=usertable;
		}
}
