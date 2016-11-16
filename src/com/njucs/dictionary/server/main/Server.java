package com.njucs.dictionary.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

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
	
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	public ServerSocket serverSocket;
	private ServerFrame sf;
	private Service sv;
	
	public static void main(String[] args) {
		new Server();
	}
	
	public Server(){
		try{
			serverSocket=new ServerSocket(8000);
			sf=new ServerFrame();
			sv=new Service();
			System.out.println("Server started ");
			mainloop();
		} catch(IOException ex){
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void mainloop() throws IOException, ClassNotFoundException{
		while(true){
			Response response=new Response(100,"ok");
			Socket socket=serverSocket.accept();
			fromClient=new ObjectInputStream(socket.getInputStream());
			toClient=new ObjectOutputStream(socket.getOutputStream());
			
			Request request=(Request)fromClient.readObject();
			System.out.println(request.getNo() + " " + request.getUser().getUsername() + " " +
					request.getUser().getPassword());
			switch(request.getNo()){
			case 1:{
				try {
					response=sv.VerifyPassword(request.getUser().getUsername(), request.getUser().getPassword());
					if(response.getNo()==100)
						sf.AddMessage("Login: ID:"+request.getUser().getUsername()+" Success!", sf.GetTypeIndex("Login"));
					else
						sf.AddMessage("Login: ID:"+request.getUser().getUsername()+" Fail!", sf.GetTypeIndex("Login"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case 2:{
				try{
					response=sv.Register(request.getUser().getUsername(), request.getUser().getPassword(), request.getUser().getEmail());
					if(response.getNo()==200){
						sf.AddMessage("Register: ID:"+request.getUser().getUsername()+" Success!",sf.GetTypeIndex("Regist"));
					}
					else
						sf.AddMessage("Register: ID:"+request.getUser().getUsername()+" Fail!",sf.GetTypeIndex("Regist"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			default:break;
			}
			toClient.writeObject(response);
		}
	}
}
