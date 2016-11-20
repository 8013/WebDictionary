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
	private static ImageIcon like=new ImageIcon("res/like.png");
	
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
			Like likes=response.getLike();
			Home.baidu.setFlag(likes.getBaidulike());			
			Home.baidu.getLikeLabel().setIcon(likes.getBaidulike()>0?like:dislike);
			Home.baidu.getLikeLabel().setText(likes.getBaidu()+"");
			
			Home.youdao.setFlag(likes.getYoudaolike());
			Home.youdao.getLikeLabel().setIcon(likes.getYoudaolike()>0?like:dislike);
			Home.youdao.getLikeLabel().setText(likes.getYoudao()+"");
			
			Home.jinshan.setFlag(likes.getJinshanlike());
			Home.jinshan.getLikeLabel().setIcon(likes.getJinshanlike()>0?like:dislike);
			Home.jinshan.getLikeLabel().setText(likes.getJinshan()+"");
		}
		else if(response.getNo()==301||response.getNo()==302){
			;
		}
		else{
			Message.Show(response.getDescription());
		}
	}
	
}

