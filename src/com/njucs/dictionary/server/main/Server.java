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
		
	private void mainloop() throws IOException, ClassNotFoundException{
		while(true){
			Socket socket=serverSocket.accept();
			HandleARequest handleArequest=new HandleARequest(socket);
			new Thread(handleArequest).start();
		}
	}
	
	private class HandleARequest implements Runnable{
		Socket socket;
		
		private ObjectInputStream fromClient;
		private ObjectOutputStream toClient;
		private String id=null;
		private boolean stop=false;
		
		public HandleARequest(Socket socket){
			this.socket=socket;
		}
		
		@Override
		public void run() {
			try {
				fromClient=new ObjectInputStream(socket.getInputStream());
				toClient=new ObjectOutputStream(socket.getOutputStream());
				String IPAddr=socket.getInetAddress().getHostAddress();
				//id=((Request)fromClient.readObject()).getUser().getUsername();
				while(!stop){
					//serverframe.AddMessage("Client IP:"+socket.getInetAddress().getHostAddress(),serverframe.GetTypeIndex("all"));
					Request request;
					request = (Request)fromClient.readObject();
					if(id==null&&request.getUser().getUsername()!=null){
						id=request.getUser().getUsername();
						AddOnlineNum();
						try {
							service.UpdateUserState(id, 1);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					Response response;
					response=HandleRequest(request, IPAddr, id);
					if(response.getNo()==111)
						stop=true;
					else
						toClient.writeObject(response);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		
		private Response HandleRequest(Request request, String IPAddr, String id){
			Response response=new Response(100,"请求号码错误");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			switch(request.getNo()){
			case 1:{
				try {
					//id=request.getUser().getUsername();
					response=service.VerifyPassword(id, request.getUser().getPassword());
					if(response.getNo()==100){
						serverframe.AddMessage("Login", "ID:"+id, "Success", sdf.format(new Date()), IPAddr);
					}
					else{
						serverframe.AddMessage("Login", "ID:"+id, "Fail", sdf.format(new Date()), IPAddr);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case 2:{
				try{
					//id=request.getUser().getUsername();
					response=service.Register(id, request.getUser().getPassword(), request.getUser().getEmail());
					if(response.getNo()==200){
						serverframe.AddMessage("Register", "ID:"+id, "Success", sdf.format(new Date()), IPAddr);
					}
					else{
						serverframe.AddMessage("Register", "ID:"+id, "Fail", sdf.format(new Date()), IPAddr);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case 3:{
				try{
					response=service.GetLikeNum(request.getWord(),id);
					serverframe.AddMessage("Searchlikenum", "ID:"+id+" Word:"+request.getWord(), "", sdf.format(new Date()), IPAddr);
				} catch (SQLException e){
					e.printStackTrace();
				}
				break;
			}
			case 4:{
				try{
					response=service.UpdateLikeNum(id,request.getWord(), request.getLike());
					if(response.getNo()==301)
						serverframe.AddMessage("Like", "ID:"+id+" Word:"+request.getWord(), "", sdf.format(new Date()), IPAddr);
					else
						serverframe.AddMessage("Cancellike", "ID:"+id+" Word:"+request.getWord(), "", sdf.format(new Date()), IPAddr);
				} catch(SQLException e){
					e.printStackTrace();
				}
				break;
			}
			case 5:{
				try {
					response=new Response(110,"");
					service.UpdateUserState(id, 0);
					serverframe.AddMessage("Logout", "ID:"+id, "Success", sdf.format(new Date()), IPAddr);
					this.id=null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case 6:{
				try {
					response=service.GetUserTable();
					serverframe.AddMessage("GetUserTable", "ID:"+id, "Success", sdf.format(new Date()), IPAddr);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case 7:{
				try {
					MinusOnlineNum();
					response=new Response(111,"");
					service.UpdateUserState(id, 0);
					serverframe.AddMessage("Exit", "ID:"+id, "Success", sdf.format(new Date()), IPAddr);
					this.id=null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case 10:{
				//心跳包的请求，也用于获取消息列表和用户列表
				try{
					//获取分享消息
					response=service.GetSharedWord(id);
					//获取用户列表
					response.setUsertable(service.GetUserTable().getUsertable());
					serverframe.AddMessage("GetSharedTable", "ID"+id, "Success", sdf.format(new Date()), IPAddr);
				} catch(SQLException e){
					e.printStackTrace();
				}
				break;
			}
			case 11:{
				//发送分享列表
				try{
					response=service.SendSharedWord(request.getSharedword());
					serverframe.AddMessage("SendSharedWord", "ID:"+id, "Success", sdf.format(new Date()), IPAddr);
				} catch(SQLException e){
					e.printStackTrace();
				}
				break;
			}
			default:break;
			}
			return response;
		}
	}
}
