package com.njucs.dictionary.client.home.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import com.njucs.dictionary.client.common.SendRequest;
import com.njucs.dictionary.modle.Like;
import com.njucs.dictionary.modle.Request;
/**
 * 翻译面板
 * 点赞时向服务器传递参数new Like(0,0,0,1,0,0);
 * 前三位一直为0，后三位为1或-1取决于是点赞还是取消点赞，六个数字一定有5个0
 * @author zhe
 *
 */
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
	private JLabel likeLabel,shareLabel;
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
		
		JPanel labelPanel=new JPanel();
		labelPanel.setLayout(new BorderLayout(0,10));
		
		likeLabel=new JLabel();
		likeLabel.setFont(font);
		labelPanel.add(likeLabel, BorderLayout.NORTH);
		
		shareLabel=new JLabel();
		labelPanel.add(shareLabel,BorderLayout.CENTER);
		this.add(labelPanel);
		
		// 分享
		shareLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				SharePanel.Show(word,name);
			}
		});
		
		// 点赞
		likeLabel.addMouseListener(new MouseAdapter() {
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
	
	public JLabel getShareLabel(){
		return shareLabel;
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
