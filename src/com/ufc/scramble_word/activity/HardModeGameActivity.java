package com.ufc.scramble_word.activity;

import com.ufc.scramble_word.util.Cronometro;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HardModeGameActivity extends Activity {
	Chronometer chronometer;
	Cronometro cro = new Cronometro();
	Processo processo = new Processo(cro);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hard_mode_game);
		Button bt_level_mode = (Button) findViewById(R.id.bt_back_level_mode);
		bt_level_mode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		chronometer.start();
		
	Button bt_unscramble = (Button) findViewById(R.id.bt_unscramble);

	bt_unscramble.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			//Emular resposta errada com campo vazio
			EditText unscrambled_word = (EditText) findViewById(R.id.et_unscrambled_word);
			if(unscrambled_word.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
			}else{
				chronometer.stop();
				setCongratulationView(true);
			}


		}
	});

}
	protected void onResume(){
		super.onResume();
		processo.execute(50);
	}

	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		if(keycode == KeyEvent.KEYCODE_MENU){
		processo.pause();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.pause);
		builder.setCancelable(false);
		// Add the buttons
		String[] gameModeOptions = {"Resume", "Audio", "Exit"};
		builder.setItems(gameModeOptions, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which == 0)
					processo.start(cro);
				if(which == 1){
					AlertDialog.Builder confAudio = new AlertDialog.Builder(HardModeGameActivity.this);
					confAudio.setTitle(R.string.audio);
					confAudio.setCancelable(false);
					confAudio.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					confAudio.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					AlertDialog audioDialog = confAudio.create();
					audioDialog.show();
				}					
				if(which == 2)
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
		processo.pause();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.congratulations);
		// Add the buttons
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.menu, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();			
			}
		});
		builder.setNeutralButton(R.string.facebook_share, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//simulando resume
				processo.start(cro);
			}
		});
		builder.setNegativeButton(R.string.next, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				cro = new Cronometro();
				processo.reset(cro);
				EditText et = (EditText) findViewById(R.id.et_unscrambled_word);
				et.setText(null);
				//Método para adicionar nova palavra na tela.
			}
		});


		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();

	}
	 public class Processo extends AsyncTask<Integer, String, Integer> {
		  
 		 private boolean cronometrando = true;
         private Cronometro cro;
         public Processo(Cronometro cro) {
             this.cro = cro;
         }
         
         public void pause(){

        	 cro.pausar();
         }
         
         public void start(Cronometro cro){

        	 this.cro = cro;
        	 cro.iniciar();
        
         }
  
         @Override
         protected void onPreExecute() {
             //Cria novo um ProgressDialogo e exibe
        	 
     		 cro.start();
         }
         
  
         @Override
         protected Integer doInBackground(Integer... paramss) {
             Integer tempo = paramss[0]; 
             while(cronometrando){
        	 try {
                     //Simula processo...
                     Thread.sleep(tempo);
                     //Atualiza a interface através do onProgressUpdate
                     publishProgress(cro.show());
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
			return 1;
         }
  
         @Override
         protected void onPostExecute(Integer result) {
         }

         @Override
         protected void onProgressUpdate(String... values) {
             //Atualiza mensagem
            TextView text = (TextView)findViewById(R.id.cronometro);
            text.setText(values[0]);
         }
         
         protected void reset(Cronometro cro){
        	 this.cro = cro;
        	 cro.start();
         }
     }
}
