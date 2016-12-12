package com.njucs.dictionary.client.common;

import com.njucs.dictionary.client.home.Home;
import com.njucs.dictionary.client.home.tools.GetNews;
import com.njucs.dictionary.client.home.tools.GetOnlineUsers;
import com.njucs.dictionary.client.home.tools.HeartBeat;
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
			new Thread(new HeartBeat()).start();
		}
		// 注册成功
		else if(response.getNo()==200){
			Message.Show("注册成功，请返回登录界面登录！");
		}
		// 获取点赞数
		else if(response.getNo()==300){
			Like likes=response.getLike();
			Home.dictionaryPanel.UpdateLike(likes);
		}
		// 获取用户列表
		else if(response.getNo()==310){
			GetOnlineUsers.userTable=response.getUsertable().Visit();
		}
		// 登出，点赞，取消赞
		else if(response.getNo()==110 || response.getNo()==301 || response.getNo()==302){
			;
		}
		else if(response.getNo()==900){
			GetNews.words=response.getWords();
		}
		else{
			Message.Show(response.getDescription());
		}
	}
	
}

