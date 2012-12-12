package com.ufc.scramble_word.activity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InviteActivity extends Activity {
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_a_friend);
        getApplicationContext();
        setGameLayout();
		
    }

    public void setGameLayout(){
		Button bt_level_mode = (Button) findViewById(R.id.bt_back_level_mode);
		bt_level_mode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		
		Button bt_invite = (Button) findViewById(R.id.bt_send_invite);
		
		bt_invite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(InviteActivity.this);
				builder.setTitle(R.string.invite_sent);
				// Add the buttons
				builder.setCancelable(false);
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		
    }
    
}
