package com.njucs.dictionary.modle;

import java.util.Vector;

class TableStruct{
	private String user;
	private int state;
	
	public TableStruct(){
	}
	
	public TableStruct(String user, int state){
		this.user=user;
		this.state=state;
	}
	
	public String getUser(){
		return user;
	}
	
	public int getState(){
		return state;
	}
	
	public void setUser(String user){
		this.user=user;
	}
	
	public void setState(int state){
		this.state=state;
	}
}

public class UserTable {
	private Vector<TableStruct> User;
	
	public UserTable(){
	}
	
	public UserTable(Vector<TableStruct> User){
		this.User=User;
	}
	
	public Vector<TableStruct> GetUserTable(){
		return User;
	}
	
	public void Add(TableStruct ts){
		User.add(ts);
	}
	
	public void Add(String user, int state){
		TableStruct ts=new TableStruct(user,state);
		User.add(ts);
	}
	
	public void Remove(int index){
		User.remove(index);
	}
	
	public void Remove(String user){
		int index=0;
		for(int i=0;i<User.size();i++){
			if(User.get(i).getUser().equals(user)){
				index=i;
				break;
			}
		}
		Remove(index);
	}
}
