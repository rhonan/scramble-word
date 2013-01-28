package com.ufc.scramble_word.util;

import java.io.DataInputStream;
import java.io.IOException;

import android.os.Handler;
import android.os.Message;

class Receiver implements Runnable {

	private DataInputStream in;
	private boolean running = true;
	private String message;
    private Handler handler;
    private Message msg;	


	public Receiver(DataInputStream in, Handler handler) {
		this.in = in;
		this.handler = handler;
	}

	@Override
	public void run() {
		// Dentro do Loop da Thread

		try {
			while (running) {// Enquanto estiver executando
				message = in.readUTF();
				msg = new Message();
				msg.arg1 = ConnectionSocket.MESSAGE_RECIVED;
				handler.sendMessage(msg);
			}
			in.close();

		} catch (IOException e) {
            msg = new Message();
            msg.arg1 = ConnectionSocket.ERROR;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);			
            running = false;
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