package com.njucs.dictionary.modle;

import java.io.Serializable;

/**
 * 单词点赞数（like）		
 * 		有道
 * 		百度
 * 		金山
 * 设置点赞数上限为99，下限为0，便于显示
 * @author zhe
 *
 *	单词点赞状态(*like)
 *		1表示点赞
 *		-1表示取消点赞
 *		0表示没有点赞(不表示取消)
 *	返回值中的点赞状态表示当前查询时的点赞状态（无-1值）
 */
public class Like implements Serializable{
	private static final long serialVersionUID = 5183554541964077492L;
	private int youdao;
	private int youdaolike; 
	private int baidu;
	private int baidulike;
	private int jinshan;
	private int jinshanlike;
	
	public Like(int baidu, int youdao, int jinshan){
		this.setBaidu(baidu);
		this.setYoudao(youdao);
		this.setJinshan(jinshan);
	}
	
	public Like(int baidu, int youdao, int jinshan, int baidulike, int youdaolike, int jinshanlike){
		this.setBaidu(baidu);
		this.setBaidulike(baidulike);
		this.setYoudao(youdao);
		this.setYoudaolike(youdaolike);
		this.setJinshan(jinshan);
		this.setJinshanlike(jinshanlike);
	}

	public int getYoudao() {
		return youdao;
	}

	public void setYoudao(int youdao) {
		this.youdao = youdao;
	}

	public int getBaidu() {
		return baidu;
	}

	public void setBaidu(int baidu) {
		this.baidu = baidu;
	}

	public int getJinshan() {
		return jinshan;
	}

	public void setJinshan(int jinshan) {
		this.jinshan = jinshan;
	}

	public int getYoudaolike() {
		return youdaolike;
	}

	public void setYoudaolike(int youdaolike) {
		this.youdaolike = youdaolike;
	}

	public int getBaidulike() {
		return baidulike;
	}

	public void setBaidulike(int baidulike) {
		this.baidulike = baidulike;
	}

	public int getJinshanlike() {
		return jinshanlike;
	}

	public void setJinshanlike(int jinshanlike) {
		this.jinshanlike = jinshanlike;
	}

}
