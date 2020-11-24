package UDPChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ArrayList<Clienthandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static final int PORT = 9876;

    public static void main(String[] args) {
        try {
            while (true) {
                DatagramSocket serverSocket = new DatagramSocket(9876);
                System.out.println("Server started");
                Clienthandler clientThread = new Clienthandler(serverSocket);
                clients.add(clientThread);
                pool.execute(clientThread);
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }

}
class Clienthandler extends JFrame implements Runnable, ActionListener {

    private JFrame main;
    private JButton sendButton;
    private TextField chat;
    private JScrollPane sp;
    private JTextArea content;
    private DatagramSocket ds;
    private DatagramPacket receivePacket;
    String temp="";
    public Clienthandler(DatagramSocket client) {
        super("Server Side");
        GUI();
        this.ds = client;
    }
    @Override
    public void run()  {
            while(true) {
                    byte[] receiveData = new byte[1024];
                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    ds.receive(receivePacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String msg = new String(receivePacket.getData());
                    temp = temp + msg+ "\n";
                    content.setText(temp);
            }
    }

    public void GUI() {
        main = new JFrame();
        main.setTitle("Server");
        main.setSize(600, 500);
        main.getContentPane().setLayout(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        content = new JTextArea();
        chat = new TextField();
        chat.setBounds(37, 400, 400, 30);

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        sendButton.setBounds(450, 400, 80 ,40);
        sp = new JScrollPane(content);
        sp.setBounds(50, 50, 500, 200);
        main.add(chat);
        main.add(sendButton);
        main.add(sp);
        main.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sendButton) {
            temp+="[SERVER]: "+ chat.getText() + "\n";
            content.setText(temp);
                byte[] sendData = new byte[1024];
                String sendMsg = "[SERVER]: "+ chat.getText();
                sendData = sendMsg.getBytes();
                InetAddress inetAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, inetAddress, port);
            try {
                ds.send(sendPacket);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            chat.setText("");
                chat.requestFocus();
                content.setVisible(false);
                content.setVisible(true);

        }
    }
}