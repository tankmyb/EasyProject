package com.socket.bio.udp.multicast.v3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
/**
 * 该类封装了MulticastSocket类,完成了MulticastSocket类实例的创建、初始化功能,
 * 并提供一个接收数据的线程,在判断接收完毕后产生事件,更新UI显示.
 * 该类由testFrame使用.
 */
public class ImageShow
    extends DataSwapListenerAdapter
    implements Runnable {
  private InetAddress ia;
  private int port = 4099;
  private MulticastSocket road;
  DataSwapEvent dsevent;
  java.awt.image.BufferedImage bi;

  public ImageShow() {
    dsevent = new DataSwapEvent(this);
    try {
      ia = InetAddress.getByName("239.66.69.18");
      road = new MulticastSocket(port);
      road.joinGroup(ia);
    }
    catch (IOException ex) {
    }
  }

  public void run() {
    byte[] buffer = new byte[DataPacket.DataSwapSize];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    DataPacket dp = new DataPacket();
    while (true) {
      packet.setLength(buffer.length);
      System.out.println("wait .. ");
      try {
        road.receive(packet);
        dp.add(packet.getData());
        if (dp.isFull()) {
         // dsevent.setImage(dp.Gereratedata());
          this.processRecvFinishedEvent(dsevent);
          dp = new DataPacket();
        }
      }
      catch (IOException ex) {
        System.out.println(ex);
      }
    }
  }


}
