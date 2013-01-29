package com.ufc.scramble_word.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class InviteActivity extends Activity {
	private Button bt_invite;
	private Button bt_try;
	private IntentFilter intentFilter;
	private AutoCompleteTextView contato;
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			NetworkInfo currentNetworkInfo = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

			if (currentNetworkInfo.isConnected()) {
				Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
				bt_invite.setVisibility(Button.VISIBLE);
				bt_try.setVisibility(Button.INVISIBLE);
			} else {
				Toast.makeText(context, "Not Connected", Toast.LENGTH_LONG)
						.show();
				bt_invite.setVisibility(Button.INVISIBLE);
				bt_try.setVisibility(Button.VISIBLE);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_a_friend);
		getApplicationContext();
		intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		setGameLayout();

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, intentFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.unregisterReceiver(receiver);
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		AlertDialog.Builder builder = new AlertDialog.Builder(
				InviteActivity.this);
		builder.setTitle(R.string.invite_sent);
		// Add the buttons
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void setGameLayout() {
		bt_try = (Button) findViewById(R.id.bt_try);
		bt_invite = (Button) findViewById(R.id.bt_send_invite);
		Button bt_level_mode = (Button) findViewById(R.id.bt_back_level_mode);
		bt_level_mode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		contato = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewContato);

		ArrayAdapter<String> adpterContatos = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,
				this.carregaInformacoesDosContatos());
		contato.setAdapter(adpterContatos);

		bt_invite.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int requestCode = 1;
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("plain/text");
				String email = getEmail(contato.getText().toString());
				intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
				intent.putExtra(Intent.EXTRA_SUBJECT,
						"You have been invited to play Scramble Word!");
				intent.putExtra(Intent.EXTRA_TEXT,
						"Come play Scramble Word with me! Download at: www.site.com");
				startActivityForResult(intent, requestCode);

			}
		});

	}

	private String[] carregaInformacoesDosContatos() {

		ArrayList<String> contatos = new ArrayList<String>();

		Cursor informacoesContatos = this.getContentResolver().query(
				ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null,
				null, ContactsContract.CommonDataKinds.Email.DATA1);

		informacoesContatos.moveToFirst();

		while (!informacoesContatos.isAfterLast()) {
			contatos.add(informacoesContatos.getString(informacoesContatos
					.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1)));

			informacoesContatos.moveToNext();

		}

		return contatos.toArray(new String[contatos.size()]);

	}

	private String getEmail(String contato) {
		String email = contato;
		return email;
	}

}
