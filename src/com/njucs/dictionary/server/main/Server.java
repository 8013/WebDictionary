package com.njucs.dictionary.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.njucs.dictionary.modle.Request;
import com.njucs.dictionary.modle.Response;

public class Server {
	
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	public ServerSocket serverSocket;
	
	public static void main(String[] args) {
		new Server();
	}
	
	public Server(){
		try{
			serverSocket=new ServerSocket(8000);
			System.out.println("Server started ");
			
			while(true){
				Socket socket=serverSocket.accept();
				fromClient=new ObjectInputStream(socket.getInputStream());
				toClient=new ObjectOutputStream(socket.getOutputStream());
				
				Request request=(Request)fromClient.readObject();
				System.out.println(request.getNo() + " " + request.getUser().getUsername() + " " +
						request.getUser().getPassword());
				
				Response response=new Response(10, request.getUser().getUsername());
				toClient.writeObject(response);
			}
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
	
}
