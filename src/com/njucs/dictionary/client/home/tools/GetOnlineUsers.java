package com.njucs.dictionary.client.home.tools;

import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.modle.Request;

public class GetOnlineUsers {
		
		static String[][] userTable;
		
		public static String[][] FromServer(){
			Request request=new Request(6);
			SendRequest.Send(request);
			return userTable;
		}
		
		public static void setUserTable(String[][] usertable){
			userTable=usertable;
		}
}
