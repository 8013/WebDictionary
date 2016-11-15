package com.njucs.dictionary.client.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.io.*;
import javax.swing.*;

import com.njucs.dictionary.client.register.Register;
import com.njucs.dictionary.modle.*;

public class Login extends JFrame {
	private static final long serialVersionUID = -6765456694853821472L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton login,register;
	public Socket socket;
	
	private void ButtonListener(){
		
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username=new String(usernameField.getText());
				String password=new String(passwordField.getPassword());
				if(username.isEmpty()){
					JOptionPane.showMessageDialog(null, "用户名不能为空");
				}
				else if(password.isEmpty()){
					JOptionPane.showMessageDialog(null, "密码不能为空");
				}
				else{
					try {
						socket=new Socket("localhost", 8000);
						Request request=new Request(1, new User(username, password));
						ObjectOutputStream toServer=new ObjectOutputStream(socket.getOutputStream());
						toServer.writeObject(request);
						ObjectInputStream fromServer=new ObjectInputStream(socket.getInputStream());
						Response response=(Response)fromServer.readObject();
						System.out.println(response.getNo() + " " + response.getDescription());
					} catch (IOException ex) {
						ex.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}	
				}
			}
		});
		
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Register.Show();
				dispose();
			}
		});
	}
	
 	public Login(){
		Font font=new Font("微软雅黑", Font.PLAIN, 20);
		
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
		
		usernameField=new JTextField();
		usernameField.setFont(font);
		usernameField.setColumns(10);
		usernamePanel.add(usernameField);
		
		JLabel passwordLabel=new JLabel("密　码：");
		passwordLabel.setFont(font);
		passwordPanel.add(passwordLabel);
		
		passwordField=new JPasswordField();
		passwordField.setFont(font);
		passwordField.setColumns(10);
		passwordPanel.add(passwordField);
		
		
		login=new JButton("登录");
		login.setFont(font);
		buttonPanel.add(login);

		
		register=new JButton("注册");
		register.setFont(font);
		buttonPanel.add(register);
		
		ButtonListener();
		pack();
	}
	
	public static void Show(){
		Login frame=new Login();
		frame.setTitle("用户登录");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
