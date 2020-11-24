package com;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class client {
    public static void main(String[] args) throws  Exception {
        DatagramSocket ds = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[1024];
        byte[] recieveData = new byte[1024];
        sendData = "getDate".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress, 9876);
        ds.send(sendPacket);
        DatagramPacket recievePacket = new DatagramPacket(recieveData, recieveData.length);
        ds.receive(recievePacket);
        String str = new String(recievePacket.getData());
        System.out.println(str);
        ds.close();
    }
}
