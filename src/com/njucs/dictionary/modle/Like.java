package com.njucs.dictionary.modle;

import java.io.Serializable;

/**
 * 单词点赞数（like）
 * 		有道
 * 		百度
 * 		必应
 * @author zhe
 *
 */
public class Like implements Serializable{
	private static final long serialVersionUID = 5183554541964077492L;
	private int youdao;
	private int baidu;
	private int bing;
	
	public Like(int youdao, int baidu, int bing){
		this.setYoudao(youdao);
		this.setBaidu(baidu);
		this.setBing(bing);
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

	public int getBing() {
		return bing;
	}

	public void setBing(int bing) {
		this.bing = bing;
	}
}
