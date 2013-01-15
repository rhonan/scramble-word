package com.ufc.scramble_word.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class InternetBroadcastReceiver extends BroadcastReceiver{
	
	public void onReceive(Context context, Intent intent) {
		CharSequence text = "Hello toast!";
		int duration = Toast.LENGTH_SHORT;
		
		
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		}
	}