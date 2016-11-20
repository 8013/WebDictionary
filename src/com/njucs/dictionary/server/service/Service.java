package com.njucs.dictionary.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.njucs.dictionary.modle.Like;
import com.njucs.dictionary.modle.Response;
import com.njucs.dictionary.server.dboption.DBOption;

public class Service extends DBOption{
	
	public Service(){
		super();
	}
	
	/*
	 * 登陆用函数
	 */
	public Response VerifyPassword(String id,String password) throws SQLException{
		String sql="select password from account where id="+id;
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
		String sql="select * from account where id="+id;
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
	
	/*
	 * 查询用函数
	 * type=0表示有道
	 * type=1表示百度
	 * type=2表示金山
	 */
	public Response GetLikeNum(String word) throws SQLException{
		Like like=new Like(0,0,0);
		for(int type=0;type<3;type++){
			String sql="select * from dictionary where word='"+word+"' and type="+type;
			ResultSet res=executeQueryRS(sql,null);
			if(res.next()){
				sql="insert into dictionary(word,type,likenumber) values(?,?,?)";
				Object[] params={word,type,0};
				ExcuteUpdate(sql,params);
			}
			else{
				int likenum=res.getInt("likenumber");
				switch(type){
				case 0:like.setYoudao(likenum);break;
				case 1:like.setBaidu(likenum);break;
				case 2:like.setJinshan(likenum);break;
				default:break;
				}
			}
		}
		return new Response(300,like);
	}
	
	public Response UpdateLikeNum(String word, Like like) throws SQLException{
		for(int type=0;type<3;type++){
			String sql="select * from dictionary where word='"+word+"' and type="+type;
			ResultSet res=executeQueryRS(sql, null);
			int num=0;
			switch(type){
			case 0:num=like.getYoudao();break;
			case 1:num=like.getBaidu();break;
			case 2:num=like.getJinshan();break;
			default:break;
			}
			if(res.next()){
				sql="insert into dictionary(word,type,likenumber) values(?,?,?)";
				Object[] params={word,type,num};
				ExcuteUpdate(sql, params);
			}
			else{
				sql="update dictionary set likenumber="+num+" where word='"+word+"' and type="+type;
				ExcuteUpdate(sql, null);
			}
		}
		return new Response(301,"");
	}
}
