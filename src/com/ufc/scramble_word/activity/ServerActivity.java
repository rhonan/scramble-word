package com.ufc.scramble_word.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.TextView;

import com.ufc.scramble_word.util.ServidorAndroid;
import com.ufc.scramble_word.util.Utils;

@SuppressLint("HandlerLeak")
public class ServerActivity extends Activity {

	private String word;
	private TextView tvWord;
	private TextView tvIp;
	private TextView lbStatus;
	private ServidorAndroid server = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			synchronized (msg) {
				switch (msg.arg1) {
				case ServidorAndroid.CONNECTED:
					lbStatus.setText("Connected.");
					break;
				case ServidorAndroid.DISCONNECTED:
					lbStatus.setText("Disconnected.");
					break;
				case ServidorAndroid.WAITING:
					lbStatus.setText("Waiting....");
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
		tvIp = (TextView) findViewById(R.id.tv_ip);
		tvIp.setText(Utils.getIPAddress(true));
		tvWord = (TextView) findViewById(R.id.tv_word);
		this.server = new ServidorAndroid(handler);
		server.start();

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
