package com.njucs.dictionary.client.home;

import javax.swing.JFrame;

public class Home extends JFrame {
	private static final long serialVersionUID = -7095027738615460603L;

	public Home(){
		
	}
	
	public static void Show(){
		Home frame=new Home();
		frame.setTitle("WebDictionary");
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
