package com.njucs.dictionary.modle;

import java.io.Serializable;

/**
 * 服务器端发送的响应类
 * 登录：
 * 		登陆成功				100
 * 		用户名不存在			101
 * 		密码错误				102
 * 注册：
 * 		注册成功				200
 * 		用户名已存在			201
 * 		邮箱已被使用			202
 * 查找：
 * 
 * @author zhe
 *
 */
public class Response implements Serializable{
	private static final long serialVersionUID = 2922287507014280279L;
	private int no;
	private String description;
	
	public Response(int no, String description){
		this.no=no;
		this.description=description;
	}
	
	public int getNo() {
		return no;
	}
	
	public String getDescription() {
		return description;
	}
	
}
