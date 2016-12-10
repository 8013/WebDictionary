package com.njucs.dictionary.client.register;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.njucs.dictionary.client.common.MyFrame;
import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.client.login.Login;
import com.njucs.dictionary.modle.Request;
import com.njucs.dictionary.modle.User;

public class Register extends MyFrame {

	private static final long serialVersionUID = 5364825161575734991L;
	
	private JTextField usernameField;
	private JPasswordField passwordField1, passwordField2;
	private JTextField emailField;
	private JButton submit, back;
	
	// 按钮响应函数
	private void ButtonListener(){
		
		// 点击提交进行文本检查，全部通过后向服务器发送注册请求
		submit.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				String username=new String(usernameField.getText());
				String password1=new String(passwordField1.getPassword());
				String password2=new String(passwordField2.getPassword());
				String email=new String(emailField.getText());
				
				if(!CheckEmpty.Pass(username, password1, password2, email))
					return ;
				
				if(!CheckLegality.Pass(username, password1, password2, email))
					return ;
				
				Request request=new Request(2, new User(username, password1, email));
				
				SendRequest.Send(request);
				
			}
		});
		
		// 点击取消按钮返回登录界面
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login.Show();
				dispose();
			}
		});
		
	}
	
	// 界面
	public Register(){
		Font font=new Font("微软雅黑", Font.PLAIN, 20);
		int columns=10;
		
		JPanel content=new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		setContentPane(content);		
		JPanel usernamePanel=new JPanel();
		content.add(usernamePanel);
		JPanel passwordPanel1=new JPanel();
		content.add(passwordPanel1);
		JPanel passwordPanel2=new JPanel();
		content.add(passwordPanel2);
		JPanel emailPanel=new JPanel();
		content.add(emailPanel);
		JPanel buttonPanel=new JPanel();
		content.add(buttonPanel);
		
		JLabel usernameLabel=new JLabel("输入账号：");
		usernameLabel.setFont(font);
		usernamePanel.add(usernameLabel);
		usernameField=new JTextField(columns);
		usernameField.setFont(font);
		usernamePanel.add(usernameField);
		
		JLabel passwordLabel1=new JLabel("输入密码：");
		passwordLabel1.setFont(font);
		passwordPanel1.add(passwordLabel1);
		passwordField1=new JPasswordField(columns);
		passwordField1.setFont(font);
		passwordPanel1.add(passwordField1);
		
		JLabel passwordLabel2=new JLabel("确认密码：");
		passwordLabel2.setFont(font);
		passwordPanel2.add(passwordLabel2);
		passwordField2=new JPasswordField(columns);
		passwordField2.setFont(font);
		passwordPanel2.add(passwordField2);
		
		JLabel emailLabel=new JLabel("电子邮箱：");
		emailLabel.setFont(font);
		emailPanel.add(emailLabel);
		emailField=new JTextField(columns);
		emailField.setFont(font);
		emailPanel.add(emailField);
		
		submit=new JButton("提交");
		submit.setFont(font);
		buttonPanel.add(submit);
		
		back=new JButton("返回登录界面");
		back.setFont(font);
		buttonPanel.add(back);
		
		ButtonListener();
		pack();
	}

	// 显示
	public static void Show(){
		Register frame=new Register();
		frame.setTitle("注册");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}
