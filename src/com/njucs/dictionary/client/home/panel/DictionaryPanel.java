package com.njucs.dictionary.client.home.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.njucs.dictionary.client.home.tools.CheckWord;
import com.njucs.dictionary.client.home.tools.GetLikes;
import com.njucs.dictionary.client.search.Baidu;
import com.njucs.dictionary.client.search.Jinshan;
import com.njucs.dictionary.client.search.Youdao;
import com.njucs.dictionary.modle.Like;
/**
 * 词典的显示界面
 * 包括搜索框，复选框和翻译区域
 * @author zhe
 *
 */
public class DictionaryPanel extends JPanel{
	private static final long serialVersionUID = -7589143892541083015L;
	
	private Font font=new Font("微软雅黑", Font.PLAIN, 18);
	private Dimension size=new Dimension(640, 640);
	private ImageIcon dislike=new ImageIcon("res/dislike.png");
	private ImageIcon like=new ImageIcon("res/like.png");
	
	private JPanel descriptionPanel;
	private TranslatePanel baidu, youdao, jinshan;
	private JCheckBox checkBoxBaidu, checkBoxYoudao, checkBoxJinshan;
	
	// 构造一个词典的面板，包括搜索框，复选框和翻译区域
	public DictionaryPanel(){
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.setPreferredSize(size);
		
		this.add(SearchPanel());
		this.add(CheckPanel());
		this.add(DescriptionPanel());
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
				baidu.setWord(word);
				baidu.getTextArea().setText(Baidu.Translate(word));
				baidu.getTextArea().setCaretPosition(0);
				youdao.setWord(word);
				youdao.getTextArea().setText(Youdao.Translate(word));
				youdao.getTextArea().setCaretPosition(0);
				jinshan.setWord(word);
				jinshan.getTextArea().setText(Jinshan.Translate(word));
				jinshan.getTextArea().setCaretPosition(0);
					
				// 从服务器获取点赞数
				GetLikes.FromServer(word);
			
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
		checkBoxBaidu=new JCheckBox("百度"+space);
		checkBoxYoudao=new JCheckBox("有道"+space);
		checkBoxJinshan=new JCheckBox("金山");
			
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
					descriptionPanel.revalidate();
				}
				else{
					descriptionPanel.add(baidu);
					descriptionPanel.revalidate();
				}
			}
		});
			
		checkBoxYoudao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkBoxYoudao.isSelected()){
					descriptionPanel.remove(youdao);
					descriptionPanel.revalidate();
				}
				else{
					descriptionPanel.add(youdao);
					descriptionPanel.revalidate();
				}
			}
		});
			
		checkBoxJinshan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkBoxJinshan.isSelected()){
					descriptionPanel.remove(jinshan);
					descriptionPanel.revalidate();
				}
				else{
					descriptionPanel.add(jinshan);
					descriptionPanel.revalidate();
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

	// 根据点赞数重新排列三个翻译面板
	private	void Sort(Like likes){
		descriptionPanel.remove(baidu);
		descriptionPanel.remove(youdao);
		descriptionPanel.remove(jinshan);
		
		int a=likes.getBaidu(),b=likes.getYoudao(),c=likes.getJinshan();
		
		if(a>=b && a>=c){
			if(checkBoxBaidu.isSelected())
				descriptionPanel.add(baidu);
			if(b>=c){
				if(checkBoxYoudao.isSelected())
					descriptionPanel.add(youdao);
				if(checkBoxJinshan.isSelected())
					descriptionPanel.add(jinshan);
				descriptionPanel.repaint();
				return;
			}
			else{
				if(checkBoxJinshan.isSelected())
					descriptionPanel.add(jinshan);
				if(checkBoxYoudao.isSelected())
					descriptionPanel.add(youdao);
				descriptionPanel.repaint();
				return;
			}
		}
		if(b>=a && b>=c){
			if(checkBoxYoudao.isSelected())
				descriptionPanel.add(youdao);
			if(a>=c){
				if(checkBoxBaidu.isSelected())
					descriptionPanel.add(baidu);
				if(checkBoxJinshan.isSelected())
					descriptionPanel.add(jinshan);
				descriptionPanel.repaint();
				return;
			}
			else{
				if(checkBoxJinshan.isSelected())
					descriptionPanel.add(jinshan);
				if(checkBoxBaidu.isSelected())
					descriptionPanel.add(baidu);
				descriptionPanel.repaint();
				return;
			}
		}
		if(c>=a && c>=b){
			if(checkBoxJinshan.isSelected())
				descriptionPanel.add(jinshan);
			if(a>=b){
				if(checkBoxBaidu.isSelected())
					descriptionPanel.add(baidu);
				if(checkBoxYoudao.isSelected())
					descriptionPanel.add(youdao);
				descriptionPanel.repaint();
				return;
			}
			else{
				if(checkBoxYoudao.isSelected())
					descriptionPanel.add(youdao);
				if(checkBoxBaidu.isSelected())	
					descriptionPanel.add(baidu);
				descriptionPanel.repaint();
				return;
			}
		}
	}
	
	// 更新翻译面板点赞标签的状态
	public void UpdateLike(Like likes){
		baidu.setFlag(likes.getBaidulike());			
		baidu.getLikeLabel().setIcon(likes.getBaidulike()>0?like:dislike);
		baidu.getLikeLabel().setText(likes.getBaidu()+"");
		
		youdao.setFlag(likes.getYoudaolike());
		youdao.getLikeLabel().setIcon(likes.getYoudaolike()>0?like:dislike);
		youdao.getLikeLabel().setText(likes.getYoudao()+"");
		
		jinshan.setFlag(likes.getJinshanlike());
		jinshan.getLikeLabel().setIcon(likes.getJinshanlike()>0?like:dislike);
		jinshan.getLikeLabel().setText(likes.getJinshan()+"");
		
		Sort(new Like(likes.getBaidu(), likes.getYoudao(), likes.getJinshan()));
		
	}
	
}
