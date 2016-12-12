package com.njucs.dictionary.client.home.panel;
/**
 * 登出面板：
 * 		注销
 * 		修改用户个人信息
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.client.home.Home;
import com.njucs.dictionary.client.login.Login;
import com.njucs.dictionary.modle.Request;
import com.njucs.dictionary.modle.User;

public class LogoutPanel extends JPanel{
	private static final long serialVersionUID = 3404867942697452263L;
	
	private String username;
	private Font font=new Font("微软雅黑", Font.PLAIN, 18);
	private Dimension size=new Dimension(280, 120);
	private JButton message, logout;
	
	private void ButtonListener(){
		
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Request request=new Request(5, new User(username));
				SendRequest.Send(request);
				Login.Show();
				Home.Destroy();
			}
		});
		
		message.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Request request=new Request(9);
				SendRequest.Send(request);
			}
		});
		
	}
	
	public LogoutPanel(String username){

		JLabel infomation=new JLabel("欢迎使用："+username, JLabel.CENTER);
		infomation.setPreferredSize(new Dimension(280, 60));
		infomation.setFont(font);
		
		message=new JButton("消息");
		message.setFont(font);
		logout=new JButton("注销");
		logout.setFont(font);
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.add(message);
		buttonPanel.add(logout);
		
		ButtonListener();
		
		this.username=username;
		this.setPreferredSize(size);
		this.add(infomation);
		this.add(buttonPanel);
		this.setBorder(BorderFactory.createLoweredSoftBevelBorder());
	}

	public String getUsername() {
		return username;
	}
	
}
