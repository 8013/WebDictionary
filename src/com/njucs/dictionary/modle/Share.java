package com.njucs.dictionary.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Share implements Serializable{
	private static final long serialVersionUID = -571660554343203972L;
	private ArrayList<String> shareList;
	private String word;
	private String type;		// "百度" "有道" "金山"
	private Date date;
	
	public Share(ArrayList<String> list, String word, String type){
		this.shareList=list;
		this.word=word;
		this.type=type;
		this.date=new Date();
	}
	
	public ArrayList<String> getShareList() {
		return shareList;
	}
	
	public String getWord() {
		return word;
	}
	
	public String getType() {
		return type;
	}
	
	public Date getDate() {
		return date;
	}
	
}
