package com.ufc.scramble_word.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class ServidorAndroid {
	public static final int WAITING = 1;
	public static final int CONECTED = 2;
	public static final int DESCONECTED = 3;
	private static final int PORTA = 6000;
	private SenderReceiverServer receiverSender;
	private Handler handler;
	private Message msg;

	public ServidorAndroid(Handler handler) {
		this.handler = handler;
		receiverSender = new SenderReceiverServer(handler);
	}

	public void start() {
		new Thread(receiverSender).start();
		msg = new Message();
		msg.arg1 = WAITING;
		handler.sendMessage(msg);
	}

	public void disconnect() throws Exception {
		receiverSender.disconnect();
		msg = new Message();
		msg.arg1 = DESCONECTED;
		handler.sendMessage(msg);

	}

	class SenderReceiverServer implements Runnable {
		private ServerSocket server;
		private Socket socket;
		private Handler handler;
		private Message msg;
		private DataInputStream in;
		private DataOutputStream out;

		public SenderReceiverServer(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void run() {
			String mensagem;

			try {
				server = new ServerSocket(ServidorAndroid.PORTA);
				socket = server.accept();
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				msg = new Message();
				msg.arg1 = ServidorAndroid.CONECTED;
				handler.sendMessage(msg);
				while (!Thread.currentThread().isInterrupted()) {
					mensagem = in.readUTF();
					enviarMensagem(mensagem);
				}
				in.close();

			} catch (IOException e) {
				msg = new Message();
				msg.arg1 = ServidorAndroid.DESCONECTED;
				handler.sendMessage(msg);
			}

		}

		public void enviarMensagem(String sendMessage) throws IOException {
			out.writeUTF(sendMessage);
			out.flush();
		}

		public void disconnect() throws Exception {
			Thread.currentThread().interrupt();
			in.close();
			out.close();
			socket.close();
			server.close();
		}
	}
}
