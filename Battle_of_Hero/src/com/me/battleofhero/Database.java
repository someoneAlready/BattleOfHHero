package com.me.battleofhero;

import java.sql.*;

public class Database {
	public Database(int uid0, int uid1, int wid){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://"+Battle_of_Hero.server+":3306/battleofhero";
		String user = "root";
		String pwd = "Alical";
		try{
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pwd);
			Statement statement = conn.createStatement();
			System.out.println("Successful!");
			
			String sql="INSERT INTO battle(uid0, uid1, wid) values("+uid0+","+uid1+","+wid+ ");";
			System.out.println(sql);
			statement.execute(sql);
			
			statement.close();
			conn.close();
		}catch(ClassNotFoundException e1){
			System.out.println("Can't find mysql driver");
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
