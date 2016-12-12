package com.njucs.dictionary.modle;

import java.io.Serializable;
import java.util.Date;

public class Word implements Serializable{
	private static final long serialVersionUID = -8552622347327339632L;
	private String word;
	private String type;
	private String from;
	private Date date;
	private boolean isread;
	
	public Word(String word, String type, String from, Date date, boolean isread){
		this.word=word;
		this.type=type;
		this.from=from;
		this.date=date;
		this.isread=isread;
	}
	
	public String getWord() {
		return word;
	}

	public String getType() {
		return type;
	}

	public String getFrom() {
		return from;
	}

	public Date getDate() {
		return date;
	}

	public boolean isRead() {
		return isread;
	}

}
