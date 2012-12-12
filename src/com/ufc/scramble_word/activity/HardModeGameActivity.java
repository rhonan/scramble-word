package com.ufc.scramble_word.activity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

public class HardModeGameActivity extends Activity {
	Chronometer chronometer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hard_mode_game);
		Button bt_level_mode = (Button) findViewById(R.id.bt_back_level_mode);
		bt_level_mode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent (HardModeGameActivity.this, MainActivity.class);
				startActivity(intent);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_hard_mode_game, menu);
	return true;
	}

	protected void setCongratulationView(boolean value) {
	/*RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl_congratulations);
	if (value)
		rl.setVisibility(View.VISIBLE);
	else
		rl.setVisibility(View.INVISIBLE);
		*/
	
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
			
		}
	});
	builder.setNegativeButton(R.string.next, new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}
	});


	// Create the AlertDialog
	AlertDialog dialog = builder.create();
	dialog.show();

}
}
