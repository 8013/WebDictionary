package com.njucs.dictionary.client.home.tools;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class CreateImage extends JFrame{
	private static final long serialVersionUID = -3892859664367141346L;
	private static BufferedImage image;
	private static Font font=new Font("宋体", Font.PLAIN, 14);
	private static String[] str;
	private static ArrayList<String> content=new ArrayList<>();
	private static int width=700;
	private static int height;
	private static String Punctuation=" ,.?;:'\"`!#$%^&*()~-=+<>[]{}\\|";
	private static FontRenderContext frc=new FontRenderContext(AffineTransform.getScaleInstance(1, 1), false, false);
	private static Rectangle2D r;
	
	private static boolean isPunctuation(char c){
		for(int i=0;i<Punctuation.length();i++){
			if(c==Punctuation.charAt(i))
				return true;
		}
		return false;
	}
	
	private static boolean isChinese(char c){
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if(ub==Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS||ub==Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
		||ub==Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A||ub==Character.UnicodeBlock.GENERAL_PUNCTUATION
		||ub==Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION||ub==Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS){
			return true;
		}
		return false;
	}
	
	private static void Split(String str){
		r=font.getStringBounds(str, frc);
		String nextline=new String();
		while(r.getWidth()>width){
			nextline=str.charAt(str.length()-1)+nextline;
			str=str.substring(0,str.length()-1);
			r=font.getStringBounds(str,frc);
		}
		if(nextline.length()>0){
			while(true){
				if(isPunctuation(str.charAt(str.length()-1)))
					break;
				else if(isChinese(str.charAt(str.length()-1))&&!isChinese(str.charAt(str.length()-2)))
					break;
				else if(!isChinese(str.charAt(str.length()-1))&&isChinese(str.charAt(str.length()-2)))
					break;
				else{
					nextline=str.charAt(str.length()-1)+nextline;
					str=str.substring(0,str.length()-1);
				}
			}
			content.add(str);
			Split(nextline);
		}
		else{
			content.add(str);
		}
	}
	
	public CreateImage(){
		JLabel label=new JLabel(new ImageIcon(image));
		this.add(label);
		this.pack();
	}
	
	public static void Handle(String buffer){
		str=buffer.split("\\n");
		for(int i=0;i<str.length;i++){
			Split(str[i]);
		}
			
		height=(int) (r.getHeight()*(content.size()+1));
		width+=50;
		//CreateImage();
		Create();
		Show();
		image.flush();
		content.clear();
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
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.setFont(font);
/*		Rectangle clip = g.getClipBounds();
		FontMetrics fm = g.getFontMetrics(font);
		int ascent = fm.getAscent();
		int descent = fm.getDescent();
*/	
		int y=(int) r.getHeight();
		for(int i=0;i<content.size();i++){
			g.drawString(content.get(i), 0, y*(i+1));
		}
		g.dispose();

	}
}
