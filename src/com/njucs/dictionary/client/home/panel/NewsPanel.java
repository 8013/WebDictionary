package com.njucs.dictionary.client.home.panel;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.njucs.dictionary.client.home.tools.GetNews;

public class NewsPanel extends JFrame{
	private static final long serialVersionUID = -300724341534592254L;
	
	public NewsPanel(){
		Font font=new Font("微软雅黑", Font.PLAIN, 18);
		JTextArea text=new JTextArea(20, 20);
		text.setFont(font);
		String str="";
		for(int i=0;i<GetNews.words.size();i++	){
			str+=GetNews.words.get(i).getFrom()+GetNews.words.get(i).getWord()+GetNews.words.get(i).getType()+GetNews.words.get(i).getDate().toString();
			str+="\n";
		}
		text.setText(str);
		this.add(text);
		pack();
	}
	
	public static void Show(){
		NewsPanel frame=new NewsPanel();
		frame.setTitle("消息");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
