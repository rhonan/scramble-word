package com.ufc.scramble_word.util;

import java.io.DataInputStream;
import java.io.IOException;

import android.os.Handler;
import android.os.Message;

class Receiver implements Runnable {
	private DataInputStream in;
	private String message;
	private Handler handler;
	private Message msg;

	public Receiver(DataInputStream in, Handler handler) {
		this.in = in;
		this.handler = handler;
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				message = in.readUTF();
				msg = new Message();
				msg.arg1 = ConnectionSocket.MESSAGE_RECEIVED;
				handler.sendMessage(msg);
			}
			in.close();

		} catch (IOException e) {
			msg = new Message();
			msg.arg1 = ConnectionSocket.ERROR;
			msg.obj = e.getMessage();
			handler.sendMessage(msg);
		}

	}

	public String getMensagem() {
		return this.message;
	}

	public void disconnect() throws Exception {
		Thread.currentThread().interrupt();
		in.close();
	}
}