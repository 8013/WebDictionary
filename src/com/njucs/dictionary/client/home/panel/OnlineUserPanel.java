package com.njucs.dictionary.client.home.panel;
import java.awt.Color;
/**
 * 在线用户面板
 * @author zhe
 *
 */
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

import com.njucs.dictionary.client.home.tools.GetOnlineUsers;

public class OnlineUserPanel extends JPanel{
	private static final long serialVersionUID = -6290221018400367247L;
	
	private Font font=new Font("微软雅黑", Font.PLAIN, 18);
	private Dimension size=new Dimension(270, 480);
	private JList<String> onlineUsers;
	
	public OnlineUserPanel(){
		onlineUsers=new JList<>(GetOnlineUsers.FromServer());
		onlineUsers.setFont(font);
		onlineUsers.setBackground(new Color(250, 250, 250));
		onlineUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane jsp=new JScrollPane(onlineUsers);
		jsp.setPreferredSize(size);
		
		this.add(jsp);
		this.setBorder(BorderFactory.createTitledBorder("用户列表"));
	}
}
