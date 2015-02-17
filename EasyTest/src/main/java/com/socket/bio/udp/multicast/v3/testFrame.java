package com.socket.bio.udp.multicast.v3;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 该类使用ImageShow更新显示的图象.
 */

public class testFrame
    extends JApplet
    implements DataSwapListener {
  private JPanel root;
  JLabel label;
  JImagePanel ip;
  java.awt.Image bi;

  public testFrame() {
    initmain();
  }

  public void init() {
    initmain();
    this.setContentPane(root);
    ImageShow is = new ImageShow();
    is.addDataSwapListener(this);
    Thread thread = new Thread(is, "test");
    thread.start();
  }

  public static void main(String[] args) {
    testFrame test = new testFrame();
    test.go(new JFrame());
    ImageShow is = new ImageShow();
    is.addDataSwapListener(test);
    Thread thread = new Thread(is, "test");
    thread.start();
  }

  public void go(JFrame frame) {
    frame.setContentPane(root);
    frame.setSize(300, 200);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.validate();
    frame.setVisible(true);
  }

  public void initmain() {
    root = new JPanel();
    //label = new JLabel();
    ip = new JImagePanel();
    root.setLayout(new BorderLayout(5, 5));
    root.add(ip, BorderLayout.CENTER);
  }

  public void setRefreshImage(java.awt.image.BufferedImage img) {
    this.bi = img;
    ip.setImage(bi);
  }

  public void paint(Graphics g) {
    super.paint(g);
  }

  public void paintComponents(Graphics g) {
    super.paintComponents(g);
    Graphics g1 = root.getGraphics();
    g1.drawImage(bi, 0, 0, this);
  }

  public void OnDataSendFinished(Object s, DataSwapEvent e) {

  }

  public void OnDataRecvFinished(Object s, DataSwapEvent e) {
    this.bi = e.getImage();
    ip.setImage(bi);
    System.out.println("recv Finished!");
  }
}


