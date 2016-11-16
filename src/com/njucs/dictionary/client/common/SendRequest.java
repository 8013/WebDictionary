package com.njucs.dictionary.client.common;

import java.io.*;
import java.net.Socket;
import com.njucs.dictionary.modle.Request;
import com.njucs.dictionary.modle.Response;
/**
 * 向服务器发送请求
 * @author zhe
 */
public class SendRequest {
	
	private static Socket socket=null;
	private static ObjectOutputStream toServer=null;
	private static ObjectInputStream fromServer=null;
	
	public static void Send(Request request){
		// socket初始化
		if(socket==null){
			try {
				socket=new Socket("localhost", 8000);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// socket不为空时对输入输出流初始化
		if(socket!=null){
			try{
				if(toServer==null){
					toServer=new ObjectOutputStream(socket.getOutputStream());
				}
				if(fromServer==null){
					fromServer=new ObjectInputStream(socket.getInputStream());
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 发送请求request，获得服务器响应信息response，并处理响应信息
		try {	
			toServer.writeObject(request);
			Response response=(Response)fromServer.readObject();		
			HandleResponse.Handle(response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void CloseSocket(){
		if(socket!=null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
