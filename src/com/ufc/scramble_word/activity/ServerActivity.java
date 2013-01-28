package com.ufc.scramble_word.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.TextView;

import com.ufc.scramble_word.util.ServidorAndroid;

@SuppressLint("HandlerLeak")
public class ServerActivity extends Activity {

	TextView lbStatus;
	private Thread server = null;
	private Message msg;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			synchronized (msg) {
				switch (msg.arg1) {
				case ServidorAndroid.CONECTED:
					lbStatus.setText("Conectado");
					break;
				case ServidorAndroid.DESCONECTED:
					lbStatus.setText("Desconectado");
					break;
				case ServidorAndroid.WAITING:
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
		msg.arg1 = ServidorAndroid.WAITING;
		handler.sendMessage(msg);
		this.server = new Thread(new ServidorAndroid(handler));
		this.server.start();

	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_server, menu);
		return true;
	}


}
