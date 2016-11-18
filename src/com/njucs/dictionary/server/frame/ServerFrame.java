package com.njucs.dictionary.server.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * 服务器界面及相应管理功能
 * 
 */

@SuppressWarnings("serial")
public class ServerFrame extends JFrame{
	private JTable MessageTable;
	private DefaultTableModel model=new DefaultTableModel();
	private JScrollPane Content;
	private JButton ClearMessage;
	private JComboBox<String> MessageType;
	private JLabel labelhead;
	private JLabel labelbottom;
	private Vector<Vector<String>> Message;
	private Vector<Vector<String>> BufferedMessage;
	private Vector<Integer>[] MessageIndex;
	private Vector<String> ColumnNames;
	private int CurrentType=0;
	private final String[] TYPE={"All","Login","Logout","Register","Like","Cancellike"};
	
	public ServerFrame(){
		super();
		
		this.setSize(600,400);
		this.getContentPane().setLayout(null);
		
		this.add(SetContent());
		this.add(SetClearButton());
		this.add(SetMessageType());
		this.add(SetLabelHead());
		this.add(SetLabelBottom());
		
		this.setTitle("WebDic-Server");
		this.setVisible(true);
		this.addWindowListener(new CloseWindow());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ServerStart();
	}
	
	private JButton SetClearButton(){
		ClearMessage=new JButton("Clear");
		ClearMessage.setBounds(480, 0, 115, 20);
		ClearMessage.addActionListener(new ClickOnButton());
		return ClearMessage;
	}
	
	private class ClickOnButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i=0;i<MessageIndex.length;i++){
				MessageIndex[i].clear();
			}
			Message.clear();
			model.setDataVector(Message, ColumnNames);
		}
		
	}
	
	private JComboBox<String> SetMessageType(){
		MessageType=new JComboBox<String>(TYPE);
		MessageType.setBounds(300, 0, 175, 20);
		MessageType.addActionListener(new ClickOnMessageType());
		return MessageType;
	}
	
	private class ClickOnMessageType implements ActionListener{

		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) {
			int index=((JComboBox<String>)e.getSource()).getSelectedIndex();
			CurrentType=index;
			Message=GetMessage();
			model.setDataVector(Message, ColumnNames);
		}
		
	}
	
	private JLabel SetLabelHead(){
		InetAddress res;
		try {
			res = InetAddress.getLocalHost();
			labelhead=new JLabel("Server Address:"+res.getHostAddress());
			labelhead.setBounds(0, 0, 300, 20);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return labelhead;
	}
	
	@SuppressWarnings("unchecked")
	private JTable SetMessageTable(){
		ColumnNames=new Vector<String>();
		Message=new Vector<Vector<String>>();
		BufferedMessage=new Vector<Vector<String>>();
		MessageIndex=new Vector[TYPE.length];
		for(int i=0;i<MessageIndex.length;i++){
			MessageIndex[i]=new Vector<Integer>();
		}
		ColumnNames.add("Type");
		ColumnNames.add("Description");
		ColumnNames.add("State");
		ColumnNames.add("IP Address");
		MessageTable=new JTable(model);
		MessageTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		model.setDataVector(Message, ColumnNames);
		return MessageTable;
	}
	
	private JLabel SetLabelBottom(){
		labelbottom=new JLabel("Current Online Client Num: 0");
		labelbottom.setBounds(0,340,600,20);
		return labelbottom;
	}
	
	private JScrollPane SetContent(){
		Content=new JScrollPane();
		Content.setBounds(0, 20, 600, 320);
		Content.setViewportView(SetMessageTable());
		return Content;
	}
	
	private class CloseWindow extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			System.exit(0);
		}
	}
	
	private Vector<Vector<String>> GetMessage(){
		if(CurrentType==0)
			return BufferedMessage;
		Message.clear();
		for(int i=0;i<MessageIndex[CurrentType].size();i++){
			Message.add(BufferedMessage.get(i));
		}
		return Message;
	}
	
	public int GetTypeIndex(String s){
		for(int i=0;i<TYPE.length;i++){
			if(s.toLowerCase().equals(TYPE[i].toLowerCase()))
				return i;
		}
		return 0;
	}
	
	private void ServerStart(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AddMessage("All","Server Start At "+sdf.format(new Date()),"","");
	}
	
	private void ServerEnd(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AddMessage("All","Server Start At "+sdf.format(new Date()),"","");
	}
	
	public void AddMessage(String Type, String Description, String State, String IPAddress){
		Vector<String> Row=new Vector<String>();
		Row.add(Type);
		Row.add(Description);
		Row.add(State);
		Row.add(IPAddress);
		BufferedMessage.add(Row);
		int index=BufferedMessage.size()-1;
		MessageIndex[0].add(index);
		int type=GetTypeIndex(Type);
		if(type!=0){
			MessageIndex[type].add(index);
		}
		if(type==CurrentType||CurrentType==0){
			//添加数据
			model.addRow(Row);
			
			//修改表格行颜色
			DefaultTableCellRenderer dtc=new DefaultTableCellRenderer(){
				public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
					if(MessageTable.getModel().getValueAt(row, 2).equals("Fail")){
						setForeground(Color.RED);
					}
					else if(MessageTable.getModel().getValueAt(row, 2).equals("Success")){
						setForeground(Color.GREEN);
					}
					else
						setForeground(Color.BLACK);
					return super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
				}
			};
			for(int i=0;i<MessageTable.getColumnCount();i++){
				MessageTable.getColumnModel().getColumn(i).setCellRenderer(dtc);
			}
			
		}
	}
	
	public void SetOnlineNum(int num){
		labelbottom.setText("Current Online Client Num:"+num);
	}
	
	public void close(){
		ServerEnd();
	}
}
