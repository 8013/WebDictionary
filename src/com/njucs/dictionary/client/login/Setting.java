package com.njucs.dictionary.client.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.njucs.dictionary.client.common.Message;

public class Setting extends JFrame {
	private static final long serialVersionUID = 3245723564916707557L;
	
	private JTextField ipField,portField;
	private JButton confirm,back;
	public static String ip="127.0.0.1";
	public static String port="8000";
	// 按钮响应函数
	private void ButtonListener(){
		
		// 点击确定设置新的IP地址和端口号
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ip=ipField.getText();
				port=portField.getText();
				Message.Show("设置成功");
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
 	public Setting(){
		Font font=new Font("微软雅黑", Font.PLAIN, 20);
		int columns=10;
		
		JPanel content=new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		setContentPane(content);		
		JPanel ipPanel=new JPanel();
		content.add(ipPanel);
		JPanel portPanel=new JPanel();
		content.add(portPanel);
		JPanel buttonPanel=new JPanel();
		content.add(buttonPanel);
		
		JLabel ipLabel=new JLabel("IP地址：");
		ipLabel.setFont(font);
		ipPanel.add(ipLabel);
		ipField=new JTextField(columns);
		ipField.setFont(font);
		ipField.setText(ip);
		ipPanel.add(ipField);
		
		JLabel portLabel=new JLabel("端口号：");
		portLabel.setFont(font);
		portPanel.add(portLabel);
		portField=new JTextField(columns);
		portField.setFont(font);
		portField.setText(port);
		portPanel.add(portField);
		
		confirm=new JButton("确定");
		confirm.setFont(font);
		buttonPanel.add(confirm);
		
		back=new JButton("返回");
		back.setFont(font);
		buttonPanel.add(back);
		
		ButtonListener();
		pack();
	}
	
	// 显示
	public static void Show(){
		Setting frame=new Setting();
		frame.setTitle("设置");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
