package com.ufc.scramble_word.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ufc.scramble_word.util.ConnectionSocket;

public class ClienteActivity extends Activity {
	
	private TextView lbStatus;
	private TextView lbPalavra;
	private String palavra;
	private EditText edMensagem;
	private Button btnDesconectar;
	private Button bt_ok;
	private ConnectionSocket connection;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			synchronized (msg) {
				switch (msg.arg1) {
				case ConnectionSocket.CONNECTED: {
					lbStatus.setText("Conectado");
					break;
				}
				case ConnectionSocket.SENDING_MESSAGE: {
					lbStatus.setText("Enviou Mensagem");
					edMensagem.setText("");
					break;
				}
				case ConnectionSocket.MESSAGE_RECIVED: {
					lbStatus.setText("recebeu Mensagem");
					palavra = ConnectionSocket.getCurentConnection()
							.getMessage();
					lbPalavra.setText(palavra);
					break;
				}
				case ConnectionSocket.ERROR: {
					lbStatus.setText("Ocorreu um erro->" + msg.obj);
					break;
				}
				case ConnectionSocket.DISCONNECTED: {
					lbStatus.setText("Servidor->Desconectou");
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

		bt_enviar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Emular resposta errada com campo vazio
				try {
					EditText ip = (EditText) findViewById(R.id.et_ip);
					EditText porta = (EditText) findViewById(R.id.et_porta);
					connection = ConnectionSocket.createConnection(ip.getText()
							.toString(), porta.getText().toString(), handler);
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

	private void mensagem() {
		setContentView(R.layout.activity_mensagem);
		lbStatus = (TextView) findViewById(R.id.tv_status);
		lbPalavra = (TextView) findViewById(R.id.tv_palavra);
		edMensagem = (EditText) findViewById(R.id.et_palavra);
		ConnectionSocket.getCurentConnection().startSender();
		ConnectionSocket.getCurentConnection().startReceiver();

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

}
