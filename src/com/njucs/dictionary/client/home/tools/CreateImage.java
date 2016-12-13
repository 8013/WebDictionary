package com.njucs.dictionary.client.home.tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class CreateImage extends JFrame{
	private static final long serialVersionUID = -3892859664367141346L;
	static int width=600;
	static int height=600;
	static Font font=new Font("微软雅黑", Font.PLAIN, 18);
	static String[] temp;
	static BufferedImage image;
	
	public CreateImage(){
		JLabel label=new JLabel(new ImageIcon(image));
		this.add(label);
		this.pack();
	}
	
	public static void Handle(String str){
		temp=str.split("\\n");
		Create();
		Show();
	}
	
	private static void Show(){
		CreateImage frame=new CreateImage();
		frame.setTitle("图片");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private static void Create(){
		image = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.setClip(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.red);
		g.setFont(font);
/*		Rectangle clip = g.getClipBounds();
		FontMetrics fm = g.getFontMetrics(font);
		int ascent = fm.getAscent();
		int descent = fm.getDescent();
*/	
		g.dispose();

	}
}
