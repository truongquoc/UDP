package UDPMessage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.StringTokenizer;

public class server {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        System.out.println("Server is started");
        byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            InetAddress inetAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String request = new String(receivePacket.getData());

            DatagramPacket resToUpper = new DatagramPacket(ToUpperCase(request).getBytes(), request.length(), inetAddress, port);
            serverSocket.send(resToUpper);
            DatagramPacket resToLower = new DatagramPacket(ToLowerCase(request).getBytes(), request.length(), inetAddress, port);
            serverSocket.send(resToLower);
            DatagramPacket resToLowerUpper = new DatagramPacket(ToLowerUpper(request).getBytes(), request.length(), inetAddress, port);
            serverSocket.send(resToLowerUpper);
            DatagramPacket resToNumberOfWord = new DatagramPacket((String.valueOf(NumberOfWords(request))).getBytes(), (String.valueOf(NumberOfWords(request))).length(), inetAddress, port);
            serverSocket.send(resToNumberOfWord);
            DatagramPacket resToNumberOfVowel = new DatagramPacket((String.valueOf(NumberOfWords(request))).getBytes(), (String.valueOf(NumberOfWords(request))).length(), inetAddress, port);
            serverSocket.send(resToNumberOfVowel);
        }

    public static String ToUpperCase(String str) {
        String convStr = "";
        char c;
        for(int index=0; index< str.length(); index++) {
            c = str.charAt(index);
            if(c>=97 && c<=122) {
                c-=32;
            }
            convStr+=c;
        }
        return convStr;
    }

    public static String ToLowerCase(String str) {
        String convStr = "";
        char c;
        for(int index=0; index< str.length(); index++) {
            c = str.charAt(index);
            if(c>=65 && c<=90) {
                c+=32;
            }
            convStr+=c;
        }
        return convStr;
    }

    public static  String ToLowerUpper(String str) {
        String convStr = "";
        char c;
        for(int index=0; index< str.length(); index++) {
            c = str.charAt(index);
            if(c>=65 && c<=90) {
                c+=32;
            }
            else if(c>=97 && c<=122) {
                c-=32;
            }
            convStr+=c;
        }
        return convStr;
    }

    public static int NumberOfWords(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }
        StringTokenizer tokens = new StringTokenizer(str);
        return tokens.countTokens();
    }

    public static  int NumberOfVowel(String str) {
        int count = 0;
        char c;
        for(int index=0; index< str.length(); index++) {
            c = str.charAt(index);
            if(c== 'a' || c=='e' || c=='i' || c=='o' || c=='u') {
                count++;
            }
        }
        return count;
    }
}
