package com.njucs.dictionary.client.register;

import com.njucs.dictionary.client.common.Message;

public class CheckEmpty {

		public static boolean Pass(String username, String password1, String password2, String email){
			
			if(username.isEmpty()){
				Message.Show("账号不能为空！");
				return false;
			}
			else if(password1.isEmpty()){
				Message.Show("密码不能为空！");
				return false;
			}
			else if(password2.isEmpty()){
				Message.Show("确认密码不能为空！");
				return false;
			}
			else if(email.isEmpty()){
				Message.Show("电子邮箱不能为空！");
				return false;
			}
			else{
				return true;
			}
		
		}
}
