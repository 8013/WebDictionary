package com.njucs.dictionary.client.home.panel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.njucs.dictionary.client.common.Message;
import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.client.home.tools.GetOnlineUsers;
import com.njucs.dictionary.modle.Request;
import com.njucs.dictionary.modle.Share;
/**
 * 分享面板
 * @author zhe
 */
public class SharePanel extends JFrame {
	private static final long serialVersionUID = 562139777066964740L;
	private JButton selectAll,share;
	private JTable table;
	boolean flag=false;
	String word,name;
	
	private void Listener(){
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				;
			}
		});
		
		// 全选
		selectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(flag)
					selectAll.setText("全选");
				else
					selectAll.setText("取消全选");
				for(int i=0;i<table.getRowCount();i++){
					table.setValueAt(!flag, i, 0);
				}
				flag=!flag;
			}
		});
		
		// 分享，即将发送请求
		share.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> shareList=new ArrayList<>();
				for(int i=0;i<table.getRowCount();i++){
					if((boolean)table.getValueAt(i, 0)==true){
						shareList.add((String) table.getValueAt(i, 1));
					}
				}
				if(shareList.isEmpty()){
					Message.Show("请选择需要分享的用户");
				}
				else{
					Request request=new Request(8, new Share(shareList, word, name));
					SendRequest.Send(request);
				}
			}
		});

	}
	
	public SharePanel(String word, String name){
		Font font=new Font("微软雅黑", Font.PLAIN, 18);
		table = new JTable(new TableModel());  		  
		table.getTableHeader().setFont(font);
		table.setFont(font);
		table.setRowHeight(30);
		
		// 设置右对齐
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(JLabel.RIGHT);
		table.setDefaultRenderer(Object.class, cr);
//		table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        JScrollPane jsp = new JScrollPane(table);  
        
        JPanel buttonPanel=new JPanel();
        selectAll=new JButton("全选");
        selectAll.setFont(font);
        share=new JButton("分享");
        share.setFont(font);
        buttonPanel.add(selectAll);
        buttonPanel.add(share);
      
        this.setLayout(new BorderLayout());
        this.add(jsp,BorderLayout.NORTH);        
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.pack();  
        this.word=word;
        this.name=name;
        Listener();
	}
	
	public static void Show(String word, String name){
		SharePanel frame=new SharePanel(word, name);
		frame.setTitle("分享");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}

class TableModel extends AbstractTableModel{
	private static final long serialVersionUID = 8130312314482457297L;
	private String[] columnNames={"选择","用户","状态"};
	private Object[][] data=new Object[GetOnlineUsers.userTable.length][3];
	
	public TableModel() {
		for(int i=0;i<GetOnlineUsers.userTable.length;i++){
			data[i][0]=false;
			data[i][1]=GetOnlineUsers.userTable[i][0];
			data[i][2]=GetOnlineUsers.userTable[i][1];
		}
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		return data[row][column];
	}
	
	@Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
	@Override  
    public boolean isCellEditable(int rowIndex, int columnIndex)  {  
        if (columnIndex == 0)  
            return true;  
        else  
            return false;  
    }
	
	@Override
	public String getColumnName(int column){
		return columnNames[column];
	}
	
    @Override
    public void setValueAt(Object aValue, int row, int column) {
        data[row][column] = aValue;
        fireTableCellUpdated(row, column);
    }
    
}
