package com.ufc.scramble_word.util;

import java.io.DataOutputStream;
import java.io.IOException;

import android.os.Handler;
import android.os.Message;

class Sender implements Runnable {

	private DataOutputStream out;
	private Handler handler;
	private Message msg;
	private String sendMessage;

	public Sender(DataOutputStream out, Handler handler) {
		this.out = out;
		this.handler = handler;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {

			try {
				if (sendMessage != null) {
					msg = new Message();
					msg.arg1 = ConnectionSocket.SENDING_MESSAGE;
					handler.sendMessage(msg);
					out.writeUTF(sendMessage);
					out.flush();
					sendMessage = null;
				}

			} catch (IOException e) {
				msg = new Message();
				msg.arg1 = ConnectionSocket.ERROR;
				msg.obj = e.getMessage();
				handler.sendMessage(msg);
			}

		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMessage(String message) {
		this.sendMessage = message;

	}

	public void disconnect() throws Exception {
		Thread.currentThread().interrupt();
		out.close();
	}

}
