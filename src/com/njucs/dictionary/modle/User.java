package com.njucs.dictionary.modle;

import java.io.Serializable;

/**
 * 用户登录注册信息：
 * 用户名，密码，邮箱
 * @author zhe
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = -1481468746888794104L;
	private String username;
	private String password;
	private String email;
	
	// 重载的构造函数
	// 3个参数		注册
	public User(String username, String password, String email){
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
	}
	// 2个参数		登录
	public User(String username, String password){
		this.setUsername(username);
		this.setPassword(password);
	}
	// 1个参数		注销
	public User(String username){
		this.setUsername(username);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
