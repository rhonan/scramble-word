package com.ufc.scramble_word.activity;

import java.util.ArrayList;
import com.ufc.scramble_word.broadcastreceiver.InternetBroadcastReceiver;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class InviteActivity extends Activity {
		
	AutoCompleteTextView contato;
	InternetBroadcastReceiver broadcastReceiver;
	IntentFilter intentFilter;
	
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_a_friend);
        getApplicationContext();
        setGameLayout();
        broadcastReceiver = new InternetBroadcastReceiver();
    	intentFilter = new IntentFilter();
    	intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    }
    
    public void onResume(){
    	super.onResume();
    	registerReceiver(broadcastReceiver, intentFilter);
    }
    
    public void onPause(){
    	super.onPause();
    	this.unregisterReceiver(broadcastReceiver);
    	
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
		AlertDialog.Builder builder = new AlertDialog.Builder(InviteActivity.this);
		builder.setTitle(R.string.invite_sent);
		// Add the buttons
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		
		
		AlertDialog dialog = builder.create();
		dialog.show();
    }

    public void setGameLayout(){
		Button bt_level_mode = (Button) findViewById(R.id.bt_back_level_mode);
		bt_level_mode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		contato = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewContato);
		
		ArrayAdapter<String> adpterContatos = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, this.carregaInformacoesDosContatos());
		contato.setAdapter(adpterContatos);
		
		Button bt_invite = (Button) findViewById(R.id.bt_send_invite);
		
		bt_invite.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				int requestCode = 1;
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("plain/text");
				String email = getEmail(contato.getText().toString());
				intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
				intent.putExtra(Intent.EXTRA_SUBJECT, "You have been invited to play Scramble Word!");
				intent.putExtra(Intent.EXTRA_TEXT, "Come play Scramble Word with me! Download at: www.site.com");
				startActivityForResult(intent, requestCode);
				

			}
		});
		
    }
    
    
    
    
	private String[] carregaInformacoesDosContatos(){

		ArrayList<String> contatos = new ArrayList<String>();

		
		
		Cursor informacoesContatos = this.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Email.DATA1);
		
		
		informacoesContatos.moveToFirst();

		while ( !informacoesContatos.isAfterLast()) {
			contatos.add( informacoesContatos.getString(informacoesContatos.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1)) );
			
			
			informacoesContatos.moveToNext();

		}

		return contatos.toArray( new String[contatos.size()] );

	}
	
	private String getEmail(String contato){
		String email = contato;
		
		
		return email;
	}
    
}
