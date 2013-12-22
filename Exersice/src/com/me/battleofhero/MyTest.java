/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.me.battleofhero;
import java.awt.*;
import java.awt.event.*;

public class MyTest extends Frame implements ActionListener{
Button b1 = new Button("Button1");
String str = "";
public void actionPerformed(ActionEvent e){
str = "red";
repaint();
}
public MyTest(){
setLayout(new FlowLayout());
add(b1);
b1.addActionListener(this);
setSize(400,300);
setVisible(true);
}
public void paint(Graphics g){
if(str.equals("red")){
g.setColor(Color.red);
g.drawString("MyTest",100,100);
}
}
public static void main(String[] args){
new MyTest();
}
}