package com.njucs.dictionary.client.login;

import javax.swing.JOptionPane;

public class CheckEmpty {

	public static boolean Pass(String username, String password){
		if(username.isEmpty()){
			JOptionPane.showMessageDialog(null, "账号不能为空");
			return false;
		}
		else if(password.isEmpty()){
			JOptionPane.showMessageDialog(null, "密码不能为空");
			return false;
		}
		else{
			return true;
		}
	}
	
}
