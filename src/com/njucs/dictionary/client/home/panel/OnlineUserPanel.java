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
import javax.swing.table.DefaultTableCellRenderer;

import com.njucs.dictionary.client.home.tools.GetOnlineUsers;

public class OnlineUserPanel extends JPanel{
	private static final long serialVersionUID = -6290221018400367247L;
	
	private Font font=new Font("微软雅黑", Font.PLAIN, 18);
	private Dimension size=new Dimension(270, 480);
	private JTable onlineUsers;
	
	public OnlineUserPanel(){
		String []columnsName={"用户","状态"};
		onlineUsers=new JTable(GetOnlineUsers.FromServer(), columnsName);
		onlineUsers.getTableHeader().setFont(font);
		onlineUsers.setFont(font);
		onlineUsers.setBackground(new Color(250, 250, 250));
		onlineUsers.setRowHeight(30);
		onlineUsers.getColumnModel().getColumn(0).setPreferredWidth(200);
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(JLabel.CENTER);
		onlineUsers.setDefaultRenderer(Object.class, cr);
		
		JScrollPane jsp=new JScrollPane(onlineUsers);
		jsp.setPreferredSize(size);
		
		this.add(jsp);
		this.setBorder(BorderFactory.createTitledBorder("用户列表"));
	}
}
