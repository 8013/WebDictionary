package com.njucs.dictionary.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.njucs.dictionary.modle.Request;
import com.njucs.dictionary.modle.Response;
import com.njucs.dictionary.server.frame.ServerFrame;
import com.njucs.dictionary.server.service.Service;

/*
 * 
 * 服务器入口
 * 
 */

public class Server {
	private ServerSocket serverSocket;
	private ServerFrame serverframe;
	private Service service;
	
	private int OnlineNum=0;
	
	public static void main(String[] args) {
		new Server();
	}
	
	public Server(){
		try{
			serverSocket=new ServerSocket(8000);
			serverframe=new ServerFrame();
			service=new Service();
			System.out.println("Server started ");
			mainloop();
		} catch(IOException ex){
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				serverSocket.close();
				serverframe.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//保证OnlineNum不会出现小于0的情况
	private synchronized void AddOnlineNum(){
		OnlineNum++;
		serverframe.SetOnlineNum(OnlineNum);
	}
	
	private synchronized void MinusOnlineNum(){
		OnlineNum--;
		serverframe.SetOnlineNum(OnlineNum);
	}
	
	private Response HandleRequest(Request request, String IPAddr){
		Response response=new Response(100,"请求号码错误");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		switch(request.getNo()){
		case 1:{
			try {
				response=service.VerifyPassword(request.getUser().getUsername(), request.getUser().getPassword());
				if(response.getNo()==100){
					serverframe.AddMessage("Login", "ID:"+request.getUser().getUsername(), "Success", sdf.format(new Date()), IPAddr);
				}
				else{
					serverframe.AddMessage("Login", "ID:"+request.getUser().getUsername(), "Fail", sdf.format(new Date()), IPAddr);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
		case 2:{
			try{
				response=service.Register(request.getUser().getUsername(), request.getUser().getPassword(), request.getUser().getEmail());
				if(response.getNo()==200){
					serverframe.AddMessage("Register", "ID:"+request.getUser().getUsername(), "Success", sdf.format(new Date()), IPAddr);
				}
				else{
					serverframe.AddMessage("Register", "ID:"+request.getUser().getUsername(), "Fail", sdf.format(new Date()), IPAddr);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
		case 3:{
			try{
				response=service.GetLikeNum(request.getWord(), request.getUser().getUsername());
				serverframe.AddMessage("Searchlikenum", "ID:"+request.getUser().getUsername()+" Word:"+request.getWord(), "", sdf.format(new Date()), IPAddr);
			} catch (SQLException e){
				e.printStackTrace();
			}
			break;
		}
		case 4:{
			try{
				response=service.UpdateLikeNum(request.getUser().getUsername(), request.getWord(), request.getLike());
				if(response.getNo()==301)
					serverframe.AddMessage("Like", "ID:"+request.getUser().getUsername()+" Word:"+request.getWord(), "", sdf.format(new Date()), IPAddr);
				else
					serverframe.AddMessage("Cancellike", "ID:"+request.getUser().getUsername()+" Word:"+request.getWord(), "", sdf.format(new Date()), IPAddr);
			} catch(SQLException e){
				e.printStackTrace();
			}
			break;
		}
		default:break;
		}
		return response;
	}
	
	private void mainloop() throws IOException, ClassNotFoundException{
		while(true){
			Socket socket=serverSocket.accept();
			HandleARequest handleArequest=new HandleARequest(socket);
			new Thread(handleArequest).start();
			AddOnlineNum();
		}
	}
	
	private class HandleARequest implements Runnable{
		Socket socket;
		
		private ObjectInputStream fromClient;
		private ObjectOutputStream toClient;

		public HandleARequest(Socket socket){
			this.socket=socket;
		}
		
		@Override
		public void run() {
			try {
				fromClient=new ObjectInputStream(socket.getInputStream());
				Response response;
				toClient=new ObjectOutputStream(socket.getOutputStream());
				String IPAddr=socket.getInetAddress().getHostAddress();
				while(true){
					//serverframe.AddMessage("Client IP:"+socket.getInetAddress().getHostAddress(),serverframe.GetTypeIndex("all"));
					Request request;
					request = (Request)fromClient.readObject();
					response=HandleRequest(request, IPAddr);
					toClient.writeObject(response);
					try{
						socket.sendUrgentData(0);
					} catch(IOException e){
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e){
				e.printStackTrace();
			}
			MinusOnlineNum();
		}
		
	}
}
