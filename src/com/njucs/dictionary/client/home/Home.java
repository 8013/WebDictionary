package com.njucs.dictionary.client.home;

import javax.swing.*;

import com.njucs.dictionary.client.home.panel.DictionaryPanel;
/**
 * WebDictionary主界面
 * @author zhe
 *
 */
public class Home extends JFrame {
	private static final long serialVersionUID = -7095027738615460603L;

	public static DictionaryPanel dictionaryPanel;
	
	// GUI构造函数
	public Home(){
		JPanel content=new JPanel();
		setContentPane(content);		
		
		dictionaryPanel=new DictionaryPanel();
		content.add(dictionaryPanel);
		
		pack();
	}
	
	// 静态显示函数
	public static void Show(){
		Home frame=new Home();
		frame.setTitle("WebDictionary");
		frame.setSize(650, 660);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
