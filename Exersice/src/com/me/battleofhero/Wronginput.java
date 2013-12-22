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

/**
 *
 * @author 陈姝宇
 */
public class Wronginput {
        private JFrame fr ;
	private JPanel iPanel ;
	private JLabel wr;
	private JButton jb;
	
	public Wronginput(String s){
		fr = new JFrame( "" );
		fr.setUndecorated(true);   //将边框去除
        iPanel = (JPanel) fr.getContentPane();
        iPanel.setOpaque( false);
        iPanel.setLayout( new FlowLayout());//设置成FlowLayOut布局
        
        wr = new JLabel( s );
        wr.setForeground(Color.orange);
        wr.setFont(new Font("隶书",Font.PLAIN,20));
        
        jb = new JButton("确 认");
        jb.setFont(new Font("隶书",Font.PLAIN,15));
        jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fr.dispose();     //隐藏提示窗口
			}
		});
        
        iPanel.add( wr );
        iPanel.add( Box.createHorizontalStrut( 3000 ) );
        iPanel.add( jb );
        
        Dimension dim=new Dimension();//窗口设置大小
        dim.setSize(240,70);
        fr.setSize(dim);
        
        Point point=new Point(480,300);//窗口设置位置
        fr.setLocation(point);
        fr.setVisible(true);
        
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
