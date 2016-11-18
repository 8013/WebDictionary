package com.njucs.dictionary.client.home;

import java.util.Random;

import javax.swing.ImageIcon;

public class GetLikes {
	
	private static ImageIcon dislike=new ImageIcon("res/dislike.png");
	
	public static void FromServer(String word){
//		Request request=new Request(3, word);
//		SendRequest.Send(request);
		int a,b,c;
		a=new Random().nextInt()%10+10;
		b=new Random().nextInt()%10+10;
		c=new Random().nextInt()%10+10;
		Home.baiduLike.setIcon(dislike);
		Home.youdaoLike.setIcon(dislike);
		Home.jinshanLike.setIcon(dislike);
		Home.baiduLike.setText(a>9?""+a:"0"+a);
		Home.youdaoLike.setText(b>9?""+b:"0"+b);
		Home.jinshanLike.setText(c>9?""+c:"0"+c);
	}
}
