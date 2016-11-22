package com.njucs.dictionary.modle;

import java.io.Serializable;
import java.util.Vector;

class TableStruct implements Serializable{
	private static final long serialVersionUID = -4030412289589493788L;
	private String user;
	private int state;
	
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
	
}

public class UserTable implements Serializable{
	private static final long serialVersionUID = 3151726305316856748L;
	private Vector<TableStruct> User;
	
	public UserTable(){
		User=new Vector<>();
	}
	
	public UserTable(Vector<TableStruct> User){
		this.User=User;
	}
	
	public Vector<TableStruct> GetUserTable(){
		return User;
	}
	
	public String[][] Visit(){
		String[][] users=new String[User.size()][];
		for(int i=0;i<User.size();i++){
			users[i]=new String[2];
			users[i][0]=User.get(i).getUser();
			users[i][1]=User.get(i).getState()>0?"在线":"离线";
		}
		return users;
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
