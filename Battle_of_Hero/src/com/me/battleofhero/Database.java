package com.me.battleofhero;

import java.sql.*;

public class Database {
	public Database(String id, int det){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://"+Battle_of_Hero.server+":3306/battleofhero";
		String user = "cg";
		String pwd = "chengang";
		try{
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pwd);
			Statement statement = conn.createStatement();
			System.out.println("Successful!");
			
			String sql="SELECT * FROM user WHERE uid = "+id+";";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()){
				sql = "UPDATE user SET urating ='"+ (rs.getInt("urating")+det) +"' WHERE uid = "+id+";";

			}
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
