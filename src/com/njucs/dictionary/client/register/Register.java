package com.njucs.dictionary.client.register;

import javax.swing.*;

public class Register extends JFrame {

	private static final long serialVersionUID = 5364825161575734991L;
	
//	private JTextField usernameField;
//	private JPasswordField passwordField1, passwordField2;
//	private JTextField emailField;
//	private JLabel tipLabel = new JLabel();// 显示提示信息

	public Register(){
//		Font font=new Font("微软雅黑", Font.PLAIN, 20);
	}
	
	public static void Show(){
		Register frame=new Register();
		frame.setTitle("注册");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
