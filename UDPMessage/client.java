package UDPMessage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

public class client {
    public static String StringToUpper, StringToLower, StringToUpperLower, NumberOfWords, NumberOfVowel;
    public static void main(String[] args) throws Exception {
        DatagramSocket ds = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter message: ");
            String msg = reader.readLine();
            sendData = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, inetAddress, 9876);
            ds.send(sendPacket);

            DatagramPacket receivePacketToUpper = new DatagramPacket(receiveData, receiveData.length);
            ds.receive(receivePacketToUpper);
            StringToUpper = new String(receivePacketToUpper.getData());

            receiveData = new byte[1024];
            DatagramPacket receivePacketToLower = new DatagramPacket(receiveData, receiveData.length);
            ds.receive(receivePacketToLower);
            StringToLower = new String(receivePacketToLower.getData());

            receiveData = new byte[1024];
            DatagramPacket receivePacketToLowerUpper = new DatagramPacket(receiveData, receiveData.length);
            ds.receive(receivePacketToLowerUpper);
            StringToUpperLower = new String(receivePacketToLowerUpper.getData());

            receiveData = new byte[1024];
            DatagramPacket receivePacketNumberOfWords = new DatagramPacket(receiveData, receiveData.length);
            ds.receive(receivePacketNumberOfWords);
            NumberOfWords = new String(receivePacketNumberOfWords.getData());

            receiveData = new byte[1024];
            DatagramPacket receivePacketNumberOfVowels = new DatagramPacket(receiveData, receiveData.length);
            ds.receive(receivePacketNumberOfVowels);
            NumberOfVowel = new String(receivePacketNumberOfVowels.getData());
            System.out.println(MsgToString());
        }

    }
    public static String MsgToString() {
        return "Uppercase: "+StringToUpper+"\n"+"LowerCase: "+StringToLower+"\n"+"UpperLower: "+StringToUpperLower+"\n"+
                "NumberOfWords: "+NumberOfWords+"\n"+"NumberOfVowels: "+NumberOfVowel+"\n";
    }
}
