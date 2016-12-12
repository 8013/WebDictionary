package com.njucs.dictionary.modle;

import java.util.Vector;

public class SharedWord {
	private Vector<String[]> sharedword;
	
	public SharedWord(){
		sharedword=new Vector<String[]>();
	}
	
	public int GetSize(){
		return sharedword.size();
	}
	
	public String[] GetInfo(int index){
		return sharedword.get(index);
	}
	
	//客户端用
	public Vector<String[]> getSharedword() {
		return sharedword;
	}

	public void setSharedword(Vector<String[]> sharedword) {
		this.sharedword = sharedword;
	}
	
	//客户端用
	public void AddSharedWord(String FromID,String ToID,String word){
		String s[]=new String[3];
		s[0]=FromID;
		s[1]=ToID;
		s[2]=word;
		this.sharedword.add(s);
	}
	
	//服务器用
	public void AddSharedWord(String FromID,String word){
		String s[]=new String[2];
		s[0]=FromID;
		s[1]=word;
		this.sharedword.add(s);
	}
}
