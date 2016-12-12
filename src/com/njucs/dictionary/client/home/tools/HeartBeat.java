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
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			Request request=new Request(6);
			if(SendRequest.SendHeartBeat(request)==false){
				break;
			}
			Home.onlineUserPanel.Update();
		}
	}
}
