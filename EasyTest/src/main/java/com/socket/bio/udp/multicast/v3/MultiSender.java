package com.socket.bio.udp.multicast.v3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
/**
 * 该类封装了MulticastSocket类,完成了MulticastSocket类实例的创建、初始化功能,
 * 并提供了一个发送数据的接口.
 */


public class MultiSender {
	public static final int MultiSender_Port=4099;
	  private MulticastSocket road;
	  private InetAddress ia;

	  public MultiSender() {
	    try {

	      //组播地址
	      ia = InetAddress.getByName("239.66.69.18");
	      road = new MulticastSocket(MultiSender_Port);
	      road.joinGroup(ia);
	    }
	    catch (UnknownHostException ex) {
	    }
	    catch (IOException ex1) {
	    }
	  }
	  public InetAddress getInetAddress(){
	    return ia;
	  }
	  public MulticastSocket getRoad(){
	    return road;
	  }
	  public void send(byte[] b){
	    DatagramPacket dp = new DatagramPacket(b, 0, b.length,
	                                               ia, MultiSender.MultiSender_Port);
	    try {
	      road.send(dp);
	    }
	    catch (IOException ex) {
	      ex.printStackTrace();
	    }
	  }


}
