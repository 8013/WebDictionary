package com.njucs.dictionary.client.home;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.njucs.dictionary.client.search.Jinshan;
import com.njucs.dictionary.client.search.Youdao;
/**
 * WebDictionary主界面
 * @author zhe
 *
 */
public class Home extends JFrame {
	private static final long serialVersionUID = -7095027738615460603L;
	
	private Font font=new Font("微软雅黑", Font.PLAIN, 18);
	private JPanel descriptionPanel;
	public static TranslatePanel baidu, youdao, jinshan;
	
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
		JTextField searchField=new JTextField(30);
		searchField.setFont(font);
		JButton searchButton=new JButton("搜索");
		searchButton.setFont(font);
		
		// 获取文本框内容，进行联网搜索
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word=searchField.getText();
				// 检测是否是英文单词
				if(!CheckWord.Pass(word))
					return ;
				
				// 联网搜索
				youdao.getTextArea().setText(Youdao.Translate(word));
				youdao.getTextArea().setCaretPosition(0);
				jinshan.getTextArea().setText(Jinshan.Translate(word));
				jinshan.getTextArea().setCaretPosition(0);
				
				// 从服务器获取点赞数
				GetLikes.FromServer(word);
				
				// 根据点赞数对面板进行排序
				// Sort
			}
		});
		
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		return searchPanel;
	}

	// 复选面板
	private JPanel CheckPanel(){
		String space="                                      ";
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
				
		// 复选操作，未被选中的从面板中移除
		checkBoxBaidu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkBoxBaidu.isSelected()){
					descriptionPanel.remove(baidu);
					descriptionPanel.repaint();
				}
				else{
					descriptionPanel.add(baidu);
					descriptionPanel.repaint();
				}
			}
		});
		
		checkBoxYoudao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkBoxYoudao.isSelected()){
					descriptionPanel.remove(youdao);
					descriptionPanel.repaint();
				}
				else{
					descriptionPanel.add(youdao);
					descriptionPanel.repaint();
				}
			}
		});
		
		checkBoxJinshan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkBoxJinshan.isSelected()){
					descriptionPanel.remove(jinshan);
					descriptionPanel.repaint();
				}
				else{
					descriptionPanel.add(jinshan);
					descriptionPanel.repaint();
				}
				
			}
		});
		
		checkPanel.add(checkBoxBaidu);
		checkPanel.add(checkBoxYoudao);
		checkPanel.add(checkBoxJinshan);
		return checkPanel;
	}

	// 单词翻译面板，包括百度翻译，有道翻译，金山翻译。
	private JPanel DescriptionPanel(){
		descriptionPanel=new JPanel();
		descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.PAGE_AXIS));
		
		baidu=new TranslatePanel(new ImageIcon("res/baidu.png"), "百度");
		youdao=new TranslatePanel(new ImageIcon("res/youdao.png"), "有道");
		jinshan=new TranslatePanel(new ImageIcon("res/jinshan.png"), "金山");
		
		descriptionPanel.add(baidu);
		descriptionPanel.add(youdao);
		descriptionPanel.add(jinshan);
		
		return descriptionPanel;
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
