package com.njucs.dictionary.client.home;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/**
 * WebDictionary主界面
 * @author zhe
 *
 */
public class Home extends JFrame {
	private static final long serialVersionUID = -7095027738615460603L;
	
	private int width=20,height=5;
	private Font font=new Font("微软雅黑", Font.PLAIN, 20);
	private Color background=new Color(245, 245, 245);
	private JTextArea baiduArea, youdaoArea, jinshanArea;
	private JPanel descriptionPanel, baiduPanel, youdaoPanel, jinshanPanel;
	
	// GUI构造函数
	public Home(){
		JPanel content=new JPanel();
		content.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setContentPane(content);		

		content.add(SearchPanel());
		content.add(CheckPanel());
		content.add(DescriptionPanel());
		pack();
	}
	
	// 搜索面板
	private JPanel SearchPanel(){
		JPanel searchPanel=new JPanel();
		JLabel searchLabel=new JLabel(new ImageIcon("res/search.png"));
		JTextField searchField=new JTextField(17);
		searchField.setFont(font);
		JButton searchButton=new JButton("搜索");
		searchButton.setFont(font);
		
		// 获取文本框内容，进行联网搜索
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word=searchField.getText();
				System.out.println(word);
				// Search
			}
		});
		
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		return searchPanel;
	}

	// 复选面板
	private JPanel CheckPanel(){
		String space="                     ";
		JPanel checkPanel=new JPanel();
		JCheckBox checkBoxBaidu=new JCheckBox("百度"+space);
		JCheckBox checkBoxYoudao=new JCheckBox("有道"+space);
		JCheckBox checkBoxJinshan=new JCheckBox("金山");
		
		checkBoxBaidu.setFont(font);
		checkBoxBaidu.setSelected(true);
		checkBoxYoudao.setFont(font);
		checkBoxYoudao.setSelected(true);
		checkBoxJinshan.setFont(font);
		checkBoxJinshan.setSelected(true);
				
		// 复选操作，响应函数
		checkBoxBaidu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkBoxBaidu.isSelected()){
					descriptionPanel.remove(baiduPanel);
					descriptionPanel.revalidate();
				}
				else{
					descriptionPanel.add(baiduPanel);
					descriptionPanel.revalidate();
				}
			}
		});
		
		checkBoxYoudao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkBoxYoudao.isSelected()){
					descriptionPanel.remove(youdaoPanel);
					descriptionPanel.revalidate();
				}
				else{
					descriptionPanel.add(youdaoPanel);
					descriptionPanel.revalidate();
				}
			}
		});
		
		checkBoxJinshan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkBoxJinshan.isSelected()){
					descriptionPanel.remove(jinshanPanel);
					descriptionPanel.revalidate();
				}
				else{
					descriptionPanel.add(jinshanPanel);
					descriptionPanel.revalidate();
				}
				
			}
		});
		
		checkPanel.add(checkBoxBaidu);
		checkPanel.add(checkBoxYoudao);
		checkPanel.add(checkBoxJinshan);
		return checkPanel;
	}

	// 单词描述面板，包括百度翻译，有道翻译，金山翻译。
	private JPanel DescriptionPanel(){
		descriptionPanel=new JPanel();
		descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.PAGE_AXIS));
		
		descriptionPanel.add(BaiduPanel());
		descriptionPanel.add(YoudaoPanel());
		descriptionPanel.add(JinshanPanel());
		
		return descriptionPanel;
	}

	// 百度翻译面板
	private JPanel BaiduPanel(){
		baiduPanel=new JPanel();
		
		JLabel baiduLabel=new JLabel(new ImageIcon("res/baidu.png"));
		baiduPanel.add(baiduLabel);
		
		baiduArea=new JTextArea("百度翻译",height, width);
		baiduArea.setFont(font);
		baiduArea.setBackground(background);
		baiduArea.setLineWrap(true);
		baiduArea.setEditable(false);
		baiduPanel.add(baiduArea);
		
		JLabel baiduLike=new JLabel(new ImageIcon("res/dislike.png"));
		baiduPanel.add(baiduLike);
		
		return baiduPanel;
	}
	
	// 有道翻译面板
	private JPanel YoudaoPanel(){
		youdaoPanel=new JPanel();
		
		JLabel youdaoLabel=new JLabel(new ImageIcon("res/youdao.png"));
		youdaoPanel.add(youdaoLabel);
		
		youdaoArea=new JTextArea("有道翻译",height, width);
		youdaoArea.setFont(font);
		youdaoArea.setBackground(background);
		youdaoArea.setLineWrap(true);
		youdaoArea.setEditable(false);
		youdaoPanel.add(youdaoArea);
		
		JLabel youdaoLike=new JLabel(new ImageIcon("res/dislike.png"));
		youdaoPanel.add(youdaoLike);
		
		return youdaoPanel;
	}
	
	// 金山翻译面板
	private JPanel JinshanPanel(){
		jinshanPanel=new JPanel();
		
		JLabel jinshanLabel=new JLabel(new ImageIcon("res/jinshan.png"));
		jinshanPanel.add(jinshanLabel);
		
		jinshanArea=new JTextArea("金山翻译",height, width);
		jinshanArea.setFont(font);
		jinshanArea.setBackground(background);
		jinshanArea.setLineWrap(true);
		jinshanArea.setEditable(false);
		jinshanPanel.add(jinshanArea);
		
		JLabel jinshanLike=new JLabel(new ImageIcon("res/dislike.png"));
		jinshanPanel.add(jinshanLike);
		
		return jinshanPanel;
	}
	
	// 静态显示函数
	public static void Show(){
		Home frame=new Home();
		frame.setTitle("WebDictionary");
		frame.setSize(500, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
