package com.njucs.dictionary.client.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class TranslatePanel extends JPanel{
	private static final long serialVersionUID = 6776279784085225536L;
	
	private int height=8,width=35;
	private boolean flag=false;
	private String name;
	private Color background=new Color(245, 245, 245);
	private Font font=new Font("微软雅黑", Font.PLAIN, 20);
	private ImageIcon like=new ImageIcon("res/like.png");
	private ImageIcon dislike=new ImageIcon("res/dislike.png");
	private JLabel likeLabel;
	private JTextArea textArea;

	public TranslatePanel(Icon icon, String name){
		this.name=name;
		
		JLabel label=new JLabel(icon);
		this.add(label);
		
		textArea=new JTextArea(name, height, width);
		textArea.setFont(font);
		textArea.setBackground(background);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		
		JScrollPane scrollPane=new JScrollPane(textArea);
		scrollPane.setPreferredSize(textArea.getPreferredSize());
		this.add(scrollPane);
		
		likeLabel=new JLabel();
		likeLabel.setFont(font);
		this.add(likeLabel);
		
		likeLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}	
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(flag==false){
					likeLabel.setIcon(like);
					flag=true;
				}
				else{
					likeLabel.setIcon(dislike);
					flag=false;
				}
			}
		});
		
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public JLabel getLikeLabel() {
		return likeLabel;
	}
	
	public String getName(){
		return name;
	}
	
}
