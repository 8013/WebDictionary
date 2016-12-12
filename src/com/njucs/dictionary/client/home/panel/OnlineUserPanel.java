package com.njucs.dictionary.client.home.panel;
import java.awt.Color;
/**
 * 在线用户面板
 * @author zhe
 *
 */
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.njucs.dictionary.client.home.tools.GetOnlineUsers;

public class OnlineUserPanel extends JPanel{
	private static final long serialVersionUID = -6290221018400367247L;
	
	private Font font=new Font("微软雅黑", Font.PLAIN, 18);
	private Dimension size=new Dimension(270, 480);
	private JTable onlineUsers;
	private OnlineUserModel model;
	
	public OnlineUserPanel(){
		model=new OnlineUserModel();
		onlineUsers=new JTable(model);
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
	
	public void Update(){
		model.Update();
		model.fireTableDataChanged();
	}
}

class OnlineUserModel extends AbstractTableModel{
	private static final long serialVersionUID = 8492855786221636701L;
	private String[] columnNames={"用户","状态"};
	private ArrayList<String[]> data=new ArrayList<>();
	
	public OnlineUserModel() {
		Update();
	}

	public void Update(){
		data.clear();
		for(int i=0;i<GetOnlineUsers.userTable.length;i++){
			data.add(GetOnlineUsers.userTable[i]);
		}
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return data.get(row)[column];
	}
	
	@Override  
    public boolean isCellEditable(int rowIndex, int columnIndex)  {  
        return false;  
    }
	
	@Override
	public String getColumnName(int column){
		return columnNames[column];
	}
	
}
