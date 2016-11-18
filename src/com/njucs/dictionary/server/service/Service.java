package com.njucs.dictionary.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.njucs.dictionary.modle.Response;
import com.njucs.dictionary.server.dboption.DBOption;

public class Service extends DBOption{
	
	public Service(){
		super();
	}
	
	public Response VerifyPassword(String id,String password) throws SQLException{
		String sql="select password from diccount where id="+id;
		ResultSet res=executeQueryRS(sql, null);
		if(res.next()){
			if(password.equals(res.getString("password"))){
				return new Response(100,"登陆成功");
			}
			else
				return new Response(102,"密码错误");
		}
		else
			return new Response(101,"账号不存在");
	}
	
	public Response Register(String id, String password, String email) throws SQLException{
		String sql="select * from diccount where id="+id;
		ResultSet res=executeQueryRS(sql,null);
		if(res.next())
			return new Response(201,"该账号已存在");
		else{
			sql="insert into diccount(id,password,email) values(?,?,?)";
			String[] params={id,password,email};
			ExcuteUpdate(sql, params);
			return new Response(200,"注册成功");
		} 
	}
}
