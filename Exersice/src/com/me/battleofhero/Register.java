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
public class Register {
    
	private JFrame frame;
	private JPanel imagePanel ;
        private ImageIcon background;
        private JLabel title, name ,test1, test2;
        private JTextField username ;
        private JPasswordField password , password1;
        java.sql.Connection connect = null;
        
	public Register(String s){
		
		/* try{
			socket = new Socket("127.22.22.1",12300);
			in  = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		 }catch(IOException el){}
		*/ 
		frame = new JFrame( s );
		//frame.setUndecorated(true);   //将边框去除
		File pic=new File("src/exercise/3.jpg");
                background = new ImageIcon( pic.toString() );//背景图片
                JLabel label = new JLabel( background );//把背景图片显示在一个标签里面
                label.setBounds( 0 , 0 , background.getIconWidth() , background.getIconHeight() );
       
                imagePanel = (JPanel) frame.getContentPane();
                imagePanel.setOpaque( false);
                imagePanel.setLayout( new FlowLayout());//设置成FlowLayOut布局
        
                title = new JLabel( "请输入您的信息" );
                name = new JLabel  ( "用 户 名 ：" );
                username = new JTextField( 10 );
                test1 = new JLabel ( "密    码 ：" );
                password = new JPasswordField( 10 );
                test2 = new JLabel ( "密码确认 ：" );
                password1 = new JPasswordField( 10 );
        
                title.setFont(new Font("隶书",Font.PLAIN,25)); //设置字体
                title.setForeground(Color.cyan);
                name.setFont(new Font("隶书",Font.PLAIN,20));
                name.setForeground(Color.WHITE);
                test1.setFont(new Font("隶书",Font.PLAIN,20)); 
                test1.setForeground(Color.WHITE);
                test2 .setFont(new Font("隶书",Font.PLAIN,20)); 
                test2.setForeground(Color.WHITE);
                JButton button1 = new JButton ( "确认" );
                JButton button2 = new JButton ( "重置" );
                button1.addActionListener( new Enter() );
                button2.addActionListener( new Reset() );
                button1.setFont(new Font("隶书",Font.PLAIN,18));
                button2.setFont(new Font("隶书",Font.PLAIN,18));
        
                for(int i = 0; i< 4; i++)
                    imagePanel.add( Box.createHorizontalStrut( 6000 ) );
                imagePanel.add( title );
                for(int i = 0; i< 4; i++)
                        imagePanel.add( Box.createHorizontalStrut( 3000 ) );
                imagePanel.add( name );
                imagePanel.add( username );
                imagePanel.add( Box.createHorizontalStrut( 3000 ) );
                imagePanel.add( test1 );
                imagePanel.add( password );
                imagePanel.add( Box.createHorizontalStrut( 3000 ) );
                imagePanel.add( test2 );
                imagePanel.add( password1 );
                imagePanel.add( Box.createHorizontalStrut( 3000 ) );
                imagePanel.add( button1 );
                imagePanel.add( button2 );
                
                frame.getLayeredPane().setLayout(null);//把背景图片添加到分层窗格的最底层作为背景       
                frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));    
        
                Dimension dim=new Dimension();//窗口设置大小
                dim.setSize(background.getIconWidth(),background.getIconHeight());
                frame.setSize(dim);
                frame.setResizable(false);
        
                Point point=new Point(350,230);//窗口设置位置
                frame.setLocation(point);
                frame.setVisible(true);
        
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

        class Enter implements ActionListener{	//发送button1的监听器
		public void actionPerformed( ActionEvent e ){
                        String name =  username.getText();
                        String num =String.valueOf( password.getPassword());
                        String values = String.valueOf(password1.getPassword());
                        char[] a=password.getPassword();
                        
			if( !num.equals(values) ){
				new Wronginput("两次密码输入不一致！！！");
				password.setText("");
				password1.setText("");
			}
                        else if( username.getText().length()!= 0 && password.getPassword().length!=0 && num.equals(values) )
                        {
                            Connection conn = null;
                            try{
                                Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
                                //conn = DriverManager.getConnection( "jdbc:mysql://10.108.72.37:3306/game" , "ccl" ,  "zzuacmlab") ;
                                conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sample" , "root" ,  "root") ;
                                PreparedStatement ps1 = conn.prepareStatement("select userPw from userInfo where userName = ?");
                                ps1.setString(1,name);
                                ResultSet rs = ps1.executeQuery(); 
                                if( rs.next() ){
                                    new Wronginput("该用户名已被注册");
                                    username.setText("");
                                    password.setText("");
                                    password1.setText("");
                                }else {
                                    PreparedStatement ps2 = conn.prepareStatement("insert into userInfo (userName, userPw) values (?, ?); ");
                                    ps2.setString( 1 , name );
                                    ps2.setString( 2 , num );
                                    ps2.executeUpdate();
                                    rs = ps1.executeQuery();
                                    if( rs.next() ) new Wronginput("注册成功");
                                    else new Wronginput("注册失败");
                                    username.setText("");
                                    password.setText("");
                                    password1.setText("");
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
      }
	
        class Reset implements ActionListener{	//发送button2的监听器
		public void actionPerformed( ActionEvent e ){
			username.setText("");
			password.setText("");
			password1.setText("");
		}
	}
    
}