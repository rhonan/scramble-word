package com.ufc.scramble_word.util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class ServidorAndroid implements Runnable{
	public static final int WAITING = 1;
	public static final int CONECTED = 2;
	public static final int DESCONECTED = 3;	
	private static final int PORTA = 1234;
	private SenderReceiverServer receiverSender;
	private ServerSocket server;
	private Socket socket;
	private Handler handler;
	private Message msg;
	public ServidorAndroid(Handler handler) {
		this.handler = handler;
	}
	@Override
	public void run() {
		try {
			server = new ServerSocket(PORTA);
			socket = server.accept();
			msg.arg1 = CONECTED;
			handler.sendMessage(msg);
			receiverSender = new SenderReceiverServer(socket);
			new Thread(receiverSender).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() throws Exception
	{
		receiverSender.disconnect();
		socket.close();
		server.close();
		
	}

	class SenderReceiverServer implements Runnable {
		private DataInputStream in;
		private DataOutputStream out;
		private boolean running = true;

		public SenderReceiverServer(Socket socket) throws IOException {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		}

		@Override
		public void run() {
			String mensagem;
			try {
				while (running) {
					mensagem = in.readUTF();
					enviarMensagem(mensagem);
				}
				in.close();

			} catch (IOException e) {
				running = false;
			}

		}

		public void enviarMensagem(String sendMessage) throws IOException {
			out.writeUTF(sendMessage);
			out.flush();
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
			out.close();
		}
	}
}
