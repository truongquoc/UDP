package UDPChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client extends JFrame implements ActionListener {

    private JFrame login, main;
    private Label label1, label2;
    private JPanel loginPanel, mainPanel;
    private JButton loginButton, sendButton;
    private TextField username, chat;
    private JScrollPane sp;
    private JTextArea content;
    private DatagramSocket ds;
    private InetAddress inetAddress;
    private int port = 9876;
    String temp="";
    public Client(String title) throws IOException, ClassNotFoundException {
        super(title);
        GUI();
        while (true) {
            ds = new DatagramSocket();
            inetAddress = InetAddress.getByName("localhost");
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            ds.receive(receivePacket);
            String serverResponse = new String(receivePacket.getData());
            temp = temp + serverResponse+ "\n";
            content.setText(temp);
            System.out.println(serverResponse);
        }
    }
    public void GUI() {
        loginPanel = new JPanel();
        loginPanel.setLayout(null);
        login = new JFrame();
        login.setTitle("Login");
        login.setSize(600,200);

        mainPanel = new JPanel();
        main = new JFrame();
        main.setTitle("Main");
        main.setSize(600, 500);
        main.getContentPane().setLayout(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label1 = new Label("Enter your name");
        label1.setBounds(37, 13, 127,16);
        username = new TextField();
        username.setBounds(176, 10, 169, 22);

        loginButton = new JButton("Enter");
        loginButton.addActionListener(this);
        loginButton.setBounds(413, 9, 97, 25);
        loginPanel.add(loginButton);
        loginPanel.add(label1);
        loginPanel.add(username);
        login.add(loginPanel);

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
        login.setVisible(true);
        main.setVisible(false);

    }
    public static void main(String[] args) throws Exception {
        new Client("Chat");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            login.setVisible(false);
            main.setVisible(true);
        }
        if(e.getSource() == sendButton) {
            temp+= username.getText() + ": " + chat.getText() + "\n";
            content.setText(temp);
            try {
                String msg = username.getText()+": "+ chat.getText();
                DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.length(), inetAddress, port);
                ds.send(sendPacket);
                chat.setText("");
                chat.requestFocus();
                content.setVisible(false);
                content.setVisible(true);
            }
            catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
}

