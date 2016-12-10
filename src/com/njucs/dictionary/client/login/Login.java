package com.njucs.dictionary.client.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.client.register.Register;
import com.njucs.dictionary.modle.*;

public class Login extends JFrame {
	private static final long serialVersionUID = -6765456694853821472L;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton login,register,setting;
	private static Login frame;
	
	// 按钮的响应函数
	private void ButtonListener(){
		
		// 点击登录进行输入检查（判空），通过后向服务器发送登录请求
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username=new String(usernameField.getText());
				String password=new String(passwordField.getPassword());

				if(!CheckEmpty.Pass(username, password))
					return ;
				
				Request request=new Request(1, new User(username, password));
				
				SendRequest.Send(request);
				
			}
		});
		
		// 点击注册跳转到注册界面
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Register.Show();
				dispose();
			}
		});
		
		// 点击设置跳转到设置界面
		setting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting.Show();
				dispose();
			}
		});
	}
	
	// 界面
 	public Login(){
		Font font=new Font("微软雅黑", Font.PLAIN, 20);
		int columns=10;
		
		JPanel content=new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		setContentPane(content);		
		JPanel usernamePanel=new JPanel();
		content.add(usernamePanel);
		JPanel passwordPanel=new JPanel();
		content.add(passwordPanel);
		JPanel buttonPanel=new JPanel();
		content.add(buttonPanel);
		
		
		JLabel usernameLabel=new JLabel("用户名：");
		usernameLabel.setFont(font);
		usernamePanel.add(usernameLabel);
		
		usernameField=new JTextField(columns);
		usernameField.setFont(font);
		usernamePanel.add(usernameField);
		
		JLabel passwordLabel=new JLabel("密　码：");
		passwordLabel.setFont(font);
		passwordPanel.add(passwordLabel);
		
		passwordField=new JPasswordField(columns);
		passwordField.setFont(font);
		passwordPanel.add(passwordField);
		
		
		login=new JButton("登录");
		login.setFont(font);
		buttonPanel.add(login);

		
		register=new JButton("注册");
		register.setFont(font);
		buttonPanel.add(register);
		
		setting=new JButton("设置");
		setting.setFont(font);
		buttonPanel.add(setting);
		
		ButtonListener();
		pack();
	}
	
 	// 显示
	public static void Show(){
		frame=new Login();
		frame.setTitle("用户登录");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	// 销毁当前窗口，便于其他模块调用（HandleResponse）
	public static void Destroy(){
		frame.dispose();
	}
}
