package com.njucs.dictionary.client.home.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.njucs.dictionary.client.home.tools.*;
import com.njucs.dictionary.client.search.*;

public class NewsPanel extends JFrame{
	private static final long serialVersionUID = -300724341534592254L;
	private Font font=new Font("微软雅黑", Font.PLAIN, 17);
	private JTable news;
	
	private void Listener(){
		
		news.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount()==2){
					int row=news.rowAtPoint(e.getPoint());
					String type=GetNews.words.get(row).getType();
					switch(type){
					case "百度":
						CreateImage.Handle(Biying.Translate(GetNews.words.get(row).getWord()));
						break;
					case "有道":
						CreateImage.Handle(Youdao.Translate(GetNews.words.get(row).getWord()));
						break;
					case "金山":
						CreateImage.Handle(Jinshan.Translate(GetNews.words.get(row).getWord()));
						break;
					default:
						break;
					}
				}
			}
		});
		
	}
	
	public NewsPanel(){
		news=new JTable(new NewsModel());
		news.getTableHeader().setFont(font);
		news.setFont(font);
		news.setBackground(new Color(250, 250, 250));
		news.setRowHeight(30);
		news.getColumnModel().getColumn(3).setPreferredWidth(200);
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(JLabel.CENTER);
		news.setDefaultRenderer(Object.class, cr);
		
		JScrollPane jsp=new JScrollPane(news);
		
		Listener();
		this.add(jsp);
		this.pack();
	}
	
	public static void Show(){
		NewsPanel frame=new NewsPanel();
		frame.setTitle("消息");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

class NewsModel extends AbstractTableModel{
	private static final long serialVersionUID = 4045748788093057086L;
	private String[] columnNames={"单词","翻译来源","分享用户","时间"};
	public static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return GetNews.words.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch(column){
		case 0:return GetNews.words.get(row).getWord();
		case 1:return GetNews.words.get(row).getType();
		case 2:return GetNews.words.get(row).getFrom();
		case 3:return dateFormat.format(GetNews.words.get(row).getDate());
		}
		return null;
	}
	
	@Override  
    public boolean isCellEditable(int rowIndex, int columnIndex)  {  
        return false;  
    }
	
	@Override
	public String getColumnName(int column){
		return columnNames[column];
	}
}
