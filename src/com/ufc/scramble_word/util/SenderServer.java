package com.ufc.scramble_word.util;

import java.io.DataOutputStream;
import java.io.IOException;

import android.os.Handler;
import android.os.Message;

public class SenderServer implements Runnable {
	   private DataOutputStream out;
	    private boolean running = true;
	    private String sendMessage;

	    public SenderServer(DataOutputStream out) {
	        this.out = out;
	    }

	    @Override
	    public void run() {
	        while (running) {// Enquanto estiver executando

	            try {
	                if (sendMessage != null) { // Se existir uma mensagem para
	                                            // enviar
	                    out.writeUTF(sendMessage); // Escreve mensagem
	                    out.flush();
	                    sendMessage = null; // Seta nulo na mensagem
	                }

	            } catch (IOException e) {
	                running = false;
	            }

	        }
	        try {
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
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

	    public void setMessage(String message) {
	        this.sendMessage = message;

	    }

	    public void disconnect() throws Exception {
	        running = false;
	        out.close();
	    }
}