/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.me.battleofhero;

import javax.swing.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.sql.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login {

	private JFrame frame = new JFrame("Login...");
	private JPanel imagePanel;
	private ImageIcon background, picture;
	private JLabel title, name, test;
	private JTextField username;
	private JPasswordField password;

	public Login() {

		background = new ImageIcon(this.getClass().getClassLoader()
				.getResource("com/me/battleofhero/8.jpg"));
		JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
		label.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight());

		picture = new ImageIcon(this.getClass().getClassLoader()
				.getResource("com/me/battleofhero/7.gif"));

		JLabel label1 = new JLabel(picture);
		label1.setBounds(5, 90, picture.getIconWidth(), picture.getIconHeight());

		imagePanel = (JPanel) frame.getContentPane();
		imagePanel.setOpaque(false);
		imagePanel.setLayout(new FlowLayout());// 设置成FlowLayOut布局

		title = new JLabel("~欢迎进入英雄PK场~");
		title.setForeground(Color.blue);
		name = new JLabel("用户名：");
		test = new JLabel("密  码：");
		username = new JTextField(10);
		username.setEditable(true);
		password = new JPasswordField(10);
		password.setEditable(true);

		title.setFont(new Font("隶书", Font.PLAIN, 23)); // 设置字体
		name.setFont(new Font("隶书", Font.PLAIN, 18));
		test.setFont(new Font("隶书", Font.PLAIN, 18));

		JButton button1 = new JButton("登 陆");
		JButton button2 = new JButton("注 册");
		button1.addActionListener(new Enter());
		button2.addActionListener(new Rgister());
		button1.setFont(new Font("隶书", Font.PLAIN, 18));
		button2.setFont(new Font("隶书", Font.PLAIN, 18));

		for (int i = 0; i < 4; i++)
			imagePanel.add(Box.createHorizontalStrut(3000));
		imagePanel.add(title);
		for (int i = 0; i < 4; i++)
			imagePanel.add(Box.createHorizontalStrut(3000));
		imagePanel.add(name);
		imagePanel.add(username);
		imagePanel.add(Box.createHorizontalStrut(3000));
		imagePanel.add(test);
		imagePanel.add(password);
		imagePanel.add(Box.createHorizontalStrut(3000));
		imagePanel.add(button1);
		imagePanel.add(button2);

		frame.getLayeredPane().setLayout(null);// 把背景图片添加到分层窗格的最底层作为背景
		frame.getLayeredPane().add(label1, new Integer(Integer.MIN_VALUE));
		frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

		Dimension dim = new Dimension();// 窗口设置大小
		dim.setSize(background.getIconWidth(), background.getIconHeight());
		// dim.setSize(700, 500);
		frame.setSize(dim);
		frame.setResizable(false);

		Point point = new Point(350, 200);// 窗口设置位置
		frame.setLocation(point);
		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class Enter implements ActionListener { // 发送button1的监听器
		public void actionPerformed(ActionEvent e) {
			String name = username.getText();
			String pass = String.valueOf(password.getPassword());
			System.out.println(pass);
			Connection conn = null;
			try {
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://" + Battle_of_Hero.server
						+ ":3306/battleofhero";
				String user = "cg";
				String pwd = "chengang";
				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, pwd);
				Statement statement = conn.createStatement();
				System.out.println("Successful!");

				String sql = "SELECT * FROM user WHERE uname = '" + name + "';";
				System.out.println(sql);
				ResultSet rs = statement.executeQuery(sql);
				if (rs.next()) {
					String correctPassword = rs.getString("upwd");
					if (pass.equals(correctPassword)) {

						LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
						cfg.title = "Battle_of_Hero";
						cfg.width = 1200;
						cfg.height = 600;
						String id = rs.getString("uid");
						new LwjglApplication(new Battle_of_Hero(id), cfg);

						frame.setVisible(false);
						frame.dispose();
					} else
						new Wronginput("密码或用户名错误");

				} else
					new Wronginput("密码或用户名错误");

			} catch (Exception c) {
				System.out.println(c.toString());
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException c) {
						c.printStackTrace();
					}
			}
		}
	}

	class Rgister implements ActionListener { // 发送button2的监听器
		public void actionPerformed(ActionEvent e) {
			new Register("注    册");
		}
	}

}
// 在FlowLayout里的控件,设置大小要用setPrefferedSize(Dimension(width,height))
