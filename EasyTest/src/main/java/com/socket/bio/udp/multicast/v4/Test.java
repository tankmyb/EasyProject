package com.socket.bio.udp.multicast.v4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Test {

    public static void main(String[] args){

        String host = "225.0.0.1";//多播地址

        int port = 9998;

        String message = "test-multicastSocket";

        try {

            InetAddress group = InetAddress.getByName(host);

            MulticastSocket s = new MulticastSocket();

            //加入多播组

            s.joinGroup(group);

            DatagramPacket dp = new DatagramPacket(message.getBytes(),message.length(),group,port);

            s.send(dp);

            s.close();

        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
