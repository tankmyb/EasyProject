package com.socket.bio.udp.multicast.v3;

import java.awt.*;
import java.net.DatagramPacket;

public class DataSwapEvent
    extends java.util.EventObject {
Object source;
String id;
java.awt.Image image;
private DatagramPacket dp;

public DataSwapEvent() {
    super(null);
}

public DataSwapEvent(Object ob) {
    super(ob);
    this.source = ob;
}

public String getId() {
    return id;
}

public void setId(String id) {
    this.id = id;
}

public void setImage(Image img) {
    this.image = img;
}

public Image getImage() {
    return image;
}

public DatagramPacket getDatagram() {
    return dp;
}

public void setDatagram(DatagramPacket dp1) {
    dp = dp1;
}
}


