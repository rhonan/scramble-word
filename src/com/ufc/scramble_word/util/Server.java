package com.ufc.scramble_word.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class Server {
	private static final int PORTA = 1234;
	public static final int WAITING = 1;
	public static final int CONECTED = 2;
	public static final int DESCONECTED = 3;
	private boolean executando = true;
	private ServerSocket serverSocket;
	private Socket socket;
	private String mensagem;
	private Message msg;
	private Handler handler;
	private DataInputStream in;
	private DataOutputStream out;

	public Server(Handler handler) {
		this.handler = handler;
	}

	public void startServer() throws IOException {
		serverSocket = new ServerSocket(PORTA);
		msg = new Message();
		msg.arg1 = Server.WAITING;
		handler.sendMessage(msg);
		socket = serverSocket.accept();
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());

		try {
			while (executando) {
				mensagem = in.readUTF();
				out.writeUTF(mensagem); // Escreve mensagem
				out.flush();
			}

			in.close();
			out.close();
			socket.close();
			serverSocket.close();
		} catch (Exception e) {
			executando = false;
		}

	}

	public void disconnect() throws Exception {
		executando = false;
		if (handler != null) {
			msg = new Message();
			msg.arg1 = Server.DESCONECTED;
			handler.sendMessage(msg);
		}

	}
}
