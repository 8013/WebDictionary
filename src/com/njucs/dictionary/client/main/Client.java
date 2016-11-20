package com.njucs.dictionary.client.main;

import com.njucs.dictionary.client.home.Home;
import com.njucs.dictionary.client.login.Login;

public class Client {
	
	private static boolean debug=false;
	
	public static void main(String[] args) {
		if(debug){
			Home.Show();
		}
		else{
			Login.Show();
		}
	}

}
