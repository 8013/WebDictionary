package com.njucs.dictionary.client.home.tools;

import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.client.home.Home;
import com.njucs.dictionary.modle.Request;

public class HeartBeat implements Runnable{
	public static boolean running=true;
	@Override
	public void run() {
		while(running){
			try {
				Thread.sleep(9999);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(running){
				Request request=new Request(6);
				if(SendRequest.SendHeartBeat(request)==false)
					break;
				Home.onlineUserPanel.Update();
				
				request=new Request(9);
				if(SendRequest.SendHeartBeat(request)==false)
					break;
				Home.logoutPanel.Update(GetNews.unread());
				
			}
		}
	}
}
