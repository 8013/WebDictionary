package com.njucs.dictionary.client.common;

import javax.swing.ImageIcon;

import com.njucs.dictionary.client.home.Home;
import com.njucs.dictionary.client.login.Login;
import com.njucs.dictionary.modle.*;
/**
 * 处理服务器返回的响应信息
 * @author zhe
 */
public class HandleResponse {
	
	private static ImageIcon dislike=new ImageIcon("res/dislike.png");
	
	public static void Handle(Response response){
		// 登陆成功
		if(response.getNo()==100){
			Home.Show();
			Login.Destroy();
		}
		// 注册成功
		else if(response.getNo()==200){
			Message.Show("注册成功，请返回登录界面登录！");
		}
		else if(response.getNo()==300){
			Like like=response.getLike();
			Home.baiduLike.setIcon(dislike);
			Home.youdaoLike.setIcon(dislike);
			Home.jinshanLike.setIcon(dislike);
			Home.baiduLike.setText(like.getBaidu()>9 ? ""+like.getBaidu() : "0"+like.getBaidu());
			Home.youdaoLike.setText(like.getYoudao()>9 ? ""+like.getYoudao() : "0"+like.getYoudao());
			Home.jinshanLike.setText(like.getJinshan()>9 ? ""+like.getJinshan() : "0"+like.getJinshan());
		}
		else if(response.getNo()==301){
			;
		}
		else{
			Message.Show(response.getDescription());
		}
	}
	
}

