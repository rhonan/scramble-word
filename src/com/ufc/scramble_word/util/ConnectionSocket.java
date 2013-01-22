package com.ufc.scramble_word.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class ConnectionSocket {

    private static ConnectionSocket connection;
    private int porta;
    private String host;
    private Socket socket;
    private Sender sender;
    private Receiver receiver;
    public static final int CONNECTED = 1;
    public static final int ERROR = 2;
    public static final int SENDING_MESSAGE = 3;
    public static final int DISCONNECTED = 4;
    private Message msg;
    private DataOutputStream out;
    private DataInputStream in;    
    private Handler handlerSender; // Handle para notificações na tela

    
    private ConnectionSocket(String host, String porta) {
        this.host = host;
        this.porta = Integer.parseInt(porta);
    }

    // Método que cria Objecto ConnectionSocket
    public static ConnectionSocket createConnection(String host, String porta) {
        connection = new ConnectionSocket(host, porta);
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
    public void startSender(Handler handler) {
        sender = new Sender(out, handler);
        new Thread(sender).start();
        this.handlerSender = handler;
    }
    
    // Inicia Thread para recebimento de mensagens    
    public void startReceiver() {
        receiver = new Receiver(in);
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
        if (handlerSender != null) {
            msg = new Message();
            msg.arg1 = ConnectionSocket.DISCONNECTED;
            handlerSender.sendMessage(msg);
        }

    }
}