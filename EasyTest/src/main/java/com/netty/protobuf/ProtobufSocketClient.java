package com.netty.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.netty.protobuf.CarInfos.Car.CarInfo;

public class ProtobufSocketClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            InputStream in = socket.getInputStream();
            CarInfo recCarInfo = CarInfo.parseFrom(in);
            System.out.println(recCarInfo.getBrand());
            System.out.println(recCarInfo.getCarNumber());
            in.close();
            socket.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}