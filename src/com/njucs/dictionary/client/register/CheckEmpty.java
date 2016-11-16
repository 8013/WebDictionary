package com.njucs.dictionary.client.register;

import javax.swing.JOptionPane;

public class CheckEmpty {

		public static boolean Pass(String username, String password1, String password2, String email){
			
			if(username.isEmpty()){
				JOptionPane.showMessageDialog(null, "账号不能为空");
				return false;
			}
			else if(password1.isEmpty()){
				JOptionPane.showMessageDialog(null, "密码不能为空");
				return false;
			}
			else if(password2.isEmpty()){
				JOptionPane.showMessageDialog(null, "确认密码不能为空");
				return false;
			}
			else if(email.isEmpty()){
				JOptionPane.showMessageDialog(null, "电子邮箱不能为空");
				return false;
			}
			else{
				return true;
			}
		
		}
}
