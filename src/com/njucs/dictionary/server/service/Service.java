package com.njucs.dictionary.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.njucs.dictionary.modle.Like;
import com.njucs.dictionary.modle.Response;
import com.njucs.dictionary.modle.Share;
import com.njucs.dictionary.modle.UserTable;
import com.njucs.dictionary.modle.Word;
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
//				return new Response(100,"登陆成功");
				UpdateUserState(id, 1);
				return new Response(100, id);
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
			sql="insert into account(id,password,email) values(?,?,?)";
			String[] params={id,password,email};
			ExcuteUpdate(sql, params);
			UpdateUserState(id, 0);
			return new Response(200,"注册成功");
		} 
	}
	
	/*
	 * 查询用函数
	 * type=0表示有道
	 * type=1表示百度
	 * type=2表示金山
	 */
	
	//不带记录状态的获取函数
	public Response GetLikeNum(String word) throws SQLException{
		Like like=new Like(0,0,0);
		for(int type=0;type<3;type++){
			String sql="select * from dictionary where word='"+word+"' and type="+type;
			ResultSet res=executeQueryRS(sql,null);
			if(!res.next()){
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
	
	//带记录状态的获取函数
	public Response GetLikeNum(String word, String id) throws SQLException{
		Like like=new Like(0,0,0,0,0,0);
		for(int type=0;type<3;type++){
			String sql="select * from dictionary where word='"+word+"' and type="+type;
			ResultSet res=executeQueryRS(sql,null);
			if(!res.next()){
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
				sql="select * from likeid where word='"+word+"' and type="+type+" and id='"+id+"'";
				res=executeQueryRS(sql, null);
				if(res.next()){
					switch(type){
					case 0:like.setYoudaolike(1);break;
					case 1:like.setBaidulike(1);break;
					case 2:like.setJinshanlike(1);break;
					default:break;
					}
				}
			}
		}
		return new Response(300,like);
	}
	
	//更新点赞数时不增加记录
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
			if(!res.next()){
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
	
	//更新点赞数同时更新记录
	public Response UpdateLikeNum(String id, String word, Like like) throws SQLException{
		
		if(like.getYoudaolike()!=0){
			String sql="select * from dictionary where word='"+word+"' and type=0";
			ResultSet res=executeQueryRS(sql, null);
			if(!res.next()){
				sql="insert into dictionary(word,type,likenumber) values(?,?,?)";
				Object[] params={word,0,like.getYoudao()};
				ExcuteUpdate(sql, params);
			}
			else{
				int num=res.getInt("likenumber");
				if(like.getYoudaolike()==1)
					num++;
				else
					num--;
				sql="update dictionary set likenumber="+num+" where word='"+word+"' and type=0";
				ExcuteUpdate(sql, null);
				sql="select * from likeid where word='"+word+"' and type=0 and id='"+id+"'";
				res=executeQueryRS(sql, null);
				if(!res.next()){
					if(like.getYoudaolike()==1){
						sql="insert into likeid(word,id,type) values(?,?,?)";
						Object[] params={word,id,0};
						ExcuteUpdate(sql, params);
					}
				}
				else{
					if(like.getYoudaolike()==-1){
						sql="delete from likeid where word='"+word+"' and type=0 and id='"+id+"'";
						ExcuteUpdate(sql, null);
					}
				}
			}
		}
		else if(like.getBaidulike()!=0){
			String sql="select * from dictionary where word='"+word+"' and type=1";
			ResultSet res=executeQueryRS(sql, null);
			if(!res.next()){
				sql="insert into dictionary(word,type,likenumber) values(?,?,?)";
				Object[] params={word,1,like.getYoudao()};
				ExcuteUpdate(sql, params);
			}
			else{
				int num=res.getInt("likenumber");
				if(like.getBaidulike()==1)
					num++;
				else
					num--;
				sql="update dictionary set likenumber="+num+" where word='"+word+"' and type=1";
				ExcuteUpdate(sql, null);
				sql="select * from likeid where word='"+word+"' and type=1 and id='"+id+"'";
				res=executeQueryRS(sql, null);
				if(!res.next()){
					if(like.getBaidulike()==1){
						sql="insert into likeid(word,id,type) values(?,?,?)";
						Object[] params={word,id,1};
						ExcuteUpdate(sql, params);
					}
				}
				else{
					if(like.getBaidulike()==-1){
						sql="delete from likeid where word='"+word+"' and type=1 and id='"+id+"'";
						ExcuteUpdate(sql, null);
					}
				}
			}
		}
		else if(like.getJinshanlike()!=0){
			String sql="select * from dictionary where word='"+word+"' and type=2";
			ResultSet res=executeQueryRS(sql, null);
			if(!res.next()){
				sql="insert into dictionary(word,type,likenumber) values(?,?,?)";
				Object[] params={word,2,like.getYoudao()};
				ExcuteUpdate(sql, params);
			}
			else{
				int num=res.getInt("likenumber");
				if(like.getJinshanlike()==1)
					num++;
				else
					num--;
				sql="update dictionary set likenumber="+num+" where word='"+word+"' and type=2";
				ExcuteUpdate(sql, null);
				sql="select * from likeid where word='"+word+"' and type=2 and id='"+id+"'";
				res=executeQueryRS(sql, null);
				if(!res.next()){
					if(like.getJinshanlike()==1){
						sql="insert into likeid(word,id,type) values(?,?,?)";
						Object[] params={word,id,2};
						ExcuteUpdate(sql, params);
					}
				}
				else{
					if(like.getJinshanlike()==-1){
						sql="delete from likeid where word='"+word+"' and type=2 and id='"+id+"'";
						ExcuteUpdate(sql, null);
					}
				}
			}
		}
		
		if(like.getBaidulike()==1||like.getJinshanlike()==1||like.getYoudaolike()==1)
			return new Response(301,"");
		else
			return new Response(302,"");
	}

	public Response GetUserTable() throws SQLException{
		UserTable usertable=new UserTable();
		String sql="select * from account";
		ResultSet res=executeQueryRS(sql, null);
		while(res!=null&&res.next()){
			String user=res.getString("id");
			int state=res.getInt("state");
			usertable.Add(user,state);
		}
		return new Response(310,usertable);
	}

	public void UpdateUserState(String id, int state) throws SQLException{
		String sql="select * from account where id='"+id+"'";
		ResultSet res=executeQueryRS(sql, null);
		if(res.next()){
			sql="update account set state="+state+" where id='"+id+"'";
			ExcuteUpdate(sql, null);
		}
	}
	
	public Response GetSharedWord(String id) throws Exception{
		String sql="select * from sharedword where ToID='"+id+"'";
		ResultSet res=executeQueryRS(sql, null);
		ArrayList<Word> words=new ArrayList<Word>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while(res.next()){
			words.add(new Word(res.getString("Word"), res.getString("type"),res.getString("FromID"),sdf.parse(res.getString("time")),new Boolean(res.getString("isread"))));
		}
		/*sql="delete from sharedword where ToID='"+id+"'";
		ExcuteUpdate(sql, null);*/
		return new Response(900,words);
	}
	
	public Response SendSharedWord(String id,Share share) throws SQLException{
		ArrayList<String> sharelist=share.getShareList();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String word=share.getWord();
		String type=share.getType();
		for(int i=0;i<sharelist.size();i++){
			String sql="insert into sharedword(FromID,ToID,Word,type,Time,isread) values(?,?,?,?,?,?)";
			Object params[]={id,sharelist.get(i),word,type,sdf.format(share.getDate()),0};
			ExcuteUpdate(sql, params);
		}
		return new Response(800,"分享成功");
	}
}
