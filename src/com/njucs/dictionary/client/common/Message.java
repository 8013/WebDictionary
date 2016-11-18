package com.njucs.dictionary.client.common;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Message extends JFrame{
	private static final long serialVersionUID = 8870223574839210316L;
	private JButton confirm;
	
	public Message(String message){
		Font font=new Font("微软雅黑", Font.PLAIN, 20);
		
		JPanel content=new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		setContentPane(content);		
		JPanel messagePanel=new JPanel();
		content.add(messagePanel);
		JPanel buttonPanel=new JPanel();
		content.add(buttonPanel);
		
		JLabel messageLabel=new JLabel(message);
		messageLabel.setFont(font);
		messagePanel.add(messageLabel);
			
		confirm=new JButton("确定");
		confirm.setFont(font);
		buttonPanel.add(confirm);
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public static void Show(String message){
		Message frame=new Message(message);
		frame.setTitle("消息");
		frame.setSize(400, 150);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
