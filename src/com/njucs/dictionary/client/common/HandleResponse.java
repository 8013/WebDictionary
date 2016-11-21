package com.njucs.dictionary.client.common;

import com.njucs.dictionary.client.home.Home;
import com.njucs.dictionary.client.login.Login;
import com.njucs.dictionary.modle.*;
/**
 * 处理服务器返回的响应信息
 * @author zhe
 */
public class HandleResponse {
	
	public static void Handle(Response response){
		// 登陆成功
		if(response.getNo()==100){
			Home.Show(response.getDescription());
			Login.Destroy();
		}
		// 注册成功
		else if(response.getNo()==200){
			Message.Show("注册成功，请返回登录界面登录！");
		}
		else if(response.getNo()==300){
			Like likes=response.getLike();
			Home.dictionaryPanel.UpdateLike(likes);
		}
		else if(response.getNo()==301||response.getNo()==302){
			;
		}
		else{
			Message.Show(response.getDescription());
		}
	}
	
}

