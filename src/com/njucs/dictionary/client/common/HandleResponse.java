package com.njucs.dictionary.client.common;

import com.njucs.dictionary.modle.Response;
/**
 * 处理服务器返回的响应信息
 * @author zhe
 */
public class HandleResponse {

	public static void Handle(Response response){
		System.out.println(response.getNo() + " " + response.getDescription());
	}
	
}
