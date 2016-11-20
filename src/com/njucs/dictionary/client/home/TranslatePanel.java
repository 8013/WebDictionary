package com.njucs.dictionary.client.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.modle.Like;
import com.njucs.dictionary.modle.Request;

public class TranslatePanel extends JPanel{
	private static final long serialVersionUID = 6776279784085225536L;
	
	private int height=7,width=30;
	private boolean flag;
	@SuppressWarnings("unused")
	private String name;
	private String word;
	private Color background=new Color(245, 245, 245);
	private Font font=new Font("微软雅黑", Font.PLAIN, 18);
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
					Like like = null;
					switch(name){
					case "百度":
						like=new Like(0, 0, 0, 1, 0, 0);
						break;
					case "有道":
						like=new Like(0, 0, 0, 0, 1, 0);
						break;
					case "金山":
						like=new Like(0, 0, 0, 0, 0, 1);
						break;
					}
					likeLabel.setText(String.valueOf(Integer.parseInt(likeLabel.getText())+1));
					Request request=new Request(4, word, like);
					SendRequest.Send(request);
				}
				else{
					likeLabel.setIcon(dislike);
					flag=false;
					Like like = null;
					switch(name){
					case "百度":
						like=new Like(0, 0, 0, -1, 0, 0);
						break;
					case "有道":
						like=new Like(0, 0, 0, 0, -1, 0);
						break;
					case "金山":
						like=new Like(0, 0, 0, 0, 0, -1);
						break;
					}
					likeLabel.setText(String.valueOf(Integer.parseInt(likeLabel.getText())-1));
					Request request=new Request(4, word, like);
					SendRequest.Send(request);
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
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public void setFlag(int flag){
		if(flag==1)
			this.flag=true;
		else{
			this.flag=false;
		}
	}
}
