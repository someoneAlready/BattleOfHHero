package com.me.battleofhero;

import java.sql.*;

public class Init {
	public static void main(String args[]) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306";
		String user = "root";
		String pwd = "Alical";
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pwd);
			Statement statement = conn.createStatement();
			System.out.println("Successful!");

			String sql[] = {
					"CREATE DATABASE battleofhero  DEFAULT CHARACTER SET utf8;",
					"USE battleofhero;", 
					"CREATE TABLE user(uid int primary key AUTO_INCREMENT, uname varchar(20), upwd varchar(20), urating int default 1000);",
					"CREATE TABLE battle(bid int primary key AUTO_INCREMENT, uid0 int, uid1 int, wid int)" };

			for (int i = 0; i < sql.length; ++i)
				statement.execute(sql[i]);

			statement.close();
			conn.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("Can't find mysql driver");
			e1.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
}
