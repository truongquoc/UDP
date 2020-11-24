package UDPChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;


public class Server {
    private static final int PORT = 9876;
    public static DatagramPacket receivePacket;
    public static int receivePort;
    public static InetAddress receiveAddress;
    public static void main(String[] args) throws Exception{
        DatagramSocket serverSocket = new DatagramSocket(9876);
         System.out.println("Server started");
        ClientHandler clientHandler = new ClientHandler(serverSocket);
        while (true) {
                byte[] receiveData = new byte[1024];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                receivePort = receivePacket.getPort();
                receiveAddress = receivePacket.getAddress();
            System.out.println("add"+receivePacket.getAddress());
            System.out.println("port"+receivePacket.getPort());

            ClientHandler.temp = ClientHandler.temp + msg+ "\n";
                ClientHandler.content.setText(ClientHandler.temp);
            }
    }

}
class ClientHandler extends JFrame implements ActionListener {

    private JFrame main;
    private JButton sendButton;
    private TextField chat;
    private JScrollPane sp;
    public static JTextArea content;
    private DatagramSocket ds;
    public static String temp="";
    public ClientHandler(DatagramSocket client) {
        super("Server Side");
        GUI();
        this.ds = client;
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
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, Server.receiveAddress, Server.receivePort);
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