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
public class Prepare{
    private String username;
    private JPanel panel ;
    private JScrollPane panel1 ;
    private JFrame frame ;
    private ImageIcon background;
    private JLabel title, name ,test1, test2;
    private JButton button1,button2;
    private boolean a = false;   //记录是否准备,默认为未准备
    Container con;
    java.sql.Connection conn = null;
    
    public Prepare(String name)
    {
        username = name;    //获取用户名
        
        frame = new JFrame("Prepare...");      //Frame 设置为GridBagLayout布局管理器
        panel = (JPanel)frame.getContentPane();
        frame.setLayout(null);
        frame.setVisible(true);
        
	File pic=new File("src/com/me/battleofhero/1.jpg");
        background = new ImageIcon( pic.toString() );//背景图片
        
        output();    //显示出界面,排行榜等
        
        
        Dimension dim=new Dimension();//窗口设置大小
        dim.setSize(background.getIconWidth(),background.getIconHeight());
        frame.setSize(dim);
        frame.setResizable(false);
         
        Point point=new Point(350,230);//窗口设置位置
        frame.setLocation(point);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Thread thread = new Thread(new rePaint());
	thread.start();
        
        Thread thr = new Thread(new test());
	thr.start();
    }
    class Prepared implements ActionListener{	//发送button2的监听器
		public void actionPerformed( ActionEvent e ){
                    a = !a;
                    System.out.println(a);
                    if( a ){
                            try{
                                Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
                                //conn = DriverManager.getConnection( "jdbc:mysql://10.108.72.37:3306/game" , "ccl" ,  "zzuacmlab") ;
                                conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sample" , "root" ,  "root") ;
                                PreparedStatement ps1 = conn.prepareStatement("insert into userpre (username) values (?)");
                                ps1.setString( 1 , username );
                                PreparedStatement ps2 = conn.prepareStatement("select * from userpre where username=?");
                                ps2.setString( 1 , username );
                                ps1.executeUpdate();
                                ResultSet rs = ps2.executeQuery(); 
                                if( rs.next() ){
                                    redraw();
                                    new Wronginput("您已准备，请等待...");
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
                        }else{
                           try{
                                Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
                                //conn = DriverManager.getConnection( "jdbc:mysql://10.108.72.37:3306/game" , "ccl" ,  "zzuacmlab") ;
                                conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sample" , "root" ,  "root") ;
                                PreparedStatement ps1 = conn.prepareStatement("delete from userpre where userName=?");
                                ps1.setString( 1 , username );
                                PreparedStatement ps2 = conn.prepareStatement("select * from userpre where username=?");
                                ps2.setString( 1 , username );
                                ps1.executeUpdate();
                                ResultSet rs = ps2.executeQuery(); 
                                if( !rs.next() ){
                                    redraw();
                                    new Wronginput("取消准备成功...");
                                }else new Wronginput("取消失败...");
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
                    }
		}
	}
    
    class Join implements ActionListener{
        public void actionPerformed( ActionEvent e ){
            String name = e.getActionCommand();
            if(a)new Wronginput( "您已准备。。。" );
            else if( name.equals(username))
                 new Wronginput( "不能和自己比赛，请重新选择" );
            else
            try{
             Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
             //conn = DriverManager.getConnection( "jdbc:mysql://192.168.1.135:3306/game" , "ccl" ,  "zzuacmlab") ;
             conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sample" , "root" ,  "root") ;
             PreparedStatement ps = conn.prepareStatement("select * from userPre where username = ?");
             ps.setString(1, name);
             ResultSet rs = ps.executeQuery();
             if( rs.next() )
             {
                // ps1 = conn.prepareStatement("update userPre set usercome ='" + username +"' where username = ?");
                 ps = conn.prepareStatement("update userpre set usercome='" + username + "' where username='"+ name +"'");
                 System.out.println(name);
                 ps.executeUpdate();
                 ps = conn.prepareStatement("select * from userPre where usercome = ? ");
                 System.out.println(name);
                 ps.setString(1 , username);
                 ResultSet r = ps.executeQuery();
                 if( r.next() )
                 new Wronginput("请等待...");
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
        }
    }
    
    public void output(){      //显示画面

        JLabel label = new JLabel( background );//把背景图片显示在一个标签里面
        label.setBounds( 0 , 0 , background.getIconWidth() , background.getIconHeight() );
        System.out.println(background.getIconWidth());
        System.out.println(background.getIconHeight());
        
        button1 = new JButton( "准备/取消准备" );
        button1.setFont(new Font("隶书",Font.PLAIN,20)); //设置字体
        button1.setForeground(Color.blue);
        button1.setBounds( 73, 3 , 170 , 40);
        panel.add( button1 );
        button1.addActionListener( new Prepare.Prepared() );
        
        int i = 0;
        
        if(a){
            JLabel label1 = new JLabel("您已准备");
            label1.setFont(new Font("隶书",Font.PLAIN,18)); //设置字体
            label1.setBounds(3, 43 , 250 , 40);
            label1.setForeground(Color.red);
            panel.add( label1 );
            i++;       //将对手框下移
        }
        
        JLabel label2 = new JLabel("可以选择想要对战的选手：");
        label2.setFont(new Font("隶书",Font.PLAIN,18)); //设置字体
        label2.setBounds(3, 33 + i*40 , 250 , 40);
        panel.add( label2 );
        
        JLabel label3 = new JLabel("~英雄排行榜~");
        label3.setFont(new Font("隶书",Font.PLAIN,18)); //设置字体
        label3.setBounds( 353, 13 , 250 , 40);
        panel.add( label3 );
        
        
        try{
             Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
             //conn = DriverManager.getConnection( "jdbc:mysql://192.168.1.135:3306/game" , "ccl" ,  "zzuacmlab") ;
             
             
             conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sample" , "root" ,  "root") ;
             PreparedStatement ps1 = conn.prepareStatement("select userName from userPre");
             ResultSet rs = ps1.executeQuery(); 
             while( rs.next())
             {
                   i++;
                   JButton  a = new JButton( rs.getString("userName") );
                   a.setFont(new Font("隶书",Font.PLAIN,20)); //设置字体
                   a.setForeground(Color.blue);
                   a.setBounds( 3  , 33 + i*40 , 300 , 40);
                   a.addActionListener( new Join() );
                   panel.add( a );
             }
             ps1 = conn.prepareStatement("select * from userPre");
             rs = ps1.executeQuery();
             i=0;
             while(rs.next())
             {
                   i++;
                   JLabel  a = new JLabel( rs.getString("userName") );
                   a.setFont(new Font("隶书",Font.PLAIN,20)); //设置字体
                   a.setForeground(Color.LIGHT_GRAY);
                   a.setBounds( 353  , 23 + i*20 , 300 , 40);
                   panel.add( a );
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
        panel.setLayout(null);//把背景图片添加到分层窗格的最底层作为背景       
        panel.add(label,new Integer(Integer.MIN_VALUE));
    }
    
    public void redraw(){    //重绘界面
        try{
             panel.invalidate();
             panel.removeAll();
             panel.repaint();
             output();
             panel.validate();
         } catch (Exception ex){    
             ex.printStackTrace();                  
         }
    }
    
    class rePaint implements Runnable{		//线程
	public void run(){
            try{
                Thread.sleep(6000);
            }catch(Exception e){
                e.printStackTrace();
            }
            redraw();
	}
    }
    class test implements Runnable{
        public void run(){
            try{
        		String driver = "com.mysql.jdbc.Driver";
        		String url = "jdbc:mysql://"+Battle_of_Hero.server+":3306/battleofhero";
        		String user = "root";
        		String pwd = "Alical";
        	
        			Class.forName(driver);
        			Connection conn = DriverManager.getConnection(url, user, pwd);
        			Statement statement = conn.createStatement();
        			System.out.println("Successful!");
        			
        			String sql="SELECT * FROM user WHERE uname = '"+username+"';";
        			ResultSet rs = statement.executeQuery(sql);
        			
        			
                conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sample" , "root" ,  "root") ;
                PreparedStatement ps1 = conn.prepareStatement("select * from userPre where username =? ");
                ps1.setString( 1 , username );
                ResultSet rs = ps1.executeQuery(); 
                
                
                if( rs.next() ){
                    if( rs.getString( "usercome" )!=null )
                    {
                       new Wronginput( rs.getString( "usercome" ) + "即将和您对战" );
                       ps1 = conn.prepareStatement("delete from userPre where username =' " + username + "'");
                       Thread.sleep(6000);
                       frame.dispose();
                       //................................................
                      
                    }
                }
            }catch( Exception e){
                e.printStackTrace();
            }
        }
    }
}
