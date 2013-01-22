package com.ufc.scramble_word.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufc.scramble_word.util.ConnectionSocket;

public class MensagemActivity extends Activity{
	  private TextView lbStatus;
	    private EditText edMensagem;
	    private Button btnDesconectar;
	    private Button btnEnviar;
	    private Handler handler = new Handler() {
	        public void handleMessage(android.os.Message msg) {
	            // Verifica mensagem do Handler e mostra na tela
	            synchronized (msg) {
	                switch (msg.arg1) {
	                case ConnectionSocket.CONNECTED:
	                    lbStatus.setText("Conectado");
	                    break;
	                case ConnectionSocket.SENDING_MESSAGE:
	                    lbStatus.setText("Enviou Mensagem");
	                    edMensagem.setText("");
	                    break;
	                case ConnectionSocket.ERROR:
	                    lbStatus.setText("Ocorreu um erro->" + msg.obj);
	                    break;
	                case ConnectionSocket.DISCONNECTED:
	                    lbStatus.setText("Servidor->Desconectou");
	                    break;

	                }
	            }
	        };

	    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mensagem);
        try {
            // Recupera Conexão atual
            ConnectionSocket.getCurentConnection().startSender(handler);
            Toast
            .makeText(
                    this,
                    "Conectado!",
                    Toast.LENGTH_LONG).show();            
        } catch (Exception e) {

            Toast
                    .makeText(
                            this,
                            "Não foi possível iniciar o Serviço para envio de Mensagens.",
                            Toast.LENGTH_LONG).show();
            finish();
        }		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mensagem, menu);
		return true;
	}



}
