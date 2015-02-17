package com.socket.bio.udp.multicast.v3;

import java.io.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * 本类利用MultiSender类发送文件数据到一个组播组发送数据.
 */

public class ImageServer
    extends Thread implements ActionListener {
  Timer timer;
  BufferedImage image;
  ArrayList streamfragments;
  int counter = 0;
  byte[] imagebyte;
  ArrayList listener;
  MultiSender sender;

  public ImageServer(ArrayList f) {
    timer = new Timer(50, this);
    timer.addActionListener(this);
    listener = new ArrayList();
    streamfragments = f;
    sender = new MultiSender();
    timer.start();
  }

  public void addDataSwapListener(DataSwapListener dsl) {
    listener.add(dsl);
  }

  public void removeDataSwapListener(DataSwapListener dsl) {
    listener.remove(dsl);
  }

  private void processEvent() {
    for (int i = 0; i < this.listener.size(); i++) {
      DataSwapEvent dse = new DataSwapEvent();
      ( (DataSwapListener)this.listener.get(i)).OnDataSendFinished(this, dse);
    }
  }

  public void actionPerformed(ActionEvent e) {
    DataPacket dp = new DataPacket(streamfragments.get(counter).toString());
    try {
      ArrayList al = dp.getDataPackets();
      Thread.sleep(1000);
      System.out.println(streamfragments.get(counter).toString());
      for (int i = 0; i < al.size(); i++) {
        imagebyte = ((String) al.get(i)).getBytes(); //(byte[]) al.get(i);
        sender.send(imagebyte);
      }
      this.processEvent();
    }
    catch (Exception ex) {
      System.out.println(ex);
    }
    counter++;
    if (counter >= streamfragments.size())
      counter = 0;
  }

  public void run() {
    while (true) {
      try {
        this.sleep(20);
      }
      catch (InterruptedException ex) {
      }
    }
  }

  public static void main(String[] args) {
    String file[];
    ArrayList al = new ArrayList();
    String path = "E:\\mzip\\"; 
    File f = new File(path);
    file = f.list();
    for (int i = 0; i < file.length; i++) {
      if (file[i].endsWith("jpg") || file[i].endsWith("bmp")) 
        al.add(path + file[i]);
    }
    ImageServer is = new ImageServer(al);
    is.start();
  }
}


