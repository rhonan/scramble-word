package com.ufc.scramble_word.activity;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setMainLayout();
		
	}

	
	public void setMainLayout() {

		Button play = (Button) findViewById(R.id.bt_play);
		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				selectGameModeLayout();

			}
		});

		Button credits = (Button) findViewById(R.id.bt_credits);
		credits.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setCreditsGameLayout();

			}
		});
		
		Button invite = (Button) findViewById(R.id.bt_invite);
		invite.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setInviteActivity();
				
			}
		});

	}

	public void setCreditsGameLayout() {
		setContentView(R.layout.credits_game);
		ImageButton mainMenu = (ImageButton) findViewById(R.id.ib_main_menu);
		mainMenu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_main);
				setMainLayout();

			}
		});

	}

	public void selectGameModeLayout() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.choose_game_mode);
		// Add the buttons
		String[] gameModeOptions = {"Easy", "Normal", "Hard"};
		builder.setItems(gameModeOptions, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which == 0)
					setEasyModeGameActivity();
				if(which == 1)
					setNormalModeGameActivity();
				if(which == 2)
					setHardModeGameActivity();
			}
			
		});


		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();
		
	}

	public void setEasyModeGameActivity() {
		Intent launchGame = new Intent(this, EasyModeGameActivity.class);
		startActivity(launchGame);
	}

	public void setNormalModeGameActivity() {
		Intent launchGame = new Intent(this, NormalModeGameActivity.class);
		startActivity(launchGame);
	}

	public void setHardModeGameActivity() {
		Intent launchGame = new Intent(this, HardModeGameActivity.class);
		startActivity(launchGame);
	}	

	public void setInviteActivity(){
		Intent launchGame = new Intent(this, InviteActivity.class);
		startActivity(launchGame);
	}
	

}

