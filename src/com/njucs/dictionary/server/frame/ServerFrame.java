package com.njucs.dictionary.server.frame;

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
import javax.swing.JTextArea;

/*
 * 服务器界面及相应管理功能
 * 
 */

@SuppressWarnings("serial")
public class ServerFrame extends JFrame{
	private JTextArea MessageBox;
	private JScrollPane Content;
	private JButton ClearMessage;
	private JComboBox<String> MessageType;
	private JLabel label;
	private StringBuilder Message;
	private Vector<String> MessageVector;
	private Vector<Integer>[] MessageIndex;
	private int CurrentType=0;
	private final String[] type={"All","Login","Logout","Regist","Like","Cancellike"};
	
	public ServerFrame(){
		super();
		
		this.setSize(600,400);
		this.getContentPane().setLayout(null);
		
		this.add(SetContent());
		this.add(SetClearButton());
		this.add(SetMessageType());
		this.add(SetLabel());
		
		this.setTitle("WebDic-Server");
		this.setVisible(true);
		this.addWindowListener(new CloseWindow());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ServerStart();
	}
	
	@SuppressWarnings("unchecked")
	private JTextArea SetMessageBox(){
		MessageVector=new Vector<String>();
		MessageIndex=new Vector[type.length];
		for(int i=0;i<MessageIndex.length;i++){
			MessageIndex[i]=new Vector<Integer>();
		}
		Message=new StringBuilder();
		MessageBox=new JTextArea();
		MessageBox.setEditable(false);
		MessageBox.setText(Message.toString());
		return MessageBox;
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
			MessageVector.clear();
			Message.setLength(0);
			MessageBox.setText(Message.toString());
		}
		
	}
	
	private JComboBox<String> SetMessageType(){
		MessageType=new JComboBox<String>(type);
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
			MessageBox.setText(GetMessage());
		}
		
	}
	
	private JLabel SetLabel(){
		InetAddress res;
		try {
			res = InetAddress.getLocalHost();
			label=new JLabel("Server Address:"+res.getHostAddress());
			label.setBounds(0, 0, 300, 20);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return label;
	}
	
	private JScrollPane SetContent(){
		Content=new JScrollPane();
		Content.setBounds(0, 20, 600, 370);
		Content.setViewportView(SetMessageBox());
		return Content;
	}
	
	private class CloseWindow extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			System.exit(0);
		}
	}
	
	private String GetMessage(){
		Message.setLength(0);
		for(int i=0;i<MessageIndex[CurrentType].size();i++){
			Message.append(MessageVector.get(MessageIndex[CurrentType].get(i)));
			Message.append("\n");
		}
		return Message.toString();
	}
	
	public int GetTypeIndex(String s){
		for(int i=0;i<type.length;i++){
			if(s.toLowerCase().equals(type[i].toLowerCase()))
				return i;
		}
		return 0;
	}
	
	private void ServerStart(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AddMessage("Server Start At \t"+sdf.format(new Date()), 0);
	}
	
	private void ServerEnd(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AddMessage("Server End At \t"+sdf.format(new Date()), 0);
	}
	
	public void AddMessage(String s, int type){
		MessageVector.add(s);
		int index=MessageVector.size()-1;
		MessageIndex[0].add(index);
		if(type!=0){
			MessageIndex[type].add(index);
		}
		if(type==CurrentType||CurrentType==0){
			Message.append(s);
			Message.append('\n');
			MessageBox.setText(Message.toString());
		}
	}
	
	public void close(){
		ServerEnd();
	}
}
