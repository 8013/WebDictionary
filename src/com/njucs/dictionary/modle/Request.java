package com.njucs.dictionary.modle;

import java.io.Serializable;

/**
 * 客户端发送的请求类，no表示请求的种类
 * 服务器端根据不同的no转向不同的处理
 * 登录					1
 * 注册					2
 * 获取点赞数	 		3
 * 更新点赞			4
 * 注销					5
 * 获取用户列表		6
 * 退出程序			7
 * 分享单词			8
 * 获取消息列表		9
 * @author zhe
 *
 */
public class Request implements Serializable{
	private static final long serialVersionUID = -5620574951901250277L;
	private int no;
	private String word;
	private User user;
	private Like like;
	private Share share;
	
	// 分享单词时使用的请求
	public Request(int no, Share share){
		this.no=no;
		this.share=share;
	}
	
	// 获取用户列表、获取消息列表、退出程序时用的请求
	public Request(int no){
		this.no=no;
	}
	
	// 更新点赞数时用的请求
	public Request(int no, String word, Like like){
		this.no=no;
		this.word=word;
		this.like=like;
	}
	
	// 获取点赞数时使用的请求
	public Request(int no, String word){
		this.no=no;
		this.word=word;
	}
	
	// 用于登录，注册，注销时构造请求
	public Request(int no, User user){
		this.no=no;
		this.user=user;
	}

	public int getNo() {
		return no;
	}

	public String getWord() {
		return word;
	}
	
	public User getUser() {
		return user;
	}

	public Like getLike() {
		return like;
	}

	public Share getShare() {
		return share;
	}

}
