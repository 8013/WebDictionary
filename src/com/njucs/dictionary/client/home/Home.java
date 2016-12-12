package com.njucs.dictionary.client.home;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

import com.njucs.dictionary.client.common.MyFrame;
import com.njucs.dictionary.client.home.panel.DictionaryPanel;
import com.njucs.dictionary.client.home.panel.LogoutPanel;
import com.njucs.dictionary.client.home.panel.OnlineUserPanel;
import com.njucs.dictionary.client.home.tools.GetNews;
import com.njucs.dictionary.client.home.tools.GetOnlineUsers;

public class Home extends MyFrame {
	private static final long serialVersionUID = -7095027738615460603L;
	private static Home frame;
	public static DictionaryPanel dictionaryPanel;
	public static OnlineUserPanel onlineUserPanel;
	public static LogoutPanel logoutPanel;
	// GUI构造函数
	public Home(String username){
		JPanel content=new JPanel(new BorderLayout());
		setContentPane(content);		
		
		dictionaryPanel=new DictionaryPanel();
		content.add(dictionaryPanel);
		
		JPanel west=new JPanel();
		west.setPreferredSize(new Dimension(300, 640));
		logoutPanel=new LogoutPanel(username);
		west.add(logoutPanel);
		
		GetOnlineUsers.FromServer();
		GetNews.FromServer();
		logoutPanel.Update(GetNews.unread());
		
		onlineUserPanel=new OnlineUserPanel();
		west.add(onlineUserPanel);
	
		content.add(west, BorderLayout.WEST);
		pack();
	}
	
	// 静态显示函数
	public static void Show(String username){
		frame=new Home(username);
		frame.setTitle("网络词典");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	// 销毁该窗口
	public static void Destroy(){
		frame.dispose();
	}
	
}
