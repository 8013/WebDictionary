package com.njucs.dictionary.client.home.tools;

import java.util.regex.Pattern;

import com.njucs.dictionary.client.common.Message;

public class CheckWord {

	public static boolean Pass(String word){
		word=word.trim();
		if(word.isEmpty()){
			Message.Show("请输入英文单词");
			return false;
		}
		else if(!Pattern.matches("\\w+( \\w+)*", word)){
			Message.Show("请输入正确的英文单词");
			return false;
		}
		else{
			return true;
		}
	}
}
