package com.ufc.scramble_word.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class ReceiverServer implements Runnable {
	private DataInputStream in;
	private boolean running = true;
	private String message;
	private Handler handler;
	private Message msg;
	private Socket socket;
	private ServerSocket serverSocket;


	public ReceiverServer(Handler handler,ServerSocket serverSocket) {
		this.handler = handler;
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		// Dentro do Loop da Thread


		//out = new DataOutputStream(socket.getOutputStream());		
		try {
			socket = serverSocket.accept();
			in = new DataInputStream(socket.getInputStream());
			msg = new Message();
			msg.arg1 = Server.CONECTED;
			handler.sendMessage(msg);			
			while (running) {// Enquanto estiver executando
				message = in.readUTF();				
			}
			in.close();

		} catch (IOException e) {
            running = false;
			msg = new Message();
			msg.arg1 = Server.DESCONECTED;
			handler.sendMessage(msg);            
		}

	}
	
	public String getMensagem()
	{
		return this.message;
	}
	
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void stop() {
        running = false;
    }

    public void disconnect() throws Exception {
        running = false;
        in.close();
    }	
}