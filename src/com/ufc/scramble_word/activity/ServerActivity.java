package com.ufc.scramble_word.activity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class ServerActivity extends Activity {

	TextView lbStatus;
	private Thread server = null;
	private Message msg;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			synchronized (msg) {
				switch (msg.arg1) {
				case ServerApp.CONECTED:
					lbStatus.setText("Conectado");
					break;
				case ServerApp.DESCONECTED:
					lbStatus.setText("Desconectado");
					break;
				case ServerApp.WAITING:
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
		msg.arg1 = ServerApp.WAITING;
		handler.sendMessage(msg);
		try {
			this.server = new Thread(new ServerApp(handler));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.server.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_server, menu);
		return true;
	}

	class ServerApp implements Runnable {
		private static final int PORTA = 12345;
		public static final int WAITING = 1;
		public static final int CONECTED = 2;
		public static final int DESCONECTED = 3;
		private ServerSocket serverSocket;
		private Socket socket = null;
		private String mensagem;
		private Message msg;
		private DataInputStream in;
		private DataOutputStream out;
		private Handler handler;

		public ServerApp(Handler handler) throws IOException {
			this.handler = handler;
			try {
				serverSocket = new ServerSocket(PORTA);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void run() {
			if (socket == null)
				try {
					socket = serverSocket.accept();
					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			while (!Thread.currentThread().isInterrupted()) {
				try {
					msg = new Message();
					msg.arg1 = CONECTED;
					handler.sendMessage(msg);
					mensagem = in.readUTF();
					out.writeUTF(mensagem);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
