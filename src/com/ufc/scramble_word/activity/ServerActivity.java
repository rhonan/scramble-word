package com.ufc.scramble_word.activity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.TextView;

import com.ufc.scramble_word.util.Server;

public class ServerActivity extends Activity {
    
	TextView lbStatus;
	private static final int PORTA = 1234;
	public static final int WAITING = 1;
	public static final int CONECTED = 2;
	public static final int DESCONECTED = 3;
	private ServerSocket serverSocket;
	private Socket socket=null;
	private String mensagem;
	private Message msg;
	private DataInputStream in;
	private DataOutputStream out;
	private Thread server = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// Verifica mensagem do Handler e mostra na tela
			synchronized (msg) {
				switch (msg.arg1) {
				case CONECTED:
					lbStatus.setText("Conectado");
					break;
				case DESCONECTED:
					lbStatus.setText("Desconectado");
					break;
				case WAITING:
					lbStatus.setText("Aguardando....");
					break;					

				}
			}
		};

	};	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server);
		lbStatus = (TextView) findViewById(R.id.tv_status);
        msg = new Message();
        msg.arg1 = WAITING;
        handler.sendMessage(msg);
        this.server = new Thread(new ServerApp());
        this.server.start();
        
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_server, menu);
		return true;
	}
	
	class ServerApp implements Runnable {
	    public void run() {
	        try {
	        	serverSocket = new ServerSocket(PORTA );
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        while (!Thread.currentThread().isInterrupted()) {
	            try {
	                if (socket == null)
	                    socket = serverSocket.accept();
		            msg = new Message();
		            msg.arg1 = CONECTED;
	                handler.sendMessage(msg);
	        		in = new DataInputStream(socket.getInputStream());
	        		out = new DataOutputStream(socket.getOutputStream());
					mensagem = in.readUTF();
					out.writeUTF(mensagem); // Escreve mensagem
					out.flush();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    }	

}
