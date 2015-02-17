package com.socket.bio.udp.multicast.v4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class TestServer {

    public static void main(String[] args){

        //接受组播和发送组播的数据报服务都要把组播地址添加进来

        String host = "225.0.0.1";//多播地址

        int port = 9998;

        int length = 1024;

        byte[] buf = new byte[length];

        MulticastSocket ms = null;

        DatagramPacket dp = null;

        StringBuffer sbuf = new StringBuffer();

        try {         

            ms = new MulticastSocket(port);

            dp = new DatagramPacket(buf, length);

            //加入多播地址

            InetAddress group = InetAddress.getByName(host);

            ms.joinGroup(group);

            System.out.println("监听多播端口打开：");

            ms.receive(dp);

            ms.close();

            int i;

            for(i=0;i<1024;i++){

                if(buf[i] == 0){

                    break;

                }

                sbuf.append((char) buf[i]);

            }          

            System.out.println("收到多播消息：" + sbuf.toString());

        } catch (IOException e) {

            e.printStackTrace();

        } 

    }

}
