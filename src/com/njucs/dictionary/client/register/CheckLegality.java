package com.njucs.dictionary.client.register;

import java.util.regex.Pattern;
import com.njucs.dictionary.client.common.Message;

public class CheckLegality {

	public static boolean Pass(String username, String password1, String password2, String email){
		// 校验用户名是否合法
		if (!Pattern.matches("\\w{6,15}", username)) {
			Message.Show("请输入合法的用户名！（6-15个字符）");
			return false;
		}
		// 校验两次输入的密码是否相同
		else if (!password1.equals(password2)) {
			Message.Show("两次输入的密码不同！");
			return false;
		}
		// 校验电子邮箱是否合法
		else if (!Pattern.matches("\\w+@\\w+\\.\\w+", email)) {
			Message.Show("请输入合法的电子邮箱！");
			return false;
		}
		else{
			return true;
		}
	}
}
