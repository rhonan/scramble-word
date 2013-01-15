package com.ufc.scramble_word.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetBroadcastReceiver extends BroadcastReceiver{
	
	public void onReceive(Context context, Intent intent) {
		
		NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
		
		  if(currentNetworkInfo.isConnected()){
              Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
          }else{
              Toast.makeText(context, "Not Connected", Toast.LENGTH_LONG).show();
          }
		}
	}