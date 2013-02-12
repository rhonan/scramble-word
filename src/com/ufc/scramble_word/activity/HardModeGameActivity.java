package com.ufc.scramble_word.activity;

import java.util.ArrayList;
import java.util.Random;

import com.ufc.scramble_word.bean.Word;
import com.ufc.scramble_word.database.DatabaseController;
import com.ufc.scramble_word.util.Cronometro;
import com.ufc.scramble_word.util.ShakeEventListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HardModeGameActivity extends Activity {

	private TextView texto;
	private Cronometro cronometro;
	private TextView scramble_word;
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	private DatabaseController dbController;
	private Word word;
	private ArrayList<Word> lista_word;
	private Random random;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hard_mode_game);
		setMainLayout();
		texto = (TextView) findViewById(R.id.cronometro);
		cronometro = new Cronometro(texto);
		cronometro.execute();
		
		getNovaPalavra();
		mostrarDica();

		/* Sensor para quando balançar o dispositivo executar alguma ação */
	    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorListener = new ShakeEventListener();   
	    mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
	      public void onShake() {
	        Toast.makeText(HardModeGameActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
	      }
	    });
	}

	@Override
	protected void onStart() {
		super.onStart();
		cronometro.start();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		cronometro.start();
		mSensorManager.registerListener(mSensorListener,
		        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		        SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		cronometro.pause();
		mSensorManager.unregisterListener(mSensorListener);
		super.onPause();
	}

	@Override
	protected void onStop() {
		cronometro.pause();
		cancelarDica();
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		cronometro.cancel(true);
		super.onDestroy();
	}

	protected void setMainLayout() {
		Button bt_level_mode = (Button) findViewById(R.id.bt_back_level_mode);
		bt_level_mode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Button bt_unscramble = (Button) findViewById(R.id.bt_unscramble);
		bt_unscramble.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Emular resposta errada com campo vazio
				EditText unscrambled_word = (EditText) findViewById(R.id.et_unscrambled_word);
				if (!unscrambled_word.getText().toString().equals(word.getConteudo())) {
					Toast.makeText(getApplicationContext(), "Wrong!",
							Toast.LENGTH_SHORT).show();
				} else {
					setCongratulationView(true);
					cancelarDica();
				}

			}
		});
	}

	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		if (keycode == KeyEvent.KEYCODE_MENU) {
			cronometro.pause();
			pausarDica();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.pause);
			builder.setCancelable(false);
			// Add the buttons
			String[] gameModeOptions = { "Resume", "Exit" };
			builder.setItems(gameModeOptions,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (which == 0){
								cronometro.start();
								continuarDica();
							}
							if (which == 1)
								finish();
						}

					});

			// Create the AlertDialog
			AlertDialog dialog = builder.create();
			dialog.show();
		}

		return super.onKeyDown(keycode, event);
	}

	protected void setCongratulationView(boolean value) {
		cronometro.pause();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.congratulations);
		// Add the buttons
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.menu,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		builder.setNeutralButton(R.string.facebook_share,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// simulando resume
						cronometro.start();
					}
				});
		builder.setNegativeButton(R.string.next,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						cronometro.reset();
						cronometro.start();
						EditText et = (EditText) findViewById(R.id.et_unscrambled_word);
						et.setText(null);
						// Método para adicionar nova palavra na tela.
						cancelarDica();
						getNovaPalavra();
						mostrarDica();
					}
				});

		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();

	}
	
	/* Método para embaralhar a palavra */
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
	
	/* Selecionando palavra de tamanho maior ou igual que 12 do Banco de Dados */
	public void getNovaPalavra(){
		dbController = new DatabaseController(getApplicationContext());
		lista_word = dbController.selecionarPalavrasDeTamanhoMaiorOuIgualQue(12);
		random = new Random();
		word = lista_word.get(random.nextInt(lista_word.size()));		
		scramble_word = (TextView) findViewById(R.id.tv_scramble_word);
		scramble_word.setText(scramble(word.getConteudo()));
	}
	
	/* Método para mostrar dica após 20 segundos */
	public void mostrarDica(){
		handler = new Handler();
		handler.postDelayed(new Runnable() {
		  @Override
		  public void run() {
			  Toast.makeText(HardModeGameActivity.this, word.getDica(), Toast.LENGTH_SHORT).show();
		  }
		}, 20000);
	}
	
	public void cancelarDica(){
		handler.removeCallbacksAndMessages(null);
	}
	
	public void pausarDica(){
		handler.removeCallbacksAndMessages(null);
	}
	
	public void continuarDica(){
		
		if(cronometro.getTempo() < 20000){
			handler = new Handler();
			handler.postDelayed(new Runnable() {
			  @Override
			  public void run() {
				  Toast.makeText(HardModeGameActivity.this, word.getDica(), Toast.LENGTH_SHORT).show();
			  }
			}, 20000 - cronometro.getTempo());
		}
		else{
			
		}
		
	}

}
