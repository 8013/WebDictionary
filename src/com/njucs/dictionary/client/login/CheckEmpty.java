package com.njucs.dictionary.client.login;

import com.njucs.dictionary.client.common.Message;

public class CheckEmpty {

	public static boolean Pass(String username, String password){
		if(username.isEmpty()){
			Message.Show("账号不能为空！");
			return false;
		}
		else if(password.isEmpty()){
			Message.Show("密码不能为空！");
			return false;
		}
		else{
			return true;
		}
	}
	
}
