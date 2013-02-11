package com.ufc.scramble_word.activity;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EasyModeGameActivity extends Activity {

	private TextView texto;
	private TextView scramble_word;
	private Cronometro cronometro;
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	private DatabaseController dbController;
	private Word word;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easy_mode_game);
		setMainLayout();
		texto = (TextView) findViewById(R.id.cronometro);
		
		dbController = new DatabaseController(getApplicationContext());
		word = dbController.selecionar(1);
		
		scramble_word = (TextView) findViewById(R.id.tv_scramble_word);
		scramble_word.setText(scramble(word.getConteudo()));
		cronometro = new Cronometro(texto);
		cronometro.execute();
		
		/* Sensor para quando balançar o dispositivo executar alguma ação */
	    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorListener = new ShakeEventListener();   
	    mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
	      public void onShake() {
	    	scramble_word.setText(scramble(word.getConteudo()));
	        Toast.makeText(EasyModeGameActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
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
				if (unscrambled_word.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Wrong!",
							Toast.LENGTH_SHORT).show();
				} else {
					setCongratulationView(true);
				}

			}
		});

	}

	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		if (keycode == KeyEvent.KEYCODE_MENU) {
			cronometro.pause();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.pause);
			builder.setCancelable(false);
			// Add the buttons
			String[] gameModeOptions = { "Resume", "Audio", "Exit" };
			builder.setItems(gameModeOptions,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (which == 0)
								cronometro.start();
							if (which == 1) {
								AlertDialog.Builder confAudio = new AlertDialog.Builder(
										EasyModeGameActivity.this);
								confAudio.setTitle(R.string.audio);
								confAudio.setCancelable(false);
								confAudio.setPositiveButton(R.string.ok,
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub

											}
										});
								confAudio.setNegativeButton(R.string.cancel,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub

											}
										});
								AlertDialog audioDialog = confAudio.create();
								audioDialog.show();
							}
							if (which == 2)
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
}
