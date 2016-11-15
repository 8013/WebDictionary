package com.njucs.dictionary.modle;

import java.io.Serializable;

/**
 * 客户端发送的请求类，no表示请求的种类
 * 服务器端根据不同的no转向不同的处理
 * no = 1		Login
 * @author zhe
 *
 */
public class Request implements Serializable{
	private static final long serialVersionUID = -5620574951901250277L;
	private int no;
	private User user;
	
	public Request(int no, User user){
		this.no=no;
		this.user=user;
	}

	public int getNo() {
		return no;
	}

	public User getUser() {
		return user;
	}

}
