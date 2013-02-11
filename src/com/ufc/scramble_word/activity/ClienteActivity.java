package com.ufc.scramble_word.activity;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufc.scramble_word.util.ConnectionSocket;
import com.ufc.scramble_word.util.ShakeEventListener;

public class ClienteActivity extends Activity {
	
	private static final String porta = "6000"; 
	private TextView lbStatus;
	private TextView lbPalavra;
	private String palavra;
	private EditText edMensagem;
	private Button btnDesconectar;
	private Button bt_ok;
	
	/* Declarando sensor */
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	
	private ConnectionSocket connection;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			synchronized (msg) {
				switch (msg.arg1) {
				case ConnectionSocket.CONNECTED: {
					lbStatus.setText("Connected");
					break;
				}
				case ConnectionSocket.SENDING_MESSAGE: {
					lbStatus.setText("Message sent");
					edMensagem.setText("");
					break;
				}
				case ConnectionSocket.MESSAGE_RECEIVED: {
					lbStatus.setText("Message received");
					palavra = ConnectionSocket.getCurentConnection()
							.getMessage();
					lbPalavra.setText(palavra);
					break;
				}
				case ConnectionSocket.ERROR: {
					lbStatus.setText("Error->" + msg.obj);
					break;
				}
				case ConnectionSocket.DISCONNECTED: {
					lbStatus.setText("Server has been disconnected.");
					break;
				}

				}
			}
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cliente);

		Button bt_enviar = (Button) findViewById(R.id.bt_enviar);
		
		/* Instanciando sensor */
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorListener = new ShakeEventListener();

		bt_enviar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Emular resposta errada com campo vazio
				try {
					EditText ip = (EditText) findViewById(R.id.et_ip);
					connection = ConnectionSocket.createConnection(ip.getText()
							.toString(), porta, handler);
					connection.connect();
					mensagem();

				} catch (Exception e) {

				}

			}
		});
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
		mSensorManager.registerListener(mSensorListener,
		        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		        SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mSensorListener);
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

	private void mensagem() {
		setContentView(R.layout.activity_mensagem);
		lbStatus = (TextView) findViewById(R.id.tv_status);
		lbPalavra = (TextView) findViewById(R.id.tv_palavra);
		edMensagem = (EditText) findViewById(R.id.et_palavra);
		ConnectionSocket.getCurentConnection().startSender();
		ConnectionSocket.getCurentConnection().startReceiver();
		
		/* Definindo a��o para quando o dispositivo for balan�ado */
	    mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
	      public void onShake() {
	        Toast.makeText(ClienteActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
	      }
	    });
		
		bt_ok = (Button) findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConnectionSocket.getCurentConnection().sendMessage(
						edMensagem.getText().toString());

			}
		});

		btnDesconectar = (Button) findViewById(R.id.bt_desconectar);
		btnDesconectar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					ConnectionSocket.getCurentConnection().disconnect();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cliente, menu);
		return true;
	}

	/* M�todo para embaralhar a palavra */
	public String scramble(String word) {
	    StringBuilder builder = new StringBuilder(word.length());
	    boolean[] used = new boolean[word.length()];
	    
	    for (int i = 0; i < word.length(); i++) {
	        int rndIndex;
	        do {
	            rndIndex = new Random().nextInt(word.length());
	        } while (used[rndIndex]);
	        used[rndIndex] = true;
	        	
	        builder.append(word.charAt(rndIndex));
	    }
	    return builder.toString();
	}
	
}
