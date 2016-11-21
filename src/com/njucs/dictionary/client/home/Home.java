package com.njucs.dictionary.client.home;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

import com.njucs.dictionary.client.home.panel.DictionaryPanel;
import com.njucs.dictionary.client.home.panel.LogoutPanel;
import com.njucs.dictionary.client.home.panel.OnlineUserPanel;

public class Home extends JFrame {
	private static final long serialVersionUID = -7095027738615460603L;
	private static Home frame;
	public static DictionaryPanel dictionaryPanel;
	
	
	// GUI构造函数
	public Home(String username){
		JPanel content=new JPanel();
		content.setLayout(new BorderLayout());
		setContentPane(content);		
		
		dictionaryPanel=new DictionaryPanel();
		content.add(dictionaryPanel);
		
		JPanel east=new JPanel();
		east.setPreferredSize(new Dimension(300, 640));
		LogoutPanel logoutPanel=new LogoutPanel(username);
		east.add(logoutPanel);
		
		OnlineUserPanel onlineUserPanel=new OnlineUserPanel();
		east.add(onlineUserPanel);
		
		content.add(east, BorderLayout.EAST);
		pack();
	}
	
	// 静态显示函数
	public static void Show(String username){
		frame=new Home(username);
		frame.setTitle("WebDictionary");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	// 销毁该窗口
	public static void Destroy(){
		frame.dispose();
	}
	
}
