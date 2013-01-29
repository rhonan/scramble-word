package com.ufc.scramble_word.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class ConnectionSocket {

    public static final int CONNECTED = 1;
    public static final int ERROR = 2;
    public static final int SENDING_MESSAGE = 3;
    public static final int MESSAGE_RECIVED = 4;    
    public static final int DISCONNECTED = 5;	
    private static ConnectionSocket connection;
    private int porta;
    private String host;
    private Socket socket;
    private Sender sender;
    private Receiver receiver;
    private Message msg;
    private DataOutputStream out;
    private DataInputStream in;    
    private Handler handler; // Handle para notificações na tela

    
    private ConnectionSocket(String host, String porta, Handler handler) {
        this.host = host;
        this.porta = Integer.parseInt(porta);
        this.handler = handler;
    }

    // Método que cria Objecto ConnectionSocket
    public static ConnectionSocket createConnection(String host, String porta,Handler handler) {
        connection = new ConnectionSocket(host, porta,handler);
        return connection;
    }

    // Retorna conexão atual
    public static ConnectionSocket getCurentConnection() {
        return connection;
    }

    // Conecta com o Servidor
    public void connect() throws Exception {
        this.socket = new Socket(host, porta);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }

    // Inicia Thread para envio de mensagens
    public void startSender() {
        sender = new Sender(out, handler);
        new Thread(sender).start();
    }
    
    // Inicia Thread para recebimento de mensagens    
    public void startReceiver () {
        receiver = new Receiver(in,handler);
        new Thread(receiver).start();
    }    

    // Método set mensagem para envio
    public void sendMessage(String mensagem) {
        sender.setMessage(mensagem);
    }
    
    // Método get mensagem recebida
    public String getMessage() {
        return receiver.getMensagem();
    }    

    // Método para disconectar do Servidor
    public void disconnect() throws Exception {
        sender.disconnect();
        receiver.disconnect();
        socket.close();
        if (handler != null) {
            msg = new Message();
            msg.arg1 = ConnectionSocket.DISCONNECTED;
            handler.sendMessage(msg);
        }

    }
}