package com.ufc.scramble_word.util;

import java.io.DataInputStream;
import java.io.IOException;

import android.os.Handler;
import android.os.Message;

class Receiver implements Runnable {

	private DataInputStream in;
	private boolean running = true;
	private String message;


	public Receiver(DataInputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		// Dentro do Loop da Thread

		try {
			while (running) {// Enquanto estiver executando
				message = in.readUTF();
			}
			in.close();

		} catch (IOException e) {
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