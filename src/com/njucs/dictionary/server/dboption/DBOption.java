package com.njucs.dictionary.server.dboption;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.CallableStatement;
import java.sql.Connection;

/*
 * 数据库相关操作
 * 
 */

public class DBOption {
	private final String Url="jdbc:mysql://localhost:3306/count?useUnicode=true&characterEncoding=utf-8&useSSL=true";
	private final String Username="zwj";
	private final String Password="19960604zhang";
	
	private Connection conn;
	private PreparedStatement pst;
	private ResultSet res;
	private CallableStatement cs;
	
	public DBOption(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(Exception e){
			System.out.println("Loading Driver Error!");
			e.printStackTrace();
		}
	}
	
	private void ConnectDB(){
		try {
			conn=DriverManager.getConnection(Url, Username, Password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//返回受影响的行数
	protected int ExcuteUpdate(String sql, Object[] params){
		int affectedline=0;
		ConnectDB();
		try {
			pst=conn.prepareStatement(sql);
			
			if(params!=null){
				for(int i=0;i<params.length;i++){
					pst.setObject(i+1, params[i]);
				}
			}
			
			affectedline=pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			CloseDB();
		}
		return affectedline;
	}
	
	protected ResultSet executeQueryRS(String sql, Object[] params){
		ConnectDB();
		try {
			pst=conn.prepareStatement(sql);
		
			if(params!=null){
				for(int i=0;i<params.length;i++){
					pst.setObject(i+1, params[i]);
				}
			}
			res=pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	protected Object executeQuerySingle(String sql, Object[] params){
		Object object=null;
		
		ConnectDB();
		
		try {
			pst=conn.prepareStatement(sql);

			if(params!=null){
				for(int i=0;i<params.length;i++){
					pst.setObject(i+1, params[i]);
				}
			}
			
			res=pst.executeQuery();
			
			if(res.next()){
				object=res.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			CloseDB();
		}
		return object;
	}
	
	protected List<Object> executeQuery(String sql,Object[] params){
		res=executeQueryRS(sql, params);
		
		ResultSetMetaData rsmd=null;
		
		int columnCount=0;
		try {
			rsmd=res.getMetaData();
			columnCount=rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<Object> list=new ArrayList<Object>();
		
		try {
			while(res.next()){
				Map<String, Object> map=new HashMap<String, Object>();
				for(int i=1;i<=columnCount;i++){
					map.put(rsmd.getColumnLabel(i),res.getObject(i));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			CloseDB();
		}
		return list;
	}
	
	protected Object excuteQuery(String sql, Object[] params, int outParamsPos, int SQLType){
		Object object=null;
		ConnectDB();
		
		try {
			cs=conn.prepareCall(sql);
			if(params!=null){
				for(int i=0;i<params.length;i++){
					cs.setObject(i+1, params[i]);
				}
			}
		
			cs.registerOutParameter(outParamsPos, SQLType);
			cs.execute();
			object=cs.getObject(outParamsPos);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			CloseDB();
		}
		return object;
	}
	
	/*protected void GetRecord(String id){
		try {
			pst=conn.prepareStatement("select * from diccount where id="+id);
			res=pst.executeQuery();
			while(res.next()){
				ID=res.getString("id");
				PASSWORD=res.getString("password");
				EMAIL=res.getString("email");
				//System.out.println(ResId+" "+ResKey+" "+ResEmail);
			}
			pst.close();
		} catch (SQLException e) {
			System.out.println("Get Record Failed!");
			e.printStackTrace();
		}
	}*/
	
	protected void CloseDB(){
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Close Driver Error!");
			e.printStackTrace();
		}
	}
}
