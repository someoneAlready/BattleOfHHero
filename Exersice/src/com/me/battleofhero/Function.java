/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.me.battleofhero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.lang.reflect.Array;
import java.util.*;
import java.sql.*; 
import java.util.Properties ;
/**
 *
 * @author 陈姝宇
 */
public class Function {
    public void output(JFrame fr){
        java.sql.Connection conn = null;
        JPanel panel ;
        JFrame frame = fr ;
        ImageIcon background;
        JLabel title, name ,test1, test2;
        JButton button1,button2;
        
        frame = new JFrame("Prepare...");      //Frame 设置为GridBagLayout布局管理器
        panel = (JPanel)frame.getContentPane();
        frame.setLayout(null);
        frame.setVisible(true);

        button1 = new JButton( "准备/取消准备" );
        button1.setFont(new Font("隶书",Font.PLAIN,20)); //设置字体
        button1.setForeground(Color.blue);
        button1.setBounds( 73, 3 , 170 , 40);
        panel.add( button1 );
      //  button1.addActionListener( new Prepare.Prepared() );
        
        JLabel label = new JLabel("可以选择想要对战的选手：");
        label.setFont(new Font("隶书",Font.PLAIN,18)); //设置字体
        label.setBounds(3, 43 , 250 , 40);
        panel.add( label );
        try{
             Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
             //conn = DriverManager.getConnection( "jdbc:mysql://192.168.1.135:3306/game" , "ccl" ,  "zzuacmlab") ;
             conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sample" , "root" ,  "root") ;
             PreparedStatement ps1 = conn.prepareStatement("select userName from userPre");
             ResultSet rs = ps1.executeQuery(); 
             int i = 1;
             while(rs.next())
             {
                   JButton  a = new JButton( rs.getString("userName") );
                   a.setFont(new Font("隶书",Font.PLAIN,20)); //设置字体
                   a.setForeground(Color.blue);
                   a.setBounds( 3  , 43 + i*40 , 300 , 40);
                   panel.add( a );
                   i++;
             }
         }catch(Exception c){
              System.out.println(c.toString());
         }finally{
              if(conn!=null)
              try {
                  conn.close();
              } catch (SQLException c) {
                  c.printStackTrace();
              }
        }
        
        
        Dimension dim=new Dimension();//窗口设置大小
        dim.setSize(500,500);
        frame.setSize(dim);
        frame.setResizable(false);

        Point point=new Point(350,230);//窗口设置位置
        frame.setLocation(point);
        frame.setVisible(true);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
