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
 */
public class Like implements Serializable{
	private static final long serialVersionUID = 5183554541964077492L;
	private int youdao;
	private int baidu;
	private int jinshan;
	
	public Like(int baidu, int youdao, int jinshan){
		this.setBaidu(baidu);
		this.setYoudao(youdao);
		this.setJinshan(jinshan);
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

}
